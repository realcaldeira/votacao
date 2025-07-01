import React, { useState, useEffect } from 'react';
import { Card, Button, Alert, Badge } from 'react-bootstrap';
import { Pauta } from '../types';
import { pautaService } from '../services/api';
import { formatDate } from '../utils/formatters';
import CriarPautaModal from './CriarPautaModal';
import SessaoVotacaoModal from './SessaoVotacaoModal';
import { useNavigate } from 'react-router-dom';

const PautaList: React.FC = () => {
  const [pautas, setPautas] = useState<Pauta[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showCriarModal, setShowCriarModal] = useState(false);
  const [showSessaoModal, setShowSessaoModal] = useState(false);
  const [selectedPauta, setSelectedPauta] = useState<Pauta | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    carregarPautas();
  }, []);

  const carregarPautas = async () => {
    try {
      setLoading(true);
      const pautasData = await pautaService.listar();
      setPautas(pautasData);
      setError(null);
    } catch (err) {
      setError('Erro ao carregar pautas');
    } finally {
      setLoading(false);
    }
  };

  const handlePautaCriada = () => {
    carregarPautas();
    setShowCriarModal(false);
  };

  const handleAbrirSessao = (pauta: Pauta) => {
    setSelectedPauta(pauta);
    setShowSessaoModal(true);
  };

  const handleSessaoAberta = () => {
    setShowSessaoModal(false);
    setSelectedPauta(null);
  };

  if (loading) {
    return (
      <div className="custom-loading fade-in">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Carregando...</span>
        </div>
        <p className="mt-3 text-muted">Carregando pautas...</p>
      </div>
    );
  }

  return (
    <div className="fade-in">
      {/* Header da Página */}
      <div className="page-header">
        <h1 className="page-title">
          📋 Pautas para Votação
        </h1>
        <p className="page-subtitle">Gerencie e vote nas pautas da cooperativa</p>
        
        <div className="btn-group-centered mt-4">
          <Button 
            onClick={() => setShowCriarModal(true)}
            className="custom-btn-primary"
            size="lg"
          >
            <i className="bi bi-plus-circle me-2"></i>
            Nova Pauta
          </Button>
        </div>
      </div>

      {error && (
        <Alert variant="danger" className="custom-alert">
          <i className="bi bi-exclamation-triangle me-2"></i>
          <strong>Erro:</strong> {error}
        </Alert>
      )}

      {pautas.length === 0 ? (
        <div className="custom-empty-state">
          <div className="custom-icon-large text-muted mb-3">
            <i className="bi bi-file-earmark-text"></i>
          </div>
          <h4 className="text-muted mb-3">Nenhuma pauta encontrada</h4>
          <p className="text-muted mb-4">Comece criando uma nova pauta para votação</p>
          <Button 
            onClick={() => setShowCriarModal(true)}
            className="custom-btn-primary"
          >
            <i className="bi bi-plus-circle me-2"></i>
            Criar Primeira Pauta
          </Button>
        </div>
      ) : (
        <div className="pautas-grid">
          {pautas.map((pauta, index) => (
            <Card key={pauta.id} className={`custom-card slide-in`} style={{ animationDelay: `${index * 0.1}s` }}>
              <div className="custom-card-header">
                <div className="d-flex justify-content-between align-items-center">
                  <h5 className="mb-0 fw-bold">{pauta.titulo}</h5>
                  <Badge bg="light" text="dark" className="custom-badge">
                    #{pauta.id}
                  </Badge>
                </div>
              </div>
              <div className="custom-card-body">
                <p className="custom-text mb-3" style={{ minHeight: '60px' }}>
                  {pauta.descricao}
                </p>
                <p className="mb-4">
                  <small className="text-muted d-flex align-items-center justify-content-center">
                    <i className="bi bi-calendar3 me-2"></i>
                    Criada em: {formatDate(pauta.dataCriacao)}
                  </small>
                </p>
                <div className="btn-group-centered">
                  <Button 
                    onClick={() => handleAbrirSessao(pauta)}
                    className="custom-btn-success"
                  >
                    <i className="bi bi-stopwatch me-2"></i>
                    Abrir Sessão
                  </Button>
                  <Button 
                    onClick={() => navigate(`/pauta/${pauta.id}`)}
                    className="custom-btn-outline"
                  >
                    <i className="bi bi-eye me-2"></i>
                    Ver Detalhes
                  </Button>
                </div>
              </div>
            </Card>
          ))}
        </div>
      )}

      <CriarPautaModal
        show={showCriarModal}
        onHide={() => setShowCriarModal(false)}
        onPautaCriada={handlePautaCriada}
      />

      {selectedPauta && (
        <SessaoVotacaoModal
          show={showSessaoModal}
          onHide={() => setShowSessaoModal(false)}
          pauta={selectedPauta}
          onSessaoAberta={handleSessaoAberta}
        />
      )}
    </div>
  );
};

export default PautaList;
