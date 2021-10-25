<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>

<html>
<head>
<title>Deleted Book</title>
</head>

<h1>Successfully deleted Book</h1>

<body>
	<div class="deleted book container">
		<form:form method="get" modelAttribute="book">
			<form:hidden path="isbn" />

		<table class="table">
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
					<tr>
						<td>${book.isbn}</td>
						<td>${book.category}</td>
						<td>${book.title}</td>
						<td>${book.author}</td>
						<td>${book.price}</td>
						<td>${book.stock}</td>
					</tr>
			</tbody>
		</table>
		<div>
			<a type="button" class="button" href="/bookstore/web/findAllBooks">Return</a>
		</div>
		</form:form>
	</div>

</body>
</html>