package com.cooperativa.votacao.dto;

import com.cooperativa.votacao.model.StatusSessao;
import java.time.LocalDateTime;

public class SessaoVotacaoResponseDTO {
    
    private Long id;
    private Long pautaId;
    private String pautaTitulo;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private Integer duracaoMinutos;
    private StatusSessao status;
    private boolean aberta;
    
    public SessaoVotacaoResponseDTO() {}
    
    public SessaoVotacaoResponseDTO(Long id, Long pautaId, String pautaTitulo, 
                                   LocalDateTime dataAbertura, LocalDateTime dataFechamento, 
                                   Integer duracaoMinutos, StatusSessao status, boolean aberta) {
        this.id = id;
        this.pautaId = pautaId;
        this.pautaTitulo = pautaTitulo;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.duracaoMinutos = duracaoMinutos;
        this.status = status;
        this.aberta = aberta;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getPautaId() {
        return pautaId;
    }
    
    public void setPautaId(Long pautaId) {
        this.pautaId = pautaId;
    }
    
    public String getPautaTitulo() {
        return pautaTitulo;
    }
    
    public void setPautaTitulo(String pautaTitulo) {
        this.pautaTitulo = pautaTitulo;
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
    
    public boolean isAberta() {
        return aberta;
    }
    
    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }
}
