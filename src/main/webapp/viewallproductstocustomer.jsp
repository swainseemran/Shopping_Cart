<%@page import="com.jsp.shoppingcart.dto.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% 
	List<Product> products = (List<Product>) request.getAttribute("productslist");
%>
	<h1>
		<a href="fetchitemsfromcart">View cart</a>
	</h1>
<table cellpadding="20px" border="1">
	<th>id</th>
	<th>brand</th>
	<th>model</th>
	<th>category</th>
	<th>price</th>
	<th>Add to cart</th>
	
	<% for(Product p : products){ %>
	
	<tr>
		<td><%= p.getId() %></td>
		<td><%= p.getBrand() %></td>
		<td><%= p.getModel() %></td>
		<td><%= p.getCategory() %></td>
		<td><%= p.getPrice() %></td>
		<td><a href="additem?id=<%= p.getId()%>">Add</a></td> 
	</tr>
	
	<% } %>
</table>
</body>
</html>

<!-- to display the form with the product details we use getId in place of href additem -->