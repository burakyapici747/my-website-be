# Build aşaması
FROM gradle:7.5.1-jdk17 AS build

WORKDIR /build

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY src/ src/

RUN ./gradlew build --no-daemon -x test

# Runtime aşaması
FROM eclipse-temurin:17-jdk-jammy

ARG PROFILE=dev
ARG APP_VERSION=1.0.0

RUN mkdir /app
COPY --from=build /build/target/spring-boot-application-*.jar /app/

EXPOSE 8080

ENV DB_URL=jdbc:mysql://db-mysql:3306/mywebsite
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url${DB_URL}", "/app/spring-boot-application-${JAR_VERSION}.jar"]
