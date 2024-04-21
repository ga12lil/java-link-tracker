FROM openjdk:21-jdk-slim

COPY target/bot.jar bot.jar

ENV JAVA_OPTS=""

ENTRYPOINT exec java $JAVA_OPTS -jar /bot.jar