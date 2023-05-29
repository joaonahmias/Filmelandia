package com.example.sitefilmes.model;
import java.util.Date;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Filme {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank
    @Size(max = 100)
    String titulo;
    String anoLancamento;
    @NotBlank
    String genero;
    @NotBlank
    String direcao;
    String elenco;
    @NotBlank
    @Size(max = 500)
    String descricao;
    @Min(value = 0, message = "O preço não pode ser negativo")
    long preco;
    Date deleted;
    String imgUri;
   
}
