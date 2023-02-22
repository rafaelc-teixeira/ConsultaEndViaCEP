# Use a base image with Amazon Corretto 11 installed
FROM amazoncorretto:17.0.4

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper files to the container
COPY gradle gradle
COPY gradlew build.gradle settings.gradle ./

RUN yum install -y unzip

RUN yum install -y wget

# Download and install Gradle 7.3.3
RUN wget https://services.gradle.org/distributions/gradle-7.3.3-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-*.zip \
    && rm -rf /tmp/*

ENV PATH="${PATH}:/opt/gradle/gradle-7.3.3/bin/"

# Copy the application source code to the container
COPY build/libs/ConsultaEndViaCEP-0.0.1-SNAPSHOT.jar consulta-end-cep-app.jar

# Build the application using Gradle
RUN ./gradlew build

# Expose the port on which the application will run
EXPOSE 8080

# Start the Spring Boot application when the container starts
CMD ["java", "-jar", "consulta-end-cep-app.jar"]