<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/chart" prefix="chart" %>
<%@page import="module.organization.domain.Unit"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/organization/CSS/organization.css" media="screen"/>

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

<logic:present role="#managers">
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
