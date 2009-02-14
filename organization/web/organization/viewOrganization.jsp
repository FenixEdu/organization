<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>

<style type="text/css">

ul ul { margin-top: 0; margin-bottom: 0; }
.tree li { list-style: none; }

ul li span {
font-weight: bold;
}
ul ul li span {
font-weight: normal;
}

</style>

<h2><bean:message key="label.organization" bundle="ORGANIZATION_RESOURCES" /></h2>

<br/>
<html:link action="/organization.do?method=prepareCreateUnit"><bean:message key="label.create.top.unit" bundle="ORGANIZATION_RESOURCES"/></html:link>
<logic:empty name="topUnits">
	<em><bean:message key="label.no.top.units" bundle="ORGANIZATION_RESOURCES" /></em>
</logic:empty>
<fr:view name="myorg" layout="organization" />
