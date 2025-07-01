package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.ResultadoVotacaoDTO;
import com.cooperativa.votacao.dto.VotoRequestDTO;
import com.cooperativa.votacao.model.SessaoVotacao;
import com.cooperativa.votacao.model.TipoVoto;
import com.cooperativa.votacao.model.Voto;
import com.cooperativa.votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotacaoService {
    
    @Autowired
    private VotoRepository votoRepository;
    
    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;
    
    @Autowired
    private CpfValidationService cpfValidationService;
    
    public void votar(Long sessaoId, VotoRequestDTO votoRequestDTO) {
        Optional<SessaoVotacao> sessaoOpt = sessaoVotacaoService.buscarSessaoEntityPorId(sessaoId);
        
        if (sessaoOpt.isEmpty()) {
            throw new IllegalArgumentException("Sessão de votação não encontrada");
        }
        
        SessaoVotacao sessao = sessaoOpt.get();
        
        if (!sessao.isAberta()) {
            throw new IllegalStateException("Sessão de votação não está aberta");
        }
        
        // Valida CPF (Tarefa Bônus 1)
        try {
            StatusValidacaoCpf statusCpf = cpfValidationService.validarCpfParaVotacao(votoRequestDTO.getCpfAssociado());
            if (statusCpf == StatusValidacaoCpf.UNABLE_TO_VOTE) {
                throw new IllegalStateException("CPF não habilitado para votação");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("CPF inválido");
        }
        
        // Verifica se o associado já votou nesta sessão
        Optional<Voto> votoExistente = votoRepository.findBySessaoVotacaoIdAndCpfAssociado(
                sessaoId, votoRequestDTO.getCpfAssociado());
        
        if (votoExistente.isPresent()) {
            throw new IllegalStateException("Associado já votou nesta sessão");
        }
        
        // Registra o voto
        Voto voto = new Voto(sessao, votoRequestDTO.getCpfAssociado(), votoRequestDTO.getVoto());
        votoRepository.save(voto);
    }
    
    public ResultadoVotacaoDTO obterResultado(Long sessaoId) {
        Optional<SessaoVotacao> sessaoOpt = sessaoVotacaoService.buscarSessaoEntityPorId(sessaoId);
        
        if (sessaoOpt.isEmpty()) {
            throw new IllegalArgumentException("Sessão de votação não encontrada");
        }
        
        SessaoVotacao sessao = sessaoOpt.get();
        
        Long totalVotos = votoRepository.countBySessaoVotacaoId(sessaoId);
        Long votosSim = votoRepository.countBySessaoVotacaoIdAndVoto(sessaoId, TipoVoto.SIM);
        Long votosNao = votoRepository.countBySessaoVotacaoIdAndVoto(sessaoId, TipoVoto.NAO);
        
        String resultado;
        if (votosSim > votosNao) {
            resultado = "APROVADA";
        } else if (votosNao > votosSim) {
            resultado = "REJEITADA";
        } else {
            resultado = "EMPATE";
        }
        
        return new ResultadoVotacaoDTO(
                sessaoId,
                sessao.getPauta().getId(),
                sessao.getPauta().getTitulo(),
                totalVotos,
                votosSim,
                votosNao,
                resultado,
                !sessao.isAberta()
        );
    }
}
