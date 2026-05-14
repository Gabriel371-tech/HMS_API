package com.hospital.hms.service;

import com.hospital.hms.model.ItemReceita;
import com.hospital.hms.model.ItemReceitaId;
import com.hospital.hms.repository.ItemReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItemReceitaService {

    @Autowired
    private ItemReceitaRepository itemReceitaRepository;

    public List<ItemReceita> listarTodos() {
        return itemReceitaRepository.findAll();
    }

    public Optional<ItemReceita> buscarPorId(ItemReceitaId id) {
        return itemReceitaRepository.findById(id);
    }

    public ItemReceita salvar(ItemReceita itemReceita) {
        return itemReceitaRepository.save(itemReceita);
    }

    public void excluir(ItemReceitaId id) {
        itemReceitaRepository.deleteById(id);
    }
}
