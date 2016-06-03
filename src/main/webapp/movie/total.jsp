<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>토오옹계</title>
<link rel="stylesheet" type="text/css" href="table.css"/>
<script type="text/javascript" src="https://code.jquery.com/jquery.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script type="text/javascript">
	$(function () {
    	$('#container').highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45
	            }
	        },
	        title: {
	            text: '박스오피스 statistic, 2016'
	        },
	        subtitle: {
	            text: 'cgv 기준'
	        },
	        plotOptions: {
	            pie: {
	                innerSize: 100,
	                depth: 45
	            }
	        },
	        series: [{
	            name: '영화별 예매율',
	            data: [
	              <c:forEach var="vo" items="${list}">
	              	['<c:out value="${vo.title }"/>', <c:out value="${vo.like }"/>],
	              </c:forEach>
	            ]
	        }]
	    });
	});
	$(function(){
		$('#container2').highcharts({
	        title: {
	            text: '영화 감성 statistics'
	        },
	        xAxis: {
	            categories: [
			 				<c:forEach var="vo" items="${list}">	
			 					'<c:out value="${vo.title }"/>',
	                     </c:forEach> 
	                           ]
	        },
	        labels: {
	            items: [{
	                html: '',
	                style: {
	                    left: '50px',
	                    top: '18px',
	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
	                }
	            }]
	        },
	        series: 
<%-- 	            type: 'column',
	            name: 'Jane',
	            data: <%=request.getAttribute("value")%>  //[3, 2, 1, 3, 4]
	            marker: {
	                lineWidth: 2,
	                lineColor: Highcharts.getOptions().colors[3],
	                fillColor: 'white'
	            	}
	        }, --%>
	        	[
	        		<%=request.getAttribute("value") %>
	        	, {
		            type: 'spline',
		            name: 'Average',
		            data: [30, 20.67, 30, 60.33, 30.33],
		            marker: {
		                lineWidth: 2,
		                lineColor: Highcharts.getOptions().colors[3],
		                fillColor: 'white'
		          }
	        	}, 
	          {
	            type: 'pie',
	            name: 'Total consumption',
	            data: [{
	                name: 'Jane',
	                y: 13,
	                color: Highcharts.getOptions().colors[0] // Jane's color
	            }, {
	                name: 'John',
	                y: 23,
	                color: Highcharts.getOptions().colors[1] // John's color
	            }, {
	                name: 'Joe',
	                y: 19,
	                color: Highcharts.getOptions().colors[2] // Joe's color
	            }],
	            center: [100, 80],
	            size: 100,
	            showInLegend: false,
	            dataLabels: {
	                enabled: false
	            }
	        }]
	    });
	});
</script>

</head>
<body>
<center>
	<h3>영화별 전체 현황 시각화</h3>
	<table id="table_content">
	<tr>
		<td class="tdcenter" colspan="2">
			<div id="container" style="height: 400px"></div>
		</td>
	<tr>
	<tr>
		<td class="tdcenter" colspan="2">	
			<div id="container2" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
		</td>
	</tr>
	</table>
	
</center>	
</body>
</html>