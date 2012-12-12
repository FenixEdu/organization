<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/chart" prefix="chart" %>
<%@page import="pt.ist.bennu.core.presentationTier.component.OrganizationChart"%>
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
	<fr:view name="party" property="childAccountabilitiesSet">
		<fr:schema type="module.organization.domain.Accountability" bundle="ORGANIZATION_RESOURCES">
			<fr:slot name="child.presentationName" key="label.name"/>
			<fr:slot name="accountabilityType.type" key="label.type"/>
			<fr:slot name="beginDate" key="label.begin"/>
			<fr:slot name="endDate" key="label.end" />
		</fr:schema>
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

			<fr:property name="sortBy" value="child.presentationName=asc"/>
		</fr:layout>
	</fr:view>
</logic:notEmpty>
