FROM openjdk:17
COPY build/libs/mini_project-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "mini_project-0.0.1-SNAPSHOT.jar"]