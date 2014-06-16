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

<logic:present name="party">
	<jsp:include page="viewPartyDetails.jsp"/>
</logic:present>

<h3 class="mtop15">
	<bean:message key="label.unit.child.add" bundle="ORGANIZATION_RESOURCES"/>
</h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<p>
		<span class="error0"> <bean:write name="message" /> </span>
	</p>
</html:messages>

<bean:define id="actionUrl">/organizationModel.do?organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;<logic:present name="unitBean" property="parent">partyOid=<bean:write name="unitBean" property="parent.externalId" />&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></logic:present></bean:define>
<fr:form action="<%= actionUrl %>">
	<html:hidden property="method" value="addUnit"/>
	
	<fr:edit id="unitBean" name="unitBean" visible="false" />

	<fr:edit id="unitBean.create.unit" name="unitBean" schema="organization.UnitBean.add.unit" >
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
	</fr:edit>
	<p class="mtop05">
		<html:submit styleClass="inputbutton"><bean:message key="label.add" bundle="ORGANIZATION_RESOURCES" /></html:submit>
		<html:cancel styleClass="inputbutton" onclick="this.form.method.value='viewModel';return true;" ><bean:message key="label.cancel" bundle="ORGANIZATION_RESOURCES" /></html:cancel>
	</p>
</fr:form>
