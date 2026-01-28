# Etapa 1: Build
FROM node:20-alpine AS build
WORKDIR /app

# Copiar package files
COPY package*.json ./

# Instalar dependencias
RUN npm ci

# Copiar código fuente
COPY . .

# Build de producción
RUN npm run build

# Etapa 2: Runtime con Nginx
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Eliminar archivos default de Nginx
RUN rm -rf ./*

# Copiar archivos build desde etapa anterior
COPY --from=build /app/dist/playlist/browser ./

# Copiar configuración de Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exponer puerto
EXPOSE 80

# Comando de inicio
CMD ["nginx", "-g", "daemon off;"]
