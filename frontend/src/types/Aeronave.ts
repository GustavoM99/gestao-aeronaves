export interface Aeronave {
  id: number;
  nome: string;
  marca: string;
  ano: number;
  descricao?: string;
  vendido: boolean;
  created: string;
  updated: string;
}

export interface AeronaveRequest {
  nome: string;
  marca: string;
  ano: number;
  descricao?: string;
  vendido: boolean;
}

export interface DecadaStatistics {
  decada: string;
  quantidade: number;
}

export interface MarcaStatistics {
  marca: string;
  quantidade: number;
}
