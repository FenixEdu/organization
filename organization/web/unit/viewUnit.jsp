<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message key="label.unit" bundle="ORGANIZATION_RESOURCES" /></h2>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<bean:message key="label.unit.view" bundle="ORGANIZATION_RESOURCES" />
<fr:view name="unit" schema="organization.Unit.view">
	<fr:layout name="tabular">
		<fr:property name="classes" value="form thwidth150px" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
</fr:view>
