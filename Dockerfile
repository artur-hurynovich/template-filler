FROM maven:3.9.1-eclipse-temurin-17-alpine as build-stage

WORKDIR /usr/app

COPY src ./src/
COPY pom.xml .

RUN mvn package -DskipTests

RUN rm -r src
RUN rm -f pom.xml

FROM openjdk:17 as run-stage

WORKDIR /usr/app

COPY --from=build-stage /usr/app/target/template-filler.jar .

EXPOSE 8080

CMD ["java", "-jar", "template-filler.jar"]
