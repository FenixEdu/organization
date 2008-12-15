<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message key="label.unit" bundle="ORGANIZATION_RESOURCES" /></h2>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<bean:message key="label.unit.view" bundle="ORGANIZATION_RESOURCES" />
<fr:view name="unit" schema="organization.Unit.view">
	<fr:layout name="tabular">
		<fr:property name="classes" value="form thwidth150px" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
</fr:view>

<html:link action="/organization.do?method=prepareEditUnit" paramId="unitOid" paramName="unit" paramProperty="OID">
	<bean:message key="label.edit" bundle="ORGANIZATION_RESOURCES" />
</html:link>,
<bean:define id="unitName" name="unit" property="partyName.content" />
<bean:define id="message">return confirm('<bean:message key="label.unit.delete.confirm.message" bundle="ORGANIZATION_RESOURCES" arg0="<%= unitName.toString() %>" />')</bean:define>
<html:link action="/organization.do?method=deleteUnit" paramId="unitOid" paramName="unit" paramProperty="OID" onclick="<%= message %>">
	<bean:message key="label.delete" bundle="ORGANIZATION_RESOURCES" />
</html:link>

<br/>
<br/>
<bean:message key="label.unit.parents" bundle="ORGANIZATION_RESOURCES" />: <html:link action="/organization.do?method=prepareAddParent" paramId="parentOid" paramName="unit" paramProperty="OID"><bean:message key="label.add.parent" bundle="ORGANIZATION_RESOURCES" /></html:link>
<logic:notEmpty name="unit" property="parentAccountabilities">
	<fr:view name="unit" property="parentAccountabilities" schema="organization.Unit.view.parent.accountability">
		<fr:layout name="tabular">
			<fr:property name="classes" value="form thwidth150px" />
			
			<fr:property name="sortBy" value="parent.partyName" />
		</fr:layout>
	</fr:view>
</logic:notEmpty>

<br/>
<br/>
<bean:message key="label.unit.children" bundle="ORGANIZATION_RESOURCES" />: <html:link action="/organization.do?method=prepareCreateUnit" paramId="parentOid" paramName="unit" paramProperty="OID"><bean:message key="label.create.child" bundle="ORGANIZATION_RESOURCES" /></html:link>
<logic:notEmpty name="unit" property="childAccountabilities">
	<fr:view name="unit" property="childAccountabilities" schema="organization.Unit.view.child.accountability">
		<fr:layout name="tabular">
			<fr:property name="classes" value="form thwidth150px" />
			
			<fr:property name="sortBy" value="child.partyName" />
		</fr:layout>
	</fr:view>
</logic:notEmpty>

<br/>
<br/>
<html:link action="/organization.do?method=viewOrganization">
	<bean:message key="label.back" bundle="ORGANIZATION_RESOURCES" />
</html:link>
