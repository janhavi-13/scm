# Use a base image with Java runtime (JDK 21)
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file to the working directory
COPY target/scm2.0-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
