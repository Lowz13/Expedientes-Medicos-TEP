# MediSync — Guía de trabajo para el equipo

> **Documento interno de coordinación** · Equipo de 4 personas · Plazo menor a dos semanas · Backend: 2 · Frontend: 2.

## 1. Objetivo compartido

Entregar un **MVP web funcional** que permita registrar pacientes, administrar médicos y citas, documentar consultas, adjuntar un archivo, descargar un PDF y enviar una confirmación real por correo. Debe poder ejecutarse con Docker Compose, probarse con Postman/K6 y demostrarse con datos ficticios.

**Regla de trabajo:** primero hacer funcionar un flujo completo **React → Spring Boot → MongoDB → React**; después ampliar módulos. No comenzar funcionalidades adicionales hasta tener la integración principal estable.

## 2. Punto de partida real

Repositorio: https://github.com/Lowz13/Expedientes-Medicos-TEP

En `main` se observaron `medisync/` (Spring Boot/Gradle), `PacienteController`, `CitaController`, `PacienteDto` y `CitaDto`. Las APIs actuales usan `ArrayList`, no MongoDB. El envío de correo solo se simula con `System.out.println`. No se observó frontend React, Docker, PDF ni seguridad. **No se ha ejecutado el proyecto para comprobar su compilación.**

`medisync/build.gradle` solicita Spring Boot `4.1.1` y Java `27`. **Primera decisión técnica:** validar versiones disponibles y fijar una combinación compatible para todos; no cambiar versiones a ciegas.

## 3. Asignación de responsabilidades

| Persona | Área | Trabajo principal | Entregable verificable |
|---|---|---|---|
| Backend A | Datos y seguridad | MongoDB, usuarios, roles, pacientes, médicos, validaciones | API con persistencia y permisos |
| Backend B | Operación clínica | Citas, consultas, archivos, PDF, correo | Flujo de cita y consulta completo |
| Frontend A | Base visual y administración | Vite/React, navegación, login, pacientes y médicos | Pantallas funcionales conectadas |
| Frontend B | Agenda y clínica | Agenda, citas, historial, consultas, archivos y PDF | Flujo médico funcional conectado |

**Responsabilidades compartidas:** decisiones de API (los 4), revisión de PR (parejas), pruebas (todos), documentación (cada persona actualiza su módulo), demo (todos conocen el flujo). Como todavía no existe diseño visual, ambos frontend acuerdan juntos un sistema de estilos mínimo antes de crear pantallas.

## 4. Acuerdos técnicos antes de programar

1. Definir JDK, Spring Boot y Gradle compatibles; verificar `./gradlew bootRun`.
2. Elegir una convención de rutas: `/api/...` y nombres de recursos en plural.
3. Acordar un único formato de error JSON, por ejemplo `{ "codigo": "CITA_OCUPADA", "mensaje": "Horario no disponible" }`.
4. Fechas de citas: almacenar UTC, presentar hora local con zona explícita; no usar fechas como cadenas ambiguas.
5. Identificadores: MongoDB `ObjectId` internamente, cadenas en respuestas JSON.
6. Citas del MVP: duración fija de 30 minutos; una cita `PROGRAMADA` por médico y fecha/hora.
7. Decidir mecanismo de autenticación (sesión/cookie o token) antes de integrar React; no mezclar dos enfoques.
8. Decidir biblioteca de componentes/estilos (Bootstrap o CSS propio) y convenciones de nombres.
9. Usar datos sintéticos; no subir expedientes reales.
10. Documentar los endpoints en una colección Postman compartida.

## 5. Modelo de datos de referencia

| Colección | Campos principales | Relaciones |
|---|---|---|
| `usuarios` | nombre, correo, passwordHash, rol, activo | 0..1 perfil médico |
| `medicos` | usuarioId, nombre, especialidad, cedulaProfesional, horario | citas y consultas |
| `pacientes` | nombre, fechaNacimiento, telefono, correo, alergias, antecedentes | citas, consultas, archivos |
| `citas` | pacienteId, medicoId, fechaHora, duracionMinutos, estado, motivo | paciente y médico |
| `consultas` | pacienteId, medicoId, citaId, fechaConsulta, diagnostico, tratamiento, observaciones | paciente, médico, cita |
| `archivos` | pacienteId, consultaId, rutaAlmacenamiento, nombreOriginal, tipoMime, tamanoBytes, subidoPor | paciente y consulta |

`auditoria` es recomendable si hay tiempo, especialmente para accesos y cambios clínicos. **No** guardar todo el historial como un solo campo de texto en `pacientes`.

## 6. Matriz de permisos acordada

| Operación | ADMIN | MEDICO |
|---|:---:|:---:|
| Gestionar pacientes, médicos y citas | Sí | No |
| Consultar datos generales y agenda | Sí | Sí |
| Consultar historiales clínicos de todos los pacientes | No | Sí |
| Registrar consultas y adjuntar archivos clínicos | No | Sí |
| Generar/descargar reportes y documentos clínicos | No | Sí |
| Modificar consultas de otro médico | No | No |

