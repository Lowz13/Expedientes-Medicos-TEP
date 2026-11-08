# MediSync — Guía de trabajo del equipo

> **Proyecto académico · 4 integrantes (2 backend, 2 frontend) · Entrega: 1 de diciembre de 2026.**

## 1. Acuerdo principal: alcance cerrado

El equipo implementará **únicamente lo necesario para cumplir el proyecto y los criterios del profesor**. No se desarrollarán funciones extras aunque estén sugeridas en maquetas o documentación antigua. Toda tarea debe corresponder a: usuarios/roles ADMIN, RECEPCIONISTA y MEDICO; pacientes, médicos, citas, consultas e historial; carga/recuperación de imagen o PDF; reporte PDF; correo real; persistencia; despliegue Docker local o nube; Postman, k6, GitHub, tres diagramas, documentación y demostración.

**No desarrollar:** portal de pacientes, facturación, inventario, chat, calendario avanzado, notificaciones internas, recordatorios automáticos, analítica, auditoría como módulo, reportes administrativos adicionales ni otras integraciones. Los pacientes **no inician sesión**. La confirmación por correo cumple el requisito de notificación; no se crea una bandeja interna.

## 2. Punto de partida después del merge de reorganización

- Repositorio: https://github.com/Lowz13/Expedientes-Medicos-TEP
- Backend: `backend/`, Java 21, Spring Boot 4.1.1 y Gradle. **`./gradlew clean compileJava` se verificó con `BUILD SUCCESSFUL`.**
- Configuración MongoDB de desarrollo: `mongodb://localhost:27017/medisync_db`; **conexión funcional aún por verificar**.
- Controladores y DTO de pacientes y citas: código básico en memoria, pendiente de persistencia.
- `frontend/`, `postman/`, `k6/` y las carpetas Java de modelos/servicios/etc. están preparados pero no equivalen a funcionalidades implementadas.
- Identidad visual: diseño A generado previamente en Google Stitch. Reglas en [`DISENO.md`](DISENO.md).

## 3. Distribución de trabajo — mismos cuatro frentes, tareas detalladas

### Backend A — Datos, usuarios y seguridad

**Responsable principal de:** base de datos, usuarios/roles, pacientes y médicos.

| Prioridad | Tarea concreta | Entregable verificable |
|---|---|---|
| 1 | Configurar MongoDB y conexión Spring Data; sustituir `ArrayList` de pacientes | Datos persisten tras reiniciar |
| 2 | Modelos y repositorios de `usuarios`, `pacientes` y `medicos` | Colecciones y operaciones reales |
| 3 | CRUD de pacientes con validaciones de datos obligatorios | Endpoints probados en Postman |
| 4 | Gestión de médicos y asociación de su usuario de acceso | API funcional para frontend |
| 5 | Login/logout, hash BCrypt y roles `ADMIN`, `RECEPCIONISTA`, `MEDICO` | Acceso y restricciones verificables |
| 6 | Permisos: ADMIN gestiona cuentas/médicos; RECEPCIONISTA gestiona pacientes; MEDICO accede solo a clínica autorizada | Pruebas positivas y `401/403` |
| 7 | Acordar DTO y respuestas con Frontend A; documentar endpoints y variables necesarias | Contrato usable sin adivinanzas |

**No asignar a Backend A:** gráficos administrativos, auditoría independiente, portal de pacientes ni otros módulos de usuario.

### Backend B — Citas y atención clínica

**Responsable principal de:** citas, consultas, historial, archivos, PDF y correo.

| Prioridad | Tarea concreta | Entregable verificable |
|---|---|---|
| 1 | Migrar citas de `ArrayList` a MongoDB, asociadas a paciente y médico | Crear/listar citas persistentes |
| 2 | Validar horario del médico y rechazar cita duplicada; gestionar cancelación | Conflicto probado en Postman |
| 3 | Listar agenda según rol: ADMIN/RECEPCIONISTA operan; MEDICO consulta la propia | API y permisos probados |
| 4 | Modelo y registro de consulta con médico/paciente/cita | Consulta persistida |
| 5 | Consultar historial por paciente, protegiendo datos; impedir editar consultas de otro médico | Acceso médico comprobado |
| 6 | Subir y descargar imagen/PDF asociada a consulta o paciente, con control de acceso | Archivo recuperable |
| 7 | Generar **un reporte de consulta en PDF** descargable | PDF válido y legible |
| 8 | Configurar Gmail SMTP con variables de entorno y enviar confirmación real al crear cita | Mensaje recibido en correo de prueba |
| 9 | Acordar DTO y respuestas con Frontend B; documentar endpoints y evidencias | Contrato funcional |

