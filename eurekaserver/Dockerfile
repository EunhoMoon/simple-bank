FROM gradle:8.10.2-jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./

COPY src ./src

RUN gradle clean build -x test

FROM openjdk:17-jdk-slim

MAINTAINER Motivank

COPY --from=builder /app/build/libs/eurekaserver-0.0.1-SNAPSHOT.jar /app/eurekaserver.jar

RUN apt-get update && apt-get install -y curl

ENTRYPOINT ["java", "-jar", "/app/eurekaserver.jar"]
