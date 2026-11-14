import Sidebar from './components/Sidebar'
import Header from './components/Header'
import Table from './components/Table'
import './styles/layout.css'

function App() {
  const columnas = [
    { key: 'nombre', label: 'Nombre completo' },
    { key: 'correo', label: 'Correo electrónico' },
    { key: 'telefono', label: 'Teléfono' },
  ]

  const pacientes = [
    {
      id: '1',
      nombre: 'Ana López',
      correo: 'ana@example.com',
      telefono: '5551234567',
    },
    {
      id: '2',
      nombre: 'Carlos Pérez',
      correo: 'carlos@example.com',
      telefono: '5559876543',
    },
  ]

  return (
    <div className="medisync-layout">
      <Sidebar
        role="RECEPCIONISTA"
        currentPath="/pacientes"
      />

      <div className="medisync-layout__content">
        <Header
          title="Pacientes"
          userName="Usuario de prueba"
          role="Recepcionista"
        />

        <main className="medisync-layout__main">
          <h2>Listado de pacientes</h2>

          <Table columns={columnas} data={pacientes} />
        </main>
      </div>
    </div>
  )
}

export default App