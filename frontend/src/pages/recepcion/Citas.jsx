import { useState } from 'react'
import Table from '../../components/Table'
import CitaForm from '../../components/CitaForm'
import Input from '../../components/Input'
import Button from '../../components/Button'

const citasIniciales = [
  { id: '1', pacienteId: 'P-001', pacienteNombre: 'Ana López', medicoId: 'M-001', medicoNombre: 'Dr. Martínez', fechaHora: '2026-11-14T09:00', estado: 'PROGRAMADA' },
  { id: '2', pacienteId: 'P-002', pacienteNombre: 'Carlos Pérez', medicoId: 'M-002', medicoNombre: 'Dra. García', fechaHora: '2026-11-14T10:30', estado: 'PROGRAMADA' },
]

function Citas() {
  const [citas, setCitas] = useState(citasIniciales)
  const [fecha, setFecha] = useState('')
  const [medico, setMedico] = useState('')
  const [mostrarFormulario, setMostrarFormulario] = useState(false)

  // Filtros sobre el estado (Punto 4)
  const citasFiltradas = citas.filter((cita) => {
    const cumpleFecha = !fecha || cita.fechaHora.startsWith(fecha)
    const cumpleMedico = !medico || cita.medicoNombre.toLowerCase().includes(medico.toLowerCase())
    return cumpleFecha && cumpleMedico
  })

  const handleGuardarCita = (nuevaCita) => {
    // Validación de conflicto: ignorar las citas CANCELADAS (Punto 6)
    const hayConflicto = citas.some(
      (cita) =>
        cita.medicoId === nuevaCita.medicoId &&
        cita.fechaHora === nuevaCita.fechaHora &&
        cita.estado !== 'CANCELADA'
    )

    if (hayConflicto) {
      return false
    }

    const citaConId = {
      ...nuevaCita,
      id: Date.now().toString(),
      pacienteNombre: `Paciente ${nuevaCita.pacienteId}`,
      medicoNombre: `Médico ${nuevaCita.medicoId}`,
      estado: 'PROGRAMADA', // Estado inicial exacto del backend (Punto 5)
    }

    setCitas([...citas, citaConId])
    setMostrarFormulario(false)
    return true
  }

  const handleCancelarCita = (id) => {
    const confirmar = window.confirm('¿Estás seguro de que deseas cancelar esta cita?')
    if (confirmar) {
      setCitas(citas.map((cita) => 
        cita.id === id ? { ...cita, estado: 'CANCELADA' } : cita
      ))
    }
  }

  const columnas = [
    { key: 'fechaHora', label: 'Fecha y Hora' },
    { key: 'pacienteNombre', label: 'Paciente' },
    { key: 'medicoNombre', label: 'Médico' },
    { 
      key: 'estado', 
      label: 'Estado',
      render: (estado) => estado.charAt(0) + estado.slice(1).toLowerCase()
    },
    {
      key: 'acciones',
      label: 'Acciones',
      render: (_, cita) => (
        <div style={{ display: 'flex', gap: '0.5rem' }}>
          {/* Solo se pueden cancelar las PROGRAMADAS (Punto 7) */}
          {cita.estado === 'PROGRAMADA' && (
            <Button variant="secondary" onClick={() => handleCancelarCita(cita.id)}>
              Cancelar
            </Button>
          )}
        </div>
      ),
    },
  ]

  return (
    <>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem', flexWrap: 'wrap', gap: '1rem' }}>
        <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap', alignItems: 'flex-end' }}>
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
        <Button onClick={() => setMostrarFormulario(!mostrarFormulario)}>
          {mostrarFormulario ? 'Ocultar Formulario' : '+ Nueva Cita'}
        </Button>
      </div>

      {mostrarFormulario && (
        <CitaForm 
          onGuardar={handleGuardarCita} 
          onCancelar={() => setMostrarFormulario(false)} 
        />
      )}

      <Table
        columns={columnas}
        data={citasFiltradas}
        emptyMessage="No hay citas para los filtros seleccionados"
      />
    </>
  )
}

export default Citas