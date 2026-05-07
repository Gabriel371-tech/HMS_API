package com.hospital.hms.service;

import com.hospital.hms.model.Usuario;
import com.hospital.hms.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario autenticar(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioOpt.get();
        if (senhaConfere(password, usuario.getPassword())) {
            if (!isBCrypt(usuario.getPassword())) {
                usuario.setPassword(passwordEncoder.encode(password));
                usuarioRepository.save(usuario);
            }
            return usuario;
        }

        return null;
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getPassword() != null && !isBCrypt(usuario.getPassword())) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        return usuarioRepository.save(usuario);
    }

    private boolean senhaConfere(String senhaInformada, String senhaSalva) {
        if (senhaInformada == null || senhaSalva == null) {
            return false;
        }

        if (isBCrypt(senhaSalva)) {
            return passwordEncoder.matches(senhaInformada, senhaSalva);
        }

        return senhaSalva.equals(senhaInformada);
    }

    private boolean isBCrypt(String senha) {
        return senha != null
                && (senha.startsWith("$2a$") || senha.startsWith("$2b$") || senha.startsWith("$2y$"));
    }
}
