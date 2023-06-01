package com.example.sitefilmes.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.sitefilmes.model.Filme;
import com.example.sitefilmes.repository.FilmeRepository;

@Service
public class FilmeService {

    private FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public void save(Filme filme){
        repository.save(filme);
    }

    public List<Filme> findAll(){
        return repository.findAllNotDeleted();
    }
    

    public Optional<Filme> findById(long id){
        return repository.findByIdNotDeleted(id);
    }

    public void delete(long id){
        Optional<Filme> filme = this.findById(id);
        if(filme.isPresent()){
            LocalDate currentDate = LocalDate.now();
            Date currentDateTime = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Filme f = filme.get();
            f.setDeleted(currentDateTime);
            save(f);

        }
        else{
            throw new RuntimeException("Filme não encontrado"); 
        }
    }

    


    




}
