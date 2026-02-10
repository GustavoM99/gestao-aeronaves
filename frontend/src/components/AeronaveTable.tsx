import { Aeronave } from '../types/Aeronave';
import './AeronaveTable.css';

interface AeronaveTableProps {
  aeronaves: Aeronave[];
  onUpdateVendido: (id: number, vendido: boolean) => void;
  onDelete: (id: number) => void;
}

export default function AeronaveTable({ aeronaves, onUpdateVendido, onDelete }: AeronaveTableProps) {
  return (
    <div className="table-container">
      <table className="aeronave-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Marca</th>
            <th>Modelo</th>
            <th>Ano</th>
            <th>Vendido</th>
            <th>Excluir</th>
          </tr>
        </thead>
        <tbody>
          {aeronaves.length === 0 ? (
            <tr>
              <td colSpan={6} className="empty-message">
                Nenhuma aeronave encontrada
              </td>
            </tr>
          ) : (
            aeronaves.map((aeronave) => (
              <tr key={aeronave.id}>
                <td>{aeronave.id}</td>
                <td>{aeronave.marca}</td>
                <td>{aeronave.nome}</td>
                <td>{aeronave.ano}</td>
                <td className="vendido-cell">
                  <input
                    type="radio"
                    checked={aeronave.vendido}
                    onChange={() => onUpdateVendido(aeronave.id, !aeronave.vendido)}
                    className="vendido-radio"
                  />
                </td>
                <td className="delete-cell">
                  <button
                    onClick={() => onDelete(aeronave.id)}
                    className="btn-delete"
                    title="Excluir aeronave"
                  >
                    X
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
