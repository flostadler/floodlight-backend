FROM adoptopenjdk/openjdk12:jdk-12.0.1_12-alpine
VOLUME /tmp
COPY target/floodlight-1.0.0-SNAPSHOT-spring-boot.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]