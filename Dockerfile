# Use a base image with Maven and JDK 21
FROM maven:3.9.9-openjdk-21

# Set the working directory
WORKDIR /app

# Copy the source code to the container
COPY . .

# Build the project with verbose output for debugging
RUN mvn clean package -e -X

# Use a smaller base image for the final build
FROM openjdk:21-jdk-slim

# Set the working directory for the final image
WORKDIR /app

# Copy the JAR file to the working directory
COPY target/scm2.0-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
