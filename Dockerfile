# Use the OpenJDK 19 image as the base image
FROM openjdk:19-jdk-slim

WORKDIR /app

# Copy the JAR file from the host machine to the container
COPY target/LPMS-LicencePlateManagementApp-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for the container
EXPOSE 8080

# Set the command to run when the container starts
CMD ["java", "-jar", "demo.jar"]
