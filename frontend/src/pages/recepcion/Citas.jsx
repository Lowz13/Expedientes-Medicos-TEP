import { useState } from 'react'
import Table from '../../components/Table'
import Input from '../../components/Input'
import Button from '../../components/Button'

// Datos mock con los estados exactos del backend (Punto 5)
const citasMock = [
  { id: '1', pacienteId: 'P-001', pacienteNombre: 'Ana López', medicoId: 'M-001', medicoNombre: 'Dr. Martínez', fechaHora: '2026-11-14T09:00', estado: 'PROGRAMADA' },
  { id: '2', pacienteId: 'P-002', pacienteNombre: 'Carlos Pérez', medicoId: 'M-002', medicoNombre: 'Dra. García', fechaHora: '2026-11-14T10:30', estado: 'PROGRAMADA' },
  { id: '3', pacienteId: 'P-003', pacienteNombre: 'María Solís', medicoId: 'M-001', medicoNombre: 'Dr. Martínez', fechaHora: '2026-11-15T11:00', estado: 'CANCELADA' },
]

function Citas() {
  const [fecha, setFecha] = useState('')
  const [medico, setMedico] = useState('')

  // Filtros aplicados sobre el estado (Punto 4)
  const citasFiltradas = citasMock.filter((cita) => {
    const cumpleFecha = !fecha || cita.fechaHora.startsWith(fecha)
    const cumpleMedico = !medico || cita.medicoNombre.toLowerCase().includes(medico.toLowerCase())
    return cumpleFecha && cumpleMedico
  })

  const columnas = [
    { key: 'fechaHora', label: 'Fecha y Hora' },
    { key: 'pacienteNombre', label: 'Paciente' },
    { key: 'medicoNombre', label: 'Médico' },
    { 
      key: 'estado', 
      label: 'Estado',
      // Mostramos el estado con la primera letra mayúscula (Punto 5)
      render: (estado) => estado.charAt(0) + estado.slice(1).toLowerCase()
    },
    {
      key: 'acciones',
      label: 'Acciones',
      render: (_, cita) => (
        <div style={{ display: 'flex', gap: '0.5rem' }}>
          {/* El botón Cancelar se implementa en FB-02, aquí solo mostramos el placeholder si está programada */}
          {cita.estado === 'PROGRAMADA' && (
            <Button variant="secondary">Cancelar</Button>
          )}
        </div>
      ),
    },
  ]

  return (
    <>
      <div style={{ display: 'flex', gap: '1rem', marginBottom: '1rem', flexWrap: 'wrap', alignItems: 'flex-end' }}>
        <Input
          id="filtroFecha"
          label="Filtrar por fecha"
          type="date"
          value={fecha}
          onChange={(e) => setFecha(e.target.value)}
        />
        <Input
          id="filtroMedico"
          label="Filtrar por médico"
          type="text"
          placeholder="Ej. Dr. Martínez"
          value={medico}
          onChange={(e) => setMedico(e.target.value)}
        />
      </div>

      <Table
        columns={columnas}
        data={citasFiltradas}
        emptyMessage="No hay citas para los filtros seleccionados"
      />
    </>
  )
}

export default Citas