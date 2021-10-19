<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!--<html>    
  <head>
    <title>View Books</title>
  </head>   
  
  <br>
  <p>TODO: Add divs and bootstrap...</p>
  <br>
  
  <body>
    <table class="table table-striped">-->

<div class="container">
	<table class="table table-striped">
		<caption>Book List</caption>
		<thead>
			<tr>
				<th>ISBN</th>
				<th>Category</th>
				<th>Title</th>
				<th>Author</th>
				<th>Price</th>
				<th>Stock</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${books}" var="book">
				<tr>
					<td>${book.isbn}</td>
					<td>${book.category}</td>
					<td>${book.title}</td>
					<td>${book.author}</td>
					<td>${book.price}</td>
					<td>${book.stock}</td>
					<td><a type="button" class="btn btn-success"
						href="/updateBook?id=${book.isbn}">Update</a></td>
					<td><a type="button" class="btn btn-warning"
						href="/deleteBook?id=${book.isbn}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div>
		<a type="button" class="button" href="/addNewBook">Add a Book</a>
	</div>
</div>

<!--</body>
</html>-->
