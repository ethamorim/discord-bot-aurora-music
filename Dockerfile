FROM gradle:8.7-jdk17 AS build
WORKDIR /app

RUN gradle build --no-daemon

FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/build/libs/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]