import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { Navbar } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './styles/custom.css';
import PautaList from './components/PautaList';
import PautaDetails from './components/PautaDetails';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <div className="app-wrapper">
          <Navbar bg="light" variant="light" expand="lg" className="custom-navbar">
            <div className="container-fluid">
              <Navbar.Brand as={Link} to="/" className="d-flex align-items-center">
                Sistema de Votação - Cooperativa
              </Navbar.Brand>
            </div>
          </Navbar>

          <div className="content-wrapper">
            <div className="main-container">
              <Routes>
                <Route path="/" element={<PautaList />} />
                <Route path="/pauta/:id" element={<PautaDetails />} />
              </Routes>
            </div>
          </div>
        </div>
      </div>
    </Router>
  );
}

export default App;
