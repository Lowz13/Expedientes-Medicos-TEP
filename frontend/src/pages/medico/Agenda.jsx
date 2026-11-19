import Sidebar from '../../components/Sidebar'
import Header from '../../components/Header'
import Table from '../../components/Table'
import '../../styles/layout.css'

// Datos de prueba del médico autenticado (Dr. Martínez).
// En la versión conectada al backend, esto vendrá filtrado por el médico logueado.
const agendaMock = [
  { id: '1', fecha: '2026-11-14', hora: '09:00', paciente: 'Ana López',    motivo: 'Consulta general' },
  { id: '3', fecha: '2026-11-15', hora: '11:00', paciente: 'María Solís',  motivo: 'Revisión'         },
]

// OJO: aquí NO hay columna de acciones.
// El médico solo lee su agenda; no reagenda ni cancela.
const columnas = [
  { key: 'fecha',    label: 'Fecha'          },
  { key: 'hora',     label: 'Hora'           },
  { key: 'paciente', label: 'Paciente'       },
  { key: 'motivo',   label: 'Motivo consulta' },
]

function Agenda() {
  return (
    <div className="medisync-layout">
      <Sidebar role="MEDICO" currentPath="/agenda" />

      <div className="medisync-layout__content">
        <Header
          title="Mi agenda"
          userName="Dr. Martínez"
          role="Médico"
        />

        <main className="medisync-layout__main">
          <h2>Agenda del día</h2>

          <Table
            columns={columnas}
            data={agendaMock}
            emptyMessage="No tienes citas programadas"
          />

          <p style={{ marginTop: '1rem', color: '#666', fontStyle: 'italic' }}>
            Esta vista es de solo lectura. Para modificar tu agenda, contacta a recepción.
          </p>
        </main>
      </div>
    </div>
  )
}

export default Agenda