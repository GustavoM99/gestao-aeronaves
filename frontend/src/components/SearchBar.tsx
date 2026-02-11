import { useState, useEffect, useCallback } from 'react';
import './SearchBar.css';

interface SearchBarProps {
  onSearch: (termo: string) => void;
}

export default function SearchBar({ onSearch }: SearchBarProps) {
  const [termo, setTermo] = useState('');

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      onSearch(termo);
    }, 500);

    return () => clearTimeout(timeoutId);
  }, [termo, onSearch]);

  const handleClear = useCallback(() => {
    setTermo('');
  }, []);

  return (
    <div className="search-container">
      <div className="search-form">
        <input
          type="text"
          value={termo}
          onChange={(e) => setTermo(e.target.value)}
          placeholder="Pesquisa por Modelo ou ID"
          className="search-input"
          onKeyDown={(e) => {
            if (e.key === 'Enter') {
              e.preventDefault();
            }
          }}
        />
        {termo && (
          <button 
            type="button" 
            className="clear-button"
            onClick={handleClear}
          >
            Limpar
          </button>
        )}
      </div>
    </div>
  );
}
