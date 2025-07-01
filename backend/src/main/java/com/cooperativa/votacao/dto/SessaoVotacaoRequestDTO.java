package com.cooperativa.votacao.dto;

public class SessaoVotacaoRequestDTO {
    
    private Integer duracaoMinutos;
    
    public SessaoVotacaoRequestDTO() {}
    
    public SessaoVotacaoRequestDTO(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }
    
    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }
    
    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }
}
