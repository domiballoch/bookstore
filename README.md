[![Actions Status](https://github.com/domiballoch/bookstore/actions/workflows/maven.yml/badge.svg)](https://github.com/domiballoch/bookstore/actions)
[![codecov](https://codecov.io/gh/domiballoch/bookstore/branch/master/graph/badge.svg?token=3DQWELQG2V)](https://codecov.io/gh/domiballoch/bookstore)
[![GitHub version](https://badge.fury.io/gh/domiballoch%2Fbookstore.svg)](https://badge.fury.io/gh/domiballoch%2Fbookstore)
[![forthebadge](https://forthebadge.com/images/badges/not-a-bug-a-feature.svg)](https://forthebadge.com)

<h1>Bookstore service</h1>

<h4>Description</h4>
Bookstore with full rest and (some)web functionality using get, post, delete, put
<br/>Fully tested at controller, service & repository level

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

<h4>Endpoints - Rest (JSON)</h4>
<h4>Book</h4>
Get
<br>http://localhost:8080/bookstore/rest/findAllBooks<br/>
http://localhost:8080/bookstore/findBook/rest/{isbn}
<br>Post<br/>
http://localhost:8080/bookstore/addNewBook/rest
<br>Put<br/>
http://localhost:8080/bookstore/updateBook/rest/{isbn}
<br>Delete</br>
http://localhost:8080/bookstore/deleteBook/rest/{isbn}
<br>Search by search term<br/>
http://localhost:8080/bookstore/search/rest/{search}
<br>Search by category<br/>
http://localhost:8080/bookstore/category/rest/{category}

<h4>User</h4>
Get
<br>http://localhost:8080/bookstore/rest/findAllUsers<br/>
http://localhost:8080/bookstore/findUser/rest/{isbn}
<br>Post<br/>
http://localhost:8080/bookstore/addNewUser/rest
<br>Put<br/>
http://localhost:8080/bookstore/updateUser/rest/{isbn}
<br>Delete</br>
http://localhost:8080/bookstore/deleteUser/rest/{isbn}

<h4>Basket</h4>
Get
http://localhost:8080/bookstore/getBasket/rest
http://localhost:8080/bookstore/calcBasket/rest
<br>Post<br/>
http://localhost:8080/bookstore/addBookToBasket/rest

<h4>Order</h4>
Get
<br>http://localhost:8080/bookstore/rest/findAllOrders<br/>
http://localhost:8080/bookstore/findOrder/rest/{isbn}
<br>Post<br/>
http://localhost:8080/bookstore/submitOrder/rest

<br>Endpoints - health check exposed only<br/>
http://localhost:8080/bookstore/actuator/health

<h4>Endpoints - views (JSP)</h4>
Get
<br>http://localhost:8080/bookstore/findAllBooks<br/>
http://localhost:8080/bookstore/findBook
<br>Post<br/>
http://localhost:8080/bookstore/addNewBook
<br>Put<br/>
http://localhost:8080/bookstore/updateBook
<br>Delete<br/>
http://localhost:8080/bookstore/deleteBook

Swagger - to come...

<h4>Logging level</h4>
info - set filepath as/if required
