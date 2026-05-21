# GreenBite BFF (Backend For Frontend)

Servicio Spring Boot que agrega respuestas de múltiples microservicios en un único endpoint optimizado para el frontend React.

## Requisitos
- Java 17+
- Maven 3.8+
- Microservicios ms-suscripciones (puerto 8081) y ms-catalogo (puerto 8082) en ejecución

## Ejecución local

```bash
mvn spring-boot:run
# Disponible en http://localhost:8080
```

## Compilar y ejecutar JAR

```bash
mvn clean package
java -jar target/greenbite-bff-1.0.0.jar
```

## Pruebas

```bash
mvn test
```

## Endpoints

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /api/dashboard/{userId} | Dashboard completo del usuario |
| GET | /actuator/health | Health check para Kubernetes |

## Patrones aplicados

- **BFF**: agrega suscripción + catálogo en una sola respuesta
- **Service Layer**: lógica de orquestación en DashboardService, no en el controlador
- **DTO**: DashboardResponseDTO controla qué campos se exponen
