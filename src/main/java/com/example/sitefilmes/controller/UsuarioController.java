package com.example.sitefilmes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.sitefilmes.model.Usuario;
import com.example.sitefilmes.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
public class UsuarioController {
    
    UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/cadastrarUsuarioPage")
    public String doCadastrarUsuario(Model model ){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "cadastrarUsuario";
    }

    @PostMapping("/doSalvarUsuario")
    public String doSalvarUsuario(@ModelAttribute @Valid Usuario usuario){
        service.create(usuario);
        return "redirect:/index";
    }

}
