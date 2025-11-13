import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1'
});

api.interceptors.request.use(config => {
  const token = localStorage.getItem('jwt');

  // não envia token na requisição de login
  if (token && !config.url.includes('/authenticate')) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;