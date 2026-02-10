import axios from 'axios';
import { Aeronave, AeronaveRequest, DecadaStatistics, MarcaStatistics } from '../types/Aeronave';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const aeronaveService = {
  getAll: async (): Promise<Aeronave[]> => {
    const response = await api.get<Aeronave[]>('/aeronaves');
    return response.data;
  },

  getById: async (id: number): Promise<Aeronave> => {
    const response = await api.get<Aeronave>(`/aeronaves/${id}`);
    return response.data;
  },

  search: async (termo: string): Promise<Aeronave[]> => {
    const response = await api.get<Aeronave[]>(`/aeronaves/find`, {
      params: { termo },
    });
    return response.data;
  },

  create: async (data: AeronaveRequest): Promise<Aeronave> => {
    const response = await api.post<Aeronave>('/aeronaves', data);
    return response.data;
  },

  update: async (id: number, data: AeronaveRequest): Promise<Aeronave> => {
    const response = await api.put<Aeronave>(`/aeronaves/${id}`, data);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/aeronaves/${id}`);
  },

  getValidManufacturers: async (): Promise<string[]> => {
    const response = await api.get<string[]>('/aeronaves/manufacturers');
    return response.data;
  },

  getDecadaStatistics: async (): Promise<DecadaStatistics[]> => {
    const response = await api.get<DecadaStatistics[]>('/aeronaves/statistics/decadas');
    return response.data;
  },

  getMarcaStatistics: async (): Promise<MarcaStatistics[]> => {
    const response = await api.get<MarcaStatistics[]>('/aeronaves/statistics/marcas');
    return response.data;
  },

  getLastWeek: async (): Promise<Aeronave[]> => {
    const response = await api.get<Aeronave[]>('/aeronaves/statistics/last-week');
    return response.data;
  },

  getNaoVendidos: async (): Promise<number> => {
    const response = await api.get<number>('/aeronaves/statistics/nao-vendidos');
    return response.data;
  },
};
