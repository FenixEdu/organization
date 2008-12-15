<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>

<h2><bean:message key="label.organization" bundle="ORGANIZATION_RESOURCES" /></h2>

<br/>
<html:link action="/organization.do?method=prepareCreateUnit"><bean:message key="label.create.top.unit" bundle="ORGANIZATION_RESOURCES"/></html:link>

<logic:notEmpty name="topUnits">
	<ul>
	<logic:iterate id="unit" name="topUnits">
		<bean:size id="children" name="unit" property="children"/>
		<li><html:link action="/organization.do?method=viewUnit" paramId="unitOid" paramName="unit" paramProperty="OID"><bean:write name="unit" property="partyName.content" /></html:link> (<bean:write name="children" />) </li>
	</logic:iterate>
	</ul>	
</logic:notEmpty>

<logic:empty name="topUnits">
	<em><bean:message key="label.no.top.units" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>