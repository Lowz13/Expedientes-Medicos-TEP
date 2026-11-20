import { useState } from 'react'
import Input from '../components/Input'
import Button from '../components/Button'
import './Login.css'

function Login() {
  const [identificador, setIdentificador] = useState('')
  const [password, setPassword] = useState('')

  function handleSubmit(event) {
    event.preventDefault()
  }

  return (
    <div className="medisync-login">
      <section className="medisync-login__brand">
        <h1>MediSync</h1>
        <p>Sistema de gestión clínica</p>
      </section>

      <section className="medisync-login__content">
        <form
          className="medisync-login__form"
          onSubmit={handleSubmit}
        >
          <h2>Iniciar sesión</h2>

          <p>Ingresa tus credenciales para acceder al sistema.</p>

          <Input
            id="identificador"
            name="identifier"
            label="Usuario o correo electrónico"
            type="text"
            placeholder="Ingresa tu usuario o correo electrónico"
            value={identificador}
            onChange={(event) => setIdentificador(event.target.value)}
            required
          />

          <Input
            id="password"
            name="password"
            label="Contraseña"
            type="password"
            placeholder="Ingresa tu contraseña"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            required
          />

          <Button type="submit">
            Iniciar sesión
          </Button>
        </form>
      </section>
    </div>
  )
}

export default Login