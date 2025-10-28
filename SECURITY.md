# 🔐 Configuración de Seguridad - Barbershop API

## ✅ Cambios Implementados

### 1. Variables de Entorno (`.env`)

Se han movido todas las credenciales sensibles a un archivo `.env` que **NO se sube a Git**.

**Archivos creados:**
- `.env` - Contiene tus credenciales reales (ignorado por Git)
- `.env.example` - Plantilla para otros desarrolladores (SÍ se sube a Git)
- `ENV_SETUP.md` - Documentación detallada

### 2. Actualización de `.gitignore`

Se agregó `.env` para evitar que las credenciales se suban accidentalmente:

```gitignore
### Environment Variables ###
.env
```

### 3. Actualización de `application.properties`

Las credenciales hardcodeadas fueron reemplazadas por variables de entorno:

```properties
# Antes (❌ INSEGURO):
spring.data.mongodb.uri=mongodb+srv://usuario:password@cluster...
jwt.secret=mi-secreto-super-largo...

# Ahora (✅ SEGURO):
spring.data.mongodb.uri=${MONGODB_URI}
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}
```

### 4. Dependencia `spring-dotenv`

Se agregó al `pom.xml` para cargar automáticamente el archivo `.env`:

```xml
<dependency>
    <groupId>me.paulschwarz</groupId>
    <artifactId>spring-dotenv</artifactId>
    <version>4.0.0</version>
</dependency>
```

## 🚀 Uso para Desarrolladores

### Primera vez:

1. Copia el archivo de ejemplo:
   ```bash
   cp .env.example .env
   ```

2. Edita `.env` con tus credenciales:
   ```bash
   nano .env
   ```

3. Ejecuta la aplicación normalmente:
   ```bash
   ./mvnw spring-boot:run
   ```

### Variables disponibles:

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `MONGODB_URI` | URI completa de MongoDB | `mongodb+srv://user:pass@cluster.mongodb.net/db` |
| `JWT_SECRET` | Clave secreta JWT (256+ bits) | `tu-clave-super-secreta-aqui` |
| `JWT_EXPIRATION` | Duración del token (ms) | `86400000` (24 horas) |

## 🔒 Seguridad en Producción

### Variables de Sistema (Linux/Mac):

```bash
export MONGODB_URI="mongodb+srv://..."
export JWT_SECRET="tu-secreto-produccion"
export JWT_EXPIRATION="86400000"
```

### Docker:

```yaml
# docker-compose.yml
environment:
  - MONGODB_URI=${MONGODB_URI}
  - JWT_SECRET=${JWT_SECRET}
  - JWT_EXPIRATION=${JWT_EXPIRATION}
```

### Kubernetes:

```yaml
# secrets.yaml
apiVersion: v1
kind: Secret
metadata:
  name: barbershop-secrets
type: Opaque
stringData:
  MONGODB_URI: "mongodb+srv://..."
  JWT_SECRET: "..."
  JWT_EXPIRATION: "86400000"
```

### Cloud Providers:

- **AWS**: AWS Secrets Manager / Parameter Store
- **Azure**: Azure Key Vault
- **Google Cloud**: Secret Manager
- **Heroku**: Config Vars
- **Railway/Render**: Environment Variables en dashboard

## ⚠️ IMPORTANTE

### ❌ NUNCA hagas esto:

1. Subir `.env` a Git
2. Compartir credenciales por chat/email
3. Hardcodear secretos en el código
4. Usar la misma clave en desarrollo y producción
5. Commitear el archivo `application.properties` con credenciales

### ✅ SÍ haz esto:

1. Usa `.env` para desarrollo local
2. Comparte solo `.env.example`
3. Usa gestores de secretos en producción
4. Rota las claves JWT periódicamente
5. Genera claves fuertes: `openssl rand -base64 32`

## 🛠️ Generación de JWT Secret Seguro

```bash
# Linux/Mac
openssl rand -base64 32

# Node.js
node -e "console.log(require('crypto').randomBytes(32).toString('base64'))"

# Python
python -c "import secrets; print(secrets.token_urlsafe(32))"
```

## 📋 Checklist de Seguridad

- [x] `.env` en `.gitignore`
- [x] `application.properties` sin credenciales
- [x] `.env.example` documentado
- [x] Dependencia `spring-dotenv` agregada
- [ ] Rotar JWT secret antes de producción
- [ ] Configurar variables en servidor de producción
- [ ] Habilitar HTTPS en producción
- [ ] Implementar rate limiting
- [ ] Configurar CORS apropiadamente

## 📚 Referencias

- [Spring Boot External Configuration](https://docs.spring.io/spring-boot/reference/features/external-config.html)
- [OWASP Secrets Management](https://cheatsheetseries.owasp.org/cheatsheets/Secrets_Management_Cheat_Sheet.html)
- [12 Factor App - Config](https://12factor.net/config)
