package com.hospital.hms.service;

import com.hospital.hms.model.Usuario;
import com.hospital.hms.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    public static final int TAMANHO_MINIMO_SENHA = 8;
    private static final int TAMANHO_MAXIMO_FOTO_BASE64 = 1_500_000;
    public static final List<String> REQUISITOS_SENHA = List.of(
            "No minimo 8 caracteres",
            "Pelo menos uma letra maiuscula",
            "Pelo menos uma letra minuscula",
            "Pelo menos um numero",
            "Pelo menos um caractere especial",
            "Nao pode conter espacos"
    );

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
        validarUsuario(usuario);

        if (usuario.getPassword() != null && !isBCrypt(usuario.getPassword())) {
            validarSenha(usuario.getPassword());
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));
    }

    public Usuario atualizarFoto(String username, String fotoBase64) {
        Usuario usuario = buscarPorUsername(username);
        usuario.setFotoBase64(normalizarFotoBase64(fotoBase64));
        return usuarioRepository.save(usuario);
    }

    public Usuario removerFoto(String username) {
        Usuario usuario = buscarPorUsername(username);
        usuario.setFotoBase64(null);
        return usuarioRepository.save(usuario);
    }

    public List<String> getRequisitosSenha() {
        return REQUISITOS_SENHA;
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

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao informado.");
        }

        if (usuario.getUsername() == null || usuario.getUsername().isBlank()) {
            throw new IllegalArgumentException("Nome de usuario e obrigatorio.");
        }

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException("Senha e obrigatoria.");
        }
    }

    private void validarSenha(String senha) {
        List<String> falhas = new ArrayList<>();

        if (senha.length() < TAMANHO_MINIMO_SENHA) {
            falhas.add("ter no minimo " + TAMANHO_MINIMO_SENHA + " caracteres");
        }

        if (!senha.chars().anyMatch(Character::isUpperCase)) {
            falhas.add("ter pelo menos uma letra maiuscula");
        }

        if (!senha.chars().anyMatch(Character::isLowerCase)) {
            falhas.add("ter pelo menos uma letra minuscula");
        }

        if (!senha.chars().anyMatch(Character::isDigit)) {
            falhas.add("ter pelo menos um numero");
        }

        if (senha.chars().noneMatch(this::isCaractereEspecial)) {
            falhas.add("ter pelo menos um caractere especial");
        }

        if (senha.chars().anyMatch(Character::isWhitespace)) {
            falhas.add("nao conter espacos");
        }

        if (!falhas.isEmpty()) {
            throw new IllegalArgumentException("A senha deve " + String.join(", ", falhas) + ".");
        }
    }

    private boolean isCaractereEspecial(int caractere) {
        return !Character.isLetterOrDigit(caractere) && !Character.isWhitespace(caractere);
    }

    private String normalizarFotoBase64(String fotoBase64) {
        if (fotoBase64 == null || fotoBase64.isBlank()) {
            return null;
        }

        String fotoNormalizada = fotoBase64.trim();
        if (fotoNormalizada.length() > TAMANHO_MAXIMO_FOTO_BASE64) {
            throw new IllegalArgumentException("A foto deve ter no maximo 1.5 MB em Base64.");
        }

        if (!fotoNormalizada.matches("^data:image/(png|jpeg|jpg|webp);base64,[A-Za-z0-9+/=\\r\\n]+$")) {
            throw new IllegalArgumentException("A foto deve ser uma imagem PNG, JPG ou WEBP em Base64.");
        }

        return fotoNormalizada.replace("\r", "").replace("\n", "");
    }
}
