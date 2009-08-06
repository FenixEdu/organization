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

<h3>
	<bean:message key="label.model.add.unit" bundle="ORGANIZATION_RESOURCES"/>
</h3>

<bean:define id="url">/organizationModel.do?method=addUnitToModel&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/></bean:define>
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

