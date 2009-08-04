<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/chart.tld" prefix="chart" %>

<div class="dataBox">
	<table>
		<tr>
			<td colspan="2">
				<strong><fr:view name="party" property="partyName"/></strong>
			</td>
		</tr>
		<tr>
			<th>
				<bean:message key="label.unit.party.types" bundle="ORGANIZATION_RESOURCES"/>:
			</th>
			<td>
				<fr:view name="party" property="partyTypes">
					<fr:layout name="separator-list">
						<fr:property name="eachLayout" value="values"/>
						<fr:property name="eachSchema" value="organization.PartyType.view.name"/>
						<fr:property name="separator" value=","/>
					</fr:layout>
				</fr:view>
			</td>
		</tr>
		<tr>
			<th>
				<bean:message key="label.models.short" bundle="ORGANIZATION_RESOURCES"/>:
			</th>
			<td>
				<fr:view name="party" property="allOrganizationModels">
					<fr:layout name="separator-list">
						<fr:property name="eachLayout" value="values"/>
						<fr:property name="eachSchema" value="module.organization.domain.OrganizationalModel.edit"/>
						<fr:property name="separator" value=","/>
						<fr:property name="link" value="/organizationModel.do?method=viewModel"/>
						<fr:property name="param" value="externalId/organizationalModelOid"/>
					</fr:layout>
				</fr:view>	
			</td>
		</tr>
	</table>
</div>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>
