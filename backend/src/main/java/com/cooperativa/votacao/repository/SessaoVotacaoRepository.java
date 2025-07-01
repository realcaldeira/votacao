package com.cooperativa.votacao.repository;

import com.cooperativa.votacao.model.SessaoVotacao;
import com.cooperativa.votacao.model.StatusSessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {
    
    List<SessaoVotacao> findByPautaId(Long pautaId);
    
    @Query("SELECT s FROM SessaoVotacao s WHERE s.pauta.id = :pautaId AND s.status = :status")
    Optional<SessaoVotacao> findByPautaIdAndStatus(@Param("pautaId") Long pautaId, @Param("status") StatusSessao status);
}
