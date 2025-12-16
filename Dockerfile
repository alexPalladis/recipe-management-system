# ---- build stage ----
FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /app

# copy Maven wrapper + pom first for better caching
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

# copy source and build
COPY src src
RUN ./mvnw -DskipTests package

# ---- run stage ----
FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
