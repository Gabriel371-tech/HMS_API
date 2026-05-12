package com.hospital.hms.controller;

import com.hospital.hms.model.*;
import com.hospital.hms.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired private MedicoService medicoService;
    @Autowired private PacienteService pacienteService;
    @Autowired private AlaService alaService;
    @Autowired private LeitoService leitoService;
    @Autowired private EspecialidadeService especialidadeService;
    @Autowired private QuartoService quartoService;
    @Autowired private TipoSanguineoService tipoSanguineoService;
    @Autowired private UsuarioService usuarioService;

    private boolean isNotLogged(HttpSession session) {
        return session.getAttribute("usuarioLogado") == null;
    }

    // DASHBOARD HOME
    @GetMapping
    public String home(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("totalMedicos", medicoService.listarTodos().size());
        model.addAttribute("totalPacientes", pacienteService.listarTodos().size());
        model.addAttribute("totalLeitos", leitoService.listarTodos().size());
        model.addAttribute("totalAlas", alaService.listarTodos().size());
        model.addAttribute("totalQuartos", quartoService.listarTodos().size());
        return "dashboard";
    }

    // MEU PERFIL
    @GetMapping("/perfil")
    public String meuPerfil(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        return "perfil";
    }

    @PostMapping("/perfil/salvar")
    public String salvarPerfil(@RequestParam String nome, HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        logado.setNome(nome);
        usuarioService.salvar(logado);
        session.setAttribute("usuarioLogado", logado); // Atualiza na sessão
        model.addAttribute("sucesso", "Perfil atualizado com sucesso!");
        return "perfil";
    }

    // CONFIGURAÇÕES
    @GetMapping("/configuracoes")
    public String configuracoes(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        return "configuracoes";
    }

    @PostMapping("/configuracoes/senha")
    public String alterarSenha(@RequestParam String senhaAtual, @RequestParam String novaSenha, @RequestParam String confirmarSenha, HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");

        if (!logado.getPassword().equals(senhaAtual)) {
            model.addAttribute("erroSenha", "A senha atual está incorreta.");
        } else if (!novaSenha.equals(confirmarSenha)) {
            model.addAttribute("erroSenha", "A nova senha e a confirmação não coincidem.");
        } else {
            logado.setPassword(novaSenha);
            usuarioService.salvar(logado);
            session.setAttribute("usuarioLogado", logado);
            model.addAttribute("sucessoSenha", "Senha alterada com sucesso!");
        }
        return "configuracoes";
    }

    // MÉDICOS
    @GetMapping("/medicos")
    public String listarMedicos(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("medicos", medicoService.listarTodos());
        return "medicos/listar";
    }

    @GetMapping("/medicos/novo")
    public String novoMedico(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("medico", new Medico());
        model.addAttribute("especialidades", especialidadeService.listarTodos());
        return "medicos/form";
    }

    @GetMapping("/medicos/editar/{id}")
    public String editarMedico(@PathVariable Long id, HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        Medico medico = medicoService.buscarPorId(id).orElse(null);
        if (medico == null) return "redirect:/dashboard/medicos";
        model.addAttribute("medico", medico);
        model.addAttribute("especialidades", especialidadeService.listarTodos());
        return "medicos/form";
    }

    @PostMapping("/medicos/salvar")
    public String salvarMedico(@ModelAttribute Medico medico) {
        medicoService.salvar(medico);
        return "redirect:/dashboard/medicos";
    }

    @GetMapping("/medicos/excluir/{id}")
    public String excluirMedico(@PathVariable Long id, HttpSession session) {
        if (isNotLogged(session)) return "redirect:/login";
        medicoService.excluir(id);
        return "redirect:/dashboard/medicos";
    }

    // PACIENTES
    @GetMapping("/pacientes")
    public String listarPacientes(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("pacientes", pacienteService.listarTodos());
        return "pacientes/listar";
    }

    @GetMapping("/pacientes/novo")
    public String novoPaciente(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("paciente", new Paciente());
        model.addAttribute("tiposSanguineos", tipoSanguineoService.listarTodos());
        return "pacientes/form";
    }

    @GetMapping("/pacientes/editar/{id}")
    public String editarPaciente(@PathVariable Long id, HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        Paciente paciente = pacienteService.buscarPorId(id).orElse(null);
        if (paciente == null) return "redirect:/dashboard/pacientes";
        model.addAttribute("paciente", paciente);
        model.addAttribute("tiposSanguineos", tipoSanguineoService.listarTodos());
        return "pacientes/form";
    }

    @PostMapping("/pacientes/salvar")
    public String salvarPaciente(@ModelAttribute Paciente paciente) {
        pacienteService.salvar(paciente);
        return "redirect:/dashboard/pacientes";
    }

    @GetMapping("/pacientes/excluir/{id}")
    public String excluirPaciente(@PathVariable Long id, HttpSession session) {
        if (isNotLogged(session)) return "redirect:/login";
        pacienteService.excluir(id);
        return "redirect:/dashboard/pacientes";
    }

    // LEITOS
    @GetMapping("/leitos")
    public String listarLeitos(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("leitos", leitoService.listarTodos());
        return "leitos/listar";
    }

    @GetMapping("/leitos/novo")
    public String novoLeito(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("leito", new Leito());
        model.addAttribute("quartos", quartoService.listarTodos());
        return "leitos/form";
    }

    @GetMapping("/leitos/editar/{id}")
    public String editarLeito(@PathVariable Long id, HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        Leito leito = leitoService.buscarPorId(id).orElse(null);
        if (leito == null) return "redirect:/dashboard/leitos";
        model.addAttribute("leito", leito);
        model.addAttribute("quartos", quartoService.listarTodos());
        return "leitos/form";
    }

    @PostMapping("/leitos/salvar")
    public String salvarLeito(@ModelAttribute Leito leito) {
        leitoService.salvar(leito);
        return "redirect:/dashboard/leitos";
    }

    @GetMapping("/leitos/excluir/{id}")
    public String excluirLeito(@PathVariable Long id, HttpSession session) {
        if (isNotLogged(session)) return "redirect:/login";
        leitoService.excluir(id);
        return "redirect:/dashboard/leitos";
    }

    // ALAS
    @GetMapping("/alas")
    public String listarAlas(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("alas", alaService.listarTodos());
        return "alas/listar";
    }

    @GetMapping("/alas/novo")
    public String novaAla(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("ala", new Ala());
        return "alas/form";
    }

    @GetMapping("/alas/editar/{id}")
    public String editarAla(@PathVariable Long id, HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        Ala ala = alaService.buscarPorId(id).orElse(null);
        if (ala == null) return "redirect:/dashboard/alas";
        model.addAttribute("ala", ala);
        return "alas/form";
    }

    @PostMapping("/alas/salvar")
    public String salvarAla(@ModelAttribute Ala ala) {
        alaService.salvar(ala);
        return "redirect:/dashboard/alas";
    }

    @GetMapping("/alas/excluir/{id}")
    public String excluirAla(@PathVariable Long id, HttpSession session) {
        if (isNotLogged(session)) return "redirect:/login";
        alaService.excluir(id);
        return "redirect:/dashboard/alas";
    }

    // QUARTOS
    @GetMapping("/quartos")
    public String listarQuartos(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("quartos", quartoService.listarTodos());
        return "quartos/listar";
    }

    @GetMapping("/quartos/novo")
    public String novoQuarto(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("quarto", new Quarto());
        model.addAttribute("alas", alaService.listarTodos());
        return "quartos/form";
    }

    @GetMapping("/quartos/editar/{id}")
    public String editarQuarto(@PathVariable Long id, HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        Quarto quarto = quartoService.buscarPorId(id).orElse(null);
        if (quarto == null) return "redirect:/dashboard/quartos";
        model.addAttribute("quarto", quarto);
        model.addAttribute("alas", alaService.listarTodos());
        return "quartos/form";
    }

    @PostMapping("/quartos/salvar")
    public String salvarQuarto(@ModelAttribute Quarto quarto) {
        quartoService.salvar(quarto);
        return "redirect:/dashboard/quartos";
    }

    @GetMapping("/quartos/excluir/{id}")
    public String excluirQuarto(@PathVariable Long id, HttpSession session) {
        if (isNotLogged(session)) return "redirect:/login";
        quartoService.excluir(id);
        return "redirect:/dashboard/quartos";
    }

    // ESPECIALIDADES
    @GetMapping("/especialidades")
    public String listarEspecialidades(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("especialidades", especialidadeService.listarTodos());
        return "especialidades/listar";
    }

    @GetMapping("/especialidades/novo")
    public String novaEspecialidade(HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        model.addAttribute("especialidade", new Especialidade());
        return "especialidades/form";
    }

    @GetMapping("/especialidades/editar/{id}")
    public String editarEspecialidade(@PathVariable Long id, HttpSession session, Model model) {
        if (isNotLogged(session)) return "redirect:/login";
        Especialidade especialidade = especialidadeService.buscarPorId(id).orElse(null);
        if (especialidade == null) return "redirect:/dashboard/especialidades";
        model.addAttribute("especialidade", especialidade);
        return "especialidades/form";
    }

    @PostMapping("/especialidades/salvar")
    public String salvarEspecialidade(@ModelAttribute Especialidade especialidade) {
        especialidadeService.salvar(especialidade);
        return "redirect:/dashboard/especialidades";
    }

    @GetMapping("/especialidades/excluir/{id}")
    public String excluirEspecialidade(@PathVariable Long id, HttpSession session) {
        if (isNotLogged(session)) return "redirect:/login";
        especialidadeService.excluir(id);
        return "redirect:/dashboard/especialidades";
    }

}
