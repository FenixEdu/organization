<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/chart" prefix="chart" %>
<%@page import="module.organization.domain.Unit"%>

<h2>
	<bean:message key="label.model" bundle="ORGANIZATION_RESOURCES"/>:
	<bean:write name="organizationalModel" property="name.content"/>
</h2>

<logic:present name="party">
	<jsp:include page="viewPartyDetails.jsp"/>
</logic:present>

<h3 class="mtop15">
	<logic:present name="unitBean" property="parent">
		<bean:message key="label.create.unit" bundle="ORGANIZATION_RESOURCES" />
	</logic:present>

	<logic:notPresent name="unitBean" property="parent">
		<bean:message key="label.create.top.unit" bundle="ORGANIZATION_RESOURCES" />
	</logic:notPresent>
</h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<p>
		<span class="error0"> <bean:write name="message" /> </span>
	</p>
</html:messages>

<bean:define id="actionUrl">/organizationModel.do?organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;<logic:present name="unitBean" property="parent">partyOid=<bean:write name="unitBean" property="parent.externalId" />&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></logic:present></bean:define>
<fr:form action="<%= actionUrl %>">
	<html:hidden property="method" value="createUnit"/>
	
	<fr:edit id="unitBean" name="unitBean" visible="false" />

	<%-- Create unit --%>
	<logic:present name="unitBean" property="parent">
		<fr:edit id="unitBean.create.unit" name="unitBean" schema="organization.UnitBean.create.unit" >
			<fr:layout name="tabular">
				<fr:property name="classes" value="form" />
				<fr:property name="columnClasses" value=",,tderror" />
				<fr:property name="requiredMarkShown" value="true" />
			</fr:layout>
		</fr:edit>

	</logic:present>

	<logic:notPresent name="unitBean" property="parent">
		<fr:edit id="unitBean.create.top.unit" name="unitBean" schema="organization.UnitBean.create.top.unit">
			<fr:layout name="tabular">
				<fr:property name="classes" value="form" />
				<fr:property name="columnClasses" value=",,tderror" />
				<fr:property name="requiredMarkShown" value="true" />
			</fr:layout>
		</fr:edit>
	</logic:notPresent>

	<html:submit styleClass="inputbutton"><bean:message key="label.create" bundle="ORGANIZATION_RESOURCES" /></html:submit>
	<html:cancel onclick="this.form.method.value='viewModel';return true;" styleClass="inputbutton"><bean:message key="label.cancel" bundle="ORGANIZATION_RESOURCES" /></html:cancel>
</fr:form>
