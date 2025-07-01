import axios from 'axios';
import { 
  Pauta, 
  PautaRequest, 
  SessaoVotacao, 
  SessaoVotacaoRequest, 
  VotoRequest, 
  ResultadoVotacao 
} from '../types';

const API_BASE_URL = process.env.REACT_APP_API_URL || '';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para tratar erros
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

export const pautaService = {
  async listar(): Promise<Pauta[]> {
    const response = await api.get('/api/v1/pautas');
    return response.data;
  },

  async buscarPorId(id: string | number): Promise<Pauta> {
    const response = await api.get(`/api/v1/pautas/${id}`);
    return response.data;
  },

  async criar(pauta: PautaRequest): Promise<Pauta> {
    const response = await api.post('/api/v1/pautas', pauta);
    return response.data;
  },
};

export const sessaoService = {
  async abrirSessao(pautaId: string | number, sessao: SessaoVotacaoRequest): Promise<SessaoVotacao> {
    try {
      const response = await api.post(`/api/v1/sessoes/pautas/${pautaId}`, sessao);
      return response.data;
    } catch (error) {
      console.error('Erro ao abrir sessão:', error);
      throw error;
    }
  },

  async listarPorPauta(pautaId: string | number): Promise<SessaoVotacao[]> {
    const response = await api.get(`/api/v1/sessoes/pautas/${pautaId}`);
    return response.data;
  },

  async buscarPorId(id: string | number): Promise<SessaoVotacao> {
    const response = await api.get(`/api/v1/sessoes/${id}`);
    return response.data;
  },
};

export const votacaoService = {
  async votar(sessaoId: string | number, voto: VotoRequest): Promise<string> {
    const response = await api.post(`/api/v1/votacao/sessoes/${sessaoId}/votos`, voto);
    return response.data;
  },

  async obterResultado(sessaoId: string | number): Promise<ResultadoVotacao> {
    const response = await api.get(`/api/v1/votacao/sessoes/${sessaoId}/resultado`);
    return response.data;
  },
};