**No asignar a Backend B:** recordatorios automáticos, centro de notificaciones, receta electrónica, otros reportes o calendario complejo.

### Frontend A — Estructura, acceso y administración

**Responsable principal de:** base React, navegación, login, pacientes y médicos.

| Prioridad | Tarea concreta | Entregable verificable |
|---|---|---|
| 1 | Inicializar React/Vite y estructura `components/`, `pages/`, `routes/`, `services/`, `styles/` | App arranca sin errores |
| 2 | Definir componentes compartidos `Sidebar`, `Header`, botones, formularios y tablas según Stitch A | Componentes reutilizables |
| 3 | Login y cierre de sesión, rutas protegidas y navegación según los tres roles | Flujo de acceso con API real |
| 4 | Pantallas ADMIN para gestionar pacientes y médicos | Altas/listados/edición conectados |
| 5 | Pantallas RECEPCIONISTA para registrar/consultar/editar datos generales de pacientes | Flujo conectado y autorizado |
| 6 | Estados de carga, validación y error para las pantallas a su cargo | Manejo de respuestas real |
| 7 | Verificar responsividad y coherencia del diseño A; capturas en PR | UI revisada |

**No asignar a Frontend A:** dashboards con gráficos, estadísticas, módulo de notificaciones, pantallas de pacientes con login.

### Frontend B — Citas, consultas y documentos

**Responsable principal de:** agenda, citas, historial, consultas, archivos y reporte PDF.

| Prioridad | Tarea concreta | Entregable verificable |
|---|---|---|
| 1 | Pantalla de citas en listado/tabla con filtros sencillos por fecha y médico | Consulta de agenda conectada |
| 2 | Registrar y cancelar citas como ADMIN/RECEPCIONISTA; mostrar conflicto de horario | Formulario y mensajes reales |
| 3 | Mostrar al MEDICO sus citas asignadas | Vista por rol conectada |
| 4 | Pantalla de historial clínico del paciente para MEDICO | Historial real desde API |
| 5 | Formulario de nueva consulta: diagnóstico, tratamiento y observaciones | Guarda consulta en MongoDB |
| 6 | Adjuntar imagen/PDF y mostrar/descargar documentos autorizados | Flujo completo de archivos |
| 7 | Acción de generación y descarga del reporte PDF | Descarga real, no simulada |
| 8 | Integrar componentes comunes con Frontend A y comprobar diseño A/responsividad | UI consistente |

**No asignar a Frontend B:** calendario drag-and-drop, recordatorios, mensajería o centro de notificaciones. El aviso por correo se envía desde Backend B.

### Responsabilidades compartidas y dependencias

| Actividad | Responsable de coordinar | Participación |
|---|---|---|
| Contratos API, DTO y permisos | Backend A + Backend B | Los dos frontend revisan antes de integrar |
| Diseño y componentes compartidos | Frontend A | Frontend B valida y reutiliza |
| Inicio de sesión y rutas protegidas | Backend A + Frontend A | Frontend B usa el mismo esquema |
| Datos y consultas clínicas | Backend B + Frontend B | Backend A apoya autorización |
| Postman | Ambos backend | Frontend verifica errores y payloads |
| k6 | Backend B | Backend A apoya endpoints/datos de prueba |
| Docker Compose o alternativa nube | Ambos backend | Frontend aporta pasos de build/ejecución |
| README, guía, diseño y tres diagramas | Todos | Cada persona documenta lo que modifica |
| Validación final y demo | Los cuatro | Cada uno conoce el flujo completo |

