<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message key="label.create.new" bundle="ORGANIZATION_RESOURCES" /></h2>

<div class="mvert1">
	<fr:form action="/organization.do?method=prepareCreateConnectionRule">
		<bean:message key="label.connection.rule" bundle="ORGANIZATION_RESOURCES" />:
		<select name="connectionRuleClassName" onchange="this.form.submit();">
			<option value=""><bean:message key="label.organization.choose.an.option" bundle="ORGANIZATION_RESOURCES" /></option>
			<option value="module.organization.domain.connectionRules.PartyTypeConnectionRule$PartyTypeConnectionRuleBean" ${simpleName == 'PartyTypeConnectionRuleBean' ? 'selected': ''}><bean:message key="label.PartyTypeConnectionRule" bundle="ORGANIZATION_RESOURCES" /></option>
			<option value="module.organization.domain.connectionRules.UniqueNameAndAcronymConnectionRule$UniqueNameAndAcronymConnectionRuleBean" ${simpleName == 'UniqueNameAndAcronymConnectionRuleBean' ? 'selected': ''}><bean:message key="label.UniqueNameAndAcronymConnectionRule" bundle="ORGANIZATION_RESOURCES" /></option>
		</select>
	</fr:form>
</div>

<fr:form action="/organization.do?method=createConnectionRule">		
	<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
		<p><span class="error0"><bean:write name="message" /></span></p>
	</html:messages>
	
	<logic:present name="connectionRuleBean">
		<bean:define id="schema">organization.<bean:write name="connectionRuleBean" property="class.simpleName" /></bean:define>	

		<fr:edit id="connectionRuleBean" name="connectionRuleBean" schema="<%= schema %>">
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle5" />
				<fr:property name="columnClasses" value=",,tderror" />
			</fr:layout>
			<fr:destination name="invalid" path="/organization.do?method=createConnectionRuleInvalid" />
		</fr:edit>

		<html:submit styleClass="inputbutton"><bean:message key="label.create" bundle="ORGANIZATION_RESOURCES" /></html:submit>
		<html:cancel styleClass="inputbutton" onclick="this.form.method.value='viewConnectionRules';return true;" ><bean:message key="label.back" bundle="ORGANIZATION_RESOURCES" /></html:cancel>

	</logic:present>

</fr:form>
