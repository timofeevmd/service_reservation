FROM maven:3.8.6-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/src/main/resources/db/migration /app/db/migration
ENTRYPOINT ["java", "-jar", "app.jar"]