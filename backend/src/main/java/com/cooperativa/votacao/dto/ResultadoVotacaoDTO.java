package com.cooperativa.votacao.dto;

public class ResultadoVotacaoDTO {
    
    private Long sessaoVotacaoId;
    private Long pautaId;
    private String pautaTitulo;
    private Long totalVotos;
    private Long votosSim;
    private Long votosNao;
    private String resultado;
    private boolean sessaoFechada;
    
    public ResultadoVotacaoDTO() {}
    
    public ResultadoVotacaoDTO(Long sessaoVotacaoId, Long pautaId, String pautaTitulo, 
                              Long totalVotos, Long votosSim, Long votosNao, 
                              String resultado, boolean sessaoFechada) {
        this.sessaoVotacaoId = sessaoVotacaoId;
        this.pautaId = pautaId;
        this.pautaTitulo = pautaTitulo;
        this.totalVotos = totalVotos;
        this.votosSim = votosSim;
        this.votosNao = votosNao;
        this.resultado = resultado;
        this.sessaoFechada = sessaoFechada;
    }
    
    public Long getSessaoVotacaoId() {
        return sessaoVotacaoId;
    }
    
    public void setSessaoVotacaoId(Long sessaoVotacaoId) {
        this.sessaoVotacaoId = sessaoVotacaoId;
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
    
    public Long getTotalVotos() {
        return totalVotos;
    }
    
    public void setTotalVotos(Long totalVotos) {
        this.totalVotos = totalVotos;
    }
    
    public Long getVotosSim() {
        return votosSim;
    }
    
    public void setVotosSim(Long votosSim) {
        this.votosSim = votosSim;
    }
    
    public Long getVotosNao() {
        return votosNao;
    }
    
    public void setVotosNao(Long votosNao) {
        this.votosNao = votosNao;
    }
    
    public String getResultado() {
        return resultado;
    }
    
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public boolean isSessaoFechada() {
        return sessaoFechada;
    }
    
    public void setSessaoFechada(boolean sessaoFechada) {
        this.sessaoFechada = sessaoFechada;
    }
}
