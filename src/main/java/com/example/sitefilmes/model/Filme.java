package com.example.sitefilmes.model;
import java.util.Date;
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
    String titulo;
    String anoLancamento;
    String genero;
    String direcao;
    String elenco;
    @NotBlank
    String descricao;
    Date deleted;
    String imgUri;
   
}
