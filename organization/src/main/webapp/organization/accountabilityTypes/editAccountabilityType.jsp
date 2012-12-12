<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message key="label.edit" bundle="ORGANIZATION_RESOURCES" /></h2>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>


<fr:edit id="accountabilityTypeBean" name="accountabilityTypeBean"
	schema="organization.AccountabilityTypeBean"
	action="/organization.do?method=editAccountabilityType">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle2" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
	<fr:destination name="cancel" path="/organization.do?method=viewAccountabilityTypes" />
</fr:edit>