**Regla de integración:** no dar por concluida una pantalla con datos estáticos ni una API sin persistencia. Confirmar que React → API → MongoDB → React funciona para el flujo correspondiente.

## 4. Matriz mínima de roles

| Acción | ADMIN | RECEPCIONISTA | MEDICO |
|---|:---:|:---:|:---:|
| Gestionar cuentas y médicos | Sí | No | No |
| Datos generales de pacientes: alta/edición/consulta | Sí | Sí | Consulta necesaria |
| Crear/cancelar citas | Sí | Sí | No |
| Ver agenda | Sí | Sí | Propia |
| Consultar historial clínico | No | No | Sí |
| Registrar consultas | No | No | Propias |
| Adjuntar/descargar archivos clínicos | No | No | Sí, autorizado |
| Generar reporte clínico PDF | No | No | Sí |
| Modificar consultas ajenas | No | No | No |

Los permisos se implementan obligatoriamente en Spring Security/backend. El frontend solo adapta las opciones visibles.

## 5. Estructura del repositorio y acuerdos de código

```text
Expedientes-Medicos-TEP/
├── backend/
│   ├── src/main/java/mx/unam/aragon/tsp/medisync/
│   │   ├── config/
│   │   ├── security/
│   │   ├── controllers/
│   │   ├── dtos/
│   │   ├── models/
│   │   ├── repositories/
│   │   └── services/
│   └── build.gradle
├── frontend/
│   └── src/
│       ├── components/
│       ├── pages/admin/
│       ├── pages/recepcion/
│       ├── pages/medico/
│       ├── routes/
│       ├── services/
│       └── styles/
├── docs/
│   ├── GUIA_EQUIPO.md
│   ├── DISENO.md
│   └── diseño/
├── postman/
├── k6/
├── docker-compose.yml     # pendiente
├── .env.example           # pendiente
└── README.md
```

Las carpetas de frontend, los Dockerfile/Compose y `.env.example` se crearán cuando se implemente la parte correspondiente, **no con archivos ficticios para aparentar avance**.

Usar rutas `/api/...`, fechas inequívocas, DTO acordados y errores HTTP coherentes: `400`, `401`, `403`, `404`, `409`. Nunca exponer trazas o datos clínicos al cliente. Datos siempre ficticios y secretos solo en variables de entorno.

## 6. Convención obligatoria para nombrar ramas

**Formato:** `<tipo>/<area>-<descripcion-corta>`.

- Tipos: `feat` (funcionalidad requerida), `fix` (corrección), `docs` (documentación), `refactor` (reorganización sin alterar comportamiento), `test` (pruebas), `chore` (mantenimiento) o `build` (compilación/despliegue).
- Áreas: `backend`, `frontend`, `docs` o `infra` cuando ayude a identificarlas.
- Usar **minúsculas, sin espacios, sin acentos ni ñ**, palabras separadas por guiones `-`.
- Una rama por tarea concreta. Evitar nombres como `cambios`, `rama-final` o `todo`.

**Ejemplos válidos:**

```text
feat/backend-pacientes-mongodb
feat/backend-login-roles
feat/backend-citas-correo
feat/frontend-login
feat/frontend-agenda-citas
feat/frontend-historial-clinico
docs/actualizar-documentacion
fix/backend-validacion-horarios
test/backend-postman-citas
build/infra-docker-compose
```

## 7. Conventional Commits — mensajes en español

**Formato:** `<tipo>(<alcance>): <descripcion en español>`.

- El **tipo** conserva el identificador estándar en inglés (`feat`, `fix`, `docs`, `refactor`, `test`, `chore`, `build`, `style`, `perf`, `ci`).
- El **alcance** es opcional y preferentemente `backend`, `frontend`, `docs`, `infra`, `pacientes`, `citas`, etc.
- La **descripción va en español**, en minúscula al inicio, con verbo en infinitivo, sin punto final; debe describir lo realizado y no prometer lo pendiente.
- **No** agregar `feat` por tareas extra: `feat` solo representa funcionalidades dentro del alcance obligatorio.
- Para cambios incompatibles, comunicarlo explícitamente en el PR y usar `!` cuando corresponda (por ejemplo `refactor(api)!: cambiar estructura de respuesta`).

