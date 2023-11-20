## Twitter Backend Java

This repository contains the backend code for a Twitter clone application. The application is built using Java, Spring Boot, and Spring Data JPA.

### Getting Started

To get started with the Twitter Backend Java application, you will need to:

Clone the repository:
```shell
git clone https://github.com/scaleracademy/twitter-backend-java
```
Navigate to the project directory:
```shell
cd twitter-backend-java
```
Install the project dependencies:
```shell
mvn install
```


Update the application.properties file to configure the database connection details:
```shell
spring.profiles.active=dev
```

Start the application:
To start the application, run the following command, after you have the database connection details configured:
```shell
mvn spring-boot:run
```

Or if you have docker compose installed, you can run the following command to start the application:
```shell
docker-compose up -d
```
The application will start running on port 8080. You can access the application at http://localhost:8080 in your web browser.

### Using the API

Please refer to the [Wiki](https://github.com/scaleracademy/twitter-backend-java/wiki/API-Endpoints) for detailed documentation on the API endpoints.


### Contributing

We welcome contributions to the Twitter Backend Java application. If you would like to contribute, please fork the repository and submit a pull request.

### Support

If you have any questions or need help using the Twitter Backend Java application, please feel free to open an issue on GitHub.
