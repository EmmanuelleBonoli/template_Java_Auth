# Backend project template

This repository is a SpringBoot project template shipped with a base configuration in order to have a clean base to work with, for you and your teammates.

This project has been initialized from SpringBoot version 3.4

🔥 To properly understand the structure of this project, open the pom.xml file and take your time reviewing it.

## Branch protection rules
- You need to manually add your protection rules from your repository instance settings :
    - Lock pushes on production, staging & development ;
    - Make PR mandatory towards production, staging & development
    - PR, any branch ➡️ development :
        - All contributors must add their review.
    - PR, development ➡️ staging :
        - Only the `development` branch can make a PR to the `staging` branch ;
        - All contributors must add their review.
    - PR, staging ➡️ production :
        - Only the `staging` branch can make a PR to the `production` branch ;
        - All contributors must add their review ;
        - The owner of the repository must approve the PR.

## Same code format : Prettier
- This project uses Prettier.
- Make sure to install it by running `npm install` after cloning this repository. 
- Prettier makes sure that everyone has the same code format and standardize it. It helps developers to be focused on creating feature, not formatting. The same format for everyone also means easier code reviews.
- The configuration file is in the `source directory` > `.prettierrc.json`.
- You can install Prettier plugin on your IDE if you want, it would help you to configure when prettier should run. For instance, you can choose to let it run after every file save. But you are not forced to install the plugin because **Prettier will run every time after you are trying to commit**.
- You can run Prettier at any time with the following command: `npm run prettier`

⚠️ You cannot edit Prettier config file. Otherwise your commit won't pass.


## Same code rules : Checkstyle
- This project uses Checkstyle The configuration file is in the `source directory` > `checkstyle.xml`.
- Checkstyle prevent a lot of typo mistakes & future bugs to come. It enforces code rules & conventions.
- It uses all recommended configurations from Checkstyle.
- You can run Checkstyle at any time with the following command: `mvn checkstyle:check`

⚠️ You cannot edit Checkstyle config file. Otherwise your commit won't pass.

## Clean git workflow : Husky
This project uses Husky. It enforces rules to keep your git workflow clean & consistent. It mainly uses the pre-commit git hook. Before each commit, Husky:
- Makes sure you did not modify Prettier, Eslint or Husky configuration files ;
- Run Prettier and Eslint automatically ;
- Validate branch name and help you to rename it with good practices if test fails.

## Automatic documentation : Swagger
- This project uses Swagger : a dynamic documentation of your whole application.
- It builds a clean documentation on your `http://localhost:8080/swagger-ui/index.html#/` and gives you a nice overview of your application.
- This endpoint is **disabled for staging and production**.

## Environments
- This application is created around 3 environments : `development`, `staging` and `production`.
- They are configured in the `src/main/resources/*.yml files. 
- You can swap between them by changing the `spring.profiles.active` value in the `application.yml` file.
- Some checks are made to make sure that anyone can't add a new environement in a snap.
- These environments are fundamentals ; be sure to know what you do when you manipulate them.
- Environment variables need to be set in .env file. Please create a file in `root directory` > `.env`. You can find an example in the `source directory` > `.env.sample`.

## Main Dependencies
- This project is a basic template for a Web API. It uses `Spring MVC` ;
- This project is connected to a Mysql Database through JDBC using `MySQL driver`. Connection details are in the `src/main/resources/application.yml` file ;
- This project uses the `ORM` `Hibernate`, a implementation of Spring Data specification ;
- This project is trying to avoid boilerplate code with `Lombok` ;
- This project is secured with `Spring Security`. It is a huge dependency, **Disabled by default**, helping you to be focused on creating your API first.
- Other dependencies are minor. You can find them in the `pom.xml` file.

## Tests
- To test the application, run the following command: `mvn test`.
- It uses Surefire & Junit.

## CI/CD
- This project has a Dockerfile to build an image of the application
- This project has a docker-compose file, still empty.
- Both of them are mandatory, not only to deploy but also to build & test the application. We will complete them later.
- This project has a CI/CD pipeline in `source directory` > `.github` > `workflows`.

Flow details:
- When a push is triggered on any branch (except staging or prod), the CI is triggered and run unit tests & integration tests.
- When a push is triggered on staging, the CI is triggered and run unit tests, integration tests **and E2E tests**.
- If it fails, details can be found on the github repository > actions tab.
- If everything is fine, then the CD is triggered and an image of the application is built with Docker and then deployed on DockerHub.
- The VPS is configured to listen successfully pushes on staging branch by a webhook, and then pull the docker image from DockerHub, stops the current running docker container & start the new one.
- User only need to refresh its browser & taadaa : the frontend application on staging environnement is now successfully deployed 🎉

👉 The pipeline is the same with the production branch. 








