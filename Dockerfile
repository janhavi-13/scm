# Use a Maven image to build the project
FROM adoptopenjdk:21-jdk-hotspot


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
