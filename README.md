# ConsultaEndViaCEP

ConsultaEndViaCEP is a simple Java application built with Spring Boot that allows you to query the address information of a Brazilian ZIP code using the [ViaCEP API].

## Prerequisites
Before you can run this application, you'll need to have the following installed on your machine:

* Java 17 or higher
* Docker

## Running the application
To run the application, you can either build and run the Docker image, or run the application directly from the source code.

## Running the Docker image
1. Clone this repository and navigate to the project directory in your terminal.
2. Build the Docker image with the following command:

    `docker build -t consulta-end-viacep .`

3. Run the Docker container with the following command:

    `docker run -p 8080:8080 consulta-end-viacep`

The application will be accessible at http://localhost:8080.

## Running the application from source

1. Clone this repository and navigate to the project directory in your terminal.

2. Run the application with the following command:
    
    `./gradlew bootRun`

The application will be accessible at http://localhost:8080.

## Testing the application with JUnit 5

ConsultaEndViaCEP includes JUnit 5 tests to ensure the application is working as expected.

To run the tests, follow these steps:

1. Clone this repository and navigate to the project directory in your terminal.
1. Run the tests with the following command:
    
        `./gradlew test`

The tests will run and the results will be displayed in the terminal.

That's it! You're now ready to use and test ConsultaEndViaCEP.


[ViaCEP API]: <https://viacep.com.br/>