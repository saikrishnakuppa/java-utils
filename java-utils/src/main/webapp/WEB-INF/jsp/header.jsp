<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>SampleWeb - ${webAppTitle}</title>
	<link type="text/css" href="<c:url value="/css/stylesheet.css" />" rel="stylesheet"/>
	<script type="text/javascript" src="<c:url value="/js/jquery-1.6.2.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/jquery.tablesorter.js" />"></script>
</head>
<body>
<table width="90%" class="tablesorter">
<td align="center">
	<table border="0" cellpadding="1" cellspacing="1">
		<tr>
			<c:forEach var="action" items="${webActions}">
				<td><a herf="<c:url value="${action.url}" />">${action.title}"</a></td>
			</c:forEach>
		</tr>
	</table>
	<br/>
	<table border="0" cellpadding="3" cellspacing="3">
		<tr>
			<c:forEach var="statusMessage" items="${statusMessages}">
				<td><c:out value="${statusMessage}"/></td>
			</c:forEach>
		</tr>
	</table>
	<br/>