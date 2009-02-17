<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>
<%@ taglib uri="/WEB-INF/organization.tld" prefix="vo" %>

<h2><bean:message key="label.organization" bundle="ORGANIZATION_RESOURCES" /></h2>

<br/>
<html:link action="/organization.do?method=prepareCreateUnit"><bean:message key="label.create.top.unit" bundle="ORGANIZATION_RESOURCES"/></html:link>
<logic:empty name="myorg" property="topUnits">
	<em><bean:message key="label.no.top.units" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>

<%-- <fr:view name="myorg" layout="organization" /> --%>
<vo:viewOrganization organization="myorg" configuration="config" />
