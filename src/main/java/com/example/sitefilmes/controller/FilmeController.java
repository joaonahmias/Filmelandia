package com.example.sitefilmes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.sitefilmes.model.Filme;
import com.example.sitefilmes.service.FilmeService;
import com.example.sitefilmes.util.UploadUtil;

import jakarta.validation.Valid;

@Controller
public class FilmeController {

    FilmeService service;

    public FilmeController(FilmeService service){
        this.service=service;
    }
    
    @GetMapping(value = {"/", "/index", "/index.html"})
    public String getIndex(Model model){
        List<Filme> filmes = service.findAll();
        for (Filme filme : filmes) {
            String caminhoImagem = "images/img-uploads/" + filme.getImgUri();
            filme.setImgUri(caminhoImagem);
        }
        model.addAttribute("filmes", filmes);
        return "index";
    }

    @GetMapping(value = "/cadastrarPage")
    public String cadastrarPage(Model model){
        Filme filme = new Filme();
        model.addAttribute("filme", filme);
        return "cadastrarFilme";
    }

    @PostMapping(value = "/doSalvar")
    public String cadastrar(@ModelAttribute @Valid Filme filme, @RequestParam("file") MultipartFile imagem, Errors errors){
        if (errors.hasErrors()){
            System.out.println("erro: "+errors.getAllErrors());
            return "cadastrarPage";
        }else{
            try{
                if(UploadUtil.fazerUploadImagem(imagem)){
                    filme.setImgUri(imagem.getOriginalFilename());
                    service.save(filme);
                    return "redirect:/index";
                }
            }catch(Exception e){
                return "redirect:/index";
            }
            return "redirect:/index";
        }
    }

    @PostMapping(value = "/doEditar")
    public String editar(@ModelAttribute @Valid Filme filme, @RequestParam("file") MultipartFile imagem, Errors errors){
        if (errors.hasErrors()){
            System.out.println("erro: "+errors.getAllErrors());
            return "editarPage";
        }else{
            try{
                if(UploadUtil.fazerUploadImagem(imagem)){
                    filme.setImgUri(imagem.getOriginalFilename());
                    service.save(filme);
                    return "redirect:/index";
                }
            }catch(Exception e){
                return "redirect:/index";
            }
            return "redirect:/index";
        }
    }


    @GetMapping("/editarPage/{id}")
    public String getEditarPage(@PathVariable(name = "id") long id, Model model){

        Optional<Filme> filme = service.findById(id);
        if (filme.isPresent()){
            model.addAttribute("filme", filme.get());
        }else{
            return "redirect:/index";
        }

        return "editarFilme";
    }

    @GetMapping("/doDeletar/{id}")
    public String doDeletar(@PathVariable(name = "id") Integer id){
        Optional<Filme> filme;
        filme = service.findById(id);
        if(filme.isPresent()){
            service.delete(id);
        }
        
        return "redirect:/index";
        

    }

    


    

    




}

