<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message key="label.edit" bundle="ORGANIZATION_RESOURCES" /></h2>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<bean:define id="partyOid" name="personBean" property="person.OID" />
<fr:form action='<%= "/organization.do?partyOid=" + partyOid %>'>
	<html:hidden property="method" value="editPerson"/>
	
	<fr:edit id="personBean" name="personBean" schema="organization.PersonBean.manage">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2" />
			<fr:property name="columnClasses" value=",,tderror" />
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
	</fr:edit>
	
	<html:submit><bean:message key="label.edit" bundle="ORGANIZATION_RESOURCES" /></html:submit>
	<html:cancel onclick="this.form.method.value='viewParty';return true;" ><bean:message key="label.back" bundle="ORGANIZATION_RESOURCES" /></html:cancel>
</fr:form>
