package com.hospital.hms.security;

import com.hospital.hms.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String nome;

    public UserDetailsImpl(Long id, String username, String password, String nome) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nome = nome;
    }

    public static UserDetailsImpl build(Usuario usuario) {
        return new UserDetailsImpl(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getNome()
        );
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
