FROM openjdk:8-jre-alpine

ARG JAR_FILE

ADD ${JAR_FILE} /app.jar

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    SLEEP=0 \
    SPRING_PROFILE="default" \
    JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS \
     -Djava.security.egd=file:/dev/./urandom \
     -Dspring.profiles.active=$SPRING_PROFILE \
     -jar app.jar

