package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.SessaoVotacaoRequestDTO;
import com.cooperativa.votacao.dto.SessaoVotacaoResponseDTO;
import com.cooperativa.votacao.service.SessaoVotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sessoes")
@CrossOrigin(origins = "*")
@Tag(name = "Sessões de Votação", description = "Gerenciamento de sessões de votação")
public class SessaoVotacaoController {
    
    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;
    
    @PostMapping("/pautas/{pautaId}")
    @Operation(summary = "Abrir sessão de votação", description = "Abre uma nova sessão de votação para uma pauta")
    public ResponseEntity<SessaoVotacaoResponseDTO> abrirSessaoVotacao(
            @PathVariable Long pautaId,
            @RequestBody(required = false) SessaoVotacaoRequestDTO requestDTO) {
        
        if (requestDTO == null) {
            requestDTO = new SessaoVotacaoRequestDTO();
        }
        
        try {
            SessaoVotacaoResponseDTO sessao = sessaoVotacaoService.abrirSessaoVotacao(pautaId, requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(sessao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
    @GetMapping("/pautas/{pautaId}")
    @Operation(summary = "Listar sessões por pauta", description = "Lista todas as sessões de uma pauta específica")
    public ResponseEntity<List<SessaoVotacaoResponseDTO>> listarSessoesPorPauta(@PathVariable Long pautaId) {
        List<SessaoVotacaoResponseDTO> sessoes = sessaoVotacaoService.listarSessoesPorPauta(pautaId);
        return ResponseEntity.ok(sessoes);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar sessão por ID", description = "Busca uma sessão específica pelo ID")
    public ResponseEntity<SessaoVotacaoResponseDTO> buscarSessaoPorId(@PathVariable Long id) {
        Optional<SessaoVotacaoResponseDTO> sessao = sessaoVotacaoService.buscarSessaoPorId(id);
        return sessao.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
}
