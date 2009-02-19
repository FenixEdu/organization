<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message key="label.unit.partyTypes" bundle="ORGANIZATION_RESOURCES" /></h2>
<h3><fr:view name="unit" property="partyName" /></h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<bean:define id="partyOid" name="unit" property="OID" />
<fr:form action='<%= "/organization.do?partyOid=" + partyOid.toString() %>'>
	<html:hidden property="method" value="editPartyPartyTypes"/>
	
	<table class="tstyle2">
		<logic:iterate id="partyType" name="partyTypes">
			<tr>
				<td>
					<html:multibox name="module.organization.presentationTier.actions.OrganizationManagementAction$OrganizationForm" property="oids">
						<bean:write name="partyType" property="OID"/>
					</html:multibox>
				</td>
				<td>
					<fr:view name="partyType" property="name" />
				</td>
			</tr>
		</logic:iterate>
	</table>
	
	<html:submit><bean:message key="label.save" bundle="ORGANIZATION_RESOURCES" /></html:submit>
	<html:cancel onclick="this.form.method.value='viewParty';return true;" ><bean:message key="label.back" bundle="ORGANIZATION_RESOURCES" /></html:cancel>
	
</fr:form>
