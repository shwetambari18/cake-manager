<!DOCTYPE HTML>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>"> 
		<title>Cake Addition Response</title>
	</head>
	<body>
		<div style="width:80%;text-align:center;">
			<h2 style="text-align:center">Status</h2>
			<label>${message}!</label>
			<hr>
			<div>
				<label>Go to </label>
				<a href="/">Cakes List</a>
			</div>
		</div>
	</body>
</html>