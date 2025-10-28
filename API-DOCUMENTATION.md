# Barbershop API - Documentación

API REST para gestión de barbería con autenticación JWT y arquitectura limpia.

## 🏗️ Arquitectura

El proyecto sigue **Clean Architecture** :

```
src/main/java/com/barbershop/api/
├── domain/                    # Capa de Dominio (Reglas de negocio)
│   ├── entities/             # Entidades del dominio (Usuario, Barbero, Cliente, Administrador)
│   ├── value_objects/        # Objetos de valor (Email, Password, Role)
│   └── repositories/         # Interfaces de repositorios (contratos)
│
├── infrastructure/           # Capa de Infraestructura (Detalles técnicos)
│   ├── persistence/
│   │   ├── mongodb/
│   │   │   ├── documents/   # DTOs de MongoDB
│   │   │   ├── mappers/     # Conversión Entity ↔ Document
│   │   │   └── repositories/ # Implementaciones de repositorios
│   │   └── spring/          # Spring Data MongoDB repositories
│   ├── security/            # JWT, filtros, configuración de seguridad
│   └── config/              # Configuraciones (Swagger, etc.)
│
├── service/                  # Capa de Aplicación (Casos de uso)
│   ├── dtos/                # DTOs de entrada/salida
│   ├── exceptions/          # Excepciones custom
│   └── interactors/         # Use cases (lógica de aplicación)
│
└── transport/               # Capa de Presentación (Interfaz)
    └── http/
        ├── controllers/     # REST Controllers
        └── handlers/        # Manejadores de excepciones
```

## 🔐 Autenticación JWT

### 1. Login
```bash
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "usuario@example.com",
  "password": "password123"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "usuario": {
    "dni": "12345678",
    "nombre": "Juan Pérez",
    "email": "usuario@example.com",
    "role": "BARBERO"
  }
}
```

### 2. Usar el token en requests
Incluir el token en el header `Authorization`:

```bash
GET /api/v1/usuarios/12345678
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 📋 Endpoints

### 🔓 **Públicos** (no requieren autenticación)

#### Registro de usuario
```bash
POST /api/v1/usuarios/registro
Content-Type: application/json

{
  "dni": "12345678",
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "password": "password123",
  "role": "CLIENTE"
}
```

#### Login
```bash
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "juan@example.com",
  "password": "password123"
}
```

#### Listar barberos
```bash
GET /api/v1/barberos
```

### 🔒 **Protegidos** (requieren JWT)

#### Obtener usuario por DNI
```bash
GET /api/v1/usuarios/{dni}
Authorization: Bearer {token}
```
Roles permitidos: `ADMINISTRADOR`, `BARBERO`

#### Obtener usuario por email
```bash
GET /api/v1/usuarios/email/{email}
Authorization: Bearer {token}
```
Roles permitidos: `ADMINISTRADOR`, `BARBERO`

#### Actualizar usuario
```bash
PUT /api/v1/usuarios/{dni}
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Juan Actualizado",
  "email": "nuevo@example.com",
  "password": "newpassword"
}
```
Roles permitidos: `ADMINISTRADOR`, `BARBERO`, `CLIENTE`

#### Eliminar usuario
```bash
DELETE /api/v1/usuarios/{dni}
Authorization: Bearer {token}
```
Roles permitidos: `ADMINISTRADOR` solamente

## 🎭 Roles

- **CLIENTE**: Usuario regular de la barbería
- **BARBERO**: Empleado barbero
- **ADMINISTRADOR**: Administrador del sistema

## 📊 Swagger/OpenAPI

Accede a la documentación interactiva en:

```
http://localhost:8080/doc/swagger-ui.html
```

En Swagger, haz clic en el botón **"Authorize"** y pega tu token JWT (sin "Bearer ") para probar los endpoints protegidos.

## 🚀 Ejecutar el proyecto

### 1. Configurar MongoDB
Edita `src/main/resources/application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://user:password@cluster.mongodb.net/barbershop
```

### 2. Compilar
```bash
./mvnw clean package
```

### 3. Ejecutar
```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 🧪 Ejemplos de uso

### 1. Registrar un barbero
```bash
curl -X POST http://localhost:8080/api/v1/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "dni": "87654321",
    "nombre": "Carlos Barbero",
    "email": "carlos@barbershop.com",
    "password": "barber123",
    "role": "BARBERO"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "carlos@barbershop.com",
    "password": "barber123"
  }'
```

### 3. Obtener lista de barberos
```bash
curl http://localhost:8080/api/v1/barberos
```

### 4. Actualizar usuario (con JWT)
```bash
curl -X PUT http://localhost:8080/api/v1/usuarios/87654321 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Carlos Barbero Actualizado"
  }'
```

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Security** con JWT
- **MongoDB** (Spring Data MongoDB)
- **Lombok**
- **Swagger/OpenAPI 3**
- **Maven**

## 📝 Notas de seguridad

- El JWT expira en 24 horas (configurable en `jwt.expiration`)
- La clave secreta JWT está en `application.properties` (cambiarla en producción)
- Las contraseñas se almacenan como texto plano (implementar BCrypt en producción)
- CORS está deshabilitado por defecto (configurar según necesidad)

## 🐛 Manejo de errores

Todas las excepciones se manejan de forma centralizada en `GlobalExceptionHandler`:

```json
{
  "timestamp": "2025-10-28T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Usuario no encontrado con DNI: 12345678"
}
```

---

**Desarrollado con Clean Architecture** 🏛️
