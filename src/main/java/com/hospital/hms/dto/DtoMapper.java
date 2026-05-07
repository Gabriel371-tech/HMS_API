package com.hospital.hms.dto;

import com.hospital.hms.dto.request.*;
import com.hospital.hms.dto.response.*;
import com.hospital.hms.model.*;

public final class DtoMapper {

    private DtoMapper() {
    }

    public static Ala toEntity(AlaRequestDTO dto) {
        Ala ala = new Ala();
        ala.setNome(dto.nome());
        ala.setAndar(dto.andar());
        return ala;
    }

    public static AlaResponseDTO toResponse(Ala ala) {
        if (ala == null) return null;
        return new AlaResponseDTO(ala.getCodala(), ala.getNome(), ala.getAndar());
    }

    public static Especialidade toEntity(EspecialidadeRequestDTO dto) {
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(dto.nome());
        especialidade.setDescricao(dto.descricao());
        return especialidade;
    }

    public static EspecialidadeResponseDTO toResponse(Especialidade especialidade) {
        if (especialidade == null) return null;
        return new EspecialidadeResponseDTO(
                especialidade.getCodespecialidade(),
                especialidade.getNome(),
                especialidade.getDescricao()
        );
    }

    public static TipoSanguineo toEntity(TipoSanguineoRequestDTO dto) {
        TipoSanguineo tipoSanguineo = new TipoSanguineo();
        tipoSanguineo.setTipo(dto.tipo());
        tipoSanguineo.setFatorrh(dto.fatorrh());
        return tipoSanguineo;
    }

    public static TipoSanguineoResponseDTO toResponse(TipoSanguineo tipoSanguineo) {
        if (tipoSanguineo == null) return null;
        return new TipoSanguineoResponseDTO(
                tipoSanguineo.getCodtipo(),
                tipoSanguineo.getTipo(),
                tipoSanguineo.getFatorrh()
        );
    }

    public static Quarto toEntity(QuartoRequestDTO dto) {
        Quarto quarto = new Quarto();
        quarto.setNumero(dto.numero());
        quarto.setTipo(dto.tipo());
        quarto.setAla(toAlaReference(resolveCodala(dto)));
        return quarto;
    }

    public static QuartoResponseDTO toResponse(Quarto quarto) {
        if (quarto == null) return null;
        return new QuartoResponseDTO(
                quarto.getCodquarto(),
                quarto.getNumero(),
                quarto.getTipo(),
                toResponse(quarto.getAla())
        );
    }

    public static Leito toEntity(LeitoRequestDTO dto) {
        Leito leito = new Leito();
        leito.setNumero(dto.numero());
        leito.setStatus(dto.status());
        leito.setQuarto(toQuartoReference(resolveCodquarto(dto)));
        return leito;
    }

    public static LeitoResponseDTO toResponse(Leito leito) {
        if (leito == null) return null;
        return new LeitoResponseDTO(
                leito.getCodleito(),
                leito.getNumero(),
                leito.getStatus(),
                toResponse(leito.getQuarto())
        );
    }

    public static Medico toEntity(MedicoRequestDTO dto) {
        Medico medico = new Medico();
        medico.setNome(dto.nome());
        medico.setCrm(dto.crm());
        medico.setTelefone(dto.telefone());
        medico.setEspecialidade(toEspecialidadeReference(resolveCodespecialidade(dto)));
        return medico;
    }

    public static MedicoResponseDTO toResponse(Medico medico) {
        if (medico == null) return null;
        return new MedicoResponseDTO(
                medico.getCodmedico(),
                medico.getNome(),
                medico.getCrm(),
                medico.getTelefone(),
                toResponse(medico.getEspecialidade())
        );
    }

    public static Paciente toEntity(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setTipoSanguineo(toTipoSanguineoReference(resolveCodtipo(dto)));
        return paciente;
    }

    public static PacienteResponseDTO toResponse(Paciente paciente) {
        if (paciente == null) return null;
        return new PacienteResponseDTO(
                paciente.getCodpaciente(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                toResponse(paciente.getTipoSanguineo())
        );
    }

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password());
        usuario.setNome(dto.nome());
        return usuario;
    }

    public static UsuarioResponseDTO toResponse(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getNome());
    }

    private static Ala toAlaReference(Long id) {
        if (id == null) return null;
        Ala ala = new Ala();
        ala.setCodala(id);
        return ala;
    }

    private static Especialidade toEspecialidadeReference(Long id) {
        if (id == null) return null;
        Especialidade especialidade = new Especialidade();
        especialidade.setCodespecialidade(id);
        return especialidade;
    }

    private static Quarto toQuartoReference(Long id) {
        if (id == null) return null;
        Quarto quarto = new Quarto();
        quarto.setCodquarto(id);
        return quarto;
    }

    private static TipoSanguineo toTipoSanguineoReference(Long id) {
        if (id == null) return null;
        TipoSanguineo tipoSanguineo = new TipoSanguineo();
        tipoSanguineo.setCodtipo(id);
        return tipoSanguineo;
    }

    private static Long resolveCodala(QuartoRequestDTO dto) {
        if (dto.codala() != null) return dto.codala();
        return dto.ala() != null ? dto.ala().codala() : null;
    }

    private static Long resolveCodquarto(LeitoRequestDTO dto) {
        if (dto.codquarto() != null) return dto.codquarto();
        return dto.quarto() != null ? dto.quarto().codquarto() : null;
    }

    private static Long resolveCodespecialidade(MedicoRequestDTO dto) {
        if (dto.codespecialidade() != null) return dto.codespecialidade();
        return dto.especialidade() != null ? dto.especialidade().codespecialidade() : null;
    }

    private static Long resolveCodtipo(PacienteRequestDTO dto) {
        if (dto.codtipo() != null) return dto.codtipo();
        return dto.tipoSanguineo() != null ? dto.tipoSanguineo().codtipo() : null;
    }
}
