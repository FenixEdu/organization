<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2>
	<bean:message key="label.model" bundle="ORGANIZATION_RESOURCES"/>
	:
	<bean:write name="organizationalModel" property="name"/>
</h2>

<h3>
	<bean:message key="label.edit" bundle="ORGANIZATION_RESOURCES" />
</h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<bean:define id="url">/organizationModel.do?method=viewModel&amp;organizationalModelOid=<bean:write name="organizationalModel" property="OID"/></bean:define>
<fr:edit id="organizationalModel" name="organizationalModel" schema="module.organization.domain.OrganizationalModel.edit"
	action="<%= url %>">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle2" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
	<fr:destination name="cancel" path="<%= url %>"/>
</fr:edit>
