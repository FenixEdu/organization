<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/chart.tld" prefix="chart" %>
<%@page import="myorg.presentationTier.component.OrganizationChart"%>
<%@page import="module.organization.domain.Unit"%>

<h2>
	<bean:message key="label.model" bundle="ORGANIZATION_RESOURCES"/>:
	<bean:write name="organizationalModel" property="name"/>
</h2>

<logic:present name="party">
	<jsp:include page="viewPartyDetails.jsp"/>
</logic:present>

<h3 class="mtop15">
	<bean:message key="label.unit.child.remove" bundle="ORGANIZATION_RESOURCES"/>
</h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<p>
		<span class="error0"> <bean:write name="message" /> </span>
	</p>
</html:messages>

<logic:empty name="party" property="childAccountabilitiesSet">
	<p>
		<bean:message bundle="ORGANIZATION_RESOURCES" key="label.unit.child.none"/>
	</p>
</logic:empty>

<logic:notEmpty name="party" property="childAccountabilitiesSet">
	<bean:define id="urlEdit">/organizationModel.do?method=prepareEditAccountability&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;partyOid=<bean:write name="party" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
	<bean:define id="urlDelete">/organizationModel.do?method=deleteAccountability&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;partyOid=<bean:write name="party" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
	<fr:view name="party" property="childAccountabilitiesSet" schema="module.organization.domain.Accountability.with.child.info">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2 tdleft thleft"/>

			<fr:property name="linkFormat(editAccountability)" value="<%= urlEdit + "&amp;accountabilityOid=${externalId}" %>" />
			<fr:property name="key(editAccountability)" value="label.edit"/>
			<fr:property name="bundle(editAccountability)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(editAccountability)" value="1"/>

			<fr:property name="linkFormat(deleteAccountability)" value="<%= urlDelete + "&amp;accountabilityOid=${externalId}" %>" />
			<fr:property name="key(deleteAccountability)" value="label.delete"/>
			<fr:property name="bundle(deleteAccountability)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="confirmationKey(deleteAccountability)" value="label.delete.confirmation.message"/>
			<fr:property name="confirmationBundle(deleteAccountability)" value="ORGANIZATION_RESOURCES"/>
			<fr:property name="order(deleteAccountability)" value="2"/>
		</fr:layout>
	</fr:view>
</logic:notEmpty>
