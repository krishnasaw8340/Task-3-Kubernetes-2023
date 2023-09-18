FROM openjdk:11
COPY target/kaiburrr-0.0.1-SNAPSHOT.jar kaiburr.jar
ENTRYPOINT ["java", "-jar", "/kaiburr.jar"]

