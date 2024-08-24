# Stage 1: Build the project
FROM openjdk:21-jdk AS build

# Install Maven dependencies and tools
RUN apt-get update && apt-get install -y wget unzip \
    # Download and install Maven
    && wget https://downloads.apache.org/maven/maven-3/3.9.1/binaries/apache-maven-3.9.1-bin.zip \
    && unzip apache-maven-3.9.1-bin.zip -d /opt \
    && ln -s /opt/apache-maven-3.9.1/bin/mvn /usr/bin/mvn \
    # Clean up
    && rm apache-maven-3.9.1-bin.zip

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Verify Maven installation
RUN mvn --version

# Build the project
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/scm2.0-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
