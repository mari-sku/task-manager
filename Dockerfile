# Maven Build (Java 17)
FROM maven:3.9.7-eclipse-temurin-17 AS build

WORKDIR /home/app
# Copy source code and pom.xml
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Runtime Image (Java 17)
FROM eclipse-temurin:17-jre

# Copy the built jar from build stage (use wildcard to be robust against version changes)
COPY --from=build /home/app/target/*.jar /usr/local/lib/taskmanager.jar

# Expose the port your app runs on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "/usr/local/lib/taskmanager.jar"]