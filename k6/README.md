# Pruebas de Integración y Rendimiento - Módulo Clínico (BB-08)

## Requisitos previos
- Contenedor de MongoDB activo (`docker compose up -d`).
- Backend de Spring Boot corriendo en local (`./gradlew bootRun`).
- [k6](https://k6.io/) instalado en el sistema.

## Ejecución de Postman
1. Importar la colección `postman/medisync-backend-b.postman_collection.json`.
2. Abrir **Collection Runner**, adjuntar el archivo de prueba en la petición `3.1 Subir Archivo Clínico` y ejecutar toda la colección.

## Ejecución de pruebas de carga (k6)
Desde la raíz del proyecto, ejecutar:
```bash
k6 run k6/medisync-test.js