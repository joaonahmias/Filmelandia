package com.example.sitefilmes.controller;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.sitefilmes.model.Usuario;
import com.example.sitefilmes.service.UsuarioService;

@Controller
public class LoginController {

    UsuarioService service;

    public LoginController(UsuarioService service){
        this.service = service;
    }


    @GetMapping("/login")
    public String getLoginPage(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "login";
    }


    @PostMapping("/validar")
    public String validarLogin(@ModelAttribute Usuario usuario,  BCryptPasswordEncoder encoder) {
        var userName = usuario.getUsername();
        var password = usuario.getSenha();

       Usuario usuarioCadastrado = (Usuario) service.loadUserByUsername(usuario.getUsername());
        System.out.println("USER RECEBIDO COMO PARAMETRO PRA TESTE "+userName);
        System.out.println("senha RECEBIDO COMO PARAMETRO PRA TESTE "+password);

        // Verificar se o usuário está cadastrado e a senha está correta
        if(usuarioCadastrado!=null){
         if (usuarioCadastrado.getUsername().equals(userName)) {

             if (usuarioCadastrado.getPassword().equals(password)) {
                if (usuarioCadastrado.getIsAdmin()) {
                    return "redirect:/index";

                }
             }

         }
        }
        return "redirect:/login";
    }


}
