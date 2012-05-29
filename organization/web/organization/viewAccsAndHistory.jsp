<%@page import="org.joda.time.LocalDate"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
    <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
	
	<%-- TODO an appropriate localized label --%>
	<%-- TODO render a link to the page where it came from --%>
	<logic:present name="returnPageURL">
		
		<html:link name="<%=request.getAttribute("returnPageURL")%>">
			<logic:present name="returnPageLabel">
				<bean:write name="returnPageLabel"/>
			</logic:present>
			
			<logic:notPresent name="returnPageLabel">
					<%="<< Voltar"%>
			</logic:notPresent>
		</html:link>
	</logic:present>
<p><strong>Relações:</strong></p>
<jsp:include page="/organization/viewAccountabilityHistory.jsp" flush="true" >
<logic:iterate id="acc" name="accountabilities">
 <jsp:param value="<%=acc%>" name="accountabilities"/>
</logic:iterate>
<%
LocalDate startDate =  request.getAttribute("startDate") == null ? null : (LocalDate) request.getAttribute("startDate");

Integer startDateDay = null;
Integer startDateMonth = null;
Integer startDateYear = null;
if (startDate != null)
{
    startDateDay = startDate.getDayOfMonth();
    startDateMonth = startDate.getMonthOfYear();
    startDateYear = startDate.getDayOfYear();
%>
 <jsp:param value="<%=startDateYear%>" name="startDateYear"/>
 <jsp:param value="<%=startDateMonth %>" name="startDateMonth"/>
 <jsp:param value="<%=startDateDay%>" name="startDateDay"/>
 <%
 } %>
 <jsp:param value="<%=request.getAttribute("showDeletedAccountabilities") %>" name="showDeletedAccountabilities"/>
</jsp:include>
	