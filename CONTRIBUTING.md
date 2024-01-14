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

## Advice on pull requests

Pull requests are the easiest way to contribute changes to git repos at Github.
They are the preferred contribution method, as they offer a nice way for
commenting and amending the proposed changes.

- You need a local "fork" of the Github repo.

- Use a `"feature branch"` for your changes. That separates the changes in the
  pull request from your other changes and makes it easy to edit/amend commits
  in the pull request. Workflow using "feature_x" as the example:
  - Update your local git fork to the tip (of the master, usually)
  - Create the feature branch with `git checkout -b feature_x`
  - Edit changes and commit them locally
  - Push them to your Github fork by `git push -u origin feature_x`. That
    creates the "feature_x" branch at your Github fork and sets it as the
    remote of this branch
  - When you now visit Github, you should see a proposal to create a pull
    request

- If you later need to add new commits to the pull request, you can simply
  commit the changes to the local branch and then use `git push` to
  automatically update the pull request.

- If you need to change something in the existing pull request (e.g. to add a
  missing signed-off-by line to the commit message), you can use `git push -f`
  to overwrite the original commits. That is easy and safe when using a feature
  branch. Example workflow:
  - Checkout the feature branch by `git checkout feature_x`
  - Edit changes and commit them locally. If you are just updating the commit
    message in the last commit, you can use `git commit --amend` to do that
  - If you added several new commits or made other changes that require
    cleaning up, you can use `git rebase -i HEAD~X` (X = number of commits to
    edit) to possibly squash some commits
  - Push the changed commits to Github with `git push -f` to overwrite the
    original commits in the "feature_x" branch with the new ones. The pull
    request gets automatically updated

- Please make sure your changes are thoroughly tested before submitting a pull request.

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


