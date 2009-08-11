<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/chart.tld" prefix="chart" %>
<%@page import="myorg.presentationTier.component.OrganizationChart"%>
<%@page import="module.organization.domain.Unit"%>

<h2>
	<bean:message key="label.model" bundle="ORGANIZATION_RESOURCES"/>
	:
	<bean:write name="organizationalModel" property="name"/>
</h2>

<logic:present role="myorg.domain.RoleType.MANAGER">
	<html:link action="/organizationModel.do?method=editModel" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
		<bean:message key="label.model.edit" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	|
	<html:link action="/organizationModel.do?method=manageModelAccountabilityTypes" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
		<bean:message key="label.model.accountabilityTypes.manage" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	|
	<html:link action="/organizationModel.do?method=prepareAddUnitToModel" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
		<bean:message key="label.model.add.unit" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	|
	<html:link action="/organizationModel.do?method=prepareCreateUnit" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
		<bean:message key="label.model.create.unit" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	|
	<html:link action="/organizationModel.do?method=deleteModel" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
		<bean:message key="label.model.delete" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	<br/>
</logic:present>

<div class="dinline">
	<bean:define id="url">/organizationModel.do?method=viewModel&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/></bean:define>
	<fr:form action="<%= url %>">
		<table>
			<tr>
			<td>
				<fr:edit id="partySearchBean" name="partySearchBean" schema="module.organization.domain.search.PartySearchBean">
					<fr:layout name="tabular">
						<fr:property name="classes" value="form"/>
						<fr:property name="columnClasses" value=",,tderror"/>
					</fr:layout>
				</fr:edit>
			</td>
			<td style="padding-right: 10px; padding-bottom: 5px; vertical-align: top; padding: 1.5em 0 1.5em;">
				<html:submit styleClass="inputbutton"><bean:message key="renderers.form.search.name" bundle="RENDERER_RESOURCES"/></html:submit>
			</td>
			</tr>
		</table>
	</fr:form>
</div>

<logic:present name="partiesChart">
	<table width="100%" align="center">
		<tr>
			<td align="center">
				<chart:orgChart id="party" name="partiesChart" type="java.lang.Object">
					<div class="orgTBox orgTBoxLight">
						<bean:define id="url">/organizationModel.do?method=viewModel&amp;partyOid=<bean:write name="party" property="externalId"/>&amp;viewName=default</bean:define>
						<html:link action="<%= url %>" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
							<bean:write name="party" property="partyName"/>
						</html:link>
					</div>
				</chart:orgChart>
			</td>
		</tr>
	</table>
</logic:present>

<logic:present name="party" scope="request">
	<jsp:include page="viewPartyDetails.jsp"/>
	<logic:notEmpty name="hooks">
		<ul>
			<logic:iterate id="hook" name="hooks">
				<bean:define id="url">/organizationModel.do?method=viewModel&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;partyOid=<bean:write name="party" property="externalId"/></bean:define>
				<html:link action="<%= url %>" paramId="viewName" paramName="hook" paramProperty="viewName">
					<bean:write name="hook" property="viewName"/>
				</html:link>
			</logic:iterate>
		</ul>
	</logic:notEmpty>
	<logic:present name="viewPage">
		<jsp:include page='<%= (String) request.getAttribute("viewPage") %>'/>
	</logic:present>
</logic:present>

<h4>
<bean:message key="label.model.accountabilityTypes" bundle="ORGANIZATION_RESOURCES"/>:
</h4>
<logic:empty name="organizationalModel" property="accountabilityTypes">
	<bean:message key="label.model.accountabilityTypes.none" bundle="ORGANIZATION_RESOURCES"/>
</logic:empty>
<logic:notEmpty name="organizationalModel" property="accountabilityTypes">
	<ul>
		<logic:iterate id="accountabilityType" name="organizationalModel" property="sortedAccountabilityTypes">
			<li>
				<bean:write name="accountabilityType" property="name.content"/>
			</li>
		</logic:iterate>
	</ul>
</logic:notEmpty>
