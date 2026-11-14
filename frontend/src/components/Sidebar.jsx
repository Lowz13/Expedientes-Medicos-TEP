import './Sidebar.css'

const menuItems = {
  ADMIN: [
    { label: 'Panel principal', path: '/' },
    { label: 'Pacientes', path: '/pacientes' },
    { label: 'Médicos', path: '/medicos' },
    { label: 'Citas', path: '/citas' },
    { label: 'Usuarios', path: '/usuarios' },
  ],
  RECEPCIONISTA: [
    { label: 'Panel principal', path: '/' },
    { label: 'Pacientes', path: '/pacientes' },
    { label: 'Citas', path: '/citas' },
  ],
  MEDICO: [
    { label: 'Panel principal', path: '/' },
    { label: 'Agenda', path: '/agenda' },
    { label: 'Historial clínico', path: '/historial' },
    { label: 'Consultas', path: '/consultas' },
  ],
}

function Sidebar({ role = 'RECEPCIONISTA', currentPath = '/', onNavigate }) {
  const items = menuItems[role] ?? []

  return (
    <aside className="medisync-sidebar">
      <div className="medisync-sidebar__brand">
        <span className="medisync-sidebar__brand-name">MediSync</span>
        <span className="medisync-sidebar__brand-subtitle">
          Gestión Clínica
        </span>
      </div>

      <nav className="medisync-sidebar__nav" aria-label="Menú principal">
        {items.map((item) => (
          <button
            key={item.path}
            type="button"
            className={`medisync-sidebar__item ${
              currentPath === item.path ? 'medisync-sidebar__item--active' : ''
            }`}
            aria-current={currentPath === item.path ? 'page' : undefined}
            onClick={() => onNavigate?.(item.path)}
          >
            {item.label}
          </button>
        ))}
      </nav>
    </aside>
  )
}

export default Sidebar