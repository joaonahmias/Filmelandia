
package com.example.sitefilmes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        

                return http
                .authorizeHttpRequests(auth -> {
                    //auth.requestMatchers("/admin/**").hasRole("ADMIN");

                    auth.requestMatchers("/admin").hasRole("ADMIN");
                    auth.requestMatchers("/cadastrarPagina").hasRole("ADMIN");
                    auth.requestMatchers("/doSalvar").hasRole("ADMIN");
                    auth.requestMatchers("/editarPage/**").hasRole("ADMIN");
                    auth.requestMatchers("/doDeletar/**").hasRole("ADMIN");
                    auth.requestMatchers("/adicionarCarrinho/**").hasRole("USER");
                    auth.requestMatchers("/verCarrinho").hasRole("USER");
                    auth.requestMatchers("/finalizarCompras").hasRole("USER");
                    auth.requestMatchers("/index").permitAll();
                    auth.anyRequest().permitAll();
                    //auth.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .build();
                
    }

    @Bean
    BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
