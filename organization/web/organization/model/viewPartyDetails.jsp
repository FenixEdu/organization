<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/chart.tld" prefix="chart" %>

<div class="infoop2">
	<table>
		<tr>
			<th>
				<bean:message key="label.unit" bundle="ORGANIZATION_RESOURCES"/>:
			</th>
			<td>
				<bean:write name="party" property="partyName.content"/>
				(<bean:write name="party" property="acronym"/>)
			</td>
		</tr>
		<tr>
			<th>
				<bean:message key="label.unit.party.types" bundle="ORGANIZATION_RESOURCES"/>:
			</th>
			<td>
				<logic:iterate indexId="i" id="partyType" name="party" property="partyTypes">
					<% if (i > 0) { %>
						,
					<% } %>
					<bean:write name="partyType" property="name.content"/>
				</logic:iterate>
			</td>
		</tr>
		<tr>
			<th>
				<bean:message key="label.models.short" bundle="ORGANIZATION_RESOURCES"/>:
			</th>
			<td>
				<logic:iterate indexId="i" id="model" name="party" property="allOrganizationModels">
					<% if (i > 0) { %>
						,
					<% } %>
					<html:link action="/organizationModel.do?method=viewModel" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="OID">
						<bean:write name="model" property="name.content"/>
					</html:link>
				</logic:iterate>
			</td>
		</tr>
	</table>
</div>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>
