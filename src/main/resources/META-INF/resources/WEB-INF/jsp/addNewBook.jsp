<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>

<html>
<head>
<title>Add New Book</title>
</head>

<h1>Add New Book</h1>

<body>
	<div class="book container">
		<form:form method="post" modelAttribute="book">
			<form:hidden path="isbn" />
			
				<form:label path="category">Category</form:label>
				<form:select path="category" class="form-control" required="required">
				<form:option value="NONE" label="---Select---" />
				<form:options items="${book.category}"/>
				<form:errors path="category" cssClass="text-warning" />
				</form:select>
			
				<form:label path="title">Title</form:label>
				<form:input path="title" type="text" class="form-control" required="required" />
				<form:errors path="title" cssClass="text-warning" />

				<form:label path="author">Author</form:label>
				<form:input path="author" type="text" class="form-control" required="required" />
				<form:errors path="author" cssClass="text-warning" />

				<form:label path="price">Price</form:label>
				<form:input path="price" type="text" class="form-control" required="required" />
				<form:errors path="price" cssClass="text-warning" />
				
				<form:label path="stock">Stock</form:label>
				<form:input path="stock" type="text" class="form-control" required="required" />
				<form:errors path="stock" cssClass="text-warning" />

			<button type="submit" class="btn btn-success">Submit new book</button>
		</form:form>
	</div>
</body>

</html>