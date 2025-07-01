package com.cooperativa.votacao.repository;

import com.cooperativa.votacao.model.TipoVoto;
import com.cooperativa.votacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    
    Optional<Voto> findBySessaoVotacaoIdAndCpfAssociado(Long sessaoVotacaoId, String cpfAssociado);
    
    @Query("SELECT COUNT(v) FROM Voto v WHERE v.sessaoVotacao.id = :sessaoId AND v.voto = :tipoVoto")
    Long countBySessaoVotacaoIdAndVoto(@Param("sessaoId") Long sessaoId, @Param("tipoVoto") TipoVoto tipoVoto);
    
    @Query("SELECT COUNT(v) FROM Voto v WHERE v.sessaoVotacao.id = :sessaoId")
    Long countBySessaoVotacaoId(@Param("sessaoId") Long sessaoId);
}
