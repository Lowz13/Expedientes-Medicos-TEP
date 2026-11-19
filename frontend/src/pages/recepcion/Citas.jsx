import { useState } from 'react'
import Sidebar from '../../components/Sidebar'
import Header from '../../components/Header'
import Table from '../../components/Table'
import CitaForm from '../../components/CitaForm'
import '../../styles/layout.css'

// Ahora las citas usan los nombres del contrato (pacienteId, medicoId, fechaHora)
// Nota: para que la tabla se vea bonita, agregamos 'pacienteNombre' y 'medicoNombre' como datos extra de prueba.
const citasIniciales = [
  { id: '1', pacienteId: 'P-001', pacienteNombre: 'Ana López', medicoId: 'M-001', medicoNombre: 'Dr. Martínez', fechaHora: '2026-11-14T09:00', estado: 'Confirmada' },
  { id: '2', pacienteId: 'P-002', pacienteNombre: 'Carlos Pérez', medicoId: 'M-002', medicoNombre: 'Dra. García', fechaHora: '2026-11-14T10:30', estado: 'Pendiente' },
]

function Citas() {
  const [citas, setCitas] = useState(citasIniciales)
  const [mostrarFormulario, setMostrarFormulario] = useState(false)

  // Función para registrar cita (viene desde CitaForm)
  const handleGuardarCita = (nuevaCita) => {
    // Validación de conflicto de horario: mismo médico, misma fechaHora
    const hayConflicto = citas.some(
      (cita) => cita.medicoId === nuevaCita.medicoId && cita.fechaHora === nuevaCita.fechaHora
    )

    if (hayConflicto) {
      return false // Le avisa al formulario que hubo error
    }

    // Si no hay conflicto, agregamos la cita (con datos extra para simular la tabla)
    const citaConId = {
      ...nuevaCita,
      id: Date.now().toString(),
      pacienteNombre: `Paciente ${nuevaCita.pacienteId}`,
      medicoNombre: `Médico ${nuevaCita.medicoId}`,
      estado: 'Pendiente',
    }

    setCitas([...citas, citaConId])
    setMostrarFormulario(false)
    return true // Le avisa al formulario que todo salió bien
  }

  // Función para cancelar cita
  const handleCancelarCita = (id) => {
    const confirmar = window.confirm('¿Estás seguro de que deseas cancelar esta cita?')
    if (confirmar) {
      setCitas(citas.map((cita) => 
        cita.id === id ? { ...cita, estado: 'Cancelada' } : cita
      ))
    }
  }

  // Configuración de la tabla
  const columnas = [
    { key: 'fechaHora', label: 'Fecha y Hora' },
    { key: 'pacienteNombre', label: 'Paciente' },
    { key: 'medicoNombre', label: 'Médico' },
    { key: 'estado', label: 'Estado' },
    {
      key: 'acciones',
      label: 'Acciones',
      render: (_, cita) => (
        <div style={{ display: 'flex', gap: '0.5rem' }}>
          <button type="button">Reagendar</button>
          <button 
            type="button" 
            onClick={() => handleCancelarCita(cita.id)}
            disabled={cita.estado === 'Cancelada'}
            style={{ opacity: cita.estado === 'Cancelada' ? 0.5 : 1 }}
          >
            {cita.estado === 'Cancelada' ? 'Cancelada' : 'Cancelar'}
          </button>
        </div>
      ),
    },
  ]

  return (
    <div className="medisync-layout">
      <Sidebar role="RECEPCIONISTA" currentPath="/citas" />

      <div className="medisync-layout__content">
        <Header title="Citas" userName="Usuario de prueba" role="Recepcionista" />

        <main className="medisync-layout__main">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
            <h2>Listado de citas</h2>
            <button 
              onClick={() => setMostrarFormulario(!mostrarFormulario)}
              style={{ padding: '0.5rem 1rem', backgroundColor: '#005f73', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
            >
              {mostrarFormulario ? 'Ocultar Formulario' : '+ Nueva Cita'}
            </button>
          </div>

          {/* Mostramos el formulario condicionalmente */}
          {mostrarFormulario && (
            <CitaForm 
              onGuardar={handleGuardarCita} 
              onCancelar={() => setMostrarFormulario(false)} 
            />
          )}

          <Table
            columns={columnas}
            data={citas}
            emptyMessage="No hay citas registradas"
          />
        </main>
      </div>
    </div>
  )
}

export default Citas