# EWallet-Backend-API
The backend api for a digital wallet application that uses MongoDB as the database.
The application enable users to create and manage their digital wallet, add funds to it, and make transactions.

`Built with Spring Boot, secured with Spring Security (JWT), documented with Swagger (API),
containerized with Docker, deployed on an AWS EC2 instance.`

## Technology  used ##
Following tools and libraries were used during the development of the API :
- **Java 17** -
- **Spring Boot** 
- **Build Tool: Maven**
- **MongoDB** 
- **Swagger** - API documentation [here](http://localhost:9090/swagger-ui/index.html#/)
- **JWT** 
- **Docker**
- **Memcached**


### Authentication and Authorization
Uses Spring Security with JWT for stateless authentication and authorization.

### Deployment
The application can be deployed on any Java Servlet container, or docker containers.

## Running the server locally ##
* **Ensure Memcached is installed and running on your machine before you run this service.
* **Clone the repository:** git clone https://github.com/musty-codified/e-wallet-API-with-springboot-and-MongoDb.git
* **Build the project using maven:** mvn clean install or (mvn clean compile followed by mvn clean package)
* **Run the application:** mvn spring-boot:run


## Running the server in a Docker Container ##
Another alternative to run the application is to use the docker-compose.yml file, which is used to run multiple services in multiple containers.
* Simply download the [Docker compose file](https://github.com/musty-codified/e-Wallet-API-with-Springboot-and-MongoDB/blob/main/docker-compose.yml)
* You can edit the file to your custom configurations
* Then navigate to where the file is located on your terminal and run 'docker compose up'
* Voilà! once the image downloads are completed and the application is running, you can then visit http://localhost:9090/swagger-ui/index.html to access the end points

### Note
For security purpose, please make sure to set appropriate access controls for MongoDB.

### Support
For any issues or queries, please raise a ticket on the GitHub repository or email me on ilemonamustapha@@gmail.com.

## API Documentation ##
The application exposes a RESTful API for creating and managing digital wallets.
It's as important to document (as is the development) and make your APIs available in a readable manner for frontend teams or external consumers.
The tool for API documentation used in this project is Swagger, you can open the same inside a browser at the following url - [here](http://localhost:9090/swagger-ui/index.html#/)






