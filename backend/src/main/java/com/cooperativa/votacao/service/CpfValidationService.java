package com.cooperativa.votacao.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class CpfValidationService {
    
    private final Random random = new Random();
    
    /**
     * Valida se um CPF é válido matematicamente
     */
    public boolean isCpfValido(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }
        
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
        
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }
        
        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }
        
        // Verifica se os dígitos calculados são iguais aos dígitos do CPF
        return Character.getNumericValue(cpf.charAt(9)) == primeiroDigito &&
               Character.getNumericValue(cpf.charAt(10)) == segundoDigito;
    }
    
    /**
     * Simula a validação externa do CPF (Tarefa Bônus 1)
     * Retorna aleatoriamente se o usuário pode votar ou não
     */
    public StatusValidacaoCpf validarCpfParaVotacao(String cpf) {
        if (!isCpfValido(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        
        // Retorna aleatoriamente o status de votação
        return random.nextBoolean() ? StatusValidacaoCpf.ABLE_TO_VOTE : StatusValidacaoCpf.UNABLE_TO_VOTE;
    }
}
