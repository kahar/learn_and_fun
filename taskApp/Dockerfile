FROM openjdk:17-jdk-alpine
MAINTAINER kahar.github.io
ARG JAR_FILE
ENV JAR_PATH "/build/libs/Task app-0.0.1-SNAPSHOT.jar"
COPY ${PATH_WITH_SPACE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]