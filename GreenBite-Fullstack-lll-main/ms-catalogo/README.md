# Microservicio de Catálogo — GreenBite

Gestiona el catálogo dinámico de productos. Usa MongoDB por su esquema flexible y escucha eventos Kafka de productores.

## Requisitos
- Java 17+
- Maven 3.8+
- MongoDB 6 en localhost:27017
- Kafka en localhost:9092

## Ejecución

```bash
mvn spring-boot:run
# Disponible en http://localhost:8082
```

## Pruebas unitarias

```bash
mvn test
# No requieren MongoDB ni Kafka activos (Mockito)
```

## Endpoints

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /api/catalogo | Catálogo completo de productos disponibles |
| GET | /api/catalogo?ciudad=Santiago | Catálogo filtrado por ciudad |
| PATCH | /api/catalogo/{id}/disponibilidad?disponible=false | Actualizar disponibilidad |

## Kafka Topics

| Topic | Rol | Descripción |
|-------|-----|-------------|
| inventario-productor | Consumer | Recibe actualizaciones de productores |
| catalogo-events | Producer | Publica cambios de disponibilidad |

## Patrones aplicados

- **Repository**: ProductoRepository — abstrae MongoDB
- **Service Layer**: CatalogoService — lógica centralizada
- **DTO**: ProductoDTO — control de campos expuestos
- **Event-Driven**: KafkaListener para actualizaciones asíncronas de inventario
