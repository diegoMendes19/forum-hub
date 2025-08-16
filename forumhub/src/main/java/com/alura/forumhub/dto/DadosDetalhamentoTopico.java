
package com.alura.forumhub.dto;

import com.alura.forumhub.model.Topico;
import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String estado,
        String autor,
        String curso
) {
    public DadosDetalhamentoTopico(Topico t) {
        this(t.getId(), t.getTitulo(), t.getMensagem(), t.getDataCriacao(),
             t.getEstado().name(), t.getAutor(), t.getCurso());
    }
}
