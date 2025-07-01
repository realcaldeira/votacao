package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.PautaRequestDTO;
import com.cooperativa.votacao.dto.PautaResponseDTO;
import com.cooperativa.votacao.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pautas")
@CrossOrigin(origins = "*")
@Tag(name = "Pautas", description = "Gerenciamento de pautas para votação")
public class PautaController {
    
    @Autowired
    private PautaService pautaService;
    
    @PostMapping
    @Operation(summary = "Criar nova pauta", description = "Cadastra uma nova pauta para votação")
    public ResponseEntity<PautaResponseDTO> criarPauta(@Valid @RequestBody PautaRequestDTO requestDTO) {
        PautaResponseDTO pauta = pautaService.criarPauta(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pauta);
    }
    
    @GetMapping
    @Operation(summary = "Listar pautas", description = "Lista todas as pautas cadastradas")
    public ResponseEntity<List<PautaResponseDTO>> listarPautas() {
        List<PautaResponseDTO> pautas = pautaService.listarPautas();
        return ResponseEntity.ok(pautas);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pauta por ID", description = "Busca uma pauta específica pelo ID")
    public ResponseEntity<PautaResponseDTO> buscarPautaPorId(@PathVariable Long id) {
        Optional<PautaResponseDTO> pauta = pautaService.buscarPautaPorId(id);
        return pauta.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
}
