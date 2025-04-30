FROM openjdk:17-jdk-slim

WORKDIR /app

# Create log directory
RUN mkdir -p /app/logs

# Copy the JAR file
#COPY target/*.jar app.jar
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]