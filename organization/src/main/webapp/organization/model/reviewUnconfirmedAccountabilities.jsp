<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/chart" prefix="chart" %>
<%@page import="module.organization.domain.Accountability"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/organization/CSS/organization.css" media="screen"/>

<h2>
	<bean:message key="label.model" bundle="ORGANIZATION_RESOURCES"/>
	:
	<bean:write name="organizationalModel" property="name"/>
</h2>

<logic:present name="party">
	<jsp:include page="viewPartyDetails.jsp"/>
</logic:present>

<h3>
	<bean:message key="label.unit.review.unconfirmed.accountabilities" bundle="ORGANIZATION_RESOURCES"/>
</h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<logic:iterate id="pair" name="unconfirmedAccountabilities">
	<bean:define id="unconfirmedAccountability" name="pair" property="key"/>
	<bean:define id="partyChart" name="pair" property="value" toScope="request"/>
	<table>
		<tr>
			<td>
				<chart:orgChart id="object" name="partyChart" type="java.lang.Object">
					<%
						if (object instanceof Accountability) {
					%>
						<div class="orgTBox orgTBoxLight">
							<bean:define id="urlA" type="java.lang.String">/organizationModel.do?method=viewModel&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %>&amp;partyOid=<bean:write name="object" property="parent.externalId"/></bean:define>
							<html:link action="<%= urlA %>" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
								<bean:write name="object" property="parent.partyName"/>
							</html:link>
						</div>
					<%
						} else {
					%>
						<div class="orgTBox orgTBoxLight">
							<bean:define id="urlP" type="java.lang.String">/organizationModel.do?method=viewModel&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %>&amp;partyOid=<bean:write name="object" property="externalId"/></bean:define>
							<html:link action="<%= urlP %>" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
								<bean:write name="object" property="partyName"/>
							</html:link>
						</div>
					<%
						}
					%>
				</chart:orgChart>
			</td>
			<td>
				<fr:view name="unconfirmedAccountability" schema="module.organization.domain.UnconfirmedAccountability">
				</fr:view>
			</td>
		</tr>
	</table>
		<font style="color: gray; font-size: x-small;">
			<bean:message key="label.unconfirmedAccountability.proposed.by" bundle="ORGANIZATION_RESOURCES"/>:
			<bean:write name="unconfirmedAccountability" property="user.presentationName"/>
			<bean:message key="label.unconfirmedAccountability.proposed.date" bundle="ORGANIZATION_RESOURCES"/>
            <joda:format value="${unconfirmedAccountability.submited.millis}" pattern="yyyy-MM-dd HH:mm"/>
		</font>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<bean:define id="urlConfirm">/organizationModel.do?method=confirmAccountability&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %>&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;partyOid=<bean:write name="party" property="externalId"/></bean:define>
	<html:link action="<%= urlConfirm %>" paramId="unconfirmedAccountabilityOid" paramName="unconfirmedAccountability" paramProperty="externalId">
		<html:img page="/organization/images/accept.png"/>
	</html:link>
	<html:link action="<%= urlConfirm %>" paramId="unconfirmedAccountabilityOid" paramName="unconfirmedAccountability" paramProperty="externalId">
		<bean:message key="label.confirm" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	<bean:define id="urlReject">/organizationModel.do?method=rejectAccountability&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %>&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;partyOid=<bean:write name="party" property="externalId"/></bean:define>
	&nbsp;&nbsp;
	<html:link action="<%= urlReject %>" paramId="unconfirmedAccountabilityOid" paramName="unconfirmedAccountability" paramProperty="externalId">
		<html:img page="/organization/images/cross.png"/>
	</html:link>
	<html:link action="<%= urlReject %>" paramId="unconfirmedAccountabilityOid" paramName="unconfirmedAccountability" paramProperty="externalId">
		<bean:message key="label.reject" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	<br/>
	<br/>
</logic:iterate>
