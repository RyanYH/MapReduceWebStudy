<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="table.css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script type="text/javascript">
$(function(){
    $('#feel').change(function(){
        $('#frm').submit();
    	});
    $('#container').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '감성별 영화추천'
            },
            xAxis: {
                categories: [
                              /* 'Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas'  */
			 			 			<c:forEach var="vo" items="${rlist }">	
		 								'<c:out value="${vo.title }"/>',
                     			</c:forEach>  
                                ]
            },
            yAxis: {
                min: 0,
                title: {
                    text: '추천'
                }
            },
            tooltip: {
                pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.percentage:.0f}%)<br/>',
                shared: true
            },
            plotOptions: {
                column: {
                    stacking: 'percent'
                }
            },
            series: [
            	/*
                name: 'John',
                data: [5, 3, 4, 7, 2]
           	 	} , 
            	{
                		name: 'Jane',
                	 data: [2, 2, 3, 2, 1]
            	}, 
            	{
               	 name: 'Joe',
                	 data: [3, 4, 4, 2, 5]
        		},
        		{
                    name: 'Joe',
                    data: [3, 4, 4, 2, 5]
            	},
            	{
                    name: 'Joe',
                    data: [3, 4, 4, 2, 5]
            	},
            	{
                    name: 'Joe',
                    data: [3, 4, 4, 2, 5]
            	} */
            	<%=request.getAttribute("data")%>
            ]
       });
   <%--   $('#container2').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '영화 추천 (감성별)'
        },
        
        xAxis: {
            type: 'category',
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '추천영화 (감성)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '감성현황: <b>{point.y:.1f} </b>'
        },
        series: [{
            name: '감성',
            data: <%=request.getAttribute("data1")%>,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.1f}', // one decimal
                y: 10, // 10 pixels down from the top
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    }); 
      --%>
 
  });
</script>
</head>
<body>
    <center>
        <h3>추천영화</h3>
        <table id="table_content" style="width:600px">
            <tr>
                <td>추천검색
                    <form action="recommand.do" method="post" id="frm">
                        <select name="feel" id="feel">
                            <c:forEach var="vo" items="${slist}">
                                <c:if test="${vo==feel}">
                                    <option selected>${vo}</option>
                                </c:if>
                                <c:if test="${vo!=feel}">
                                    <option>${vo}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </form>
                </td>
            </tr>
        </table>
        <table id="table_content" style="width:600px">
            <tr>
            		<c:forEach var="m" items="${mlist }">
                	<td class="tdcenter">
                   	 <a href="detail.do?no=${m.no}">
                    	<img src="${m.image}"></a>
                	</td>
                </c:forEach>
            </tr>
            <tr>
           	 <c:forEach var="m" items="${mlist }">
               	 <td class="tdcenter">
                    ${m.title}
                	</td>
                </c:forEach>
            </tr>
        </table>
        <table id="table_content" style="width:600px">
        	<tr>
        		<td class="tdcenter">
					<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        		</td>
        	</tr>
        </table>
      <table id="table_content" width="600">
        <tr>
           <td class="tdcenter">
              <div id="container2" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
           </td>
        </tr>
      </table>
    </center>
</body>
</html> 