package com.cooperativa.votacao.dto;

import com.cooperativa.votacao.model.TipoVoto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VotoRequestDTO {
    
    @NotBlank(message = "CPF do associado é obrigatório")
    private String cpfAssociado;
    
    @NotNull(message = "Voto é obrigatório")
    private TipoVoto voto;
    
    public VotoRequestDTO() {}
    
    public VotoRequestDTO(String cpfAssociado, TipoVoto voto) {
        this.cpfAssociado = cpfAssociado;
        this.voto = voto;
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
}
