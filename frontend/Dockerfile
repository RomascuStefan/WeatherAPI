FROM node:latest AS builder

WORKDIR /app
COPY package*.json angular.json tsconfig.* ./
COPY src/ src/
COPY public/ public/
RUN npm install

ARG configuration=production
RUN npm run build --configuration=$configuration

FROM nginx:stable-alpine
COPY --from=builder /app/dist/weatherapp-fe/browser /usr/share/nginx/html
EXPOSE 80
