<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/chart.tld" prefix="chart" %>
<%@page import="myorg.presentationTier.component.OrganizationChart"%>
<%@page import="module.organization.domain.Unit"%>


<html:link action="/organization.do?method=viewPartyTypes"  styleClass="secondaryLink">
	<bean:message key="label.party.type" bundle="ORGANIZATION_RESOURCES"/>
</html:link>
|
<html:link action="/organization.do?method=viewAccountabilityTypes"  styleClass="secondaryLink">
	<bean:message key="label.party.type" bundle="ORGANIZATION_RESOURCES"/>
</html:link>
|
<html:link action="/organization.do?method=viewConnectionRules"  styleClass="secondaryLink">
	<bean:message key="label.connection.rules" bundle="ORGANIZATION_RESOURCES"/>
</html:link>
|
<html:link action="/organization.do?method=viewOrganization"  styleClass="secondaryLink">
	<bean:message key="label.organizational.structure" bundle="ORGANIZATION_RESOURCES"/>
</html:link>
|
<html:link action="/organization.do?method=managePersons"  styleClass="secondaryLink">
	<bean:message key="label.persons.manage" bundle="ORGANIZATION_RESOURCES"/>
</html:link>


<h2><bean:message key="label.models" bundle="ORGANIZATION_RESOURCES"/></h2>

<logic:present role="myorg.domain.RoleType.MANAGER">
	<p class="mtop05">
		<html:link action="/organizationModel.do?method=prepareCreateModel">
			+ <bean:message key="label.models.create" bundle="ORGANIZATION_RESOURCES"/>
		</html:link>
	</p>
</logic:present>

<logic:empty name="organizationalModels">
	<bean:message key="label.models.none" bundle="ORGANIZATION_RESOURCES"/>
</logic:empty>

<logic:notEmpty name="organizationalModels">
	<chart:orgChart id="organizationalModel" name="organizationalModelChart" type="java.lang.Object">
		<div class="orgTBox orgTBoxLight">
			<html:link action="/organizationModel.do?method=viewModel" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
				<bean:write name="organizationalModel" property="name.content"/>
			</html:link>
		</div>
	</chart:orgChart>
</logic:notEmpty>
