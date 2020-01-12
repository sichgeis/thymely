# Thymely a Time Tacking Web App

This application is a sample app to showcase a tech stack of:

- Postgres in the data tier
- Java using Spring-Boot in the logic layer
- Angular 8 in the frontend

## Development

To install the project dependencies run this command. Rerun only when dependencies change in [package.json](client/package.json) change.

    npm install

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    npm start

## Building for production

### Packaging as jar

To build the final jar and optimize the thymely application for production, run:

    ./mvnw -Pprod clean verify

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.jar

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify

## Using Docker to simplify development

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw -Pprod verify jib:dockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d
