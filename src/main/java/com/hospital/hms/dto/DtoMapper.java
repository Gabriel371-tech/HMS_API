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
        leito.setStatus(dto.status());
        leito.setQuarto(toQuartoReference(resolveCodquarto(dto)));
        return leito;
    }

    public static LeitoResponseDTO toResponse(Leito leito) {
        if (leito == null) return null;
        return new LeitoResponseDTO(
                leito.getCodleito(),
                leito.getStatus(),
                toResponse(leito.getQuarto())
        );
    }

    public static Medico toEntity(MedicoRequestDTO dto) {
        Medico medico = new Medico();
        medico.setNome(dto.nome());
        medico.setCrm(dto.crm());
        medico.setEspecialidade(toEspecialidadeReference(resolveCodespecialidade(dto)));
        return medico;
    }

    public static MedicoResponseDTO toResponse(Medico medico) {
        if (medico == null) return null;
        return new MedicoResponseDTO(
                medico.getCodmedico(),
                medico.getNome(),
                medico.getCrm(),
                toResponse(medico.getEspecialidade())
        );
    }

    public static Paciente toEntity(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setDataNascimento(resolveDataNascimento(dto));
        paciente.setTipoSanguineo(toTipoSanguineoReference(resolveCodtipo(dto)));
        return paciente;
    }

    public static PacienteResponseDTO toResponse(Paciente paciente) {
        if (paciente == null) return null;
        return new PacienteResponseDTO(
                paciente.getCodpaciente(),
                paciente.getNome(),
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

    public static Consulta toEntity(ConsultaRequestDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setDatahora(dto.datahora());
        consulta.setMotivo(dto.motivo());
        consulta.setPaciente(toPacienteReference(dto.codpacientefk()));
        consulta.setMedico(toMedicoReference(dto.codmedicofk()));
        return consulta;
    }

    public static ConsultaResponseDTO toResponse(Consulta consulta) {
        if (consulta == null) return null;
        return new ConsultaResponseDTO(
                consulta.getCodconsulta(),
                consulta.getDatahora(),
                consulta.getMotivo(),
                toResponse(consulta.getPaciente()),
                toResponse(consulta.getMedico())
        );
    }

    public static Receita toEntity(ReceitaRequestDTO dto) {
        Receita receita = new Receita();
        receita.setValidade(dto.validade());
        receita.setConsulta(toConsultaReference(dto.codconsultafk()));
        return receita;
    }

    public static ReceitaResponseDTO toResponse(Receita receita) {
        if (receita == null) return null;
        return new ReceitaResponseDTO(
                receita.getCodreceita(),
                receita.getValidade(),
                toResponse(receita.getConsulta())
        );
    }

    public static Medicamento toEntity(MedicamentoRequestDTO dto) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNomegenerico(dto.nomegenerico());
        medicamento.setLaboratorio(dto.laboratorio());
        return medicamento;
    }

    public static MedicamentoResponseDTO toResponse(Medicamento medicamento) {
        if (medicamento == null) return null;
        return new MedicamentoResponseDTO(
                medicamento.getCodmedicamento(),
                medicamento.getNomegenerico(),
                medicamento.getLaboratorio()
        );
    }

    public static ExameConsulta toEntity(ExameConsultaRequestDTO dto) {
        ExameConsulta exameConsulta = new ExameConsulta();
        exameConsulta.setId(new ExameConsultaId(dto.codconsultafk(), dto.codexamefk()));
        exameConsulta.setConsulta(toConsultaReference(dto.codconsultafk()));
        exameConsulta.setResultadourl(dto.resultadourl());
        exameConsulta.setDatarealizacao(dto.datarealizacao());
        return exameConsulta;
    }

    public static ExameConsultaResponseDTO toResponse(ExameConsulta exameConsulta) {
        if (exameConsulta == null) return null;
        return new ExameConsultaResponseDTO(
                exameConsulta.getId().getCodconsulta(),
                exameConsulta.getId().getCodexame(),
                exameConsulta.getResultadourl(),
                exameConsulta.getDatarealizacao(),
                toResponse(exameConsulta.getConsulta())
        );
    }

    public static ItemReceita toEntity(ItemReceitaRequestDTO dto) {
        ItemReceita itemReceita = new ItemReceita();
        itemReceita.setId(new ItemReceitaId(dto.codreceitafk(), dto.codmedicamentofk()));
        itemReceita.setReceita(toReceitaReference(dto.codreceitafk()));
        itemReceita.setMedicamento(toMedicamentoReference(dto.codmedicamentofk()));
        itemReceita.setPosologia(dto.posologia());
        return itemReceita;
    }

    public static ItemReceitaResponseDTO toResponse(ItemReceita itemReceita) {
        if (itemReceita == null) return null;
        return new ItemReceitaResponseDTO(
                itemReceita.getId().getCodreceita(),
                itemReceita.getId().getCodmedicamento(),
                itemReceita.getPosologia(),
                toResponse(itemReceita.getReceita()),
                toResponse(itemReceita.getMedicamento())
        );
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

    private static Paciente toPacienteReference(Long id) {
        if (id == null) return null;
        Paciente paciente = new Paciente();
        paciente.setCodpaciente(id);
        return paciente;
    }

    private static Medico toMedicoReference(Long id) {
        if (id == null) return null;
        Medico medico = new Medico();
        medico.setCodmedico(id);
        return medico;
    }

    private static Consulta toConsultaReference(Long id) {
        if (id == null) return null;
        Consulta consulta = new Consulta();
        consulta.setCodconsulta(id);
        return consulta;
    }

    private static Receita toReceitaReference(Long id) {
        if (id == null) return null;
        Receita receita = new Receita();
        receita.setCodreceita(id);
        return receita;
    }

    private static Medicamento toMedicamentoReference(Long id) {
        if (id == null) return null;
        Medicamento medicamento = new Medicamento();
        medicamento.setCodmedicamento(id);
        return medicamento;
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
        if (dto.codtipodk() != null) return dto.codtipodk();
        if (dto.codtipo() != null) return dto.codtipo();
        return dto.tipoSanguineo() != null ? dto.tipoSanguineo().codtipo() : null;
    }

    private static java.time.LocalDate resolveDataNascimento(PacienteRequestDTO dto) {
        return dto.datanasc() != null ? dto.datanasc() : dto.dataNascimento();
    }
}
