import Table from '../../components/Table'

// Datos mock ajustados a los estados del backend
const agendaMock = [
  { id: '1', fechaHora: '2026-11-14T09:00', pacienteNombre: 'Ana López', motivo: 'Consulta general', estado: 'PROGRAMADA' },
  { id: '3', fechaHora: '2026-11-15T11:00', pacienteNombre: 'María Solís', motivo: 'Revisión', estado: 'PROGRAMADA' },
]

const columnas = [
  { key: 'fechaHora', label: 'Fecha y Hora' },
  { key: 'pacienteNombre', label: 'Paciente' },
  { key: 'motivo', label: 'Motivo consulta' },
  { 
    key: 'estado', 
    label: 'Estado',
    render: (estado) => estado.charAt(0) + estado.slice(1).toLowerCase()
  },
]

function Agenda() {
  return (
    <>
      <h2>Agenda del día</h2>

      <Table
        columns={columnas}
        data={agendaMock}
        emptyMessage="No tienes citas programadas"
      />

      <p style={{ marginTop: '1rem', color: 'var(--color-muted)', fontStyle: 'italic' }}>
        Esta vista es de solo lectura. Para modificar tu agenda, contacta a recepción.
      </p>
    </>
  )
}

export default Agenda