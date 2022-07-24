FROM openjdk:18-jdk-alpine
LABEL maintainer="he305@mail.ru"
VOLUME /tmp

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
