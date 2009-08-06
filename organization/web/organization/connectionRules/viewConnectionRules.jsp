<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>

<h2><bean:message key="label.connection.rules" bundle="ORGANIZATION_RESOURCES" /></h2>
<br/>
<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<html:link action="/organization.do?method=prepareCreateConnectionRule"><bean:message key="label.create.new" bundle="ORGANIZATION_RESOURCES"/></html:link>

<logic:notEmpty name="connectionRules">
	<fr:view name="connectionRules" schema="organization.ConnectionRule.view">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2"/>

			<fr:property name="linkFormat(deleteConnectionRule)" value="/organization.do?method=deleteConnectionRule&amp;connectionRuleOid=${externalId}" />
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
	<em><bean:message key="label.no.connection.rules" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>
