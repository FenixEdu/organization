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

<logic:present name="accountability">
	<fr:view name="accountability">
		<fr:schema type="module.organization.domain.Accountability" bundle="ORGANIZATION_RESOURCES">
			<fr:slot name="child.partyName" key="label.name"/>
			<fr:slot name="accountabilityType.type" key="label.type"/>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2 tdleft thleft"/>
		</fr:layout>
	</fr:view>

	<bean:define id="urlEdit">/organizationModel.do?method=prepareManageChildAccountabilities&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;partyOid=<bean:write name="party" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
	<fr:edit id="accountability" name="accountability" action="<%= urlEdit %>">
		<fr:schema type="module.organization.domain.Accountability" bundle="ORGANIZATION_RESOURCES">
			<fr:slot name="beginDate" key="label.begin" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			<fr:slot name="endDate" key="label.end"/>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="form listInsideClear" />
			<fr:property name="columnClasses" value="width100px,,tderror" />
		</fr:layout>
	</fr:edit>
</logic:present>
