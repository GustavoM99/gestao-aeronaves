import { useState, useEffect } from 'react';
import { aeronaveService } from './services/api';
import { Aeronave, AeronaveRequest, DecadaStatistics, MarcaStatistics } from './types/Aeronave';
import AeronaveForm from './components/AeronaveForm';
import AeronaveTable from './components/AeronaveTable';
import Statistics from './components/Statistics';
import SearchBar from './components/SearchBar';
import './App.css';

function App() {
  const [aeronaves, setAeronaves] = useState<Aeronave[]>([]);
  const [manufacturers, setManufacturers] = useState<string[]>([]);
  const [decadaStats, setDecadaStats] = useState<DecadaStatistics[]>([]);
  const [marcaStats, setMarcaStats] = useState<MarcaStatistics[]>([]);
  const [lastWeekCount, setLastWeekCount] = useState<number>(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(null);
      const [aeronavesData, manufacturersData, decadas, marcas, lastWeek] = await Promise.all([
        aeronaveService.getAll(),
        aeronaveService.getValidManufacturers(),
        aeronaveService.getDecadaStatistics(),
        aeronaveService.getMarcaStatistics(),
        aeronaveService.getLastWeek(),
      ]);
      setAeronaves(aeronavesData);
      setManufacturers(manufacturersData);
      setDecadaStats(decadas);
      setMarcaStats(marcas);
      setLastWeekCount(lastWeek.length);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Erro ao carregar dados');
      console.error('Erro ao carregar dados:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleCreate = async (data: AeronaveRequest) => {
    try {
      await aeronaveService.create(data);
      await loadData();
    } catch (err: any) {
      throw new Error(err.response?.data?.message || 'Erro ao criar aeronave');
    }
  };

  const handleUpdate = async (id: number, vendido: boolean) => {
    try {
      const aeronave = aeronaves.find(a => a.id === id);
      if (aeronave) {
        await aeronaveService.update(id, { ...aeronave, vendido });
        await loadData();
      }
    } catch (err: any) {
      alert(err.response?.data?.message || 'Erro ao atualizar aeronave');
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir esta aeronave?')) {
      try {
        await aeronaveService.delete(id);
        await loadData();
      } catch (err: any) {
        alert(err.response?.data?.message || 'Erro ao excluir aeronave');
      }
    }
  };

  const handleSearch = async (termo: string) => {
    try {
      setLoading(true);
      if (termo.trim() === '') {
        const data = await aeronaveService.getAll();
        setAeronaves(data);
      } else {
        const data = await aeronaveService.search(termo);
        setAeronaves(data);
      }
    } catch (err: any) {
      setError(err.response?.data?.message || 'Erro ao buscar aeronaves');
    } finally {
      setLoading(false);
    }
  };

  if (loading && aeronaves.length === 0) {
    return <div className="loading">Carregando...</div>;
  }

  return (
    <div className="app">
      <header className="app-header">
        <h1>Gestão de Aeronaves</h1>
      </header>

      {error && <div className="error-banner">{error}</div>}

      <div className="container">
        <AeronaveForm 
          manufacturers={manufacturers} 
          onSubmit={handleCreate}
        />

        <Statistics 
          decadas={decadaStats}
          lastWeekCount={lastWeekCount}
        />

        <SearchBar onSearch={handleSearch} />

        <AeronaveTable 
          aeronaves={aeronaves}
          onUpdateVendido={handleUpdate}
          onDelete={handleDelete}
        />

        <div className="marcas-section">
          <h3>Marcas</h3>
          <div className="marcas-list">
            {marcaStats.map(stat => (
              <span key={stat.marca} className="marca-stat">
                {stat.marca}: {stat.quantidade}
              </span>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
