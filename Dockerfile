# Use a base image with Maven and JDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set the working directory for the build process
WORKDIR /app

# Copy the entire project to the working directory
COPY . .

# Build the project to generate the JAR file
RUN mvn clean package

# Use a smaller base image for the final build
FROM openjdk:21-jdk-slim

# Set the working directory for the final image
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/scm2.0-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
