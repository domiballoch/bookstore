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
http://localhost:8080/bookstore/rest/findBook/{isbn}
http://localhost:8080/bookstore/rest/getBookstock/{isbn}
<br>Post<br/>
http://localhost:8080/bookstore/rest/addNewBook
<br>Put<br/>
http://localhost:8080/bookstore/rest/updateBook/{isbn}
<br>Delete</br>
http://localhost:8080/bookstore/rest/deleteBook/{isbn}
<br>Search by search term<br/>
http://localhost:8080/bookstore/rest/search/{search}
<br>Search by category<br/>
http://localhost:8080/bookstore/rest/category/{category}

<h4>User</h4>
Get
<br>http://localhost:8080/bookstore/rest/findAllUsers<br/>
http://localhost:8080/bookstore/rest/findUser/{isbn}
<br>Post<br/>
http://localhost:8080/bookstore/rest/addNewUser
<br>Put<br/>
http://localhost:8080/bookstore/rest/updateUser/{isbn}
<br>Delete</br>
http://localhost:8080/bookstore/rest/deleteUser/{isbn}

<h4>Basket</h4>
Get
<br>http://localhost:8080/bookstore/rest/getBasket</br>
<br>Post<br/>
http://localhost:8080/bookstore/rest/addBookToBasket/{isbn}
<br>Delete<br/>
http://localhost:8080/bookstore/rest/removeBookFromBasket/{isbn}

<h4>Order</h4>
Get
<br>http://localhost:8080/bookstore/rest/findAllOrders<br/>
http://localhost:8080/bookstore/rest/findOrder/{isbn}
<br>Post<br/>
http://localhost:8080/bookstore/rest/submitOrder

<br>Endpoints - health check exposed only<br/>
http://localhost:8080/bookstore/actuator/health

<h4>Endpoints - views (JSP)</h4>
Get
<br>http://localhost:8080/bookstore/findAllBooks<br/>
http://localhost:8080/bookstore/web/findBook
<br>Post<br/>
http://localhost:8080/bookstore/web/addNewBook
<br>Put<br/>
http://localhost:8080/bookstore/web/updateBook
<br>Delete<br/>
http://localhost:8080/bookstore/web/deleteBook

Swagger - to come...

<h4>Logging level</h4>
info - set filepath as/if required
