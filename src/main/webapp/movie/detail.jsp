<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vo.title }</title>
<link rel="stylesheet" type="text/css" href="table.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script type="text/javascript">
$(function () {
    $('#container').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: '최근 관람객들의 반응을 확인하세요!'
        }, 
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
	                	['긍정', <c:out value="${nlist.get(1) }"/>],
	                	['부정', <c:out value="${nlist.get(3) }"/>]
            		 ]
        }]
    });
});
</script>
</head>
<body>
	<center>
		<h3>${vo.title }상세보기</h3>
		<table id="table_content" style="width: 600px">
			<tr>
				<td width=40% class="tdcenter" rowspan=6>
				<img src="${vo.image }"></td>
				<th colspan=2>${vo.title }</th>
			</tr>
			<tr>
				<td width=20% align=right>영화순위</td>
				<td width=40%>${vo.no }위</td>
			</tr>
			<tr>
				<td width=20% align=right>예매율</td>
				<td width=40%>${vo.percent }%</td>
			</tr>
			<tr>
				<td width=20% align=right>찜</td>
				<td width=40%>♥&nbsp;${vo.like }</td>
			</tr>
			<tr>
				<td width=20% align=right>개봉일</td>
				<td width=40%>${vo.regdate }</td>
			</tr>
			<tr>
				<td width=20% align=right>평점</td>
				<td width=40%>${vo.reserve }점(100점 만점)</td>
			</tr>
		</table>
		<table id="table_content" style="width:600px">
			<tr>
				<th>${vo.title }영화 분석!</th>
			</tr>
			<tr>
				<td class="tdcenter">
					<img src="feel.png">
				</td>
			</tr>
			<tr>
				<th>${vo.title }&nbsp;긍정부정 분석!</th>
			</tr>
			<tr>
				<td class="tdcenter">
					<div id="container" style="height:400px"></div>
				</td>
			</tr>
		</table>
<%-- 		<table id="table_content" style="width:600px">
			<tr>
				<th>${vo.title }&nbsp;긍정부정 분석!</th>
			</tr>
			<tr>
				<td class="tdcenter">
					<div id="container" style="height:400px"></div>
				</td>
			</tr>
		</table> --%>
		<table id="table_content" style="width:900px">
			<tr>
				<td width=20% align=left>긍정,부정평가</td>
				<%-- <c:forEach var="vp" items="${nlist }"> --%>
				<td width=10%>♥ ${nlist.get(1)}</td>
				<td width=10%>♡ ${nlist.get(3)}</td>
		<%-- 		</c:forEach> --%>
			</tr>
			<tr>
				<td align=right>
					<a href="list.do">목록</a>&nbsp;&nbsp;
					<a href="recommand.do">추천</a>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>