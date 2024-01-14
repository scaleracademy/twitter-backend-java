# Contributing Guidelines

Welcome to the Twitter Backend Java repository! We appreciate your interest in contributing to the development of this Twitter clone application. By contributing, you are helping us make this project better.

## Contributing
We welcome and encourage contributions! To contribute:

1. Fork the repository.
2. Create a branch: ```git checkout -b feature/your-feature-name```.
3. Make your changes and commit them: ```git commit -m 'Add some feature'```.
4. Push to the branch: ```git push origin feature/your-feature-name```.
5. Submit a pull request.
> Please make sure to follow our code of conduct and contribution guidelines.

## Code Style
Follow the existing code style of the project.
Consistent code style makes the codebase more readable and maintainable.

## Issues and Discussions
- Feel free to open an `issue` for `bug reports`, `feature requests`, or any other relevant topics.
- Engage in `discussions` and share your insights.

## Getting Started

To start contributing, follow these steps:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/scaleracademy/twitter-backend-java
   ```

2. **Navigate to the Project Directory:**
    ```bash
    cd twitter-backend-java
    ```

3. **Install Dependencies:**
    ```bash
    mvn install
    ```

4. **Load the .env file:**
    ```bash
    cp .env.example .env
    source .env
    ```

5. **Update `application.properties`:**
   > Configure the database connection details in the application.properties file:
    ```properties
    spring.profiles.active=dev
    ```

6. **Start the Application:**
   > To start the application, run the following command (after configuring the database connection details):
    ```bash
    mvn spring-boot:run
    ```

   > Alternatively, if you have `Docker Compose` installed:
    ```bash
    docker-compose up -d
    ```

> The application will run on port 8080, and you can access it at `http://localhost:8080` in your web browser.


### Using the API

Please refer to the [Wiki](https://github.com/scaleracademy/twitter-backend-java/wiki/API-Endpoints) for detailed documentation on the API endpoints.


