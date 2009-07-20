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
	<html:link action="/organizationModel.do?method=editModel" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="OID">
		<bean:message key="label.model.edit" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	|
	<html:link action="/organizationModel.do?method=manageModelAccountabilityTypes" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="OID">
		<bean:message key="label.model.accountabilityTypes.manage" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	|
	<html:link action="/organizationModel.do?method=prepareAddUnitToModel" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="OID">
		<bean:message key="label.model.add.unit" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	|
	<html:link action="/organizationModel.do?method=prepareCreateUnit" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="OID">
		<bean:message key="label.model.create.unit" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	|
	<html:link action="/organizationModel.do?method=deleteModel" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="OID">
		<bean:message key="label.model.delete" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	<br/>
</logic:present>

<div class="dinline">
	<bean:define id="url">/organizationModel.do?method=viewModel&amp;organizationalModelOid=<bean:write name="organizationalModel" property="OID"/></bean:define>
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

<bean:define id="partyChart" name="partyChart" type="module.organization.presentationTier.actions.OrganizationModelAction.PartyChart"/>
<logic:notEmpty name="partyChart">

	<logic:present name="partyChart" property="unit">
		<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
			<span class="error0"> <bean:write name="message" /> </span>
			<br />
		</html:messages>

		<bean:define id="unit" name="partyChart" property="unit"/>
		<div class="infoop2">
			<table>
				<tr>
					<th>
						<bean:message key="label.unit" bundle="ORGANIZATION_RESOURCES"/>:
					</th>
					<td>
						<bean:write name="unit" property="partyName.content"/>
						(<bean:write name="unit" property="acronym"/>)
					</td>
				</tr>
				<tr>
					<th>
						<bean:message key="label.unit.party.types" bundle="ORGANIZATION_RESOURCES"/>:
					</th>
					<td>
						<logic:iterate indexId="i" id="partyType" name="unit" property="partyTypes">
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
						<logic:iterate indexId="i" id="model" name="unit" property="allOrganizationModels">
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

		<logic:present role="myorg.domain.RoleType.MANAGER">
		<bean:define id="url">/organizationModel.do?method=prepareEditUnit&amp;organizationalModelOid=<bean:write name="organizationalModel" property="OID"/></bean:define>
		<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="OID">
			<bean:message key="label.unit.edit" bundle="ORGANIZATION_RESOURCES"/>
		</html:link>
		|
		<bean:define id="url">/organizationModel.do?method=managePartyPartyTypes&amp;organizationalModelOid=<bean:write name="organizationalModel" property="OID"/></bean:define>
		<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="OID">
			<bean:message key="label.unit.partyTypes.manage" bundle="ORGANIZATION_RESOURCES"/>
		</html:link>
		|
		<bean:define id="url">/organizationModel.do?method=prepareAddUnit&amp;organizationalModelOid=<bean:write name="organizationalModel" property="OID"/></bean:define>
		<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="OID">
			<bean:message key="label.unit.child.add" bundle="ORGANIZATION_RESOURCES"/>
		</html:link>
		|
		<bean:define id="url">/organizationModel.do?method=prepareCreateUnit&amp;organizationalModelOid=<bean:write name="organizationalModel" property="OID"/></bean:define>
		<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="OID">
			<bean:message key="label.unit.child.create" bundle="ORGANIZATION_RESOURCES"/>
		</html:link>
		|
		<bean:define id="url">/organizationModel.do?method=deleteUnit&amp;organizationalModelOid=<bean:write name="organizationalModel" property="OID"/></bean:define>
		<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="OID">
			<bean:message key="label.unit.delete" bundle="ORGANIZATION_RESOURCES"/>
		</html:link>
		</logic:present>
	</logic:present>

	<table width="100%" align="center">
		<tr>
			<td align="center">
				<chart:orgChart id="party" name="partyChart" type="java.lang.Object">
					<%
						if (partyChart.getElement() == party) {
					%>
							<div class="orgTBox orgTBoxLight">
								<bean:write name="party" property="partyName"/>
							</div>
					<%
						} else {
					%>
							<div class="orgTBox orgTBoxLight">
								<bean:define id="url">/organizationModel.do?method=viewModel&amp;partyOid=<bean:write name="party" property="OID"/></bean:define>
								<html:link action="<%= url %>" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="OID">
									<bean:write name="party" property="partyName"/>
								</html:link>
							</div>
					<%
						}
					%>
				</chart:orgChart>
			</td>
		</tr>
	</table>
</logic:notEmpty>

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
