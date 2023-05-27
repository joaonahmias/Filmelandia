package com.example.sitefilmes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sitefilmes.model.Usuario;
import com.example.sitefilmes.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    UsuarioRepository repository;
    BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, BCryptPasswordEncoder encoder){
        this.repository = repository;
        this.encoder = encoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = repository.findUsuarioByUsername(username);
        if (user.isPresent()){
            return user.get();
        }else{
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
        }
    }

    public void create(Usuario usuario){
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        this.repository.save(usuario);
    }

    public List<Usuario> listAll(){
        return  repository.findAll();
    }
    
}
