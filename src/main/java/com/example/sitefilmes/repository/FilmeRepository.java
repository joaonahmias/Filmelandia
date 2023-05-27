package com.example.sitefilmes.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sitefilmes.model.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    Optional<Filme> findById(long id);
}
