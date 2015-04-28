<%@ include file="/WEB-INF/jsp/header.jsp" %>

<c:forEach var="queueManager" items="${queueManagers}">
<table style="width:600;" class="tablesorter">
	<thead>
		<tr>
			<th colspan="3">${queueManager.region}, ${queueManager.env}, Depth Updated: <span id="${queueManager.region}depthupdated">&ndsp;</span></th>
		</tr>
	</thead>
	<c:forEach var="queueId" items="${queueManager.queueIds}" varStatus="status">
		<tr>
			<td><c:out value="${queueId.description}" /></td>
			<td><c:out value="${queueId.name}" /></td>
			<td><span id="${queueManager.region}depth${status.count}">&nbsp;</span></td>
		</tr>
	</c:forEach>
</table> 

</c:forEach>

<c:url value="/queues/poll" var="poll"/>
<script type="text/javascript">
	${document}.ready(fuction() 
	{
		$.ajaxSetup({cache:false});
		function poll() {
			$.getJSON("${poll}", function(pollResult) {
				$.each(pollResult, fuction(i, field)) {
					$("#"+field.region+"depthupdated").html(field.updated);
					$.each(field.depths, fuction(j, field2)) {
						$("#"+field.region+"depth"+(j+1)).html(field2);	
					}
					)
				}
				);
			}
			)	
		}
		poll();
		setInterval(poll, 3000);
	});
	
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>