# Stage 1: Build the project
FROM maven:3.9.1-openjdk-17 AS build

# Install OpenJDK 21
RUN apt-get update && apt-get install -y wget \
    && wget https://download.java.net/java/GA/jdk21/35/ndk21-linux-x64_bin.tar.gz \
    && tar -xzf jdk-21_linux-x64_bin.tar.gz -C /opt \
    && update-alternatives --install /usr/bin/java java /opt/jdk-21/bin/java 1 \
    && update-alternatives --install /usr/bin/javac javac /opt/jdk-21/bin/javac 1

# Set JAVA_HOME
ENV JAVA_HOME=/opt/jdk-21

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

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
