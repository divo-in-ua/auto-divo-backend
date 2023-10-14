# Use Amazon Corretto 17 as the parent image
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY build/libs/divo-auto-1.jar /app/divo-auto.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "divo-auto.jar"]