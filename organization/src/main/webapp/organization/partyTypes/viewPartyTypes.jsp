<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<h2><bean:message key="label.party.types" bundle="ORGANIZATION_RESOURCES" /></h2>

<ul class="mbottom15">
	<li>
		<html:link action="/organization.do?method=prepareCreatePartyType"><bean:message key="label.create.new" bundle="ORGANIZATION_RESOURCES"/></html:link>
	</li>
</ul>

<logic:notEmpty name="partyTypes">
	<fr:view name="partyTypes" schema="organization.PartyType.view">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2 tdleft thleft"/>

			<fr:property name="linkFormat(editPartyType)" value="/organization.do?method=prepareEditPartyType&amp;partyTypeOid=\${externalId}" />
			<fr:property name="key(editPartyType)" value="label.edit"/>
			<fr:property name="bundle(editPartyType)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(editPartyType)" value="1"/>

			<fr:property name="linkFormat(deletePartyType)" value="/organization.do?method=deletePartyType&amp;partyTypeOid=\${externalId}" />
			<fr:property name="key(deletePartyType)" value="label.delete"/>
			<fr:property name="bundle(deletePartyType)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="confirmationKey(deletePartyType)" value="label.delete.confirmation.message"/>
			<fr:property name="confirmationBundle(deletePartyType)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(deletePartyType)" value="2"/>
			
			<fr:property name="sortBy" value="type" />
		</fr:layout>
	</fr:view>
</logic:notEmpty>

<logic:empty name="partyTypes">
	<em><bean:message key="label.no.party.types" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>
