<%@ include file="/WEB-INF/jsp/header.jsp" %>

<link type="text/css" href="<c:url value="/css/calendar.css" />" rel="stylesheet"/>
<script type="text/javascript" src="<c:url value="/js/datetimepicker_css.js" />"></script>

<c:out value="${environment}" />

<table>
	<tr>
		<td valign="top">
			<c:url value="/actions/action_1" var="action_1"/>
			<form:form modelAttribute="form1" action="${action_d} method="get">
				<table class="tablesorter">
					<thead>
						<th colspan="2">Action 1</th>
					</thead>
					<tr>
						<td>Id</td>
						<td><form:input path="id"/>
							<br/>
							<form:errors path="id" cssClass="errors"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="right"><input type="submit" value="Search"/></td>
					</tr>
				</table>
			</form:form>
		</td>
		<td>
			<c:url value="/actions/action_2" var="action_2"/>
			<form:form modelAttribute="form2" action="${action_2}" method="get">
				<table class="tablesorter">
					<thead>
						<th colspan="2">Action 2</th>
					</thead>
					<tr>
						<td width="80">Id</td>
						<td>
							<form:select path="idType">
								<form:option value="">Select</form:option>
								<form:options items="${idTypes}"/><form:errors path="id" cssClass="errors"/>
							</form:select>
							<form:input path="id"/><form:errors path="id" cssClass="errors"/>	
						</td>
					</tr>
					<tr>
						<td colspan="2" align="right"><input type="submit" value="Search"/></td>
					</tr>
				</table>
			</form:form>
		</td>
	</tr>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>