<%@page import="java.util.Collection"%>
<%@page import="java.util.Collections"%>
<%@page import="module.organization.domain.Accountability"%>
<%@page import="java.util.SortedSet"%>
<%@page import="module.organization.domain.AccountabilityType"%>
<%@page import="module.organization.domain.Party"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.joda.time.LocalDate"%>
<%@page import="pt.ist.fenixframework.pstm.AbstractDomainObject"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
    <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%--Let's get the relevant parameters and digest them --%>
<%

//let's retrieve the accountabilityHistory list based on the parameters that have been set

String[] partiesStrings = request.getParameterValues("parties");
request.setAttribute("parties",partiesStrings);
ArrayList<Party> parties = new ArrayList<Party>();
for(String partyString : partiesStrings)
{
    parties.add((Party) AbstractDomainObject.fromExternalId(partyString));
    
}

String[] accountabilitiesStrings = request.getParameterValues("accountabilities");
request.setAttribute("accountabilities",accountabilitiesStrings);
ArrayList<AccountabilityType> accountabilities = new ArrayList<AccountabilityType>();
for (String accString : accountabilitiesStrings)
{
    accountabilities.add((AccountabilityType) AbstractDomainObject.fromExternalId(accString));
}

String startDateYear = request.getParameter("startDateYear");
String startDateMonth = request.getParameter("startDateMonth");
String startDateDay = request.getParameter("startDateDay");

LocalDate startDate = null;

if (startDateYear != null && startDateMonth != null && startDateDay != null)
{
    startDate = new LocalDate(Integer.parseInt(startDateYear), Integer.parseInt(startDateMonth), Integer.parseInt(startDateDay));
}

request.setAttribute("startDate", startDate.toString());

String endDateYear = request.getParameter("endDateYear");
String endDateMonth = request.getParameter("endDateMonth");
String endDateDay = request.getParameter("endDateDay");

LocalDate endDate = null;

if (endDateYear != null && endDateMonth != null && endDateDay != null)
{
    endDate = new LocalDate(Integer.parseInt(endDateYear), Integer.parseInt(endDateMonth), Integer.parseInt(endDateDay));
}

request.setAttribute("endDate", endDate.toString());

//retrieve the ordered accountabilities
Object accItemsToDisplay = Accountability.getActiveAndInactiveAccountabilities(accountabilities, parties, startDate, endDate);
request.setAttribute("accItemsToDisplay", accItemsToDisplay);

%>
<style> 
tr.disabled {
color: #999;
}
</style> 

<logic:notEmpty name="accItemsToDisplay">
	<table class="tstyle2 mvert1">
	  <tr>
	    <th><bean:message bundle="ORGANIZATION_RESOURCES" key="label.viewAccountabilityHistory.accCreationDate" /></th>
	    <th><bean:message bundle="ORGANIZATION_RESOURCES" key="label.viewAccountabilityHistory.accCreator" /></th>
	    <th><bean:message bundle="ORGANIZATION_RESOURCES" key="label.viewAccountabilityHistory.accBeginDate" /></th>
	    <th><bean:message bundle="ORGANIZATION_RESOURCES" key="label.viewAccountabilityHistory.accEndDate" /></th>
	    <th><bean:message bundle="ORGANIZATION_RESOURCES" key="label.viewAccountabilityHistory.accountabilityType" /></th>
	    <th><bean:message bundle="ORGANIZATION_RESOURCES" key="label.viewAccountabilityHistory.party.child" /></th>
	    <th><bean:message bundle="ORGANIZATION_RESOURCES" key="label.viewAccountabilityHistory.party.parent" /></th>
	  </tr>
	  <logic:iterate  name="accItemsToDisplay" id="accItem">
	  <logic:equal value="false" name="accItem" parameter="activeNow" >
	  	<tr class="disabled">
	  </logic:equal>
	  <logic:notEqual value="false" name="accItem" parameter="activeNow" >
	  	<tr>
	  </logic:notEqual>
	    <td><bean:write name="accItem" property="creationDate"/></td>
	    <td><bean:write name="accItem" property="creatorUser"/></td>
	    <td><bean:write name="accItem" property="beginDate"/></td>
	    <td><bean:write name="accItem" property="endDate"/></td>
	    <td><bean:write name="accItem" property="accountabilityType.name.content"/></td>
	    <td><bean:write name="accItem" property="child.partyName"/></td>
	    <td><bean:write name="accItem" property="parent.partyName"/></td>
	  </tr>
	  
	  </logic:iterate>
	</table>
 </logic:notEmpty>
<logic:empty name="accItemsToDisplay">
	<p><i>Sem histórico</i></p>
</logic:empty>
<%--
 <fr:view name="accItemsToDisplay">
 	<fr:schema bundle="ORGANIZATION_RESOURCES" type="module.organization.domain.Accountability"></fr:schema>
 </fr:view>

 --%>