<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${request.getContextPath() }/lib/css/gridComponent.css" media="all" />
<title>Insert title here</title>
</head>
<body>
	<div id="GridComponent">
		<style>
			.gridComponent{
				background-color:rgba(0,0,0,0.1);
			}
			.header{
				width:100px;
				height:25px;
				background: -webkit-linear-gradient(#e2efff, #d3e7ff);
				display:inline-block;
				text-align:center;
				padding-top:5px;
				cursor:pointer;
				user-select:none;
			}
			.Gridrow{
				height:20px;
			}
			.GridCell{
				display:inline-block;
				width:100px;
			}
		</style>
		<div name="Grid" id="${ param.ID }" class="gridComponent">
			<div name="gridHeader" id="GridHeader${ param.ID }">
				<!--  CREATION DES HEADERS DE LA GRID -->
				<c:forEach items="${ gridHead }" var="pHeaderParam" varStatus="status">
						<div class="header" style="${ pHeaderParam[1]==''?'':pHeaderParam[1] }">
							<c:out value="${ pHeaderParam[0] }"/>
						</div>
				</c:forEach>
				<!--  CREATION DES ROWS DE DONNEES -->
				<c:forEach var="pData" items="${gridData}">
					<div class='GridRow' id="${ pData.key }">
						<c:forEach var="pValue" items="${ pData.value }">
					   		<div class='GridCell'>
					   			<c:out value="${ pValue }"/>
					   		</div>
					    </c:forEach>
				    </div>
				</c:forEach>
				
			</div>
		</div>
	</div>
</body>
</html>