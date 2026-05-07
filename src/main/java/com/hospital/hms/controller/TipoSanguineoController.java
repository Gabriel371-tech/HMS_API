package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.TipoSanguineoRequestDTO;
import com.hospital.hms.dto.response.TipoSanguineoResponseDTO;
import com.hospital.hms.service.TipoSanguineoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-sanguineos")
public class TipoSanguineoController {

    @Autowired
    private TipoSanguineoService tipoSanguineoService;

    @GetMapping
    public List<TipoSanguineoResponseDTO> listar() {
        return tipoSanguineoService.listarTodos().stream()
                .map(DtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSanguineoResponseDTO> buscar(@PathVariable Long id) {
        return tipoSanguineoService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoSanguineoResponseDTO> salvar(@RequestBody TipoSanguineoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(tipoSanguineoService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSanguineoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody TipoSanguineoRequestDTO request
    ) {
        return tipoSanguineoService.buscarPorId(id)
                .map(tipoExistente -> {
                    var tipoSanguineo = DtoMapper.toEntity(request);
                    tipoSanguineo.setCodtipo(id);
                    return ResponseEntity.ok(DtoMapper.toResponse(tipoSanguineoService.salvar(tipoSanguineo)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (tipoSanguineoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        tipoSanguineoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
