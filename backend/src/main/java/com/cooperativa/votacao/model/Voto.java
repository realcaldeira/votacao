package com.cooperativa.votacao.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "voto", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sessao_votacao_id", "cpf_associado"})
})
public class Voto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_votacao_id", nullable = false)
    private SessaoVotacao sessaoVotacao;
    
    @Column(name = "cpf_associado", nullable = false)
    private String cpfAssociado;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVoto voto;
    
    @Column(name = "data_voto")
    private LocalDateTime dataVoto;
    
    public Voto() {
        this.dataVoto = LocalDateTime.now();
    }
    
    public Voto(SessaoVotacao sessaoVotacao, String cpfAssociado, TipoVoto voto) {
        this();
        this.sessaoVotacao = sessaoVotacao;
        this.cpfAssociado = cpfAssociado;
        this.voto = voto;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public SessaoVotacao getSessaoVotacao() {
        return sessaoVotacao;
    }
    
    public void setSessaoVotacao(SessaoVotacao sessaoVotacao) {
        this.sessaoVotacao = sessaoVotacao;
    }
    
    public String getCpfAssociado() {
        return cpfAssociado;
    }
    
    public void setCpfAssociado(String cpfAssociado) {
        this.cpfAssociado = cpfAssociado;
    }
    
    public TipoVoto getVoto() {
        return voto;
    }
    
    public void setVoto(TipoVoto voto) {
        this.voto = voto;
    }
    
    public LocalDateTime getDataVoto() {
        return dataVoto;
    }
    
    public void setDataVoto(LocalDateTime dataVoto) {
        this.dataVoto = dataVoto;
    }
}
