package com.hospital.hms.controller;

import com.hospital.hms.model.Usuario;
import com.hospital.hms.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