En el backend, comprobar rol, usuario activo y permisos sobre el recurso. React solo refleja esos permisos visualmente.

## 7. Contrato inicial de API para integración

**Propuesto; no equivale a implementación existente.** Los cuatro deben aprobar campos, respuestas y errores antes de crear formularios.

| Método | Endpoint | Responsable | Estado de partida |
|---|---|---|---|
| GET | `/api/pacientes` | Backend A | Básico en memoria |
| POST | `/api/pacientes` | Backend A | Básico en memoria |
| GET | `/api/pacientes/{id}` | Backend A | Pendiente |
| PUT | `/api/pacientes/{id}` | Backend A | Pendiente |
| GET | `/api/doctores` | Backend A | Pendiente |
| POST | `/api/doctores` | Backend A | Pendiente |
| POST | `/api/auth/login` | Backend A | Pendiente |
| GET | `/api/citas` | Backend B | Básico en memoria |
| POST | `/api/citas` | Backend B | Básico en memoria |
| PATCH | `/api/citas/{id}/cancelar` | Backend B | Pendiente |
| GET | `/api/pacientes/{id}/historial` | Backend B | Pendiente |
| POST | `/api/consultas` | Backend B | Pendiente |
| GET | `/api/consultas/{id}/reporte` | Backend B | Pendiente |
| POST | `/api/pacientes/{id}/archivos` | Backend B | Pendiente |
| GET | `/api/archivos/{id}` | Backend B | Pendiente |

### Ejemplos JSON de referencia (sujetos a acuerdo)

Crear paciente:

```json
{
  "nombre": "Paciente de Prueba",
  "fechaNacimiento": "1995-05-12",
  "telefono": "5550000000",
  "correo": "paciente.ejemplo@example.com",
  "alergias": [],
  "antecedentes": "Sin antecedentes reportados"
}
```

Agendar cita:

```json
{
  "pacienteId": "<id-paciente>",
  "medicoId": "<id-medico>",
  "fechaHora": "2026-10-20T16:00:00Z",
  "motivo": "Consulta general"
}
```

Respuestas sugeridas: `201` creación, `200` consulta, `400` validación, `401` sin autenticación, `403` sin permiso, `404` no encontrado, `409` cita duplicada. El frontend debe manejar errores sin mostrar trazas internas.

## 8. Pantallas mínimas y diseño sin maqueta previa

| Pantalla | Contenido | Responsable |
|---|---|---|
| Login | Correo, contraseña, error de autenticación | Frontend A |
| Panel principal | Menú por rol, accesos rápidos | Frontend A |
| Pacientes | Lista, búsqueda, alta y edición | Frontend A |
| Médicos | Lista y alta/edición (ADMIN) | Frontend A |
| Agenda | Lista por fecha y médico, alta/cancelación (ADMIN) | Frontend B |
| Historial clínico | Consultas del paciente (MEDICO) | Frontend B |
| Nueva consulta | Diagnóstico, tratamiento, observaciones | Frontend B |
| Documentos y PDF | Adjuntar, listar y descargar | Frontend B |

### Reglas de diseño mínimas

- Definir tipografía, 2–3 colores, tamaños de botones, campos y mensajes antes de programar.
- Usar componentes compartidos: `Navbar`, `Sidebar`, `Button`, `Input`, `Modal`, `Table`, `Alert`.
- Mantener navegación consistente, estados de carga, vacíos y errores.
- Etiquetas visibles en formularios; navegación por teclado y contraste legible.
- La agenda puede ser una **tabla filtrable**; calendario complejo queda fuera del MVP.
- Hacer primero un boceto de baja fidelidad, no esperar un diseño perfecto.

## 9. Organización de carpetas recomendada

```text
Expedientes-Medicos-TEP/
├── medisync/                   # Spring Boot ya existe
│   └── src/main/java/.../
│       ├── expedientes/controllers/
│       ├── expedientes/dtos/
│       ├── expedientes/models/
│       ├── expedientes/repositories/
│       ├── expedientes/services/
│       └── security/
├── frontend/                   # React + Vite por crear
│   └── src/
│       ├── components/
│       ├── pages/
│       ├── services/
│       └── styles/
├── postman/
├── k6/
├── docs/
├── docker-compose.yml
└── README.md
```

## 10. Flujo GitHub para trabajar sin pisarse

Trabajar con `main` protegida por revisión y ramas por funcionalidad:

- `feat/backend-pacientes-mongo`
- `feat/backend-citas-consultas`
- `feat/frontend-base-pacientes`
- `feat/frontend-agenda-historial`

Comandos de ejemplo:

```bash
git clone https://github.com/Lowz13/Expedientes-Medicos-TEP.git
cd Expedientes-Medicos-TEP
git switch main
git pull origin main
git switch -c feat/frontend-base-pacientes
# realizar cambios
git status
git diff --check
git add .
git commit -m "feat(frontend): agregar listado de pacientes"
git push -u origin feat/frontend-base-pacientes
```

