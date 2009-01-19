<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message key="label.create" bundle="ORGANIZATION_RESOURCES" /></h2>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<bean:define id="actionUrl">/organization.do?<logic:present name="unitBean" property="parent">unitOid=<bean:write name="unitBean" property="parent.OID" /></logic:present></bean:define>

<fr:form action="<%= actionUrl %>">
	<html:hidden property="method" value="createUnit"/>
	
	<fr:edit id="unitBean" name="unitBean" visible="false" />

	<%-- Create unit --%>
	<logic:present name="unitBean" property="parent">
		<bean:message key="label.create.unit" bundle="ORGANIZATION_RESOURCES" />
		<fr:edit id="unitBean.create.unit" name="unitBean" schema="organization.UnitBean.create.unit" >
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle2" />
				<fr:property name="columnClasses" value=",,tderror" />
			</fr:layout>
		</fr:edit>
		
		<html:submit><bean:message key="label.create" bundle="ORGANIZATION_RESOURCES" /></html:submit>
		<html:cancel onclick="this.form.method.value='viewUnit';return true;" ><bean:message key="label.cancel" bundle="ORGANIZATION_RESOURCES" /></html:cancel>
	</logic:present>

	<%-- Create top unit --%>
	<logic:notPresent name="unitBean" property="parent">
		<bean:message key="label.create.top.unit" bundle="ORGANIZATION_RESOURCES" />
		<fr:edit id="unitBean.create.top.unit" name="unitBean" schema="organization.UnitBean.create.top.unit">
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle2" />
				<fr:property name="columnClasses" value=",,tderror" />
			</fr:layout>
		</fr:edit>
		
		<html:submit><bean:message key="label.create" bundle="ORGANIZATION_RESOURCES" /></html:submit>
		<html:cancel onclick="this.form.method.value='viewOrganization';return true;" ><bean:message key="label.cancel" bundle="ORGANIZATION_RESOURCES" /></html:cancel>
	</logic:notPresent>
	
</fr:form>
