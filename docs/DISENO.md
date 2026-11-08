# MediSync — Reglas de diseño e interfaz

> **Referencia:** diseño A seleccionado en Google Stitch. Este documento es la guía compartida para traducir las pantallas acordadas a React. **El diseño no autoriza agregar funcionalidades fuera del alcance académico.**

## 1. Objetivo y fuente de verdad

Mantener una apariencia clínica, limpia, profesional, minimalista y consistente en español (México). Las capturas/exportaciones del **diseño A de Google Stitch** son la referencia visual para distribución y contenido de las pantallas. Cuando una maqueta muestre una función extra, prevalece el alcance del [README](../README.md) y se elimina dicha función de la implementación.

Las pantallas no deben inventar módulos nuevos: únicamente representan autenticación, gestión de pacientes/médicos/citas, consultas, historial, archivos y reporte PDF.

## 2. Identidad visual

| Token | Color | Uso |
|---|---|---|
| `--color-primary` | `#176B87` | Navegación, botones principales, elementos activos |
| `--color-primary-hover` | `#13566D` | Hover del botón principal |
| `--color-secondary` | `#149C91` | Acentos y acciones secundarias justificadas |
| `--color-background` | `#F5F8FB` | Fondo general |
| `--color-surface` | `#FFFFFF` | Tarjetas, formularios, tablas |
| `--color-text` | `#203047` | Texto principal |
| `--color-muted` | `#64748B` | Texto secundario |
| `--color-border` | `#E2E8F0` | Bordes y divisores |
| `--color-success` | `#10B981` | Confirmaciones correctas |
| `--color-warning` | `#F59E0B` | Advertencias |
| `--color-error` | `#EF4444` | Errores y acciones destructivas |
| `--color-info` | `#0284C7` | Información contextual |

**Tipografía:** `Inter`, con alternativas del sistema `Arial, sans-serif`. Respetar jerarquía consistente de títulos, etiquetas y texto secundario. No mezclar familias tipográficas arbitrariamente.

### Variables CSS de referencia

```css
:root {
  --color-primary: #176B87;
  --color-primary-hover: #13566D;
  --color-secondary: #149C91;
  --color-background: #F5F8FB;
  --color-surface: #FFFFFF;
  --color-text: #203047;
  --color-muted: #64748B;
  --color-border: #E2E8F0;
  --color-success: #10B981;
  --color-warning: #F59E0B;
  --color-error: #EF4444;
  --color-info: #0284C7;
  --radius-card: 12px;
  --radius-control: 8px;
  --sidebar-width: 260px;
  --header-height: 64px;
  --font-app: "Inter", Arial, sans-serif;
}
```

Estos valores son la **base acordada**; pequeños ajustes responsivos se permiten si mantienen la apariencia y el comportamiento del diseño A.

## 3. Estructura visual y componentes

- **Login:** composición dividida en dos columnas en escritorio, con área de identidad/ilustración y formulario. En móvil debe adaptarse a una columna sin desplazamiento horizontal.
- **Aplicación interna:** barra lateral de aproximadamente **260 px** y encabezado de **64 px** en escritorio; contenido en fondo claro y tarjetas blancas.
- **Tarjetas:** bordes redondeados de **12 px**. **Controles, campos y botones:** radio de **8 px**.
- **Tablas:** encabezados claros, separación legible y acciones alineadas. Permitir desplazamiento horizontal únicamente dentro de tablas cuando sea imprescindible en móvil.
- **Formularios:** etiquetas visibles, campos agrupados, validación junto al campo y acciones Guardar/Cancelar coherentes.
- **Estados:** carga, vacío, éxito y error con textos en español. No usar solo color para indicar información.
- **Navegación:** elementos disponibles según rol, con nombres y ubicación consistentes. La autorización real corresponde a Spring Boot.

Componentes React compartidos que **sí sirven a las pantallas requeridas**: `Sidebar`, `Header`, `Button`, `Input`, `Select`, `Table`, `Modal` (solo si una confirmación lo requiere), `Alert`, `ProtectedRoute`. No crear componentes o módulos que no se utilicen en el flujo académico.

## 4. Pantallas permitidas según rol

