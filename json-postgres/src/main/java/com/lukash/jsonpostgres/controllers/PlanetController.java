package com.lukash.jsonpostgres.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukash.jsonpostgres.entities.Planet;
import com.lukash.jsonpostgres.form.PlanetForm;
import com.lukash.jsonpostgres.repositories.PlanetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PlanetController {

    private final static Logger logger = LoggerFactory.getLogger(PlanetController.class);

    private final PlanetRepository planetRepository;

    private static List<Planet> planets = new ArrayList<Planet>();

    @Autowired
    public PlanetController(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = { "/planetList" }, method = RequestMethod.GET)
    public String planetList(Model model) {

        planets = (List<Planet>) planetRepository.findAll();
        model.addAttribute("planets", planets);

        return "planetList";
    }

    @RequestMapping(value = { "/addPlanet" }, method = RequestMethod.GET)
    public String showAddPlanetPage(Model model) {
        PlanetForm planetForm = new PlanetForm();
        model.addAttribute("planetForm", planetForm);

        return "addPlanet";
    }

    @RequestMapping(value = { "/addPlanet" }, method = RequestMethod.POST)
    public String savePlanet(Model model, //
                           @ModelAttribute("planetForm") PlanetForm planetForm) {

        String name = planetForm.getName();

        if (name != null && name.length() > 0 //
                ) {
            Planet newPlanet = new Planet(name);
            planetRepository.save(newPlanet);
            return "redirect:/planetList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPlanet";
    }

    @RequestMapping(value ={"/deletePlanet"}, method = RequestMethod.GET)
    public String showDeletePlanetPage(Model model){
        PlanetForm planetForm = new PlanetForm();
        model.addAttribute("planetForm", planetForm);

        return "deletePlanet";
    }
    @RequestMapping(value ={"/deletePlanet"}, method = RequestMethod.POST)
    public String deletePlanet(Model model, @ModelAttribute("planet") PlanetForm planetForm){
        String name = planetForm.getName();

        if(name!=null && name.length()>0){
            planetRepository.deleteById(Long.parseLong(name));
            logger.info("Delete " + name);
            return "redirect:/planetList";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "deletePlanet";
    }


    /*@RequestMapping("planet")
    public void planet() {

        File jsonFile = null;
        try {
            jsonFile = ResourceUtils.getFile("classpath:planets.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Planet> planets = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            planetRepository.saveAll(planets);
            //logger.info("All planets saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
