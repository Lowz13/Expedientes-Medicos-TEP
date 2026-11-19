import { useState } from 'react'

// Recibimos 'onGuardar' (para enviar datos) y 'onCancelar' (para cerrar el form)
function CitaForm({ onGuardar, onCancelar }) {
  // Usamos EXACTAMENTE los nombres acordados en el contrato
  const [pacienteId, setPacienteId] = useState('')
  const [medicoId, setMedicoId] = useState('')
  const [fechaHora, setFechaHora] = useState('')
  const [error, setError] = useState('')

  const handleSubmit = (e) => {
    e.preventDefault()
    
    // 1. Validación de campos vacíos
    if (!pacienteId || !medicoId || !fechaHora) {
      setError('Todos los campos son obligatorios.')
      return
    }

    // 2. Enviamos los datos al componente padre (Citas.jsx)
    // El padre se encargará de revisar si hay conflictos de horario
    const exito = onGuardar({ pacienteId, medicoId, fechaHora })
    
    // 3. Si el padre devuelve 'false', significa que hubo un conflicto de horario
    if (!exito) {
      setError('Conflicto de horario: Ya existe una cita para este médico en esa fecha y hora.')
    } else {
      // Limpiamos el formulario si todo salió bien
      setPacienteId('')
      setMedicoId('')
      setFechaHora('')
      setError('')
    }
  }

  return (
    <div style={{ border: '1px solid #ccc', padding: '1rem', marginBottom: '1rem', borderRadius: '8px', backgroundColor: '#f9f9f9' }}>
      <h3>Registrar Nueva Cita</h3>
      
      {error && <p style={{ color: 'red', fontWeight: 'bold' }}>{error}</p>}

      <form onSubmit={handleSubmit} style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap', alignItems: 'flex-end' }}>
        
        <div>
          <label style={{ display: 'block', marginBottom: '5px' }}>ID Paciente:</label>
          <input 
            type="text" 
            value={pacienteId} 
            onChange={(e) => setPacienteId(e.target.value)} 
            placeholder="Ej. P-001"
          />
        </div>

        <div>
          <label style={{ display: 'block', marginBottom: '5px' }}>ID Médico:</label>
          <input 
            type="text" 
            value={medicoId} 
            onChange={(e) => setMedicoId(e.target.value)} 
            placeholder="Ej. M-001"
          />
        </div>

        <div>
          <label style={{ display: 'block', marginBottom: '5px' }}>Fecha y Hora:</label>
          <input 
            type="datetime-local" 
            value={fechaHora} 
            onChange={(e) => setFechaHora(e.target.value)} 
          />
        </div>

        <div style={{ display: 'flex', gap: '0.5rem' }}>
          <button type="submit" style={{ padding: '0.5rem 1rem', cursor: 'pointer' }}>Guardar</button>
          <button type="button" onClick={onCancelar} style={{ padding: '0.5rem 1rem', cursor: 'pointer' }}>Cerrar</button>
        </div>

      </form>
    </div>
  )
}

export default CitaForm