# Use the Eclipse Temurin JDK 17 Alpine image as the base image
FROM eclipse-temurin:17-jdk-alpine

# Create a volume named '/tmp' in the container
VOLUME /tmp

# Copy the JAR file(s) from the host machine into the container and rename it to 'app.jar'
COPY user_service/target/*.jar app.jar

# Set the default command to execute when the container starts. It runs the JAR file using the Java command
ENTRYPOINT ["java","-jar","/app.jar"]