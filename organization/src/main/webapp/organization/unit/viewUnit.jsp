<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message key="label.unit" bundle="ORGANIZATION_RESOURCES" /></h2>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<bean:message key="label.unit.view" bundle="ORGANIZATION_RESOURCES" />
<fr:view name="unit" schema="organization.Unit.view">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle2" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
</fr:view>

<html:link action="/organization.do?method=prepareEditUnit" paramId="partyOid" paramName="unit" paramProperty="externalId">
	<bean:message key="label.edit" bundle="ORGANIZATION_RESOURCES" />
</html:link>, 
<bean:define id="unitName" name="unit" property="partyName.content" />
<bean:define id="message">return confirm('<bean:message key="label.unit.delete.confirm.message" bundle="ORGANIZATION_RESOURCES" arg0="<%= unitName.toString() %>" />')</bean:define>
<html:link action="/organization.do?method=deleteUnit" paramId="partyOid" paramName="unit" paramProperty="externalId" onclick="<%= message %>">
	<bean:message key="label.delete" bundle="ORGANIZATION_RESOURCES" />
</html:link>, 
<html:link action="/organization.do?method=prepareEditPartyPartyTypes" paramId="partyOid" paramName="unit" paramProperty="externalId">
	<bean:message key="label.party.partyTypes" bundle="ORGANIZATION_RESOURCES" />
</html:link>

<br/>
<br/>
<br/>

<bean:message key="label.unit.parents" bundle="ORGANIZATION_RESOURCES" />: <html:link action="/organization.do?method=prepareAddParent" paramId="partyOid" paramName="unit" paramProperty="externalId"><bean:message key="label.add.parent" bundle="ORGANIZATION_RESOURCES" /></html:link>
<logic:notEmpty name="unit" property="parentAccountabilities">
	<fr:view name="unit" property="parentAccountabilities" schema="organization.Unit.view.parent.accountability">
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

<logic:empty name="unit" property="parentAccountabilities">
	<br/>
	<em><bean:message key="label.unit.no.parents" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>

<br/>
<br/>

<bean:message key="label.unit.children" bundle="ORGANIZATION_RESOURCES" />: <html:link action="/organization.do?method=prepareAddChild" paramId="partyOid" paramName="unit" paramProperty="externalId"><bean:message key="label.add.child" bundle="ORGANIZATION_RESOURCES" /></html:link>, <html:link action="/organization.do?method=prepareCreateUnit" paramId="partyOid" paramName="unit" paramProperty="externalId"><bean:message key="label.create.child" bundle="ORGANIZATION_RESOURCES" /></html:link>
<logic:notEmpty name="unit" property="childAccountabilities">
	<fr:view name="unit" property="childAccountabilities" schema="organization.Unit.view.child.accountability">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2" />
			
			<fr:property name="linkFormat(viewParty)" value="/organization.do?method=viewParty&amp;partyOid=\${child.externalId}" />
			<fr:property name="key(viewParty)" value="label.view"/>
			<fr:property name="bundle(viewParty)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(viewParty)" value="1"/>
			<fr:property name="visibleIf(viewParty)" value="child.unit"/>
			
			<fr:property name="linkFormat(removeChild)" value="/organization.do?method=removeChild&amp;accOid=\${externalId}" />
			<fr:property name="key(removeChild)" value="label.remove"/>
			<fr:property name="bundle(removeChild)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="confirmationKey(removeChild)" value="label.remove.confirmation.message" />
			<fr:property name="confirmationBundle(removeChild)" value="ORGANIZATION_RESOURCES" />
			<fr:property name="order(removeChild)" value="2"/>
			<fr:property name="visibleIf(removeChild)" value="child.person"/>
			
			<fr:property name="sortBy" value="child.partyName" />
		</fr:layout>
	</fr:view>
</logic:notEmpty>

<logic:empty name="unit" property="childAccountabilities">
	<br/>
	<em><bean:message key="label.unit.no.children" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>

<br/>
<br/>

<html:link action="/organization.do?method=viewOrganization">
	<bean:message key="label.back" bundle="ORGANIZATION_RESOURCES" />
</html:link>
