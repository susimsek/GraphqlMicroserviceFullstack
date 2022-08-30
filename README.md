# Graphql Fullstack Microservice Application With OAuth2 Authentication Using Spring Boot & React

Graphql Fullstack Microservice Application With OAuth2

<img src="https://github.com/susimsek/GraphqlMicroserviceFullstack/blob/main/images/introduction.png" alt="Graphql Fullstack Microservice App" width="100%" height="100%"/> 

# Graphql

GraphQL is a query language and server-side runtime for application programming interfaces (APIs) that prioritizes
giving clients exactly the data they request and no more.
GraphQL is designed to make APIs fast, flexible, and developer-friendly.
It can even be deployed within an integrated development environment (IDE) known as GraphiQL.
As an alternative to REST, GraphQL lets developers construct requests that pull data from multiple data sources in a
single API call.

# OAuth2 Authorization Server
We are using oauth2 authorization server to secure the application.

<img src="https://github.com/susimsek/GraphqlMicroserviceFullstack/blob/main/images/auth-server.png" alt="Spring Boot Authorization Server" width="100%" height="100%"/>

To log in to your app, you'll need to have Spring OAuth2 Authorization Server up and running.

The OAuth2 Authorization Server can be accessed from this link via kubernetes.  
http://auth.susimsek.github.io

The OAuth2 Authorization Server can be accessed from this link via heroku.  
https://graphql-fullstack-auth-service.herokuapp.com

The default user credentials information is as follows. 
You can log in to the authorization server using that credential information or login to the authorization server with gmail.


```sh
username: admin
password: password
```

# Application

The GraphQL Fullstack application can be accessed from this link.  
http://gqlmsweb.susimsek.github.io

## Dashboard
<img src="https://github.com/susimsek/GraphqlMicroserviceFullstack/blob/main/images/ui-dashboard.png" alt="Graphql Fullstack Microservice App Dashboard" width="100%" height="100%"/>   

## Product Page
<img src="https://github.com/susimsek/GraphqlMicroserviceFullstack/blob/main/images/ui-product-page.png" alt="Graphql Fullstack Microservice App Product Page" width="100%" height="100%"/>  

## Login Page
<img src="https://github.com/susimsek/GraphqlMicroserviceFullstack/blob/main/images/ui-login-page.png" alt="Graphql Fullstack Microservice App Login Page" width="100%" height="100%"/>

# Development

Before you can build this project, you must install and configure the following dependencies on your machine.

## Prerequisites for Backend 

* Java 17
* Kotlin
* Maven 3.x
* Mongodb
* Nodejs 14+ (required for Apollo Gateway)

### Run the microservice

You can install the dependencies by typing the following command

```sh
mvn clean install
```

You can run the spring boot microservice by typing the following command

```sh
mvn spring-boot:run
```

### Supergraph Composition

## Prerequisites

* Rover

You can federate multiple subgraphs into a supergraph by typing the following command

```sh
rover supergraph compose --config ./deploy/supergraph.yaml > ./deploy/supergraph.graphql
```


### Run the apollo gateway

You can install the dependencies by typing the following command

```sh
npm install
```

You can run the apollo gateway(accessible on http://127.0.0.1:4000) by typing the following command

```sh
npm run start:dev
```

## Prerequisites for Frontend

* Nodejs 14+

### Run the app

You can install the dependencies by typing the following command

```sh
npm install
```

You can run the react app(accessible on http://127.0.0.1:3000) by typing the following command

```sh
npm start
```

# Sonar

## Code Quality For Backend

You can test code quality locally via sonarqube by typing the following command

```sh
mvn -Psonar compile initialize sonar:sonar
```

## Code Quality For Apollo Gateway

You can test code quality locally via sonarqube by typing the following command

```sh
npm run sonar
```

## Code Quality For Frontend

You can test code quality locally via sonarqube by typing the following command

```sh
npm run sonar
```

# Detekt

Detekt a static code analysis tool for the Kotlin programming language  

You can run detekt by typing the following command

```sh
mvn antrun:run@detekt
```

# Caching

Redis is an Open Source, in-memory data structure store that can be used as a performant caching solution Depending on your configuration, you can choose to use Redis as a single server node or as a distributed cache   

Default cache expire time is 1 hour but you can change it via vault by changing the following environment variable

```sh
CACHE_EXPIRATION=3600s
```

# Docker

You can also fully dockerize your application and all the services that it depends on. To achieve this, first build a docker image of your app.

## Build Docker Image for Backend

The docker image of microservice can be built as follows:

```sh
mvn -Pjib verify jib:dockerBuild
```

## Build Docker Image for Frontend

The docker image of ui can be built as follows:

```sh
docker build -t web .
```

## Deployment with Docker Compose

### Prerequisites for Docker Compose Deployment

* Jq
* Docker
* Docker Compose

You can deploy app by running the following bash command


```sh
 sudo chmod +x deploy.sh
```

```sh
 ./deploy.sh -d
```

You can uninstall app the following bash command

```sh
 ./deploy.sh -d -r
```

The Fullstack GraphQL App be accessed with nginx from the link below.  
http://127.0.0.1

The OAuth2 Authorization Server be accessed from the link below.  
http://127.0.0.1:9000


## Deployment Kubernetes with Helm

### Prerequisites for Kubernetes Deployment

* Kubernetes
* Helm

You can deploy app by running the following bash command

```sh
 sudo chmod +x deploy.sh
```

```sh
 ./deploy.sh -k
```

You can uninstall app the following bash command

```sh
 ./deploy.sh -k -r
```

You can upgrade the App (if you have made any changes to the generated manifests) by running the
following bash command

```sh
 ./deploy.sh -u
```

The Fullstack GraphQL App be accessed with ingress from the link below.(default nginx ingress)  
http://gqlmsweb.susimsek.github.io

The OAuth2 Authorization Server be accessed with ingress from the link below. (default nginx ingress)  
http://auth.susimsek.github.io

# Used Technologies
## Backend Side
* Java 17
* Kotlin
* Docker
* Docker Compose
* Kubernetes
* Helm
* Sonarqube
* Detekt
* Heroku(only Authorization Server)
* Circleci
* Snyk
* Vault
* Consul
* Nginx
* Mongodb
* Redis(for Caching)
* Elasticsearch
* Kibana
* Logstash
* Apollo Gateway
* Spring Boot
* Spring Cloud
* Spring Boot Web(only Authorization Server)
* Spring Boot Web Flux
* Spring Boot Graphql
* Spring Boot Validation
* Spring Boot Security
* Spring Security OAuth2 Authorization Server
* Spring Security OAuth2 Resource Server
* Spring Security OAuth2 Client
* Spring Boot Thymeleaf
* Spring Boot Actuator
* Spring Boot Configuration Processor
* Federation Graphql Java Support
* Logstash Logback Encoder
* Querydsl

## Backend Side for TDD
* Testcontainers
* Spring Boot Test
* Spring Security Test
* Spring Graphql Test
* Kotlin Test Junit
* Mockito Kotlin
* Reactor Test

## Frontend Side
* React
* React Router
* React Router Dom
* Typescript
* Apollo Client
* Graphql
* Graphql Codegen
* Axios
* Bootstrap
* React Bootstrap
* Crypto js