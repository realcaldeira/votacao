export interface Pauta {
  id: string | number;
  titulo: string;
  descricao: string;
  dataCriacao: string;
}

export interface PautaRequest {
  titulo: string;
  descricao: string;
}

export interface SessaoVotacao {
  id: string | number;
  pautaId: string | number;
  pautaTitulo: string;
  dataAbertura: string;
  dataFechamento: string;
  duracaoMinutos: number;
  status: 'ABERTA' | 'FECHADA';
  aberta: boolean;
}

export interface SessaoVotacaoRequest {
  duracaoMinutos?: number;
}

export interface VotoRequest {
  cpfAssociado: string;
  voto: 'SIM' | 'NAO';
}

export interface ResultadoVotacao {
  sessaoVotacaoId: string | number;
  pautaId: string | number;
  pautaTitulo: string;
  totalVotos: number;
  votosSim: number;
  votosNao: number;
  resultado: string;
  sessaoFechada: boolean;
}
