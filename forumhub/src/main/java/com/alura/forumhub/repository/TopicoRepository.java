
package com.alura.forumhub.repository;

import com.alura.forumhub.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    @Query(value = "SELECT * FROM topicos t WHERE " +
            "(:curso IS NULL OR LOWER(t.curso) LIKE LOWER(CONCAT('%', :curso, '%'))) AND " +
            "(:ano IS NULL OR YEAR(t.data_criacao) = :ano)",
            nativeQuery = true)
    Page<Topico> buscarComFiltros(String curso, Integer ano, Pageable pageable);
}
