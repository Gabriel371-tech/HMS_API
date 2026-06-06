package com.hospital.hms.controller;

import java.util.Map;

import com.hospital.hms.model.Usuario;
import com.hospital.hms.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController{

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Usuario usuario = usuarioService.autenticar(username, password);
        if (usuario != null) {
            return ResponseEntity.ok(Map.of("user", usuario));
        } else {
            return ResponseEntity.status(401).body(Map.of("erro", "Credenciais inválidas"));
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Map<String, String> body) {
        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(body.get("username"));
            usuario.setPassword(body.get("password"));
            usuario.setNome(body.get("nome"));
            usuarioService.salvar(usuario);
            return ResponseEntity.status(201).body(Map.of("message", "Usuário criado com sucesso"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body(Map.of("erro", "Username já está em uso."));
        }
    }
}