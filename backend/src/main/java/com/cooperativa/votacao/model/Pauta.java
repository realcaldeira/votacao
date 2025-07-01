package com.cooperativa.votacao.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pauta")
public class Pauta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String titulo;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    
    @OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SessaoVotacao> sessoesVotacao = new ArrayList<>();
    
    public Pauta() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    public Pauta(String titulo, String descricao) {
        this();
        this.titulo = titulo;
        this.descricao = descricao;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public List<SessaoVotacao> getSessoesVotacao() {
        return sessoesVotacao;
    }
    
    public void setSessoesVotacao(List<SessaoVotacao> sessoesVotacao) {
        this.sessoesVotacao = sessoesVotacao;
    }
}
