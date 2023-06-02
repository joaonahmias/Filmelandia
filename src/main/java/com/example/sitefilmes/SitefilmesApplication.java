package com.example.sitefilmes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.sitefilmes.model.Usuario;
import com.example.sitefilmes.repository.UsuarioRepository;

@SpringBootApplication
public class SitefilmesApplication {

    @Bean
CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
    return args -> {

        // Excluir todos os usuários existentes
        usuarioRepository.deleteAll();

        List<Usuario> users = Stream.of(
                new Usuario("João", "joaonahmias", encoder.encode("123"), true),
                new Usuario("Lara", "larabelo", encoder.encode("123"), false),
                new Usuario("Liryel", "liryelbelo", encoder.encode("user2"), false)
        ).collect(Collectors.toList());

        for (var e : users) {
            System.out.println(e);
        }
        usuarioRepository.saveAll(users);
    };
}


	public static void main(String[] args) {
		SpringApplication.run(SitefilmesApplication.class, args);
	}

}
