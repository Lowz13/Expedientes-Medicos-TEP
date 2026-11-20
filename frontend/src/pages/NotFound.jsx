import { Link } from 'react-router-dom'

function NotFound() {
  return (
    <section>
      <h2>404 - Página no encontrada</h2>

      <p>La página que estás buscando no existe.</p>

      <Link to="/">Regresar al panel principal</Link>
    </section>
  )
}

export default NotFound