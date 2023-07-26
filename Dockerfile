# Use an OpenJDK base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY Controller/target/Controller-0.0.1-SNAPSHOT.war app.war


ENV SPRING_PROFILES_ACTIVE=prod


# Expose the port your application runs on
#EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "app.war"]
