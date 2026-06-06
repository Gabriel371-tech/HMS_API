package com.hospital.hms.config;

import com.hospital.hms.model.TipoSanguineo;
import com.hospital.hms.model.Usuario;
import com.hospital.hms.repository.TipoSanguineoRepository;
import com.hospital.hms.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, TipoSanguineoRepository tipoSanguineoRepository) {
        return args -> {
            // Criar usuário padrão
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setNome("Administrador");
                usuarioRepository.save(admin);
                System.out.println("Usuário padrão criado: admin/admin123");
            }

            // Criar tipos sanguíneos padrão
            if (tipoSanguineoRepository.count() == 0) {
                List<TipoSanguineo> tipos = Arrays.asList(
                    new TipoSanguineo(null, "A", "+"),
                    new TipoSanguineo(null, "A", "-"),
                    new TipoSanguineo(null, "B", "+"),
                    new TipoSanguineo(null, "B", "-"),
                    new TipoSanguineo(null, "AB", "+"),
                    new TipoSanguineo(null, "AB", "-"),
                    new TipoSanguineo(null, "O", "+"),
                    new TipoSanguineo(null, "O", "-")
                );
                tipoSanguineoRepository.saveAll(tipos);
                System.out.println("Tipos sanguíneos padrão criados.");
            }
        };
    }
}
