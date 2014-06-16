<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message key="label.person" bundle="ORGANIZATION_RESOURCES" /></h2>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<fr:view name="person" schema="organization.domain.Person.view">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle2" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
</fr:view>

<html:link action="/organization.do?method=prepareEditPerson" paramId="partyOid" paramName="person" paramProperty="externalId">
	<bean:message key="label.edit" bundle="ORGANIZATION_RESOURCES" />
</html:link>, 
<bean:define id="message">return confirm('<bean:message key="label.remove.confirmation.message" bundle="ORGANIZATION_RESOURCES" />;')</bean:define>
<html:link action="/organization.do?method=deletePerson" paramId="partyOid" paramName="person" paramProperty="externalId" onclick="<%= message %>">
	<bean:message key="label.delete" bundle="ORGANIZATION_RESOURCES" />
</html:link>, 
<html:link action="/organization.do?method=prepareEditPartyPartyTypes" paramId="partyOid" paramName="person" paramProperty="externalId">
	<bean:message key="label.party.partyTypes" bundle="ORGANIZATION_RESOURCES" />
</html:link>

<br/>
<br/>
<br/>

<bean:message key="label.unit.parents" bundle="ORGANIZATION_RESOURCES" />: <html:link action="/organization.do?method=prepareAddParent" paramId="partyOid" paramName="person" paramProperty="externalId"><bean:message key="label.add.parent" bundle="ORGANIZATION_RESOURCES" /></html:link>
<logic:notEmpty name="person" property="parentAccountabilities">
	<fr:view name="person" property="parentAccountabilities" schema="organization.Unit.view.parent.accountability">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2" />
			
			<fr:property name="linkFormat(viewParty)" value="/organization.do?method=viewParty&amp;partyOid=\${parent.externalId}" />
			<fr:property name="key(viewParty)" value="label.view"/>
			<fr:property name="bundle(viewParty)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(viewParty)" value="1"/>

			<fr:property name="linkFormat(removeParent)" value="/organization.do?method=removeParent&amp;accOid=\${externalId}" />
			<fr:property name="key(removeParent)" value="label.remove"/>
			<fr:property name="bundle(removeParent)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="confirmationKey(removeParent)" value="label.remove.confirmation.message" />
			<fr:property name="confirmationBundle(removeParent)" value="ORGANIZATION_RESOURCES" />
			<fr:property name="order(removeParent)" value="2"/>
			
			<fr:property name="sortBy" value="parent.partyName" />
		</fr:layout>
	</fr:view>
</logic:notEmpty>
<logic:empty name="person" property="parentAccountabilities">
	<br/>
	<em><bean:message key="label.person.no.parents" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>
