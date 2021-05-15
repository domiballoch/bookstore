<h1>Bookstore service</h1>

![Actions Status](https://github.com/domiballoch/bookstore/actions/workflows/maven.yml/badge.svg)
![codecov](https://codecov.io/gh/domiballoch/bookstore/branch/master/graph/badge.svg?token=3DQWELQG2V)](https://codecov.io/gh/domiballoch/bookstore)

<h4>Prerequisites</h4>
Java 11
<br/>Spring Boot 2.4.5
<br/>Maven 3.6.3
<br/>MySQL 8

<h4>Build project</h4>
mvn clean install
<br/>spring-boot:run

<h4>Server port / context path</h4>
8080/bookstore

<h4>Database schema</h4
execute bookstore.sql

<h4>Connection</h4>
Login with root or create user and change yml
<br/>url: jdbc:mysql://localhost:3306/bookstore
<br/>username: root

<h4>Endpoints - Rest only (output as JSON)</h4>
<h4>Get</h4>
http://localhost:8080/bookstore/rest/findAllBooks
<br/>http://localhost:8080/bookstore/findBook/rest/{isbn}
<h4>Post</h4>
http://localhost:8080/bookstore/addNewBookToBookstore/rest
<h4>Put</h4>
http://localhost:8080/bookstore/amendBook/rest
<h4>Delete</h4>
http://localhost:8080/bookstore/deleteBookFromBookstore/rest/{isbn}

<h4>Endpoints - with views (output as JSP)</h4>
<h4>Get</h4>
http://localhost:8080/bookstore/findAllBooks
<br/>http://localhost:8080/bookstore/findBook/{isbn}
<h4>Post</h4>
http://localhost:8080/bookstore/addNewBookToBookstore
<h4>Put</h4>
http://localhost:8080/bookstore/amendBook
<h4>Delete</h4>
http://localhost:8080/bookstore/deleteBookFromBookstore/{isbn}

<h4>Endpoints - health check exposed only</h4>
http://localhost:8080/bookstore/actuator/health

<h4>Swagger - to come...</h4>

<h4>Logging level</h4>
info - set filepath as/if required
