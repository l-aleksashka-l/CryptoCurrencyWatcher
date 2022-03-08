package com.lukash.jsonpostgres.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukash.jsonpostgres.entities.Lord;
import com.lukash.jsonpostgres.form.LordForm;
import com.lukash.jsonpostgres.repositories.LordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LordController {

    private final static Logger logger = LoggerFactory.getLogger(LordController.class);

    private final LordRepository lordRepository;

    private static List<Lord> lords = new ArrayList<Lord>();

    static {
        lords.add(new Lord(12,"Bill"));
        lords.add(new Lord(23, "Steve"));
    }


    @Autowired
    public LordController(LordRepository lordRepository) {
        this.lordRepository = lordRepository;
    }



    @Value("${error.message}")
    private String errorMessage;


    @Value("${error.numberMessage}")
    private String errorNumberMessage;

    /*@RequestMapping("lord")
    public void lord() {

        File jsonFile = null;
        try {
            jsonFile = ResourceUtils.getFile("classpath:lords.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Lord> lords = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            lordRepository.saveAll(lords);
            //logger.info("All lords saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    @RequestMapping(value = { "/lordList" }, method = RequestMethod.GET)
    public String LordList(Model model) {

        lords = null;
        lords = (List<Lord>) lordRepository.findAll();

        model.addAttribute("lords", lords);

        return "lordList";
    }

    @RequestMapping(value = { "/addLord" }, method = RequestMethod.GET)
    public String showAddLordPage(Model model) {

        LordForm lordForm = new LordForm();
        model.addAttribute("lordForm", lordForm);

        return "addLord";
    }

    @RequestMapping(value = { "/addLord" }, method = RequestMethod.POST)
    public String saveLord(Model model, //
                             @ModelAttribute("lordForm") LordForm lordForm) {

        String name = lordForm.getName();
        String age = lordForm.getAge();


        if (name != null && name.length() > 0 //
                && age != null && age.length() > 0) {
            try{
            Lord newLord = new Lord(Integer.parseInt(age), name);
            lordRepository.save(newLord);
            lords.add(newLord);
            }
            catch(NumberFormatException ex){
                model.addAttribute("errorNumberMessage", errorNumberMessage);
                return "addLord";
            }

            return "redirect:/lordList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addLord";
    }

}




