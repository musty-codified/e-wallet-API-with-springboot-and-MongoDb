# EWallet-Backend-API
The backend api for a digital wallet application that uses MongoDB as the database.
The application enable users to create and manage their digital wallet, add funds to it, and make transactions.


## Technology ##
Following tools and libraries were used during the development of the API :
- **Java 17** - Java version
- **Spring Boot** - Server-side framework
- **Build Tool: Maven**
- **MongoDB** - Database
- **Swagger** - API documentation [here](http://localhost:9090/swagger-ui/index.html#/)
- **JWT** - Authentication mechanism for REST APIs
- **Docker** - Containerizing framework
- **Memcached** - distributed memory-caching service


### Authentication and Authorization
Uses Spring Security with JWT for stateless authentication and authorization.

### Deployment
The application can be deployed on any Java Servlet container, or docker containers.

## Response and Exception Handling ##
API response are all being sent in a uniform manner allowing us to create uniform objects which result in a response as shown below :

[//]: # (```)

[//]: # ({)

[//]: # (    "message": "Request successful",)

[//]: # (    "status": true,)

[//]: # (    "data": {)

[//]: # (        "body": "1st comment ",)

[//]: # (        "userId": "UUakBFkkmY",)

[//]: # (        "postId": 1,)

[//]: # (        "username": "John",)

[//]: # (        "commentLikeResponses": [)

[//]: # (            {)

[//]: # (                "id": 1,)

[//]: # (                "liked": false,)

[//]: # (                "likesCount": 0,)

[//]: # (                "postId": null,)

[//]: # (                "userId": "UUakBFkkmY")

[//]: # (            })

[//]: # (        ])

[//]: # (    })

[//]: # (})

[//]: # (```)

And when there is an exception, the following responses are sent back (result of "/api/v1/auth/resend-token" POST request):

[//]: # (```)

[//]: # ({)

[//]: # (    "timeStamp": "2023-08-06T09:10:40.910+00:00",)

[//]: # (    "message": "User is not found",)

[//]: # (    "debugMessage": "User not found")

[//]: # (})

[//]: # (```)

## Running the server locally ##
* Ensure Memcached is installed and running on your machine before you run this service.
* **Clone the repository:** git clone https://github.com/musty-codified/e-wallet-API-with-springboot-and-MongoDb.git
* **Build the project using maven:** mvn clean install or (compile and package)
* **Run the application:** mvn spring-boot:run

You may Use the following commands:

```
maven clean compile
```
This will compile the project.
```
maven package
```
This will package the application and generate the Jar file
Next, to run the Spring Boot app from a command line you can use the java -jar command and the name of the jar file.

```
java -jar target/EWallet-API-0.0.1-SNAPSHOT.jar
```

Alternatively, you can also use Maven plugin to run the app. Use the below example to run your Spring Boot app with Maven plugin :
```
mvn spring-boot:run
```

[//]: # (## Running the server in a Docker Container ##)

[//]: # (Make sure the docker desktop is up and running in your machine before building the container image from the docker file :)

[//]: # (run the following command :)

[//]: # (```)

[//]: # (docker build -t spring-boot-social-api . )

[//]: # (```)

[//]: # (Command to run the container :)

[//]: # ()
[//]: # (```)

[//]: # (  docker run -p 8081:8081 spring-boot-social-api)

[//]: # ()
[//]: # (```)

[//]: # (Please note when you build the container image and if mysql is running locally on your system, you will need to specify the network name and container name in the command )

[//]: # (and configure the application.properties to use the container name as the host name file to be able to connect to the database from within the container.)

[//]: # (```)

[//]: # (docker run --network springboot-mysql-net --name springboot-mysql-container -p 8081:8081 spring-boot-social-api)

[//]: # (```)

[//]: # (## Docker Compose ##)

[//]: # (Another alternative to run the application is to use the docker-compose.yml file, which is used to run multiple services in multiple containers.)

[//]: # (And to run the application, please execute the following command :)

[//]: # ()
[//]: # (```)

[//]: # (docker-compose up -d --build)

[//]: # (```)

## API Documentation ##
It's as important to document (as is the development) and make your APIs available in a readable manner for frontend teams or external consumers.
The tool for API documentation used in this project is Swagger, you can open the same inside a browser at the following url - [here](http://localhost:9090/swagger-ui/index.html#/)

[//]: # (Some important api endpoints are as follows :)

[//]: # ()
[//]: # (- http://localhost:8081/api/v1/users/signup &#40;HTTP:POST&#41;)

[//]: # (- http://localhost:8081/api/v1/users/login &#40;HTTP:POST&#41;)

[//]: # (- http://localhost:8081/api/v1/users/userId/posts &#40;HTTP:POST&#41;)

[//]: # (- http://localhost:8081/api/v1/users/userId/posts/postId &#40;HTTP:GET&#41;)

[//]: # (- http://localhost:8081/api/v1/users/userId/posts/postId/comments &#40;HTTP:POST&#41;)

[//]: # (- http://localhost:8081/api/v1/users/userId/posts/postId/post_like &#40;HTTP:POST&#41;)









