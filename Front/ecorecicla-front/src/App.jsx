import { useState, useEffect } from 'react';
import { api } from './Services/api';

function App() {
  const [residuos, setResiduos] = useState([]);
  const [filtroEstado, setFiltroEstado] = useState('');
  
  const [form, setForm] = useState({
    municipio: '', estado: '', quantidadeGerada: '', taxaReciclagem: '', ano: ''
  });

  const carregarDados = () => {
    api.get('')
      .then(response => setResiduos(response.data))
      .catch(error => console.error("Erro na API:", error));
  };

  useEffect(() => {
    carregarDados();
  }, []);

  const handleFiltrar = () => {
    if (filtroEstado.trim() === '') {
      carregarDados();
    } else {
      api.get(`/estado/${filtroEstado}`)
        .then(response => setResiduos(response.data))
        .catch(error => console.error("Erro ao filtrar:", error));
    }
  };

  const handleInputChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault(); 
    
    api.post('', form)
      .then(() => {
        alert("✅ Registro cadastrado com sucesso!");
        carregarDados(); 
        setForm({ municipio: '', estado: '', quantidadeGerada: '', taxaReciclagem: '', ano: '' });
      })
      .catch(error => console.error("Erro ao salvar:", error));
  };


  const getNivelSustentabilidade = (taxa) => {
    if (taxa < 10) return { texto: 'Péssimo', cor: '#e74c3c' }; // Vermelho
    if (taxa < 25) return { texto: 'Ruim', cor: '#f39c12' };    // Laranja
    if (taxa < 50) return { texto: 'Bom', cor: '#3498db' };     // Azul
    return { texto: 'Ótimo', cor: '#27ae60' };                  // Verde
  };

  return (
    <div style={{ backgroundColor: '#e8f8f5', minHeight: '100vh', padding: '20px 0' }}>
      
      <div style={{ padding: '20px', fontFamily: 'sans-serif', maxWidth: '1050px', margin: '0 auto', backgroundColor: 'white', borderRadius: '12px', boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }}>
        
        <h1 style={{ textAlign: 'center', color: '#2c3e50' }}>♻️ Gestão Ambiental de Resíduos</h1>

        <div style={{ display: 'flex', gap: '20px', marginTop: '30px' }}>
          
          <div style={{ flex: '1', padding: '20px', backgroundColor: '#f9f9f9', borderRadius: '8px', border: '1px solid #ddd' }}>
            <h3>Cadastrar Novo Registro</h3>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
              <input type="text" name="estado" placeholder="Estado (Ex: SP)" value={form.estado} onChange={handleInputChange} required />
              <input type="text" name="municipio" placeholder="Município" value={form.municipio} onChange={handleInputChange} required />
              <input type="number" name="quantidadeGerada" placeholder="Qtd Gerada (Toneladas)" step="0.01" value={form.quantidadeGerada} onChange={handleInputChange} required />
              <input type="number" name="taxaReciclagem" placeholder="Taxa de Reciclagem (%)" step="0.01" value={form.taxaReciclagem} onChange={handleInputChange} required />
              <input type="number" name="ano" placeholder="Ano (Ex: 2024)" value={form.ano} onChange={handleInputChange} required />
              
              <button type="submit" style={{ padding: '10px', backgroundColor: '#20b2aa', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>
                Salvar Dados
              </button>
            </form>
          </div>

          <div style={{ flex: '2' }}>
            <div style={{ display: 'flex', gap: '10px', marginBottom: '15px' }}>
              <input 
                type="text" 
                placeholder="Filtrar por Estado (Ex: SP)" 
                value={filtroEstado} 
                onChange={(e) => setFiltroEstado(e.target.value)} 
                style={{ padding: '8px', flex: '1', borderRadius: '4px', border: '1px solid #ccc' }}
              />
              <button onClick={handleFiltrar} style={{ padding: '8px 15px', backgroundColor: '#20b2aa', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>
                Buscar
              </button>
            </div>

            <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse', backgroundColor: 'white' }}>
              <thead style={{ backgroundColor: '#20b2aa', color: 'white' }}>
                <tr>
                  <th>ID</th>
                  <th>Estado</th>
                  <th>Município</th>
                  <th>Toneladas de lixo Gerado</th>
                  <th>Taxa Reciclagem</th>
                  <th>Nível de Sustentabilidade</th>
                </tr>
              </thead>
              <tbody>
                {residuos.map(registro => {
                  // Calcula o nível para cada linha da tabela
                  const nivel = getNivelSustentabilidade(registro.taxaReciclagem);
                  
                  return (
                    <tr key={registro.id} style={{ textAlign: 'center' }}>
                      <td>{registro.id}</td>
                      <td>{registro.estado}</td>
                      <td>{registro.municipio}</td>
                      <td>{registro.quantidadeGerada}</td>
                      <td>
                        <strong>{registro.taxaReciclagem}%</strong>
                      </td>
                      <td>
                       
                        <span style={{ 
                          backgroundColor: nivel.cor, 
                          color: 'white', 
                          padding: '5px 10px', 
                          borderRadius: '15px', 
                          fontSize: '0.85em', 
                          fontWeight: 'bold' 
                        }}>
                          {nivel.texto}
                        </span>
                      </td>
                    </tr>
                  );
                })}
                {residuos.length === 0 && (
                  <tr>
                    
                    <td colSpan="7" style={{ textAlign: 'center', padding: '20px' }}>Nenhum registro encontrado para este estado.</td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>

        </div>
      </div>
    </div>
  );
}

export default App;