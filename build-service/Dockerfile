FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY catalog-service/pom.xml catalog-service/pom.xml
COPY compatibility-service/pom.xml compatibility-service/pom.xml
COPY build-service/pom.xml build-service/pom.xml

COPY build-service/src build-service/src

RUN mvn -pl build-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build-service/target/build-service-*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]