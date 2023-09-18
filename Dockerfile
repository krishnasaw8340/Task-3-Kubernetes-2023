FROM openjdk:17-jdk-alpine
LABEL authors="krish"
ARG JAR_FILE=target/*.jar
COPY target/kaiburrr-0.0.1-SNAPSHOT.jar kaiburr.jar
ENTRYPOINT ["java", "-jar", "/kaiburr.jar"]