| Tipo | Cuándo usarlo | Ejemplo de commit |
|---|---|---|
| `feat` | Implementar un requisito | `feat(backend): guardar pacientes en mongodb` |
| `fix` | Corregir un defecto | `fix(citas): impedir horarios duplicados` |
| `docs` | Editar documentación | `docs: actualizar guia de equipo y diseno` |
| `refactor` | Cambiar estructura sin nuevas funciones | `refactor(frontend): reutilizar componente de tabla` |
| `test` | Crear/ajustar pruebas | `test(backend): agregar casos postman de permisos` |
| `chore` | Mantenimiento | `chore: limpiar archivos temporales` |
| `build` | Dependencias o build | `build(backend): configurar dependencia de correo` |
| `style` | Formato, sin cambio funcional | `style(frontend): ajustar espaciado de formularios` |

**Ejemplo:**

```bash
git add backend/src/main/java
 git commit -m "feat(backend): registrar pacientes en mongodb"
```

Antes de ejecutar `git add`, revisar siempre `git status` para no incluir secretos ni archivos generados.

## 8. Flujo completo Git y Pull Request hasta `main`

### 8.1 Actualizar `main` local y crear una rama nueva

```bash
git switch main
git pull --ff-only origin main
git switch -c docs/actualizar-documentacion
```

**Nunca desarrollar directamente en `main`.** Si otra persona está trabajando en el mismo archivo, coordinar antes de editar.

### 8.2 Trabajar y verificar

```bash
git status
git diff --check
# Ejecutar las pruebas pertinentes a la tarea
# backend: cd backend && ./gradlew clean compileJava
# frontend: ejecutar comandos de build/test cuando exista package.json
```

Revisar qué archivos cambiaron y que no haya `.env`, credenciales SMTP, información real de pacientes, builds ni `node_modules`.

### 8.3 Registrar commits pequeños y claros

```bash
git add README.md docs/GUIA_EQUIPO.md docs/DISENO.md
git commit -m "docs: actualizar documentacion y reglas del equipo"
git status
```

Cambiar las rutas de `git add` según la tarea. Se prefieren commits autocontenidos y revisables.

### 8.4 Publicar la rama

```bash
git push -u origin docs/actualizar-documentacion
```

Si se agregan correcciones al mismo PR, crear otro commit y ejecutar `git push` de nuevo.

### 8.5 Abrir Pull Request

En GitHub: **Pull requests → New pull request**, o el enlace que aparece después del push. Verificar **base: `main`** y **compare: la rama de trabajo**.

El PR debe incluir:

- Título claro, preferentemente con Conventional Commits en español.
- Objetivo y requisito académico que cubre.
- Archivos y comportamiento modificados; sin funcionalidades extra.
- Pruebas **realmente ejecutadas** y resultados; si algo no se probó, indicarlo.
- Capturas si cambia la interfaz, comparadas con [`DISENO.md`](DISENO.md).
- Dependencias o puntos pendientes y, cuando corresponda, vínculo al issue.

**Plantilla breve de PR:**

```markdown
## Objetivo
Explicar qué requisito del proyecto resuelve.

## Cambios
- ...

## Verificación
- Comando ejecutado y resultado: ...
- Capturas (si aplica): ...

## Fuera de alcance
No se agregaron funcionalidades extra.
```

### 8.6 Revisión, correcciones y merge

1. Solicitar revisión **al menos a un compañero que no haya escrito el cambio**.
2. Resolver comentarios y conflictos en la **misma rama**; volver a ejecutar las pruebas afectadas.
3. Confirmar que el PR no incluye secretos, cambios ajenos ni funcionalidades extras.
4. Comprobar los checks de GitHub si están configurados; no afirmar que existen si no los hay.
5. Tras aprobación del equipo, quien tenga permiso integra el PR a `main` con el método acordado, preferentemente **Squash and merge** para una tarea pequeña.
6. Confirmar que el PR aparece como **Merged** y que los archivos están en `main`.
7. Eliminar la rama remota desde GitHub si ya no se utilizará.

