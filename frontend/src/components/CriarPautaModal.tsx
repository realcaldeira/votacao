import React, { useState } from 'react';
import { Modal, Button, Form, Alert } from 'react-bootstrap';
import { PautaRequest } from '../types';
import { pautaService } from '../services/api';

interface CriarPautaModalProps {
  show: boolean;
  onHide: () => void;
  onPautaCriada: () => void;
}

const CriarPautaModal: React.FC<CriarPautaModalProps> = ({ show, onHide, onPautaCriada }) => {
  const [formData, setFormData] = useState<PautaRequest>({
    titulo: '',
    descricao: '',
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prev: PautaRequest) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.titulo.trim() || !formData.descricao.trim()) {
      setError('Título e descrição são obrigatórios');
      return;
    }

    try {
      setLoading(true);
      setError(null);
      await pautaService.criar(formData);
      setFormData({ titulo: '', descricao: '' });
      onPautaCriada();
    } catch (err) {
      setError('Erro ao criar pauta');
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    setFormData({ titulo: '', descricao: '' });
    setError(null);
    onHide();
  };

  return (
    <Modal show={show} onHide={handleClose} size="lg" centered>
      <div className="modal-content-custom">
        <Modal.Header closeButton className="modal-header-custom border-0">
          <Modal.Title className="modal-title-custom">
            <span className="me-2">📝</span>
            Nova Pauta para Votação
          </Modal.Title>
        </Modal.Header>
        <Modal.Body className="modal-body-custom">
          {error && (
            <Alert variant="danger" className="alert-custom alert-danger-custom">
              <strong>⚠️ Erro:</strong> {error}
            </Alert>
          )}
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-4">
              <Form.Label className="form-label-custom">
                <span className="me-2">🏷️</span>
                Título da Pauta
              </Form.Label>
              <Form.Control
                type="text"
                name="titulo"
                value={formData.titulo}
                onChange={handleChange}
                placeholder="Ex: Aprovação do Orçamento 2025"
                className="form-control-custom"
                required
              />
              <Form.Text className="text-muted">
                Escolha um título claro e objetivo para a pauta
              </Form.Text>
            </Form.Group>
            <Form.Group className="mb-4">
              <Form.Label className="form-label-custom">
                <span className="me-2">📄</span>
                Descrição Detalhada
              </Form.Label>
              <Form.Control
                as="textarea"
                rows={5}
                name="descricao"
                value={formData.descricao}
                onChange={handleChange}
                placeholder="Descreva os detalhes da pauta, incluindo contexto, objetivos e pontos importantes para discussão..."
                className="form-control-custom"
                required
              />
              <Form.Text className="text-muted">
                Forneça informações suficientes para que os associados possam tomar uma decisão informada
              </Form.Text>
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer className="border-0 pt-0">
          <Button 
            variant="outline-secondary" 
            onClick={handleClose}
            className="btn-custom"
          >
            <span className="me-2">❌</span>
            Cancelar
          </Button>
          <Button 
            variant="primary" 
            onClick={handleSubmit}
            disabled={loading || !formData.titulo.trim() || !formData.descricao.trim()}
            className="btn-custom btn-primary-custom"
          >
            {loading ? (
              <>
                <div className="loading-spinner me-2"></div>
                Criando...
              </>
            ) : (
              <>
                <span className="me-2">✅</span>
                Criar Pauta
              </>
            )}
          </Button>
        </Modal.Footer>
      </div>
    </Modal>
  );
};

export default CriarPautaModal;
