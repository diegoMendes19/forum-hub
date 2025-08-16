
package com.alura.forumhub.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String titulo;

    @Column(nullable=false, columnDefinition="TEXT")
    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EstadoTopico estado = EstadoTopico.ABERTO;

    @Column(nullable=false)
    private String autor;

    @Column(nullable=false)
    private String curso;
}
