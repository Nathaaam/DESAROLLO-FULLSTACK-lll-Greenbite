# GreenBite Frontend

Componente frontend React para la plataforma GreenBite.

## Requisitos
- Node.js 18+
- npm 9+

## Instalación y ejecución

```bash
npm install
npm start         # http://localhost:3000
```

## Pruebas unitarias

```bash
npm test
```

## Patrones de diseño implementados

| Patrón | Dónde |
|--------|-------|
| Container/Presentational | SubscriptionContainer + SubscriptionCard |
| Custom Hook | useSubscription, useCatalog, useGreenPoints |
| Observer (Context API) | AppContext + useAppContext |

## Variables de entorno

Crear `.env` en la raíz:
```
REACT_APP_BFF_URL=http://localhost:8080/api
```
