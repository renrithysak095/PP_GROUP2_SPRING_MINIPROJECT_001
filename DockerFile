FROM openjdk:17
COPY target/mini_project-0.0.1-SNAPSHOT.jar MINIPROJECT.jar
ENTRYPOINT ["java", "-jar", "MINIPROJECT.jar"]