| Pantalla | Contenido imprescindible | Rol | Responsable frontend |
|---|---|---|---|
| Login | Correo, contraseña, error de acceso | Todos | Frontend A |
| Panel principal | Navegación y acceso a módulos autorizados; sin métricas inventadas | Todos | Frontend A (base), B (integración) |
| Pacientes | Listado, búsqueda sencilla, alta, edición y consulta de datos generales | ADMIN / RECEPCIONISTA | Frontend A |
| Médicos | Listado, alta/edición y datos necesarios para agenda | ADMIN; consulta necesaria para RECEPCIONISTA | Frontend A |
| Citas / agenda | Listado por fecha y médico, registrar/cancelar según permiso; médico ve su agenda | ADMIN / RECEPCIONISTA / MEDICO (consulta) | Frontend B |
| Historial clínico | Consultas del paciente autorizado | MEDICO | Frontend B |
| Nueva consulta | Formulario de diagnóstico, tratamiento y observaciones | MEDICO | Frontend B |
| Archivos y reporte PDF | Adjuntar imagen/PDF, consultar/descargar autorizado y descargar reporte | MEDICO | Frontend B |

Los permisos anteriores reflejan el alcance del sistema; pueden aparecer distintas vistas dentro de la misma página según el rol, sin crear pantallas innecesarias.

## 5. Qué NO se implementa aunque aparezca en Stitch

- **Centro de notificaciones o campana funcional:** el requisito de notificación se cumple mediante el **correo real de confirmación**. No crear bandeja interna.
- Calendario complejo con arrastrar y soltar: **agenda en listado o tabla por fecha/médico**.
- Gráficos, KPIs, analítica y reportes administrativos que no sean el PDF requerido.
- Chat, portal de pacientes, recetas digitales, pagos, inventario y recordatorios automáticos.
- Animaciones complejas, bibliotecas visuales adicionales sin necesidad o datos simulados presentados como reales.

## 6. Reglas de implementación frontend

1. Usar **React + Vite**, navegación con **React Router** y consumo de la API mediante **Axios**. Consolidar estilos en `frontend/src/styles/`; usar Bootstrap y CSS propio con criterio consistente.
2. Mantener componentes compartidos en `frontend/src/components/`, páginas por rol en `frontend/src/pages/admin/`, `recepcion/` y `medico/`, rutas en `frontend/src/routes/` y cliente API en `frontend/src/services/` cuando se inicialice la aplicación.
3. No replicar lógica de autorización en el frontend como única defensa. Mostrar solo acciones permitidas y manejar `401`, `403`, validaciones y problemas de red.
4. Diseño adaptable en escritorio, tableta y móvil; sin desbordamiento horizontal de la página, excepto tablas contenidas.
5. Etiquetas asociadas a los campos, foco visible, navegación por teclado, mensajes comprensibles y contraste suficiente. No depender de iconos sin texto accesible.
6. Usar terminología unificada: **Paciente, Médico, Recepcionista, Administrador, Cita, Consulta, Historial clínico, Documento**.
7. Usar datos ficticios para desarrollo y capturas. No incluir imágenes de pacientes reales ni expedientes sensibles.
8. Antes de aceptar un PR visual, comparar el resultado con el diseño A y comprobar el flujo funcional correspondiente.

## 7. Checklist de revisión visual por PR

- [ ] La pantalla pertenece al alcance acordado.
- [ ] Usa los colores y tipografía de esta guía.
- [ ] Sigue el layout del diseño A de Stitch.
- [ ] Reutiliza componentes compartidos y no duplica estilos innecesariamente.
- [ ] Funciona en pantalla de escritorio y móvil.
- [ ] Muestra estados de carga, vacío y error cuando corresponde.
- [ ] Respeta las acciones visibles para ADMIN, RECEPCIONISTA y MEDICO.
- [ ] Está conectada con la API real o se identifica claramente como pendiente.
- [ ] El PR incluye capturas de la pantalla y explicación de lo verificado.

## 8. Control de cambios

Cualquier ajuste de colores, tipografía, disposición o componentes se acuerda entre los dos integrantes de frontend y se documenta aquí **antes del merge**. Los cambios que alteren una funcionalidad o un rol deben revisarse también con backend. No sustituir el diseño A por una nueva propuesta visual sin acuerdo del equipo.
