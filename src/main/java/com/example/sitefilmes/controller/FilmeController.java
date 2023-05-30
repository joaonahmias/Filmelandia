package com.example.sitefilmes.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sitefilmes.model.Filme;
import com.example.sitefilmes.service.FilmeService;
import com.example.sitefilmes.util.UploadUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class FilmeController {

    FilmeService service;

    public FilmeController(FilmeService service){
        this.service=service;
    }
    
    @GetMapping(value = {"/admin", "/admin.html"})
    public String getAdmin(Model model){
        List<Filme> filmes = service.findAll();
        for (Filme filme : filmes) {
            String caminhoImagem = "images/img-uploads/" + filme.getImgUri();
            filme.setImgUri(caminhoImagem);
        }
        model.addAttribute("filmes", filmes);
        return "admin";
    }

    @GetMapping(value = {"/","/index", "/index.html"})
    public String getIndex(Model model){
        List<Filme> filmes = service.findAll();
        for (Filme filme : filmes) {
            String caminhoImagem = "images/img-uploads/" + filme.getImgUri();
            filme.setImgUri(caminhoImagem);
        }
        model.addAttribute("filmes", filmes);
        return "index";
    }

    @GetMapping(value = "/cadastrarPagina")
    public String cadastrarPage(Model model){
        Filme filme = new Filme();
        model.addAttribute("filme", filme);
        model.addAttribute("acao", "cadastrar");
        return "cadastrarFilme";
    }

    @PostMapping(value = "/doSalvar")
    public String cadastrar(@ModelAttribute @Valid Filme filme, Errors errors, @RequestParam("file") MultipartFile imagem, @RequestParam(value = "acao", required = false) String acao,RedirectAttributes x){
        if (errors.hasErrors()){
            if ("editar".equals(acao)) {
                return "editarFilme";
            } else {
                return "cadastrarFilme";
            }
        }else{
            try{
                String nomeArquivo = filme.getTitulo()+ "-"+ filme.getId()+ "-"+imagem.getOriginalFilename();
                if(UploadUtil.fazerUploadImagem(imagem,nomeArquivo)){
                    filme.setImgUri(nomeArquivo);
                    service.save(filme);
                    x.addFlashAttribute("mensagem", "Operação Realizada com Sucesso");
                    return "redirect:/admin";
                }
                else{
                    if ("editar".equals(acao)) {
                        if(imagem.isEmpty()){
                            service.save(filme);
                            x.addFlashAttribute("mensagem", "Operação Realizada com Sucesso");
                            return "redirect:/admin";
                        }
                        else{
                            return "editarFilme";
                        }
                    } else {
                        errors.rejectValue("imgUri", "file.empty", "Por favor, selecione um arquivo do tipo png.");
                        return "cadastrarFilme";
                    }
                }
            }catch(Exception e){
                return "redirect:/admin";
            }
        }
    }


    @GetMapping("/editarPage/{id}")
    public String getEditarPage(@PathVariable(name = "id") long id, Model model){

        Optional<Filme> filme = service.findById(id);
        if (filme.isPresent()){
            model.addAttribute("filme", filme.get());
            model.addAttribute("acao", "editar");
        }else{
            return "redirect:/admin";
        }

        return "editarFilme";
    }

    @GetMapping("/doDeletar/{id}")
    public String doDeletar(@PathVariable(name = "id") Integer id,RedirectAttributes x){
        Optional<Filme> filme;
        filme = service.findById(id);
        if(filme.isPresent()){
            service.delete(id);
            x.addFlashAttribute("mensagem", "Operação Realizada com Sucesso");
        }
        
        return "redirect:/admin";
        

    }


    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarCarrinho(@PathVariable(name = "id") Integer id, HttpServletRequest request){
        //procurando a sessao
        HttpSession sessao = request.getSession(true);
        ArrayList<Filme> carrinho = (ArrayList<Filme>) sessao.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<Filme>();
            sessao.setAttribute("carrinho", carrinho);
        }
        //adiconando o filme desejado a sessao
        Optional<Filme> filme;
        filme = service.findById(id);
        if(filme.isPresent()){
            Filme filmeEncontrado = filme.get();
            carrinho.add(filmeEncontrado);
        }

        return "redirect:/index";

    }
    

    @GetMapping("/verCarrinho")
    public String verCarrinho(Model model,HttpServletRequest request,RedirectAttributes x){
        HttpSession sessao = request.getSession(true);
        ArrayList<Filme> carrinho = (ArrayList<Filme>) sessao.getAttribute("carrinho");
        model.addAttribute("carrinho", carrinho);
        if(carrinho==null || carrinho.isEmpty()){
            x.addFlashAttribute("mensagem", "O carrinho está vazio");
            return "redirect:/index";
        }
        else{
            for (Filme filme : carrinho) {
                String caminhoImagem = "images/img-uploads/" + filme.getImgUri();
                filme.setImgUri(caminhoImagem);
            }
            return "verCarrinhoPage";
        }

    }
    

    @GetMapping("/finalizarCompras")
    public String finalizarCarrinho(Model model,HttpServletRequest request,RedirectAttributes x){
        HttpSession sessao = request.getSession(true);
        if(sessao.isNew()){
            x.addFlashAttribute("mensagem", "O carrinho está vazio");
        }
        else{
            x.addFlashAttribute("mensagem", "Compras finalizadas.");
        }
        sessao.invalidate();
        return "redirect:/index";
        

    }
    
    

    




}

