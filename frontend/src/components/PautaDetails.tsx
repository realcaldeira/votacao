import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Card, Button, Alert, Badge, Form } from 'react-bootstrap';
import { Pauta, SessaoVotacao, VotoRequest, ResultadoVotacao } from '../types';
import { pautaService, sessaoService, votacaoService } from '../services/api';
import { formatDate, formatCPF, cleanCPF } from '../utils/formatters';

const PautaDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [pauta, setPauta] = useState<Pauta | null>(null);
  const [sessoes, setSessoes] = useState<SessaoVotacao[]>([]);
  const [resultados, setResultados] = useState<{ [key: string]: ResultadoVotacao }>({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [cpfAssociado, setCpfAssociado] = useState('');
  const [votandoSessao, setVotandoSessao] = useState<string | number | null>(null);

  useEffect(() => {
    const carregarDados = async () => {
      if (!id) return;

      try {
        setLoading(true);
        const pautaData = await pautaService.buscarPorId(parseInt(id));
        const sessoesData = await sessaoService.listarPorPauta(parseInt(id));
        
        setPauta(pautaData);
        setSessoes(sessoesData);

        // Carregar resultados para cada sessão
        const resultadosData: { [key: string]: ResultadoVotacao } = {};
        for (const sessao of sessoesData) {
          try {
            const resultado = await votacaoService.obterResultado(sessao.id);
            resultadosData[sessao.id.toString()] = resultado;
          } catch (err) {
            console.log(`Erro ao carregar resultado da sessão ${sessao.id}`);
          }
        }
        setResultados(resultadosData);
        setError(null);
      } catch (err) {
        setError('Erro ao carregar dados da pauta');
      } finally {
        setLoading(false);
      }
    };
    
    if (id) {
      carregarDados();
    }
  }, [id]);

  const handleVotar = async (sessaoId: string | number, tipoVoto: 'SIM' | 'NAO') => {
    if (!cpfAssociado.trim()) {
      alert('Por favor, informe seu CPF');
      return;
    }

    try {
      setVotandoSessao(sessaoId);
      const votoRequest: VotoRequest = {
        cpfAssociado: cleanCPF(cpfAssociado), // Usa a função utilitária
        voto: tipoVoto,
      };
      
      await votacaoService.votar(sessaoId, votoRequest);
      alert('Voto registrado com sucesso!');
      
      // Recarregar resultados
      const resultado = await votacaoService.obterResultado(sessaoId);
      setResultados(prev => ({
        ...prev,
        [sessaoId.toString()]: resultado,
      }));
    } catch (err: any) {
      if (err.response?.status === 409) {
        alert('Você já votou nesta sessão ou a sessão está fechada');
      } else if (err.response?.status === 400) {
        alert('CPF inválido ou não habilitado para votação');
      } else {
        alert('Erro ao registrar voto');
      }
    } finally {
      setVotandoSessao(null);
    }
  };

  const handleCpfChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const formatted = formatCPF(e.target.value);
    setCpfAssociado(formatted);
  };

  if (loading) {
    return (
      <div className="text-center py-5">
        <div className="custom-loading">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Carregando...</span>
          </div>
          <p className="mt-3 text-muted">Carregando detalhes da pauta...</p>
        </div>
      </div>
    );
  }

  if (!pauta) {
    return (
      <Alert variant="danger" className="custom-alert">
        <i className="bi bi-exclamation-triangle me-2"></i>
        Pauta não encontrada
      </Alert>
    );
  }

  return (
    <div className="container-fluid">
      {/* Header da Pauta */}
      <Card className="custom-card mb-4 shadow-sm">
        <Card.Body className="p-4">
          <div className="d-flex align-items-start mb-3">
            <div className="custom-icon-badge me-3">
              <i className="bi bi-file-earmark-text"></i>
            </div>
            <div className="flex-grow-1">
              <Card.Title className="h3 mb-2 text-primary">{pauta.titulo}</Card.Title>
              <Card.Text className="lead text-muted mb-3">{pauta.descricao}</Card.Text>
              <Card.Text className="small text-secondary">
                <i className="bi bi-calendar3 me-2"></i>
                Criada em: {formatDate(pauta.dataCriacao)}
              </Card.Text>
            </div>
          </div>
        </Card.Body>
      </Card>

      {error && (
        <Alert variant="danger" className="custom-alert mb-4">
          <i className="bi bi-exclamation-triangle me-2"></i>
          {error}
        </Alert>
      )}

      {/* Formulário de CPF */}
      <Card className="custom-card mb-4 shadow-sm">
        <Card.Body className="p-4">
          <h5 className="text-primary mb-3">
            <i className="bi bi-person-badge me-2"></i>
            Identificação do Associado
          </h5>
          <Form.Group>
            <Form.Label className="fw-bold text-secondary">
              <i className="bi bi-card-text me-2"></i>
              Seu CPF (necessário para votar)
            </Form.Label>
            <div className="input-group">
              <span className="input-group-text bg-light">
                <i className="bi bi-person"></i>
              </span>
              <Form.Control
                type="text"
                value={cpfAssociado}
                onChange={handleCpfChange}
                placeholder="000.000.000-00"
                maxLength={14}
                className="custom-input"
              />
            </div>
            <Form.Text className="text-muted small mt-2">
              <i className="bi bi-info-circle me-1"></i>
              Informe seu CPF para poder participar das votações
            </Form.Text>
          </Form.Group>
        </Card.Body>
      </Card>

      {/* Sessões de Votação */}
      <div className="d-flex align-items-center mb-4">
        <h3 className="text-primary mb-0">
          <i className="bi bi-stopwatch me-2"></i>
          Sessões de Votação
        </h3>
        <span className="badge bg-secondary ms-3">{sessoes.length} sessão(es)</span>
      </div>
      
      {sessoes.length === 0 ? (
        <div className="custom-empty-state text-center py-5">
          <div className="custom-icon-large text-muted mb-3">
            <i className="bi bi-hourglass-split"></i>
          </div>
          <h5 className="text-muted mb-2">Nenhuma sessão aberta</h5>
          <p className="text-secondary">
            Nenhuma sessão de votação foi aberta para esta pauta ainda.
          </p>
        </div>
      ) : (
        <div className="sessoes-grid">
          {sessoes.map((sessao) => {
            const resultado = resultados[sessao.id.toString()];
            const isVotando = votandoSessao === sessao.id;
            
            return (
              <Card key={sessao.id} className={`custom-card h-100 ${sessao.aberta ? 'border-success' : 'border-secondary'}`}>
                <Card.Body className="p-4">
                    <div className="d-flex justify-content-between align-items-center mb-3">
                      <h5 className="mb-0">
                        <i className="bi bi-chat-square-dots me-2"></i>
                        Sessão #{sessao.id}
                      </h5>
                      <Badge 
                        bg={sessao.aberta ? 'success' : 'secondary'}
                        className="custom-badge"
                      >
                        <i className={`bi ${sessao.aberta ? 'bi-unlock' : 'bi-lock'} me-1`}></i>
                        {sessao.aberta ? 'ABERTA' : 'FECHADA'}
                      </Badge>
                    </div>
                    
                    <div className="custom-info-list mb-3">
                      <div className="custom-info-item">
                        <i className="bi bi-play-circle text-success"></i>
                        <span className="label">Abertura:</span>
                        <span className="value">{formatDate(sessao.dataAbertura)}</span>
                      </div>
                      <div className="custom-info-item">
                        <i className="bi bi-stop-circle text-danger"></i>
                        <span className="label">Fechamento:</span>
                        <span className="value">{formatDate(sessao.dataFechamento)}</span>
                      </div>
                      <div className="custom-info-item">
                        <i className="bi bi-clock text-primary"></i>
                        <span className="label">Duração:</span>
                        <span className="value">{sessao.duracaoMinutos} minuto(s)</span>
                      </div>
                    </div>

                    {sessao.aberta && (
                      <div className="custom-voting-section mb-3">
                        <h6 className="text-primary mb-3">
                          <i className="bi bi-hand-thumbs-up me-2"></i>
                          Registrar Voto
                        </h6>
                        <div className="d-grid gap-2">
                          <Button
                            variant="success"
                            onClick={() => handleVotar(sessao.id, 'SIM')}
                            disabled={isVotando || !cpfAssociado.trim()}
                            className="custom-btn-success"
                          >
                            {isVotando ? (
                              <>
                                <span className="spinner-border spinner-border-sm me-2"></span>
                                Votando...
                              </>
                            ) : (
                              <>
                                <i className="bi bi-check-circle me-2"></i>
                                Votar SIM
                              </>
                            )}
                          </Button>
                          <Button
                            variant="danger"
                            onClick={() => handleVotar(sessao.id, 'NAO')}
                            disabled={isVotando || !cpfAssociado.trim()}
                            className="custom-btn-danger"
                          >
                            {isVotando ? (
                              <>
                                <span className="spinner-border spinner-border-sm me-2"></span>
                                Votando...
                              </>
                            ) : (
                              <>
                                <i className="bi bi-x-circle me-2"></i>
                                Votar NÃO
                              </>
                            )}
                          </Button>
                        </div>
                        {!cpfAssociado.trim() && (
                          <small className="text-warning d-block mt-2">
                            <i className="bi bi-exclamation-triangle me-1"></i>
                            Informe seu CPF acima para votar
                          </small>
                        )}
                      </div>
                    )}

                    {resultado && (
                      <div className="custom-card bg-light p-3">
                        <h6 className="text-primary mb-3">
                          <i className="bi bi-bar-chart me-2"></i>
                          Resultado da Votação
                        </h6>
                        
                        <div className="custom-result-grid">
                          <div className="custom-result-item">
                            <span className="result-icon">
                              <i className="bi bi-people text-primary"></i>
                            </span>
                            <span className="result-label">Total:</span>
                            <span className="result-value">{resultado.totalVotos}</span>
                          </div>
                          
                          <div className="custom-result-item">
                            <span className="result-icon">
                              <i className="bi bi-hand-thumbs-up text-success"></i>
                            </span>
                            <span className="result-label">SIM:</span>
                            <span className="result-value">{resultado.votosSim}</span>
                          </div>
                          
                          <div className="custom-result-item">
                            <span className="result-icon">
                              <i className="bi bi-hand-thumbs-down text-danger"></i>
                            </span>
                            <span className="result-label">NÃO:</span>
                            <span className="result-value">{resultado.votosNao}</span>
                          </div>
                        </div>

                        <div className="text-center mt-3">
                          <Badge 
                            bg={
                              resultado.resultado === 'APROVADA' ? 'success' :
                              resultado.resultado === 'REJEITADA' ? 'danger' : 'warning'
                            }
                            className="custom-badge-large"
                          >
                            <i className={`bi ${
                              resultado.resultado === 'APROVADA' ? 'bi-check-circle' :
                              resultado.resultado === 'REJEITADA' ? 'bi-x-circle' : 'bi-hourglass-split'
                            } me-2`}></i>
                            {resultado.resultado}
                          </Badge>
                        </div>
                      </div>
                    )}
                  </Card.Body>
                </Card>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default PautaDetails;
