<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message key="label.connection.rules" bundle="ORGANIZATION_RESOURCES" /></h2>

<p class="mtop05">
	<strong><bean:message key="label.accountability.type" bundle="ORGANIZATION_RESOURCES" /> <fr:view name="accountabilityType" property="name" /></strong>
</p>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<p>
		<span class="error0"><bean:write name="message" /></span>
	</p>
</html:messages>

<bean:define id="accountabilityTypeOid" name="accountabilityType" property="externalId"  type="java.lang.String"/>
<fr:form action='<%= "/organization.do?accountabilityTypeOid=" + accountabilityTypeOid %>'>
	<html:hidden property="method" value="associateConnectionRules"/>
	
	<logic:notEmpty name="connectionRules">
	
		<script type="text/javascript">
		<!--
			function setCheckBoxValue(value) {
				elements = document.getElementsByTagName('input');
				for (i = 0; i < elements.length; i++) {
					if (elements[i].type == 'checkbox') {
						elements[i].checked = value;	
					}
				}
			}
		//-->
		</script>
		
		
		<a href="javascript:setCheckBoxValue(true)"><bean:message key="label.select.all" bundle="ORGANIZATION_RESOURCES" /></a> | <a href="javascript:setCheckBoxValue(false)"><bean:message key="label.select.none" bundle="ORGANIZATION_RESOURCES" /></a>
		<table class="tstyle2">
			<logic:iterate id="connectionRule" name="connectionRules">
				<tr>
					<td>
						<html:multibox name="module.organization.presentationTier.actions.OrganizationManagementAction$OrganizationForm" property="oids">
							<bean:write name="connectionRule" property="externalId"/>
						</html:multibox>
					</td>
					<td>
						<bean:write name="connectionRule" property="description" />
					</td>
				</tr>
			</logic:iterate>
		</table>
		
		<html:submit><bean:message key="label.save" bundle="ORGANIZATION_RESOURCES" /></html:submit>
	</logic:notEmpty>
	
	<logic:empty name="connectionRules">
		<p>
			<em><bean:message key="label.no.connection.rules" bundle="ORGANIZATION_RESOURCES" /></em>
		</p>
	</logic:empty>
	
	<html:cancel onclick="this.form.method.value='viewAccountabilityTypes';return true;" ><bean:message key="label.back" bundle="ORGANIZATION_RESOURCES" /></html:cancel>
	
</fr:form>
