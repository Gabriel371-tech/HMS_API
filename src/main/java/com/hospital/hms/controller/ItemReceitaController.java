package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.ItemReceitaRequestDTO;
import com.hospital.hms.dto.response.ItemReceitaResponseDTO;
import com.hospital.hms.model.ItemReceitaId;
import com.hospital.hms.service.ItemReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/itens-receita")
public class ItemReceitaController {

    @Autowired
    private ItemReceitaService itemReceitaService;

    @GetMapping
    public List<ItemReceitaResponseDTO> listar() {
        return itemReceitaService.listarTodos().stream().map(DtoMapper::toResponse).toList();
    }

    @GetMapping("/{codreceita}/{codmedicamento}")
    public ResponseEntity<ItemReceitaResponseDTO> buscar(
            @PathVariable Long codreceita,
            @PathVariable Long codmedicamento
    ) {
        return itemReceitaService.buscarPorId(new ItemReceitaId(codreceita, codmedicamento))
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemReceitaResponseDTO> salvar(@RequestBody ItemReceitaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(itemReceitaService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{codreceita}/{codmedicamento}")
    public ResponseEntity<ItemReceitaResponseDTO> atualizar(
            @PathVariable Long codreceita,
            @PathVariable Long codmedicamento,
            @RequestBody ItemReceitaRequestDTO request
    ) {
        ItemReceitaId id = new ItemReceitaId(codreceita, codmedicamento);
        return itemReceitaService.buscarPorId(id)
                .map(itemExistente -> {
                    var itemReceita = DtoMapper.toEntity(new ItemReceitaRequestDTO(
                            codreceita,
                            codmedicamento,
                            request.posologia()
                    ));
                    return ResponseEntity.ok(DtoMapper.toResponse(itemReceitaService.salvar(itemReceita)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codreceita}/{codmedicamento}")
    public ResponseEntity<Void> excluir(@PathVariable Long codreceita, @PathVariable Long codmedicamento) {
        ItemReceitaId id = new ItemReceitaId(codreceita, codmedicamento);
        if (itemReceitaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        itemReceitaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
