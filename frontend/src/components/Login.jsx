import { useState } from 'react';
import api from '../api/axios.js';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async () => {
    console.log('Tentando login com:', { username, password });

    try {
      const response = await api.post('/authenticate', {
        username,
        password
      });

      console.log('Resposta da API:', response.data);

      const token = response.data.token;
      if (!token) {
        throw new Error('Token não recebido');
      }

      localStorage.setItem('jwt', token);
      alert('Login bem-sucedido!');
    } catch (error) {
      console.error('Erro ao fazer login:', error);

      if (error.response) {
        console.error('Status:', error.response.status);
        console.error('Dados do erro:', error.response.data);
      }

      alert('Falha no login');
    }
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Login</h2>
      <input
        type="email"
        placeholder="Email"
        value={username}
        onChange={e => setUsername(e.target.value)}
      />
      <br />
      <input
        type="password"
        placeholder="Senha"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />
      <br />
      <button onClick={handleLogin}>Entrar</button>
    </div>
  );
}

export default Login;