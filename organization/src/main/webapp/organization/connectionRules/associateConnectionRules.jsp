<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message key="label.connection.rules" bundle="ORGANIZATION_RESOURCES" /></h2>

<p class="mtop05">
	<strong><bean:message key="label.accountability.type" bundle="ORGANIZATION_RESOURCES" /> <fr:view name="accountabilityType" property="name" /></strong>
</p>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<p>
		<span class="error0"><bean:write name="message" /></span>
	</p>
</html:messages>

<fr:edit name="accountabilityType" action='/organization.do?accountabilityTypeOid=${accountabilityType.externalId}&method=viewAccountabilityTypes'>
	
	<fr:layout name="tabular">
		<fr:property name="classes" value="form listInsideClear" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
	<fr:schema type="module.organization.domain.AccountabilityType" bundle="ORGANIZATION_RESOURCES">
		<fr:slot name="connectionRules" layout="option-select" key="label.unit.party.type">
			<fr:property name="eachSchema" value="organization.ConnectionRule.view"/>
	        <fr:property name="providerClass" value="module.organization.presentationTier.renderers.providers.ConnectionRulesProvider" />
	        <fr:property name="classes" value="nobullet noindent"/>
	        <fr:property name="sortBy" value="description"/>
	    </fr:slot>
	</fr:schema>
</fr:edit>
