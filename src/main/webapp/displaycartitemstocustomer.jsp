<%@page import="com.jsp.shoppingcart.dto.Item"%>
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
	List<Item> items = (List<Item>) request.getAttribute("itemslist");//request is implicitely create and it is created by web container there are 9 implicitely
	double totalprice = (double) request.getAttribute("totalprice");
%>

	<table cellpadding="20px" border="1">
		<th>Brand</th>
		<th>Model</th>
		<th>Category</th>
		<th>Price</th>
		<th>Qunatity</th>
		
		
		<% for(Item i : items) { %>
		
		<tr>
			<td><%= i.getBrand() %></td>
			<td><%= i.getModel() %></td>
			<td><%= i.getCatagory()%></td>
			<td><%= i.getPrice() %></td>
			<td><%= i.getQuantity() %></td>
		</tr>
		
		<% } %>
		
	</table>
	<h2>Total Price: <%= totalprice %></h2>
	<br>
	
	<a href="addorder">Buy Now</a>
</body>
</html>