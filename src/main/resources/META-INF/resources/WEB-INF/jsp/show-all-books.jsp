<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>

<html>
<head>
<title>View Books</title>
</head>

<h1>Book List</h1>

<body>
	<div class="table container">

		<table class="table">
			<caption>List of all books</caption>
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
							href="/bookstore/web/updateBook?isbn=${book.isbn}">Update</a></td>
						<td><a type="button" class="btn btn-warning"
							href="/bookstore/web/deleteBook?isbn=${book.isbn}">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<a type="button" class="button" href="/bookstore/web/addNewBook">Add a Book</a>
		</div>
	</div>

</body>
</html>
