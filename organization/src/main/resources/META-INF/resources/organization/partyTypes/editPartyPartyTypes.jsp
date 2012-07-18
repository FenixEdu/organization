<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message key="label.party.partyTypes" bundle="ORGANIZATION_RESOURCES" /></h2>
<h3><fr:view name="party" property="partyName" /></h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<bean:define id="partyOid" name="party" property="externalId"  type="java.lang.String"/>
<fr:form action='<%= "/organization.do?partyOid=" + partyOid %>'>
	<html:hidden property="method" value="editPartyPartyTypes"/>
	
	<table class="tstyle2">
		<logic:iterate id="partyType" name="partyTypes">
			<tr>
				<td>
					<html:multibox name="module.organization.presentationTier.actions.OrganizationManagementAction$OrganizationForm" property="oids">
						<bean:write name="partyType" property="externalId"/>
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
