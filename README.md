#Bookstore service

##Prerequisites
Java 11
Spring Boot 2.4.5
Maven 3.6.3
MySQL 8

##Build project
mvn clean install
spring-boot:run

##Server port / context path
8080/bookstore

##Database schema
execute bookstore.sql

##Connection
Login with root or create user and change yml
url: jdbc:mysql://localhost:3306/bookstore
username: root

##Endpoints - Rest only (output as JSON)
###Get
http://localhost:8080/bookstore/rest/findAllBooks
http://localhost:8080/bookstore/findBook/rest/{isbn}
###Post
http://localhost:8080/bookstore/addNewBookToBookstore/rest
###Put
http://localhost:8080/bookstore/amendBook/rest
###Delete
http://localhost:8080/bookstore/deleteBookFromBookstore/rest/{isbn}

##Endpoints - with views (output as JSP)
http://localhost:8080/bookstore/findAllBooks
http://localhost:8080/bookstore/findBook/{isbn}
###Post
http://localhost:8080/bookstore/addNewBookToBookstore
###Put
http://localhost:8080/bookstore/amendBook
###Delete
http://localhost:8080/bookstore/deleteBookFromBookstore/{isbn}

##Endpoints - health check exposed only
http://localhost:8080/bookstore/actuator/health

##Swagger - to come...

#Logging level
info - set filepath as/if required