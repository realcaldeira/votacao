package com.cooperativa.votacao.config;

import com.cooperativa.votacao.service.SessaoVotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    
    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;
    
    /**
     * Executa a cada 30 segundos para fechar sessões expiradas
     */
    @Scheduled(fixedRate = 30000)
    public void fecharSessoesExpiradas() {
        sessaoVotacaoService.fecharSessoesExpiradas();
    }
}
