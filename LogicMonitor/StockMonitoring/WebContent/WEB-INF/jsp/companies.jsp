<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="resourcefiles/css/tableStyle.css">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Stock Monitoring</title>
</head>
<body>
	<script>
		$(document).ready(function() {

			$("#addCompany").hide();

		});
	</script>
	
	<center>
		<table class="container">
			<thead>
				<tr>
					<th><h1>Company</h1></th>
					<th><h1>Company Code</h1></th>
					<th><h1>Last Updated</h1></th>
					<th><h1>Stock Price($ USD)</h1></th>
					<th><h1>Delete</h1></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="co" items="${companiesList}">
				<tr>
					<td><a href="viewCompany?code=${co.code}">${co.name}</a></td>
					<td>${co.code}</td>
					<td>${co.lastUpdated}</td>
					<td>${co.stockPrice}</td>
					<td><a href="deleteCompany?name=${co.name}">Delete</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<button type="button" class="button" id="add">Add Company</button>
		<button type="button" class="button" id="refresh">Refresh</button>
			
		<div id="addCompany" class="container">
			<input type="text" placeholder="Company Name" id="coname"> 
			<input type="text" placeholder="Company Code" id="cocode">
			<button type="button" class="button" id="saveCompany">Save</button>
			<button type="button" class="button" id="cancel">Cancel</button>
		</div>
		<br />
	
		<script> 
		$("#refresh").click(function(){
			location.reload();
		});
		$('#add').click(function() {
			$("#addCompany").show();
			$("#cancel").click(function(){
				$("#addCompany").hide();
			});
			$("#saveCompany").click(function() {
				console.log("saving company");
				var coname = $('#coname').val();
				var cocode = $('#cocode').val();
				$.ajax({
					type : "GET",
					url : "AddCompany",
					data : {
						name : coname,
						code : cocode
					},
					contentType : "application/json; charset=utf-8",
					success : function(res) {
						location.reload();
					}
				});
				$("#addCompany").hide();
				$("#coname").val("");
				$("#cocode").val("")
			});
		});
		
		$("#view").click(function() {
			myRowIndex = $(this).parent().index();
		    myColIndex = $(this).index();
			console.log(myRowIndex);
		    if (!cocode) {
				alert("Please select a company to view its historic stocks data.");
				exit();
			}
			$.ajax({
				type : "GET",
				url : "CompanyHistory",
				data : {
					code : cocode
				},
				contentType : "application/json; charset=utf-8",
				success : function(res) {
					var stocks = res["stocks"];
					var parsedStocks = JSON.parse(stocks);
					var trace = {
						x : parsedStocks["map"]["x"],
						y : parsedStocks["map"]["y"],
						type : 'scatter'
					};
					var layout = {
						title : "Historic Stocks of "+ coname,
						xaxis : {
							title : "Datetime",
							titlefont : {
								family : 'Courier New, monospace',
								size : 18,
								color : '#7f7f7f'
							}
						},
						yaxis : {
							title : 'Stock Price',
							titlefont : {
								family : 'Courier New, monospace',
								size : 18,
								color : '#7f7f7f'
							}
						}
					};
					
					var chartData = [ trace ];
					Plotly.newPlot('chart', chartData, layout);
				}
			});
		});

		</script>
		
	</center>
</body>
</html>