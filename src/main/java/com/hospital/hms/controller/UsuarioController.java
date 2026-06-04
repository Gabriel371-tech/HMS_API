package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.model.Usuario;
import com.hospital.hms.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Usuario usuario = usuarioService.autenticar(username, password);
        if (usuario != null) {
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("erro", "Credenciais inválidas");
            return "login";
        }
    }

    @GetMapping("/cadastro")
    public String cadastroPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("requisitosSenha", usuarioService.getRequisitosSenha());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.salvar(usuario);
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            usuario.setPassword(null);
            model.addAttribute("usuario", usuario);
            model.addAttribute("erro", ex.getMessage());
            model.addAttribute("requisitosSenha", usuarioService.getRequisitosSenha());
            return "cadastro";
        }
    }

    @GetMapping("/api/usuarios/{username}")
    @ResponseBody
    public ResponseEntity<?> buscarUsuario(@PathVariable String username) {
        try {
            Usuario usuario = usuarioService.buscarPorUsername(username);
            return ResponseEntity.ok(DtoMapper.toResponse(usuario));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/api/usuarios/{username}/foto")
    @ResponseBody
    public ResponseEntity<?> atualizarFoto(@PathVariable String username, @RequestBody Map<String, String> payload) {
        try {
            String fotoBase64 = payload != null ? payload.get("fotoBase64") : null;
            Usuario usuario = usuarioService.atualizarFoto(username, fotoBase64);
            return ResponseEntity.ok(DtoMapper.toResponse(usuario));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("erro", ex.getMessage()));
        }
    }

    @DeleteMapping("/api/usuarios/{username}/foto")
    @ResponseBody
    public ResponseEntity<?> removerFoto(@PathVariable String username) {
        try {
            Usuario usuario = usuarioService.removerFoto(username);
            return ResponseEntity.ok(DtoMapper.toResponse(usuario));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("erro", ex.getMessage()));
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
