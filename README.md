# Flatbnb 🏠

**Aplicación estilo red social para alquiler de viviendas vacacionales**, desarrollada con **Spring**, **Angular**, y Docker.

## 📋 Descripción
Flatbnb permite a usuarios publicar, buscar y reservar viviendas vacacionales, con funcionalidades sociales como comentarios y valoraciones.

## 🛠 Tecnologías
- **Backend**: Java, Spring Boot  
- **Frontend**: Angular (TypeScript, HTML, CSS)  
- **Contenedores**: Docker  
- Base de datos y otros servicios (detalles en Dependencias)

## 📁 Estructura del Proyecto
- `flatbnb` – backend (Spring Boot REST API)  
- `flatbnb-angular` – frontend (Angular)  
- `Flatbnb.postman_collection.json` – colección API para Postman  
- `Modelo de datos flatbnb.png` – modelo ER de la base de datos  
- `Guion de Memorias Flatbnb.pdf` – diseño funcional/documentación  
- Archivos de organización: tareas, Figma, pautas

## ⚙️ Requisitos
- Java 11+  
- Node.js & npm/yarn  
- Docker & Docker Compose  
- PostgreSQL (u otra BD relacional)

## 🚀 Instalación & Uso

### Opción A: Con Docker Compose
```bash
git clone https://github.com/pablocamara9/Flatbnb.git
cd Flatbnb
docker-compose up --build
```
- Backend en `http://localhost:8080`  
- Frontend en `http://localhost:4200`

### Opción B: Manual
1. **Backend**:
   ```bash
   cd flatbnb
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
2. **Frontend**:
   ```bash
   cd flatbnb-angular
   npm install
   ng serve
   ```
3. Importa la colección Postman (`Flatbnb.postman_collection.json`) y prueba los endpoints.

## ✅ Funcionalidades
- Registro e inicio de sesión de usuarios  
- CRUD sobre viviendas  
- Búsqueda y filtrado por ubicación y características  
- Reservas con fechas y disponibilidad  
- Valoraciones y comentarios  
- Perfil y panel de usuario
