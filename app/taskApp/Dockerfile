FROM openjdk:17-jdk-alpine
MAINTAINER kahar.github.io
ENV JAR_FILE "/build/libs/taskApp-0.0.1-SNAPSHOT.jar"
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]