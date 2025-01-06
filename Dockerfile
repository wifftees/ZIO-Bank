FROM openjdk:11-jre-slim
WORKDIR /app

# Copy the project files to the container
COPY . /app

# Run SBT assembly to build the fat JAR
# RUN sbt assembly

# Expose the port your application listens on
EXPOSE 8080

# Run the generated JAR
CMD ["java", "-jar", "/app/target/scala-2.13/bank-assembly-0.1.0-SNAPSHOT.jar"]
