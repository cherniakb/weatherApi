# weather-api

# Tech, Framework and other Dependencies

Java version: 21

Gradle version: 8.5

SpringBoot version: 3.2.2



# Running the service locally
The application can be started locally and accessed via the endpoint http://localhost:8080. The application can be
started in two ways:

- By running Application.java
- By running the gradle bootRun task from the project's root directory:

`gradlew infrastructure:authentication-service:bootRun`



# Database
The weather-api use H2 in memory database to simplify local running. The database console is enabled and can be accessed via the endpoint http://localhost:8080/h2-console. The JDBC URL is jdbc:h2:mem:testdb, the username is admin and the password is admin.

# REST API
The REST API documentation can be accessed via the endpoint http://localhost:8080/swagger-ui.html.