<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message key="label.create.new" bundle="ORGANIZATION_RESOURCES" /></h2>

<fr:form action="/organization.do">
	<html:hidden name="module.organization.presentationTier.actions.OrganizationManagementAction$OrganizationForm" property="method" value="prepareCreateConnectionRule" />

	<bean:message key="label.connection.rule" bundle="ORGANIZATION_RESOURCES" />
	<html:select name="module.organization.presentationTier.actions.OrganizationManagementAction$OrganizationForm" property="connectionRuleClassName" onchange="this.form.submit();">
		<html:option value=""><bean:message key="label.organization.choose.an.option" bundle="ORGANIZATION_RESOURCES" /></html:option>
		<html:option value="module.organization.domain.connectionRules.PartyTypeConnectionRule$PartyTypeConnectionRuleBean"><bean:message key="label.PartyTypeConnectionRule" bundle="ORGANIZATION_RESOURCES" /></html:option>
		<html:option value="module.organization.domain.connectionRules.UniqueNameAndAcronymConnectionRule$UniqueNameAndAcronymConnectionRuleBean"><bean:message key="label.UniqueNameAndAcronymConnectionRule" bundle="ORGANIZATION_RESOURCES" /></html:option>
	</html:select>
</fr:form>

<fr:form action="/organization.do">	
	<html:hidden name="module.organization.presentationTier.actions.OrganizationManagementAction$OrganizationForm" property="method" value="createConnectionRule" />
	
	<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
		<span class="error0"> <bean:write name="message" /> </span>
		<br />
	</html:messages>
	
	<logic:present name="connectionRuleBean">
		<bean:define id="schema">organization.<bean:write name="connectionRuleBean" property="class.simpleName" /></bean:define>	

		<fr:edit id="connectionRuleBean" name="connectionRuleBean" schema="<%= schema %>">
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle2" />
				<fr:property name="columnClasses" value=",,tderror" />
			</fr:layout>
			<fr:destination name="invalid" path="/organization.do?method=createConnectionRuleInvalid" />
		</fr:edit>
	</logic:present>

	<html:submit><bean:message key="label.create" bundle="ORGANIZATION_RESOURCES" /></html:submit>
	<html:cancel onclick="this.form.method.value='viewConnectionRules';return true;" ><bean:message key="label.back" bundle="ORGANIZATION_RESOURCES" /></html:cancel>

</fr:form>
