import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import Sessoes from './components/Sessoes';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/sessoes" element={<Sessoes />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;