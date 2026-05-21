# Microservicio de Suscripciones — GreenBite

Gestiona el ciclo de vida de suscripciones (crear, pausar, cancelar). Usa PostgreSQL y publica eventos en Kafka.

## Requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL 15 en localhost:5432
- Kafka en localhost:9092

## Configurar base de datos

```sql
CREATE DATABASE greenbite_suscripciones;
CREATE USER greenbite WITH PASSWORD 'greenbite123';
GRANT ALL PRIVILEGES ON DATABASE greenbite_suscripciones TO greenbite;
```

## Ejecución

```bash
mvn spring-boot:run
# Disponible en http://localhost:8081
```

## Pruebas unitarias

```bash
mvn test
# Los tests usan Mockito — no requieren PostgreSQL ni Kafka activos
```

## Endpoints

| Método | URL | Descripción |
|--------|-----|-------------|
| POST | /api/suscripciones | Crear suscripción |
| GET | /api/suscripciones/usuario/{userId} | Obtener suscripción activa |
| PATCH | /api/suscripciones/{id}/pausar | Pausar suscripción |
| DELETE | /api/suscripciones/{id} | Cancelar suscripción |

## Patrones aplicados

- **Repository**: SuscripcionRepository — abstrae PostgreSQL
- **Service Layer**: SuscripcionService — lógica de negocio centralizada
- **DTO**: SuscripcionDTO — control de exposición de datos
- **Event-Driven**: publica eventos en Kafka topic `suscripcion-events`
