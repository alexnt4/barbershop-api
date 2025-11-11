# 💈 Barbershop API

API REST para gestión de una barbería desarrollada con Spring Boot siguiendo los principios de Clean Architecture.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Ejecución](#-ejecución)
- [Documentación API](#-documentación-api)
- [Arquitectura](#-arquitectura)
- [Seguridad](#-seguridad)
- [Testing](#-testing)
- [Contribuir](#-contribuir)

## ✨ Características

- 🔐 **Autenticación JWT** - Sistema de autenticación seguro con tokens JWT
- 👥 **Gestión de Usuarios** - CRUD completo para usuarios con diferentes roles
- 💇 **Gestión de Barberos** - Sistema especializado para barberos con ratings y disponibilidad
- � **Gestión de Inventario** - Control completo de productos con entradas/salidas automáticas
- 📊 **Reportes de Inventario** - Estadísticas y análisis del stock actual
- 🔴 **Caché con Redis** - Optimización de rendimiento en consultas frecuentes
- �🔒 **Control de Acceso Basado en Roles** - Tres roles: CLIENTE, BARBERO, ADMINISTRADOR
- 📚 **Documentación Swagger** - API completamente documentada con OpenAPI 3.0
- 🗄️ **MongoDB** - Base de datos NoSQL para almacenamiento flexible
- 🏗️ **Clean Architecture** - Código mantenible y testeable

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.5.7**
  - Spring Security
  - Spring Data MongoDB
  - Spring Data Redis
  - Spring Cache
  - Spring Web
- **MongoDB Atlas**
- **Redis** - Caché en memoria
- **JWT (jjwt 0.12.6)**
- **Lombok**
- **Swagger/OpenAPI 3**
- **Maven**

## 📦 Requisitos Previos

- Java 21 o superior
- Maven 3.6+
- Cuenta en MongoDB Atlas (o MongoDB local)
- Redis (Docker recomendado)
- Git (opcional)

## 🚀 Instalación

1. **Clonar el repositorio**
```bash
git clone <url-del-repositorio>
cd barbershop-api
```

2. **Instalar dependencias**
```bash
./mvnw clean install
```

## ⚙️ Configuración

### Variables de Entorno

1. **Crear archivo `.env`** en la raíz del proyecto:

```env
# MongoDB Configuration
MONGODB_URI=mongodb+srv://usuario:password@cluster.mongodb.net/barbershop

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=

# JWT Configuration
JWT_SECRET=tu-clave-secreta-super-segura-minimo-256-bits-para-hs256
JWT_EXPIRATION=86400000
```

2. **Configurar MongoDB Atlas**:
   - Crea una cuenta en [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
   - Crea un cluster gratuito
   - Obtén tu connection string
   - Reemplaza `MONGODB_URI` en el archivo `.env`

3. **Instalar y configurar Redis** (ver [REDIS-SETUP.md](REDIS-SETUP.md)):
   
   **Opción rápida con Docker**:
   ```bash
   docker run -d --name redis-barbershop -p 6379:6379 redis:latest
   ```
   
   **Verificar**:
   ```bash
   docker exec -it redis-barbershop redis-cli ping
   # Respuesta esperada: PONG
   ```

4. **Generar JWT Secret**:
```bash
# Genera una clave segura de 256 bits
openssl rand -base64 32
```

### Archivo application.properties

El archivo `src/main/resources/application.properties` ya está configurado para usar las variables de entorno del archivo `.env`:

```properties
# MongoDB
spring.data.mongodb.uri=${MONGODB_URI}

# JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
```

## 🏃 Ejecución

### Desarrollo

```bash
./mvnw spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

### Producción

```bash
# Compilar
./mvnw clean package -DskipTests

# Ejecutar
java -jar target/barbershop-api-0.0.1-SNAPSHOT.jar
```

## 📖 Documentación API

### Swagger UI

Accede a la documentación interactiva en:
```
http://localhost:8080/swagger-ui.html
```

### Endpoints Principales

#### Autenticación
- `POST /api/v1/auth/login` - Iniciar sesión y obtener token JWT

#### Usuarios
- `POST /api/v1/usuarios/registro` - Registrar nuevo usuario (público)
- `GET /api/v1/usuarios/{dni}` - Obtener usuario por DNI
- `GET /api/v1/usuarios/email/{email}` - Obtener usuario por email
- `PUT /api/v1/usuarios/{dni}` - Actualizar usuario
- `DELETE /api/v1/usuarios/{dni}` - Eliminar usuario (solo ADMIN)

#### Barberos
- `GET /api/v1/barberos` - Listar todos los barberos (público)

#### Productos e Inventario
- `POST /api/v1/productos/registro` - Registrar nuevo producto
- `GET /api/v1/productos` - Listar todos los productos (con caché Redis)
- `GET /api/v1/productos/{id}` - Obtener producto por ID
- `POST /api/v1/productos/movimiento` - Registrar entrada/salida de inventario
- `GET /api/v1/productos/stock-bajo` - Productos con stock bajo
- `GET /api/v1/productos/reporte` - Reporte completo de inventario (con caché Redis)

### Autenticación en Swagger

1. **Login**: Usa el endpoint `/api/v1/auth/login` con tus credenciales
2. **Copiar Token**: Copia el valor del campo `token` de la respuesta
3. **Autorizar**: Click en el botón "Authorize" 🔓
4. **Pegar Token**: Pega el token (sin "Bearer ")
5. **Confirmar**: Click en "Authorize" y luego "Close"

### Ejemplo de Registro

```json
POST /api/v1/usuarios/registro

{
  "dni": "12345678",
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "password": "Password123",
  "role": "BARBERO"
}
```

**Requisitos de contraseña**:
- Mínimo 8 caracteres
- Al menos 1 mayúscula
- Al menos 1 minúscula
- Al menos 1 número
- Sin espacios

### Ejemplo de Login

```json
POST /api/v1/auth/login

{
  "email": "juan@example.com",
  "password": "Password123"
}
```

Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "usuario": {
    "dni": "12345678",
    "nombre": "Juan Pérez",
    "email": "juan@example.com",
    "role": "BARBERO"
  }
}
```

## 🏛️ Arquitectura

El proyecto sigue los principios de **Clean Architecture** con 4 capas:

```
src/main/java/com/barbershop/api/
├── domain/                    # Capa de Dominio
│   ├── entities/              # Entidades del negocio
│   │   ├── Usuario.java
│   │   └── especializaciones/
│   │       ├── Barbero.java
│   │       ├── Cliente.java
│   │       └── Administrador.java
│   ├── value_objects/         # Objetos de valor
│   │   ├── Email.java
│   │   ├── Password.java
│   │   └── Role.java
│   └── repositories/          # Interfaces de repositorios
│
├── infrastructure/            # Capa de Infraestructura
│   ├── persistence/
│   │   ├── mongodb/
│   │   │   ├── documents/     # Documentos MongoDB
│   │   │   ├── mappers/       # Conversión Entity ↔ Document
│   │   │   └── repositories/  # Implementación de repositorios
│   │   └── spring/            # Spring Data Repositories
│   ├── security/              # JWT y Spring Security
│   │   ├── JwtProvider.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── SecurityConfig.java
│   └── config/                # Configuraciones
│
├── service/                   # Capa de Servicio (Casos de Uso)
│   ├── dtos/                  # Data Transfer Objects
│   ├── exceptions/            # Excepciones del negocio
│   └── interactors/           # Casos de uso
│
└── transport/                 # Capa de Transporte
    └── http/
        ├── controllers/       # Controllers REST
        └── handlers/          # Manejo de excepciones
```

### Principios Aplicados

- **Separación de Responsabilidades**: Cada capa tiene una responsabilidad específica
- **Inversión de Dependencias**: Las capas externas dependen de las internas
- **Independencia de Framework**: La lógica de negocio no depende de Spring
- **Testeable**: Fácil de testear gracias a la separación de capas

## 🔒 Seguridad

### Variables de Entorno

**IMPORTANTE**: Nunca subas el archivo `.env` al repositorio. Este archivo contiene credenciales sensibles.

El archivo `.gitignore` ya incluye:
```gitignore
.env
.env.local
.env.*.local
```

### JWT Configuration

- **Algoritmo**: HS256
- **Expiración**: 24 horas (configurable)
- **Secret**: Mínimo 256 bits (debe cambiarse en producción)

### Roles y Permisos

| Endpoint | CLIENTE | BARBERO | ADMIN |
|----------|---------|---------|-------|
| POST /auth/login | ✅ | ✅ | ✅ |
| POST /usuarios/registro | ✅ | ✅ | ✅ |
| GET /usuarios/{dni} | ❌ | ✅ | ✅ |
| PUT /usuarios/{dni} | ✅* | ✅* | ✅ |
| DELETE /usuarios/{dni} | ❌ | ❌ | ✅ |
| GET /barberos | ✅ | ✅ | ✅ |

*Solo puede actualizar su propio usuario

### Contraseñas

Las contraseñas se validan con el siguiente formato:
- Mínimo 8 caracteres
- Al menos 1 letra mayúscula
- Al menos 1 letra minúscula
- Al menos 1 número
- Sin espacios en blanco

**Ejemplos válidos**: `Password123`, `Barber2024`, `Secure1Pass`

## 🧪 Testing

```bash
# Ejecutar tests
./mvnw test

# Ejecutar tests con coverage
./mvnw test jacoco:report

# Compilar sin tests
./mvnw clean package -DskipTests
```

## 📝 Roadmap

- [ ] Implementar BCrypt para encriptación de contraseñas
- [ ] Agregar validaciones con `@Valid` en DTOs
- [ ] Implementar paginación en listados
- [ ] Agregar tests unitarios e integración
- [ ] Sistema de citas para clientes
- [ ] Gestión de servicios y precios
- [ ] Sistema de notificaciones
- [ ] Métricas con Actuator

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la [MIT License](LICENSE).

## 👨‍💻 Autor

**Alex**

---

⭐ Si este proyecto te ha sido útil, considera darle una estrella!

## 🆘 Soporte

Si tienes alguna pregunta o problema:

1. Revisa la [documentación API](http://localhost:8080/swagger-ui.html)
2. Consulta el archivo [API-DOCUMENTATION.md](API-DOCUMENTATION.md)
3. Abre un issue en GitHub

## 🔧 Troubleshooting

### Error: "No static resource swagger-ui.html"
- Verifica que la dependencia sea `springdoc-openapi-starter-webmvc-ui`
- Reinicia la aplicación

### Error: "403 Forbidden"
- Asegúrate de estar autenticado con el token JWT
- Verifica que tu rol tenga permisos para el endpoint

### Error: "Password validation failed"
- La contraseña debe tener mínimo 8 caracteres
- Debe contener al menos 1 mayúscula, 1 minúscula y 1 número

### Error: "Connection to MongoDB failed"
- Verifica que el `MONGODB_URI` en `.env` sea correcto
- Asegúrate de tener conexión a Internet
- Verifica que tu IP esté en la whitelist de MongoDB Atlas

### Error: "Unable to connect to Redis"
- Verifica que Redis esté corriendo: `docker ps | findstr redis`
- Reinicia Redis: `docker restart redis-barbershop`
- Consulta la [guía completa de Redis](REDIS-SETUP.md)

---

## 🔴 Redis Cache

Este proyecto utiliza Redis para optimizar el rendimiento de consultas frecuentes:

### Endpoints Cacheados:
- `GET /productos` - Lista completa (TTL: 5 min)
- `GET /productos/reporte` - Reporte de inventario (TTL: 5 min)

### Invalidación Automática:
El caché se limpia automáticamente al:
- Registrar nuevos productos
- Registrar movimientos de inventario (entrada/salida)

### Instalación Rápida:
```bash
# Docker (recomendado)
docker run -d --name redis-barbershop -p 6379:6379 redis:latest

# Verificar
docker exec -it redis-barbershop redis-cli ping
```

📖 **Guía completa**: Ver [REDIS-SETUP.md](REDIS-SETUP.md) para instalación detallada en Windows/Linux/macOS.
- La contraseña debe tener mínimo 8 caracteres
- Debe contener al menos 1 mayúscula, 1 minúscula y 1 número

### Error: "Connection to MongoDB failed"
- Verifica que el `MONGODB_URI` en `.env` sea correcto
- Asegúrate de tener conexión a Internet
- Verifica que tu IP esté en la whitelist de MongoDB Atlas