Abrir Pull Request a `main`, describir cambios, pruebas ejecutadas y capturas si hay interfaz. Solicitar revisión de otra persona; integrar ramas pequeñas con frecuencia. **No** usar `git add .` sin revisar `git status` para evitar subir `.env`, archivos clínicos, bases de datos o credenciales.

## 11. Plan de 12 días (ajustable)

| Días | Backend A | Backend B | Frontend A | Frontend B |
|---|---|---|---|---|
| 1–2 | Validar build, Mongo, contrato API | Definir cita/consulta, contrato API | Crear Vite, estructura y login visual | Bocetos, componentes y agenda visual |
| 3–5 | Pacientes, médicos, auth | Citas, conflictos y persistencia | Login y CRUD pacientes/médicos | Agenda conectada y manejo de errores |
| 6–8 | Permisos, validaciones, soporte | Consultas, PDF, archivos, correo | Pulir navegación y roles | Historial, consultas y descargas |
| 9–10 | Postman, revisión de seguridad | K6, Docker e integración | Pruebas UI y correcciones | Pruebas UI, README y diagramas |
| 11–12 | Ensayo y correcciones | Ensayo y correcciones | Presentación y demo | Presentación y demo |

**Punto de control del día 5:** registrar un paciente desde React, guardarlo en MongoDB, recargar y comprobar que sigue existiendo. Si no funciona, congelar extras.

## 12. Checklist de aceptación del MVP

- [ ] Compila y arranca backend.
- [ ] Frontend muestra la aplicación y consume API real.
- [ ] Login y permisos ADMIN/MEDICO comprobados.
- [ ] CRUD básico de pacientes y médicos persistente.
- [ ] Crear cita y rechazar horario ocupado.
- [ ] Registrar consulta y consultar historial.
- [ ] Adjuntar y descargar archivo con autorización.
- [ ] Generar PDF válido.
- [ ] Enviar correo real (no solo `println`).
- [ ] Docker Compose ejecuta servicios y conserva datos tras reinicio.
- [ ] Postman: casos positivos, negativos y de permisos.
- [ ] K6: prueba ejecutada, métricas guardadas y entorno documentado.
- [ ] README actualizado con 3 diagramas y arranque comprobado.
- [ ] Demo ensayada y presentación preparada.

## 13. Guía rápida de pruebas

**Postman:** probar login válido e inválido; crear/listar paciente; campos vacíos; cita ocupada; consultar historial con rol médico; intento de acceso sin permiso; subida de PDF permitido y archivo no permitido; respuesta PDF con tipo MIME correcto.

**K6:** comenzar con lecturas (`GET /api/pacientes`, `GET /api/citas`) usando credenciales de prueba y una base con datos sintéticos. Registrar latencia p95, tasa de errores y número de usuarios virtuales. No ejecutar carga contra correos reales ni contra un servidor compartido sin permiso.

## 14. Riesgos y soluciones

| Riesgo | Acción |
|---|---|
| Java 27 o Spring Boot incompatibles con el equipo | Verificar herramientas y acordar versiones el día 1 |
| React y API no coinciden | Contrato JSON y ejemplos compartidos desde el día 1 |
| Datos desaparecen | Sustituir `ArrayList` por MongoDB y configurar volumen persistente |
| Correo falla en demo | Probar SMTP antes del día 8 y documentar errores |
| Citas duplicadas | Validación backend + índice único parcial para citas programadas |
| Fuga de datos | Datos ficticios, roles backend y archivos privados |
| Falta de tiempo | No añadir funcionalidades fuera del MVP; reservar 2 días para ensayo |

## 15. Reunión diaria de 10 minutos

Cada persona responde: **qué terminé ayer, qué haré hoy y qué bloqueo tengo**. Actualizar tablero de tareas con columnas `Pendiente`, `En progreso`, `En revisión`, `Terminado`. Cada PR debe poder probarse y contener un alcance pequeño.

## 16. Guion mínimo de demo

1. Mostrar inicio de sesión de ADMIN.
2. Crear médico y paciente.
3. Agendar cita; enseñar correo recibido.
4. Intentar agendar mismo horario y mostrar rechazo.
5. Iniciar sesión como MEDICO.
6. Consultar historial y registrar consulta.
7. Adjuntar documento y descargar reporte PDF.
8. Mostrar persistencia y evidencias de Postman/K6.

Preparar datos sintéticos de respaldo, una copia local dockerizada y capturas de resultados de pruebas. No exponer contraseñas ni información médica real en la pantalla compartida.

## 17. Qué actualizar cada vez que cambie el código

- `README.md`: estado real, instalación, variables, puertos, diagramas y endpoints implementados.
- `GUIA_EQUIPO.md`: responsables, acuerdos, bloqueos y tareas.
- `postman/`: colección alineada con API real.
- `k6/`: scripts y resultados reproducibles.

**Definición de terminado:** código integrado en `main`, validado con prueba pertinente, sin secretos, con documentación actualizada y verificable durante la demo.
