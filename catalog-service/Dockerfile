FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY catalog-service/pom.xml catalog-service/pom.xml
COPY compatibility-service/pom.xml compatibility-service/pom.xml
COPY build-service/pom.xml build-service/pom.xml

COPY catalog-service/src catalog-service/src

RUN mvn -pl catalog-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/catalog-service/target/catalog-service-*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]