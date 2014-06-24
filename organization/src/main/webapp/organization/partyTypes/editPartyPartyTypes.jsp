<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message key="label.party.partyTypes" bundle="ORGANIZATION_RESOURCES" /></h2>
<h3><fr:view name="party" property="partyName" /></h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<fr:edit name="party" action="/organization.do?partyOid=${party.externalId}&method=viewParty">
	<fr:layout name="tabular">
		<fr:property name="classes" value="form listInsideClear" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
	<fr:schema type="module.organization.domain.Party" bundle="ORGANIZATION_RESOURCES">
		<fr:slot name="partyTypes" layout="option-select" key="label.unit.party.type">
			<fr:property name="eachLayout" value="values-dash"/>
	        <fr:property name="providerClass" value="module.organization.presentationTier.renderers.providers.PartyTypesProvider" />
	        <fr:property name="classes" value="nobullet noindent"/>
	        <fr:property name="sortBy" value="name"/>
	    </fr:slot>
	</fr:schema>
	<fr:destination name="cancel" path="/organization.do?partyOid=${party.externalId}&method=viewParty" />
</fr:edit>
