package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.EspecialidadeRequestDTO;
import com.hospital.hms.dto.response.EspecialidadeResponseDTO;
import com.hospital.hms.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @GetMapping
    public List<EspecialidadeResponseDTO> listar() {
        return especialidadeService.listarTodos().stream()
                .map(DtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> buscar(@PathVariable Long id) {
        return especialidadeService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> salvar(@RequestBody EspecialidadeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(especialidadeService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody EspecialidadeRequestDTO request
    ) {
        return especialidadeService.buscarPorId(id)
                .map(especialidadeExistente -> {
                    var especialidade = DtoMapper.toEntity(request);
                    especialidade.setCodespecialidade(id);
                    return ResponseEntity.ok(DtoMapper.toResponse(especialidadeService.salvar(especialidade)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (especialidadeService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        especialidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
