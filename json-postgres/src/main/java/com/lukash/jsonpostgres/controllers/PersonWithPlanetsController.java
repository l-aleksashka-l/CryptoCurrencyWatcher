package com.lukash.jsonpostgres.controllers;


import com.lukash.jsonpostgres.entities.Lord;
import com.lukash.jsonpostgres.entities.PersonWithPlanets;
import com.lukash.jsonpostgres.entities.Planet;
import com.lukash.jsonpostgres.form.LordForm;
import com.lukash.jsonpostgres.form.PersonWithPlanetForm;
import com.lukash.jsonpostgres.form.PlanetForm;
import com.lukash.jsonpostgres.repositories.LordRepository;
import com.lukash.jsonpostgres.repositories.PersonWithPlanetsRepository;
import com.lukash.jsonpostgres.repositories.PlanetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonWithPlanetsController {

    private final static Logger logger = LoggerFactory.getLogger(PersonWithPlanetsController.class);

    private final PersonWithPlanetsRepository personWithPlanetsRepository;
    private final PlanetRepository planetRepository;
    private final LordRepository lordRepository;

    @Autowired
    public PersonWithPlanetsController(PersonWithPlanetsRepository personWithPlanetsRepository, PlanetRepository planetRepository, LordRepository lordRepository) {
        this.personWithPlanetsRepository = personWithPlanetsRepository;
        this.planetRepository = planetRepository;
        this.lordRepository = lordRepository;
    }

    private static List<PersonWithPlanets> persons = new ArrayList<PersonWithPlanets>();
    private static List<Planet> planets = new ArrayList<Planet>();
    private static List<Lord> lords = new ArrayList<Lord>();
    private static List<PersonWithPlanets> tempPersons = new ArrayList<>();


    @Value("${error.message}")
    private String errorMessage;

    @Value("${error.nothingPlanetMessage}")
    private String errorNothingPlanetMessage;

    @Value("${error.nothingLordMessage}")
    private String errorNothingLordMessage;

    @Value("${error.badMessage}")
    private String errorBadMessage;

    @RequestMapping(value = { "/deletePlanet" }, method = RequestMethod.POST)
    public String deletePlanet(Model model, //
                               @ModelAttribute("planetForm") PlanetForm planetForm) {

        String name = planetForm.getName();

        if (name != null && name.length() > 0 //
        ) {
            planets = (List<Planet>) planetRepository.findAll();
            persons = (List<PersonWithPlanets>) personWithPlanetsRepository.findAll();
            for(PersonWithPlanets person: persons){
                if(person.getPlanetName().equals(name)) {
                    persons.remove(person);
                    personWithPlanetsRepository.deleteAll();
                    personWithPlanetsRepository.saveAll(persons);
                    break;
                }
            }
            for(Planet planet: planets){
                if(planet.getName().equals(name)) {
                    planets.remove(planet);
                    planetRepository.deleteAll();
                    planetRepository.saveAll(planets);
                    return "redirect:/planetList";
                }
            }

            model.addAttribute("errorNothingPlanetMessage", errorNothingPlanetMessage);
            return "deletePlanet";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "deletePlanet";
    }

    @RequestMapping(value = { "/addPlanet" }, method = RequestMethod.POST)
    public String savePlanet(Model model, //
                             @ModelAttribute("planetForm") PlanetForm planetForm) {

        String name = planetForm.getName();

        if (name != null && name.length() > 0 //
        ) {
            Planet newPlanet = new Planet(name);
            planetRepository.save(newPlanet);
            PersonWithPlanets newPerson = new PersonWithPlanets(null, name);
            personWithPlanetsRepository.save(newPerson);
            return "redirect:/planetList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPlanet";
    }

    @RequestMapping(value = { "/transList" }, method = RequestMethod.GET)
    public String transList(Model model) {

        persons = null;
        persons = (List<PersonWithPlanets>) personWithPlanetsRepository.findAll();

        model.addAttribute("persons", persons);

        return "transList";
    }

    @RequestMapping(value = { "/givePlanet" }, method = RequestMethod.GET)
    public String showGivePlanetPage(Model model) {

        PersonWithPlanetForm personWithPlanetForm = new PersonWithPlanetForm();
        model.addAttribute("personWithPlanetForm", personWithPlanetForm);


        persons = null;
        persons = (List<PersonWithPlanets>) personWithPlanetsRepository.findAll();
        for(PersonWithPlanets person: persons){
            if(person.getLordName() == null)
                tempPersons.add(person);
        }
        model.addAttribute("tempPerson", tempPersons);
        return "givePlanet";
    }

    @RequestMapping(value = { "/givePlanet" }, method = RequestMethod.POST)
    public String givePlanet(Model model, //
                               @ModelAttribute("personWithPlanetForm") PersonWithPlanetForm personWithPlanetForm) {

        String planetName = personWithPlanetForm.getPlanetName();
        String lordName = personWithPlanetForm.getLordName();

        lords = (List<Lord>) lordRepository.findAll();
        if (planetName != null && planetName.length() > 0 && lordName != null && lordName.length() > 0 ) {
            boolean a = true;

            for(PersonWithPlanets person: tempPersons){
                if(person.getPlanetName().equals(planetName)) {
                    a = false;
                    break;
                }
            }
            if(a){
                model.addAttribute("errorBadMessage", errorBadMessage);
                return "givePlanet";
            }
            for(Lord lord: lords){
                if(lord.getName().equals(lordName)) {
                    System.out.println(lord.getName());
                    personWithPlanetsRepository.save(new PersonWithPlanets(lordName,planetName));
                    tempPersons = null;
                    return "redirect:/index";
                }
            }


            model.addAttribute("errorNothingLordMessage", errorNothingLordMessage);
            return "givePlanet";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "givePlanet";
    }
    /*@RequestMapping(value = { "/write" }, method = RequestMethod.GET)
    public String planetList(Model model) {
        lords = (List<Lord>) lordRepository.findAll();
        planets = (List<Planet>) planetRepository.findAll();
        int[] array = new int[planets.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) Math.round((Math.random() * 600));
        }
        int i = 0;
        for(Planet planet: planets){
            persons.add(new PersonWithPlanets(lords.get(array[i]).getName(),planet.getName()));
            i++;
        }
        personWithPlanetsRepository.saveAll(persons);

        return "planetList";
    }*/
}
