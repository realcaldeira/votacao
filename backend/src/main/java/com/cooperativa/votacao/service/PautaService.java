package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.PautaRequestDTO;
import com.cooperativa.votacao.dto.PautaResponseDTO;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PautaService {
    
    @Autowired
    private PautaRepository pautaRepository;
    
    public PautaResponseDTO criarPauta(PautaRequestDTO requestDTO) {
        Pauta pauta = new Pauta(requestDTO.getTitulo(), requestDTO.getDescricao());
        pauta = pautaRepository.save(pauta);
        return convertToResponseDTO(pauta);
    }
    
    public List<PautaResponseDTO> listarPautas() {
        return pautaRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<PautaResponseDTO> buscarPautaPorId(Long id) {
        return pautaRepository.findById(id)
                .map(this::convertToResponseDTO);
    }
    
    public Optional<Pauta> buscarPautaEntityPorId(Long id) {
        return pautaRepository.findById(id);
    }
    
    private PautaResponseDTO convertToResponseDTO(Pauta pauta) {
        return new PautaResponseDTO(
                pauta.getId(),
                pauta.getTitulo(),
                pauta.getDescricao(),
                pauta.getDataCriacao()
        );
    }
}