### 8.7 Actualizar repositorio local después del merge

```bash
git switch main
git pull --ff-only origin main
git branch -d docs/actualizar-documentacion
```

Si la rama se integró por squash y Git no reconoce sus commits como ancestros, `git branch -d` podría negarse. **Primero comprobar el merge y que no existan cambios pendientes**; solo entonces considerar `git branch -D nombre-rama`. No forzar borrados sin verificar.

**Nunca hacer `git push --force` a `main` ni resolver conflictos descartando trabajo de otros.**

## 9. Orden de integración y calendario de trabajo

La entrega es el **1 de diciembre de 2026**. La siguiente secuencia organiza dependencias, no garantiza que una tarea ya esté completada.

| Etapa | Backend A | Backend B | Frontend A | Frontend B |
|---|---|---|---|---|
| 1. Acuerdos y base | MongoDB y contrato usuarios/pacientes/médicos | Contrato citas/consultas/archivos | Vite, diseño base, rutas/login | Componentes compartidos y vistas de agenda |
| 2. Flujo administrativo | Persistencia, login y roles | Persistencia de citas, conflictos | Pacientes, médicos y vista recepcionista | Registrar/listar/cancelar citas |
| 3. Flujo clínico | Restricciones y apoyo a permisos | Historial, consultas, archivos, PDF, correo | Integración, validaciones y pulido | Historial, consulta y descarga PDF/archivos |
| 4. Verificación final | Postman de auth y CRUD | Postman citas/clínica, k6, despliegue | Responsividad y errores | Pruebas UI y documentación de flujos |
| 5. Entrega | Correcciones y demostración | Correcciones y demostración | Capturas y demostración | Capturas y demostración |

**Prioridad de integración:** primero demostrar que un paciente se registra desde React, permanece en MongoDB y aparece al recargar; luego conectar citas y finalmente el flujo clínico y entregables técnicos. No abrir trabajo opcional para “aprovechar tiempo”.

## 10. Criterios para dar una tarea por terminada

- [ ] Resuelve una tarea del alcance acordado, sin extras.
- [ ] Se probó localmente el flujo correspondiente.
- [ ] Si guarda datos, conserva información tras reinicio.
- [ ] Si usa roles, su autorización está en backend.
- [ ] Si cambia interfaz, cumple `DISENO.md` y responde en móvil.
- [ ] Si agrega API, su colección Postman está actualizada.
- [ ] Los secretos y datos personales reales no están en Git.
- [ ] La documentación técnica afectada está actualizada.
- [ ] PR revisado y fusionado a `main`.

**Aceptación de entrega:** login con tres roles; administración de pacientes/médicos/citas según permisos; médico consulta historial y registra consulta; carga/descarga de archivo; reporte PDF; correo recibido; persistencia; ejecución demostrable; pruebas Postman/k6 y tres diagramas actualizados. No exigir características ajenas al proyecto.

## 11. Comunicación y bloqueos

En cada reunión breve, cada integrante informa: tarea terminada, siguiente tarea y bloqueo concreto. Cuando backend y frontend compartan un flujo, acordar antes el JSON, la ruta, el estado HTTP y los permisos. Si una funcionalidad del diseño no corresponde a los requisitos, **no se implementa** y se documenta la exclusión. Los cambios de alcance solo pueden hacerse por acuerdo del equipo ante un requisito explícito del profesor.

## 12. Documentos que deben mantenerse al día

- `README.md`: estado real, ejecución comprobada, endpoints, tecnologías, tres diagramas y requisitos.
- `docs/GUIA_EQUIPO.md`: reparto de tareas, Git/PR, acuerdos y dependencias.
- `docs/DISENO.md`: colores, componentes, pantallas y criterios visuales Stitch A.
- `postman/` y `k6/`: pruebas ejecutables, sin secretos.
- `docs/diseño/`: capturas o exportaciones del diseño A cuando se incorporen.

No marcar como implementada ninguna parte que todavía sea una propuesta o una carpeta vacía.
