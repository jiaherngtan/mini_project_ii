## build Angular
FROM node:19 AS angular

WORKDIR /app

COPY frontend/angular.json .
COPY frontend/package.json .
COPY frontend/package-lock.json .
COPY frontend/tsconfig.app.json .
COPY frontend/tsconfig.json .
COPY frontend/tsconfig.spec.json .
COPY frontend/src ./src

RUN npm install -g @angular/cli
RUN npm i
RUN ng build

###############################################################################################################
## build Spring Boot
FROM maven:3.9.0-eclipse-temurin-19 AS springboot

WORKDIR /app

## build
COPY backend/mvnw .
COPY backend/mvnw.cmd .
COPY backend/pom.xml .
COPY backend/src ./src

COPY --from=angular /app/dist/frontend ./src/main/resources/static

RUN mvn package -Dmaven.test.skip=true

###############################################################################################################
FROM eclipse-temurin:19-jre

WORKDIR /app

COPY --from=springboot /app/target/backend-0.0.1-SNAPSHOT.jar backend.jar



## run
ENV PORT=8080
ENV TMDB_API_KEY=${TMDB_API_KEY}
ENV ACCESS_KEY=${ACCESS_KEY}
ENV SECRET_KEY=${SECRET_KEY}
ENV SPRINGBOOT_DATASOURCE_USERNAME=root
ENV SPRINGBOOT_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV SPRINGBOOT_DATASOURCE_URL=jdbc:mysql://containers-us-west-194.railway.app:6989/railway
ENV SPRING_REDIS_PASSWORD=${SPRING_REDIS_PASSWORD}

EXPOSE ${PORT}

ENTRYPOINT java -Dserver.port=${PORT} -jar backend.jar