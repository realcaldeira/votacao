package com.cooperativa.votacao.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessao_votacao")
public class SessaoVotacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;
    
    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;
    
    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;
    
    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;
    
    @Enumerated(EnumType.STRING)
    private StatusSessao status;
    
    @OneToMany(mappedBy = "sessaoVotacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Voto> votos = new ArrayList<>();
    
    public SessaoVotacao() {
        this.dataAbertura = LocalDateTime.now();
        this.status = StatusSessao.ABERTA;
    }
    
    public SessaoVotacao(Pauta pauta, Integer duracaoMinutos) {
        this();
        this.pauta = pauta;
        this.duracaoMinutos = duracaoMinutos != null ? duracaoMinutos : 1;
        this.dataFechamento = this.dataAbertura.plusMinutes(this.duracaoMinutos);
    }
    
    public boolean isAberta() {
        return status == StatusSessao.ABERTA && LocalDateTime.now().isBefore(dataFechamento);
    }
    
    public void fechar() {
        this.status = StatusSessao.FECHADA;
        this.dataFechamento = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Pauta getPauta() {
        return pauta;
    }
    
    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }
    
    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }
    
    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
    
    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }
    
    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }
    
    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }
    
    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }
    
    public StatusSessao getStatus() {
        return status;
    }
    
    public void setStatus(StatusSessao status) {
        this.status = status;
    }
    
    public List<Voto> getVotos() {
        return votos;
    }
    
    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }
}
