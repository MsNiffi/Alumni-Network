FROM gradle:jdk19 AS gradle
WORKDIR /app
COPY . .
RUN gradle bootJar

FROM amazoncorretto:19-alpine as runtime
WORKDIR /app
ENV PORT 8080
ARG JAR_FILE=/app/build/libs/*.jar
COPY --from=gradle ${JAR_FILE} /app/app.jar

RUN chown -R 1000:1000 /app
USER 1000:1000

ENTRYPOINT [ "java", "-jar", "-Dserver.port=${PORT}", "app.jar" ]