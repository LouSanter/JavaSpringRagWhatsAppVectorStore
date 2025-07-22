package com.lousanter.rag.AI.controller;

import com.lousanter.rag.AI.service.IAService;
import com.lousanter.rag.Model.Curso;
import com.lousanter.rag.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IAController {


    @Autowired
    IAService service;
    @Autowired
    ProfesorRepo profesorRepo;
    @Autowired
    CarreraRepo carreraRepo;
    @Autowired
    CursoRepo cursoRepo;
    @Autowired
    HistorialAcademicoRepo historialAcademicoRepo;


    @GetMapping({"/", "/home"})
    public String index(Model model) {
        model.addAttribute("profesores", profesorRepo.findAll());
        model.addAttribute("cursos", cursoRepo.findAll());
        model.addAttribute("carreras", carreraRepo.findAll());
        model.addAttribute("historiales", historialAcademicoRepo.findAll());
        return "home";
    }


    @GetMapping("/conversar")
    @ResponseBody
    public String conversar(@RequestParam String sms){
        String response = service.procesarConVectorStore(sms);
        System.out.println(response);
        return response;
    }

}
