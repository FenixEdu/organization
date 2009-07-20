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

<jsp:include page="viewPartyDetails.jsp"/>

<h3>
	<bean:message key="label.unit.edit" bundle="ORGANIZATION_RESOURCES"/>
</h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>


<bean:define id="actionUrl">/organizationModel.do?organizationalModelOid=<bean:write name="organizationalModel" property="OID"/>&amp;partyOid=<bean:write name="party" property="OID"/></bean:define>
<fr:form action='<%= actionUrl %>'>
	<html:hidden property="method" value="editUnit"/>
	
	<fr:edit id="unitBean" name="unitBean" visible="false" />

	<%-- edit unit --%>
	<fr:edit id="unitBean.edit.unit" name="unitBean" schema="organization.UnitBean.edit.unit" >
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
	</fr:edit>
	
	<html:submit><bean:message key="label.edit" bundle="ORGANIZATION_RESOURCES" /></html:submit>
	<html:cancel onclick="this.form.method.value='viewModel';return true;" ><bean:message key="label.back" bundle="ORGANIZATION_RESOURCES" /></html:cancel>
</fr:form>
