# Shopping List #

*Shopping list* is a simple app that helps you to manage your list.

The idea of this app was born during quarantine so I decided to get my hands dirty.

## The code ##

The repository is organizated in two folders: one for the app and one for the server.

### App ###

The app is build using Ionic 5 and Angular 10.
A special thanks to my friend Alessandro Valenti for angular advice.

Before running the app take a look to *configuration.json_sample* inside assets folder and set variables.

After that run:

```bash
npm install
```

Your app is ready for running, you just need to run:

```bash
ionic serve
```

### Server ###

The server is build using Spring Boot.

Before running the server take a look to *application.properties_sample* inside the resources folder and set the variables.

For running the server use Spring Boot VS Code extensions, Eclipse or build the code and run on a Tomcat.
