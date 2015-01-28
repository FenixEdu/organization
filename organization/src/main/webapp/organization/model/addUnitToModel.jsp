<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/chart" prefix="chart" %>
<%@page import="org.fenixedu.bennu.core.presentationTier.component.OrganizationChart"%>
<%@page import="module.organization.domain.Unit"%>

<h2>
	<bean:message key="label.model" bundle="ORGANIZATION_RESOURCES"/>:
	<bean:write name="organizationalModel" property="name.content"/>
</h2>

<h3 class="mtop05">
	<bean:message key="label.model.add.unit" bundle="ORGANIZATION_RESOURCES"/>
</h3>

<bean:define id="url">/organizationModel.do?method=addUnitToModel&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/></bean:define>
<fr:form action="<%= url %>">

	<fr:edit id="partySearchBean" name="partySearchBean" schema="module.organization.domain.search.PartySearchBean">
		<fr:layout name="tabular">
			<fr:property name="classes" value="form mbottom0 mtop15"/>
			<fr:property name="columnClasses" value=",,tderror"/>
		</fr:layout>
	</fr:edit>

	<p class="mtop05">
		<html:submit styleClass="inputbutton"><bean:message key="renderers.list.management.add" bundle="RENDERER_RESOURCES"/></html:submit>
	</p>

</fr:form>
