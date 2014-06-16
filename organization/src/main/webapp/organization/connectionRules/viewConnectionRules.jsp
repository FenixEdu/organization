<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<h2><bean:message key="label.connection.rules" bundle="ORGANIZATION_RESOURCES" /></h2>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<p>
		<span class="error0"> <bean:write name="message" /> </span>
	</p>
</html:messages>

<ul class="mbottom15">
	<li><html:link action="/organization.do?method=prepareCreateConnectionRule"><bean:message key="label.create.new" bundle="ORGANIZATION_RESOURCES"/></html:link></li>
</ul>

<logic:notEmpty name="connectionRules">
	<fr:view name="connectionRules" schema="organization.ConnectionRule.view">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2 tdleft thleft"/>

			<fr:property name="linkFormat(deleteConnectionRule)" value="/organization.do?method=deleteConnectionRule&amp;connectionRuleOid=\${externalId}" />
			<fr:property name="key(deleteConnectionRule)" value="label.delete"/>
			<fr:property name="bundle(deleteConnectionRule)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="confirmationKey(deleteConnectionRule)" value="label.delete.confirmation.message"/>
			<fr:property name="confirmationBundle(deleteConnectionRule)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(deleteConnectionRule)" value="1"/>
			
			<fr:property name="sortBy" value="externalId" />
		</fr:layout>
	</fr:view>
</logic:notEmpty>

<logic:empty name="connectionRules">
	<p><em><bean:message key="label.no.connection.rules" bundle="ORGANIZATION_RESOURCES" /></em></p>
</logic:empty>
