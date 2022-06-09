# Prime clinic
## Online doctor appoinment

Prime clinic is a platform to manage private clinic. 
The key feature of the project - videochat with doctor.


## Tech

The backend stack:
- Java 8 - main language
- Spring boot - rest api framework
- PostgreSQL - database
- Flyway - database migrations
- Swagger - documentation
- Firebase - extra database for chat

## Features

- Manage clinic staff
- Doctor is able to explose client medical cart
- Clients are able to find all the required information about clinic, payment etc.
- Clients are able to take an online appointment using chat or videochat

## Local start

Prime clinic requires [Java 8](https://www.oracle.com/java/technologies/downloads/) and [Maven](https://maven.apache.org/)  to run.

1) Run this command from the command line 

```sh
./mvnw dependency:go-offline -B
```
2) And then this from the project root 

```sh
java BackendApplication -dev
```

## Docker

Primedoc is very easy to install and deploy in a Docker container.

By default, the Docker will expose port 8080, so change this within the
Dockerfile if necessary. When ready, simply use the Dockerfile to
build the image.

```sh
cd primeclinic
docker build -t <youruser>/dillinger:${mvn} .
```

This will create the primedoc image and pull in the necessary dependencies.
Be sure to swap out `${mvn}` with the actual
version of Dillinger.

Once done, run the Docker image and map the port to whatever you wish on
your host. In this example, we simply map port 8000 of the host to
port 8080 of the Docker (or whatever port was exposed in the Dockerfile):

```sh
docker run -d -p 8000:8080 --restart=always --cap-add=SYS_ADMIN --name=primecoc
<youruser>/primedoc:${mvn}
```
Verify the deployment by navigating to your server address in
your preferred browser.

```sh
127.0.0.1:8000
```

## License

MIT



   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>


