<html>
<head>
<script type="text/javascript" src="resourcefiles/js/plotly-latest.min.js"></script>
</head>
	<div id="chart" style="width: 800px; height: 480px;"></div>
<body>
	<script>
		var parsedStocks = ${stocks};
		//var parsedStocks = JSON.parse(stocks);
		var trace = {
			x : parsedStocks["map"]["x"],
			y : parsedStocks["map"]["y"],
			type : 'scatter'
		};
		var layout = {
			title : "Historic Stocks",
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
	
	</script>
</body>
</html>