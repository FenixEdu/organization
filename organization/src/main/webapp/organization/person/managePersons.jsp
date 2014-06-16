<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<h2><bean:message key="label.persons.manage" bundle="ORGANIZATION_RESOURCES"/></h2>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<html:form action="/organization.do?method=searchPerson">
	<bean:message key="label.name" bundle="ORGANIZATION_RESOURCES" />: <html:text property="name" />
	<html:submit styleClass="inputbutton"><bean:message key="label.search" bundle="ORGANIZATION_RESOURCES" /></html:submit>
</html:form>

<ul>
	<li>
		<html:link action="/organization.do?method=prepareCreatePerson">
			<bean:message key="label.create.person" bundle="ORGANIZATION_RESOURCES" />
		</html:link>
	</li>
</ul>
<logic:present name="persons">
	<logic:notEmpty name="persons">
		<fr:view name="persons" schema="organization.domain.Person.view.short">
			<fr:layout name="tabular">
				<fr:property name="classes" value="table" />
				<fr:property name="columnClasses" value=",,tderror" />
				
				<fr:property name="linkFormat(viewParty)" value="/organization.do?method=viewParty&amp;partyOid=\${externalId}" />
				<fr:property name="key(viewParty)" value="label.view"/>
				<fr:property name="bundle(viewParty)" value="ORGANIZATION_RESOURCES"/>
				<fr:property name="order(viewParty)" value="1"/>
				</fr:layout>
		</fr:view>
	</logic:notEmpty>
	
	<logic:empty name="persons">
		<strong><em><bean:message key="label.no.persons" bundle="ORGANIZATION_RESOURCES"/></em></strong>
	</logic:empty>
</logic:present>
