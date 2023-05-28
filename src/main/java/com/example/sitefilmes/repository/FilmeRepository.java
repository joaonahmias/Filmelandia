package com.example.sitefilmes.repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sitefilmes.model.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    @Query("SELECT f FROM Filme f WHERE f.id = :id AND f.deleted IS NULL")
    Optional<Filme> findByIdNotDeleted(@Param("id") long id);
    @Query("SELECT f FROM Filme f WHERE f.deleted IS NULL")
    List<Filme> findAllNotDeleted();
    
}

