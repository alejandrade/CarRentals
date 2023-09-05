# Use Amazon Corretto 17 as the base image
FROM amazoncorretto:17.0.8-al2-native-jdk

# Create the /log directory in the root of the image
RUN mkdir -p /log && chmod 755 /log

# Copy your application JAR or files
COPY build/libs/your-application.jar /app.jar

# Set the working directory
WORKDIR /

# Define the command to run your application
CMD ["java", "-jar", "/app.jar"]
