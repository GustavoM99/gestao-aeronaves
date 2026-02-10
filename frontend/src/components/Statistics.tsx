import { DecadaStatistics } from '../types/Aeronave';
import './Statistics.css';

interface StatisticsProps {
  decadas: DecadaStatistics[];
  lastWeekCount: number;
}

export default function Statistics({ decadas, lastWeekCount }: StatisticsProps) {
  return (
    <div className="statistics-container">
      <div className="stats-grid">
        {decadas.map(stat => (
          <div key={stat.decada} className="stat-card">
            <div className="stat-label">{stat.decada}</div>
            <div className="stat-value">{stat.quantidade} Aeronaves</div>
          </div>
        ))}
        <div className="stat-card highlight">
          <div className="stat-label">Essa semana</div>
          <div className="stat-value">{lastWeekCount} aeronaves</div>
        </div>
      </div>
    </div>
  );
}
