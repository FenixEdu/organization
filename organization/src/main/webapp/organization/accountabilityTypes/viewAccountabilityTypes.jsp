<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<h2><bean:message key="label.accountability.types" bundle="ORGANIZATION_RESOURCES" /></h2>

<ul class="mbottom15">
	<li>
		<html:link action="/organization.do?method=prepareCreateAccountabilityType"><bean:message key="label.create.new" bundle="ORGANIZATION_RESOURCES"/></html:link>
	</li>
</ul>

<logic:notEmpty name="accountabilityTypes">
	<fr:view name="accountabilityTypes" schema="organization.AccountabilityType.view">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2 tdleft thleft"/>

			<fr:property name="linkFormat(editAccountabilityType)" value="/organization.do?method=prepareEditAccountabilityType&amp;accountabilityTypeOid=\${externalId}" />
			<fr:property name="key(editAccountabilityType)" value="label.edit"/>
			<fr:property name="bundle(editAccountabilityType)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(editAccountabilityType)" value="1"/>

			<fr:property name="linkFormat(editAccountabilityType)" value="/organization.do?method=prepareAssociateConnectionRules&amp;accountabilityTypeOid=\${externalId}" />
			<fr:property name="key(editAccountabilityType)" value="label.associate.connection.rules"/>
			<fr:property name="bundle(editAccountabilityType)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(editAccountabilityType)" value="2"/>
		
			<fr:property name="linkFormat(deleteAccountabilityType)" value="/organization.do?method=deleteAccountabilityType&amp;accountabilityTypeOid=\${externalId}" />
			<fr:property name="key(deleteAccountabilityType)" value="label.delete"/>
			<fr:property name="bundle(deleteAccountabilityType)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="confirmationKey(deleteAccountabilityType)" value="label.delete.confirmation.message"/>
			<fr:property name="confirmationBundle(deleteAccountabilityType)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(deleteAccountabilityType)" value="3"/>
			
			<fr:property name="sortBy" value="type" />
		</fr:layout>
	</fr:view>
</logic:notEmpty>

<logic:empty name="accountabilityTypes">
	<em><bean:message key="label.no.accountability.types" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>
