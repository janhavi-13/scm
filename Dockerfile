# Use a Maven image (e.g., Maven with OpenJDK 17)
FROM maven:3.8.6-openjdk-17 AS build

# Install OpenJDK 21
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package

# Use a smaller JDK image to run the JAR
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/scm2.0-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
