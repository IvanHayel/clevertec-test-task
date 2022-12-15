FROM gradle:7.5.1-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build -x test --no-daemon

FROM openjdk:17-slim-bullseye
LABEL maintainer="Ivan Hayel <i.hayel.dev@gmail.com>"
EXPOSE 8080
RUN mkdir /app
ARG JAR_FILE=/home/gradle/src/build/libs/clevertec-test-task-0.0.1.jar
COPY --from=build ${JAR_FILE} /app/clevertec-test-task.jar
ENTRYPOINT ["java", "-jar", "/app/clevertec-test-task.jar"]