import React, { useState } from 'react';
import { Modal, Form, Button, Alert } from 'react-bootstrap';
import { Pauta, SessaoVotacaoRequest } from '../types';
import { sessaoService } from '../services/api';

interface SessaoVotacaoModalProps {
  show: boolean;
  onHide: () => void;
  pauta: Pauta;
  onSessaoAberta: () => void;
}

const SessaoVotacaoModal: React.FC<SessaoVotacaoModalProps> = ({
  show,
  onHide,
  pauta,
  onSessaoAberta,
}) => {
  const [duracaoMinutos, setDuracaoMinutos] = useState<number>(1);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (duracaoMinutos < 1) {
      setError('Duração deve ser de pelo menos 1 minuto');
      return;
    }

    try {
      setLoading(true);
      setError(null);
      const sessaoRequest: SessaoVotacaoRequest = { duracaoMinutos };
      await sessaoService.abrirSessao(pauta.id, sessaoRequest);
      onSessaoAberta();
    } catch (err: any) {
      if (err.response?.status === 409) {
        setError('Já existe uma sessão aberta para esta pauta');
      } else {
        setError('Erro ao abrir sessão de votação');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    setDuracaoMinutos(1);
    setError(null);
    onHide();
  };

  return (
    <Modal show={show} onHide={handleClose} size="lg" className="custom-modal">
      <Modal.Header closeButton className="custom-modal-header">
        <Modal.Title className="d-flex align-items-center">
          <i className="bi bi-stopwatch me-2 text-primary"></i>
          Abrir Sessão de Votação
        </Modal.Title>
      </Modal.Header>
      <Form onSubmit={handleSubmit}>
        <Modal.Body className="p-4">
          {error && (
            <Alert variant="danger" className="custom-alert">
              <i className="bi bi-exclamation-triangle me-2"></i>
              {error}
            </Alert>
          )}
          
          <div className="custom-card p-3 mb-4">
            <h6 className="text-primary mb-3">
              <i className="bi bi-file-text me-2"></i>
              Informações da Pauta
            </h6>
            <div className="mb-3">
              <label className="form-label fw-bold text-secondary">Título:</label>
              <p className="custom-text">{pauta.titulo}</p>
            </div>
            
            <div className="mb-0">
              <label className="form-label fw-bold text-secondary">Descrição:</label>
              <p className="custom-text text-muted">{pauta.descricao}</p>
            </div>
          </div>

          <Form.Group className="mb-3">
            <Form.Label className="fw-bold text-secondary">
              <i className="bi bi-clock me-2"></i>
              Duração da Sessão
            </Form.Label>
            <div className="input-group">
              <Form.Control
                type="number"
                min="1"
                max="1440"
                value={duracaoMinutos}
                onChange={(e) => setDuracaoMinutos(parseInt(e.target.value) || 1)}
                placeholder="1"
                className="custom-input"
              />
              <span className="input-group-text bg-light">
                <i className="bi bi-clock text-primary"></i>
              </span>
            </div>
            <Form.Text className="text-muted small mt-2 d-block">
              <i className="bi bi-info-circle me-1"></i>
              Tempo em minutos (mínimo 1, máximo 1440)
            </Form.Text>
          </Form.Group>

          <div className="custom-card bg-light p-3">
            <h6 className="text-warning mb-2">
              <i className="bi bi-exclamation-circle me-2"></i>
              Atenção
            </h6>
            <p className="mb-0 small text-muted">
              Uma vez aberta, a sessão de votação ficará disponível pelo tempo especificado. 
              Após o término, não será possível receber novos votos.
            </p>
          </div>
        </Modal.Body>
        <Modal.Footer className="custom-modal-footer">
          <Button 
            variant="outline-secondary" 
            onClick={handleClose}
            className="custom-btn-outline me-2"
            disabled={loading}
          >
            <i className="bi bi-x-circle me-2"></i>
            Cancelar
          </Button>
          <Button 
            variant="success" 
            type="submit" 
            disabled={loading}
            className="custom-btn-success"
          >
            {loading ? (
              <>
                <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                Abrindo Sessão...
              </>
            ) : (
              <>
                <i className="bi bi-play-circle me-2"></i>
                Abrir Sessão
              </>
            )}
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

export default SessaoVotacaoModal;
