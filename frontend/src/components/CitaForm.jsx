import { useState } from 'react'
import Input from './Input'
import Button from './Button'

function CitaForm({ onGuardar, onCancelar }) {
  const [pacienteId, setPacienteId] = useState('')
  const [medicoId, setMedicoId] = useState('')
  const [fechaHora, setFechaHora] = useState('')
  const [error, setError] = useState('')

  const handleSubmit = (e) => {
    e.preventDefault()
    
    // Validar espacios en blanco (Punto 9)
    if (!pacienteId.trim() || !medicoId.trim() || !fechaHora) {
      setError('Todos los campos son obligatorios y no pueden contener solo espacios.')
      return
    }

    const exito = onGuardar({ pacienteId, medicoId, fechaHora })
    
    if (!exito) {
      setError('Conflicto de horario: Ya existe una cita activa para este médico en esa fecha y hora.')
      // Mantenemos los datos en el formulario para que el usuario cambie solo la hora
    } else {
      setPacienteId('')
      setMedicoId('')
      setFechaHora('')
      setError('')
    }
  }

  return (
    <div style={{ border: '1px solid var(--color-border)', padding: '1rem', marginBottom: '1rem', borderRadius: '8px', backgroundColor: 'var(--color-surface)' }}>
      <h3>Registrar Nueva Cita</h3>
      
      {error && <p style={{ color: 'var(--color-error)', fontWeight: 'bold' }}>{error}</p>}

      <form onSubmit={handleSubmit} style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap', alignItems: 'flex-end' }}>
        
        <Input 
          id="pacienteId"
          label="ID Paciente"
          type="text" 
          value={pacienteId} 
          onChange={(e) => setPacienteId(e.target.value)} 
          placeholder="Ej. P-001"
        />

        <Input 
          id="medicoId"
          label="ID Médico"
          type="text" 
          value={medicoId} 
          onChange={(e) => setMedicoId(e.target.value)} 
          placeholder="Ej. M-001"
        />

        <Input 
          id="fechaHora"
          label="Fecha y Hora"
          type="datetime-local" 
          value={fechaHora} 
          onChange={(e) => setFechaHora(e.target.value)} 
        />

        <div style={{ display: 'flex', gap: '0.5rem', marginBottom: '0.5rem' }}>
          <Button type="submit">Guardar</Button>
          <Button variant="secondary" onClick={onCancelar}>Cerrar</Button>
        </div>

      </form>
    </div>
  )
}

export default CitaForm