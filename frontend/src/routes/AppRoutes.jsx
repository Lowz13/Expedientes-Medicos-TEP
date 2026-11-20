import { Routes, Route } from 'react-router-dom'
import MainLayout from '../layouts/MainLayout'
import Dashboard from '../pages/Dashboard'
import NotFound from '../pages/NotFound'
import Login from '../pages/Login'
import PendingPage from '../pages/PendingPage'

// 1. Importamos nuestras pantallas reales
import Citas from '../pages/recepcion/Citas'
import Agenda from '../pages/medico/Agenda'

function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />

      <Route element={<MainLayout />}>
        <Route index element={<Dashboard />} />

        <Route
          path="pacientes"
          element={<PendingPage title="Pacientes" />}
        />

        <Route
          path="medicos"
          element={<PendingPage title="Médicos" />}
        />

        {/* 2. Reemplazamos PendingPage por nuestros componentes */}
        <Route path="citas" element={<Citas />} />
        <Route path="agenda" element={<Agenda />} />

        <Route
          path="historial"
          element={<PendingPage title="Historial clínico" />}
        />

        <Route
          path="consultas"
          element={<PendingPage title="Consultas" />}
        />

        <Route
          path="usuarios"
          element={<PendingPage title="Gestión de usuarios" />}
        />

        <Route path="*" element={<NotFound />} />
      </Route>
    </Routes>
  )
}

export default AppRoutes