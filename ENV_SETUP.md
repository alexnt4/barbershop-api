# Barbershop API - Environment Variables

Este proyecto utiliza variables de entorno para configuración sensible.

## Configuración Inicial

1. **Copia el archivo de ejemplo:**
   ```bash
   cp .env.example .env
   ```

2. **Edita `.env` con tus credenciales reales:**
   - `MONGODB_URI`: Tu URI de conexión a MongoDB Atlas
   - `JWT_SECRET`: Una clave secreta fuerte (mínimo 256 bits)
   - `JWT_EXPIRATION`: Tiempo de expiración del token en milisegundos (default: 24 horas)

## Variables de Entorno

### MongoDB
- `MONGODB_URI`: URI de conexión completa a MongoDB
  - Formato: `mongodb+srv://usuario:contraseña@cluster.mongodb.net/database?appName=app`

### JWT
- `JWT_SECRET`: Clave secreta para firmar tokens JWT
  - Requisito: Mínimo 256 bits para HS256
  - Genera una con: `openssl rand -base64 32`
- `JWT_EXPIRATION`: Duración del token en milisegundos
  - Default: `86400000` (24 horas)

## Seguridad

⚠️ **IMPORTANTE:**
- **NUNCA** subas el archivo `.env` a Git
- El archivo `.env` está en `.gitignore`
- Comparte solo `.env.example` con el equipo
- En producción, usa variables de entorno del sistema o secretos del servidor

## Ejecución

Las variables se cargan automáticamente al iniciar la aplicación gracias a `spring-dotenv`.

```bash
./mvnw spring-boot:run
```
