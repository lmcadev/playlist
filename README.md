# Proyecto de prueba técnica Playlist

Proyecto de gestión de playlists desarrollada como prueba técnica en un monorepositorio que incluye un Backend en Java con Maven, JPA, Spring Security, expuesto atravez de un API REST. Un Fronent en ANgular y utilizando como base de datos H2, Las pruebas del API son realizadas en Postman

# Tecnologias

### Backend
- Modelo arquitectónico: capas
- Arquitectura de Software: MVC
- Java 21
- Maven
- Spring Boot 3.2
- JPA
- API REST

### Frontend

- Angular 21
- CSS
- HTML
- Bootstrap 5

### Persistencia
- H2 DB


# Credenciales

### Consola BD h2

- URL: http://localhost:8080/h2-console
- usuario: admin
- contraseña: 123
- JDBC URL: jdbc:h2:mem:testdb

### Login web o postman

- usuario: admin@quipux.com
- contraseña: admin123

# Cómo Ejecutar el Proyecto

## Opción 1: Ejecución Local (Desarrollo)

### Backend
```bash
cd Backend
mvn spring-boot:run
```
URL backend: http://localhost:8080

### Frontend
```bash
cd Frontend/playlist
npm install
npm start
```
URL frontend: http://localhost:4200

---

## Opción 2: Ejecución con Docker

### Requisitos
- Docker Desktop instalado

### Comandos
```bash
# Construir y ejecutar
docker-compose up --build

**Acceso:**
- Frontend: http://localhost
- Backend: http://localhost:8080
- Consola H2: http://localhost:8080/h2-console

```

---


# Coleccion POSTMAN

- https://www.postman.com/lmcadev-bf32a4df-9679015/workspace/quipux/collection/50328885-6f0a7f00-e8d4-4ef3-96e7-a55e115537c3?action=share&source=copy-link&creator=50328885