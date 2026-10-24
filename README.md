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

## Historical project and configuration

This repository is retired. Its Git history has been cleaned of the former JWT signing secret, bundled TLS private key, and personal commit email. Existing clones should be replaced with a fresh clone.

Before running the application or integration tests, generate a fresh signing secret (`openssl rand -base64 64`) and supply it through `JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET`. There is no committed fallback. The JHipster generator configuration deliberately leaves `jwtSecretKey` empty; configure a new private secret when regenerating.

The optional production TLS example requires your own keystore through `TLS_KEY_STORE` (for example, `file:/private/path/keystore.p12`) and `TLS_KEY_STORE_PASSWORD`. No keystore is supplied. Never reuse the old signing secret or certificate/private key. Configuration and source checks were performed for this historical cleanup; the retired application was not deployed or integration tested.
