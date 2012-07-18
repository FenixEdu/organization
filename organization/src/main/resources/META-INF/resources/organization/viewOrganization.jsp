<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/organization" prefix="vo" %>

<h2><bean:message key="label.organization" bundle="ORGANIZATION_RESOURCES" /></h2>

<br/>
<html:link action="/organization.do?method=prepareCreateUnit"><bean:message key="label.create.top.unit" bundle="ORGANIZATION_RESOURCES"/></html:link>
<logic:empty name="myorg" property="topUnits">
	<em><bean:message key="label.no.top.units" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>

<%-- <fr:view name="myorg" layout="organization" /> --%>
<vo:viewOrganization organization="myorg" configuration="config">
	<vo:property name="rootClasses" value="tree" />
	<vo:property name="viewPartyUrl" value="/organization.do?method=viewParty&amp;partyOid=%s" />
</vo:viewOrganization>
