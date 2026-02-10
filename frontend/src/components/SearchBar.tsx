import { useState } from 'react';
import './SearchBar.css';

interface SearchBarProps {
  onSearch: (termo: string) => void;
}

export default function SearchBar({ onSearch }: SearchBarProps) {
  const [termo, setTermo] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch(termo);
  };

  return (
    <div className="search-container">
      <form onSubmit={handleSubmit} className="search-form">
        <input
          type="text"
          value={termo}
          onChange={(e) => setTermo(e.target.value)}
          placeholder="Pesquisa por Modelo ou ID"
          className="search-input"
        />
        <button type="submit" className="search-button">
          Buscar
        </button>
        {termo && (
          <button 
            type="button" 
            className="clear-button"
            onClick={() => {
              setTermo('');
              onSearch('');
            }}
          >
            Limpar
          </button>
        )}
      </form>
    </div>
  );
}
