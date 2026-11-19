import { useState } from 'react'
import Sidebar from '../../components/Sidebar'
import Header from '../../components/Header'
import Table from '../../components/Table'
import '../../styles/layout.css'

// Datos de prueba mientras el contrato de API (BB-01) no esté listo.
const citasMock = [
  { id: '1', fecha: '2026-11-14', hora: '09:00', paciente: 'Ana López',  medico: 'Dr. Martínez', estado: 'Confirmada' },
  { id: '2', fecha: '2026-11-14', hora: '10:30', paciente: 'Carlos Pérez', medico: 'Dra. García',   estado: 'Pendiente'  },
  { id: '3', fecha: '2026-11-15', hora: '11:00', paciente: 'María Solís', medico: 'Dr. Martínez', estado: 'Confirmada' },
  { id: '4', fecha: '2026-11-15', hora: '12:30', paciente: 'Luis Ramírez', medico: 'Dra. García',  estado: 'Cancelada'  },
]

// Columnas de la tabla. La columna "acciones" usa el render() del componente Table
// para mostrar botones SOLO en la vista de recepción (el médico no los verá).
const columnas = [
  { key: 'fecha',    label: 'Fecha'   },
  { key: 'hora',     label: 'Hora'    },
  { key: 'paciente', label: 'Paciente' },
  { key: 'medico',   label: 'Médico'  },
  { key: 'estado',   label: 'Estado'  },
  {
    key: 'acciones',
    label: 'Acciones',
    render: () => (
      <div style={{ display: 'flex', gap: '0.5rem' }}>
        <button type="button">Reagendar</button>
        <button type="button">Cancelar</button>
      </div>
    ),
  },
]

function Citas() {
  const [fecha, setFecha]   = useState('')
  const [medico, setMedico] = useState('')

  // Filtrado simple por fecha y/o médico
  const citasFiltradas = citasMock.filter((cita) => {
    const cumpleFecha  = !fecha  || cita.fecha  === fecha
    const cumpleMedico = !medico || cita.medico.toLowerCase().includes(medico.toLowerCase())
    return cumpleFecha && cumpleMedico
  })

  return (
    <div className="medisync-layout">
      <Sidebar role="RECEPCIONISTA" currentPath="/citas" />

      <div className="medisync-layout__content">
        <Header
          title="Citas"
          userName="Usuario de prueba"
          role="Recepcionista"
        />

        <main className="medisync-layout__main">
          <h2>Listado de citas</h2>

          <div style={{ display: 'flex', gap: '1rem', marginBottom: '1rem', flexWrap: 'wrap' }}>
            <label>
              Filtrar por fecha:{' '}
              <input
                type="date"
                value={fecha}
                onChange={(e) => setFecha(e.target.value)}
              />
            </label>

            <label>
              Filtrar por médico:{' '}
              <input
                type="text"
                value={medico}
                onChange={(e) => setMedico(e.target.value)}
                placeholder="Ej. Dr. Martínez"
              />
            </label>
          </div>

          <Table
            columns={columnas}
            data={citasFiltradas}
            emptyMessage="No hay citas para los filtros seleccionados"
          />
        </main>
      </div>
    </div>
  )
}

export default Citas