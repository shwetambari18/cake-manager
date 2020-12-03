<!DOCTYPE HTML>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/styles.css"/>"> 
		<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.5.1.js'/>"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/jquery.validate.min.js"></script>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 
		<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
		<title>Cake Manager</title>
		<script>
		
			$(document).ready(function(){
			  $("#btnReset").click(function(){
			    $("#name").val('');
			    $("#flavour").val('');
			    $("#icingType").val('');
			    $("#price").val('');
			    $("#size").val('');
			  });
			  
				  $("#frmAddNewCake").validate({
					  rules : {
						  'name': {
							  required:true,
							  lettersonly:true
						  },
						  'flavour': {
							  required:true,
							  lettersonly:true
						  },
						  'icingType': {
							  required:true
						  },
						  'size': {
							  required:true
						  },
						  'price': {
							  required:true,
							  currency:true,
							  max:9999.99
						  },
					  },
						messages : {
							  'name':{
								  required:"Please enter cake Name",
								  lettersonly:"Please enter letters only"
							  },
							  'flavour':{
								  required :"Please enter Flavour",
								  lettersonly:"Please enter letters only"
							  },
							  'icingType':"Please select Icing type",
							  'size':"Please select cake Size",
							  'price':{
								  required : "Please enter cake Price",
								  currency: "Please specify valid amount in numbers '12.34' format",
								  max: "Maximum amount can be 9999.99 only"
							  }
						  },
					 	 errorPlacement: function(error, element) {
				            if (element.attr("type") == "radio") {
				                error.insertBefore(element);
				            } else {
				                error.insertAfter(element);
				            }
				        }
				  });
				  
				  jQuery.validator.addMethod("lettersonly", function(value, element) {
					  return this.optional(element) || /^[a-z][a-z\s]+$/i.test(value);
					}, "Letters only please"); 
				  jQuery.validator.addMethod("numbersonly", function(value, element) {
					  return this.optional(element) || /^[0-9]+$/i.test(value);
					}, "Numbers only please"); 
				  $.validator.addMethod("currency", function (value, element) {
					  return this.optional(element) || /^(\d{1,3}(\,\d{3})*|(\d+))(\.\d{2})?$/.test(value);
					}, "Please specify a valid amount");
			});
		</script>
	</head>
	<body>
		<div class="main-div">
			<h2 style="text-align:center">List of Cakes</h2>
			<table class="table table-striped table-bordered table-sm">
				<thead class="thead-dark" >
					<tr>
						<th>Cake Name</th>
						<th>Flavour</th>
						<th>Size</th>
						<th>Icing Type</th>
						<th>Price(£)</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cakeList}" var="cake1">
					    <tr>
					    	<td>${cake1.name}</td>
					   		<td>${cake1.flavour}</td>
					   		<td>${cake1.size}</td>
					   		<td>${cake1.icingType}</td>
					   		<td>${cake1.price}</td>
					   	</tr>	
					</c:forEach>
				</tbody>
			</table>
			<hr>
			<form:form id="frmAddNewCake" modelAttribute="cake"  action="/addCake" method="post" >
					<h3>Add a new Cake</h3>
					<div>
						<label for="name">Cake Name:<span class="requiredfield">*</span> </label>
						<input type="text" id="name" name="name" value="${cake.name}"  placeholder="please enter cake name">
					</div>
					<div>
						<label for="flavour">Cake Flavour:<span class="requiredfield">*</span> </label>
						<input type="text" id="flavour" name="flavour" value="${cake.flavour }" placeholder="please enter flavour">
					</div>
					<div>
						<label for="size">Select a Size:<span class="requiredfield">*</span></label>
						<input type="radio" name="size" id="small" value="Small" >
						<label for="small">Small</label>
						<input type="radio" name="size" id="medium" value="Medium">
						<label for="medium">Medium</label>
						<input type="radio" name="size" id="large" value="Large">
						<label for="large">Large</label>
					</div>
					 <div>
			            <label for="icingType">Select Icing Type:<span class="requiredfield">*</span></label>
			            <select name="icingType" id="icingType">
			           		 <option value="">Please select</option>
			                <option value="Freshcream">Freshcream</option>
			                <option value="Buttercream">Buttercream</option>
			                <option value="Fondant">Fondant</option>
			            </select>
			         </div>
			         <div>
						<label for="price">Cake Price:<span class="requiredfield">*</span> </label>
						<input type="text" id="price" name="price" value="${cake.price }" placeholder="price">
					</div>
					<label class="requiredFieldLabel"><span class="requiredfield">*</span> marked fields are required</label>
				<div>
					<button id="btnReset" type="button">Reset</button>
					<button id="btnSubmit">Submit</button>
				</div>	
			</form:form>
			<hr>
		</div>
	</body>
</html>