package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.ResultadoVotacaoDTO;
import com.cooperativa.votacao.dto.VotoRequestDTO;
import com.cooperativa.votacao.service.VotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votacao")
@CrossOrigin(origins = "*")
@Tag(name = "Votação", description = "Registro de votos e resultados")
public class VotacaoController {
    
    @Autowired
    private VotacaoService votacaoService;
    
    @PostMapping("/sessoes/{sessaoId}/votos")
    @Operation(summary = "Registrar voto", description = "Registra o voto de um associado em uma sessão")
    public ResponseEntity<String> votar(
            @PathVariable Long sessaoId,
            @Valid @RequestBody VotoRequestDTO votoRequestDTO) {
        
        try {
            votacaoService.votar(sessaoId, votoRequestDTO);
            return ResponseEntity.ok("Voto registrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
    @GetMapping("/sessoes/{sessaoId}/resultado")
    @Operation(summary = "Obter resultado da votação", description = "Obtém o resultado atual de uma sessão de votação")
    public ResponseEntity<ResultadoVotacaoDTO> obterResultado(@PathVariable Long sessaoId) {
        try {
            ResultadoVotacaoDTO resultado = votacaoService.obterResultado(sessaoId);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
