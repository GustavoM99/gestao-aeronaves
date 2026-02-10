import { useState } from 'react';
import { AeronaveRequest } from '../types/Aeronave';
import './AeronaveForm.css';

interface AeronaveFormProps {
  manufacturers: string[];
  onSubmit: (data: AeronaveRequest) => Promise<void>;
}

export default function AeronaveForm({ manufacturers, onSubmit }: AeronaveFormProps) {
  const [formData, setFormData] = useState<AeronaveRequest>({
    nome: '',
    marca: '',
    ano: new Date().getFullYear(),
    descricao: '',
    vendido: false,
  });
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setSubmitting(true);

    try {
      await onSubmit(formData);
      setFormData({
        nome: '',
        marca: '',
        ano: new Date().getFullYear(),
        descricao: '',
        vendido: false,
      });
    } catch (err: any) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="form-container">
      <h2>Gestão de Aeronaves</h2>
      {error && <div className="form-error">{error}</div>}
      
      <form onSubmit={handleSubmit} className="aeronave-form">
        <div className="form-group">
          <label>Marca</label>
          <select
            value={formData.marca}
            onChange={(e) => setFormData({ ...formData, marca: e.target.value })}
            required
          >
            <option value="">Selecione...</option>
            {manufacturers.map(m => (
              <option key={m} value={m}>{m}</option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Aeronave</label>
          <input
            type="text"
            value={formData.nome}
            onChange={(e) => setFormData({ ...formData, nome: e.target.value })}
            placeholder="Nome da aeronave"
            required
            minLength={2}
            maxLength={100}
          />
        </div>

        <div className="form-row">
          <div className="form-group">
            <label>Ano</label>
            <input
              type="number"
              value={formData.ano}
              onChange={(e) => setFormData({ ...formData, ano: parseInt(e.target.value) })}
              min={1900}
              max={2100}
              required
            />
          </div>

          <div className="form-group">
            <label>Vendido</label>
            <select
              value={formData.vendido.toString()}
              onChange={(e) => setFormData({ ...formData, vendido: e.target.value === 'true' })}
            >
              <option value="false">Não</option>
              <option value="true">Sim</option>
            </select>
          </div>
        </div>

        <button type="submit" className="btn-submit" disabled={submitting}>
          {submitting ? 'Gravando...' : 'Gravar'}
        </button>
      </form>
    </div>
  );
}
