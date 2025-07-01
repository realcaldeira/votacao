// Funções utilitárias para formatação de dados

/**
 * Formata uma data para exibição em português brasileiro
 * @param dateValue - Valor da data (string, Date ou timestamp)
 * @returns String formatada ou 'Data inválida' se a data for inválida
 */
export const formatDate = (dateValue: any): string => {
  if (!dateValue) {
    return 'Data não informada';
  }

  let date: Date;

  // Se já é um objeto Date
  if (dateValue instanceof Date) {
    date = dateValue;
  }
  // Se é uma string
  else if (typeof dateValue === 'string') {
    // Tenta fazer o parse da string
    date = new Date(dateValue);
  }
  // Se é um número (timestamp)
  else if (typeof dateValue === 'number') {
    date = new Date(dateValue);
  }
  // Outros casos
  else {
    return 'Data inválida';
  }

  // Verifica se a data é válida
  if (isNaN(date.getTime())) {
    return 'Data inválida';
  }

  // Formata a data
  return date.toLocaleString('pt-BR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  });
};

/**
 * Formata uma data para exibição apenas com data (sem hora)
 * @param dateValue - Valor da data
 * @returns String formatada ou 'Data inválida' se a data for inválida
 */
export const formatDateOnly = (dateValue: any): string => {
  if (!dateValue) {
    return 'Data não informada';
  }

  let date: Date;

  if (dateValue instanceof Date) {
    date = dateValue;
  } else if (typeof dateValue === 'string') {
    date = new Date(dateValue);
  } else if (typeof dateValue === 'number') {
    date = new Date(dateValue);
  } else {
    return 'Data inválida';
  }

  if (isNaN(date.getTime())) {
    return 'Data inválida';
  }

  return date.toLocaleDateString('pt-BR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  });
};

/**
 * Formata CPF adicionando máscara
 * @param cpf - CPF sem máscara
 * @returns CPF formatado (000.000.000-00)
 */
export const formatCPF = (cpf: string): string => {
  if (!cpf) return '';
  
  const numbers = cpf.replace(/\D/g, '');
  
  if (numbers.length !== 11) return cpf;
  
  return numbers.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
};

/**
 * Remove máscara do CPF
 * @param cpf - CPF com máscara
 * @returns CPF apenas com números
 */
export const cleanCPF = (cpf: string): string => {
  return cpf.replace(/\D/g, '');
};

/**
 * Valida se uma data está no formato correto
 * @param dateValue - Valor a ser validado
 * @returns true se a data é válida
 */
export const isValidDate = (dateValue: any): boolean => {
  if (!dateValue) return false;
  
  const date = new Date(dateValue);
  return !isNaN(date.getTime());
};

/**
 * Converte uma data para ISO string se necessário
 * @param dateValue - Valor da data
 * @returns ISO string ou valor original
 */
export const ensureDateFormat = (dateValue: any): string => {
  if (!dateValue) return new Date().toISOString();
  
  if (typeof dateValue === 'string' && dateValue.includes('T')) {
    return dateValue; // Já está em formato ISO
  }
  
  const date = new Date(dateValue);
  if (isNaN(date.getTime())) {
    return new Date().toISOString(); // Fallback para data atual
  }
  
  return date.toISOString();
};
