package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.SessaoVotacaoRequestDTO;
import com.cooperativa.votacao.dto.SessaoVotacaoResponseDTO;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.SessaoVotacao;
import com.cooperativa.votacao.model.StatusSessao;
import com.cooperativa.votacao.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessaoVotacaoService {
    
    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;
    
    @Autowired
    private PautaService pautaService;
    
    public SessaoVotacaoResponseDTO abrirSessaoVotacao(Long pautaId, SessaoVotacaoRequestDTO requestDTO) {
        Optional<Pauta> pautaOpt = pautaService.buscarPautaEntityPorId(pautaId);
        if (pautaOpt.isEmpty()) {
            throw new IllegalArgumentException("Pauta não encontrada");
        }
        
        // Verifica se já existe uma sessão aberta para esta pauta
        Optional<SessaoVotacao> sessaoAberta = sessaoVotacaoRepository
                .findByPautaIdAndStatus(pautaId, StatusSessao.ABERTA);
        
        if (sessaoAberta.isPresent() && sessaoAberta.get().isAberta()) {
            throw new IllegalStateException("Já existe uma sessão de votação aberta para esta pauta");
        }
        
        Pauta pauta = pautaOpt.get();
        Integer duracao = requestDTO.getDuracaoMinutos() != null ? requestDTO.getDuracaoMinutos() : 1;
        
        SessaoVotacao sessao = new SessaoVotacao(pauta, duracao);
        sessao = sessaoVotacaoRepository.save(sessao);
        
        return convertToResponseDTO(sessao);
    }
    
    public List<SessaoVotacaoResponseDTO> listarSessoesPorPauta(Long pautaId) {
        return sessaoVotacaoRepository.findByPautaId(pautaId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<SessaoVotacaoResponseDTO> buscarSessaoPorId(Long id) {
        return sessaoVotacaoRepository.findById(id)
                .map(this::convertToResponseDTO);
    }
    
    public Optional<SessaoVotacao> buscarSessaoEntityPorId(Long id) {
        return sessaoVotacaoRepository.findById(id);
    }
    
    public void fecharSessoesExpiradas() {
        List<SessaoVotacao> sessoes = sessaoVotacaoRepository.findAll();
        LocalDateTime agora = LocalDateTime.now();
        
        for (SessaoVotacao sessao : sessoes) {
            if (sessao.getStatus() == StatusSessao.ABERTA && 
                agora.isAfter(sessao.getDataFechamento())) {
                sessao.fechar();
                sessaoVotacaoRepository.save(sessao);
            }
        }
    }
    
    private SessaoVotacaoResponseDTO convertToResponseDTO(SessaoVotacao sessao) {
        return new SessaoVotacaoResponseDTO(
                sessao.getId(),
                sessao.getPauta().getId(),
                sessao.getPauta().getTitulo(),
                sessao.getDataAbertura(),
                sessao.getDataFechamento(),
                sessao.getDuracaoMinutos(),
                sessao.getStatus(),
                sessao.isAberta()
        );
    }
}
