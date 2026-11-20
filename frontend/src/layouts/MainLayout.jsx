import { Outlet, useLocation, useNavigate } from 'react-router-dom'
import Sidebar from '../components/Sidebar'
import Header from '../components/Header'
import '../styles/layout.css'

const pageTitles = {
  '/': 'Panel principal',
  '/pacientes': 'Pacientes',
  '/medicos': 'Médicos',
  '/citas': 'Citas',
  '/agenda': 'Agenda médica',
  '/historial': 'Historial clínico',
  '/consultas': 'Consultas',
  '/usuarios': 'Gestión de usuarios',
}

function MainLayout({
  role = 'RECEPCIONISTA',
  userName = 'Usuario de prueba',
}) {
  const location = useLocation()
  const navigate = useNavigate()

  const title = pageTitles[location.pathname] || 'Página no encontrada'

  return (
    <div className="medisync-layout">
      <Sidebar
        role={role}
        currentPath={location.pathname}
        onNavigate={navigate}
      />

      <div className="medisync-layout__content">
        <Header
          title={title}
          userName={userName}
          role={role}
        />

        <main className="medisync-layout__main">
          <Outlet />
        </main>
      </div>
    </div>
  )
}

export default MainLayout