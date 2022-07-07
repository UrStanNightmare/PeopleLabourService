FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
EXPOSE 27020
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Xmx2g","-jar","/app/app.jar"]
