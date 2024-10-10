FROM gradle:8.10.2-jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./

COPY src ./src

RUN gradle clean build -x test

FROM openjdk:17-jdk-slim

MAINTAINER Motivank

COPY --from=builder /app/build/libs/cards-0.0.1-SNAPSHOT.jar /app/cards.jar

ENTRYPOINT ["java", "-jar", "/app/cards.jar"]
