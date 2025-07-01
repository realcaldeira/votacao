package com.cooperativa.votacao.dto;

import javax.validation.constraints.NotBlank;

public class PautaRequestDTO {
    
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    
    public PautaRequestDTO() {}
    
    public PautaRequestDTO(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
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
}
