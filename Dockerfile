FROM openjdk:17-jdk-slim

# Add a volume to store logs
VOLUME /tmp

# Set the environment variable to allow the jar to be executed
ARG JAR_FILE=target/*.jar

# Copy the jar file into the container
COPY ${JAR_FILE} app.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
