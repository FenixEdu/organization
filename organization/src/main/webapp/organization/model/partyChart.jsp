<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/chart" prefix="chart" %>
<%@page import="org.fenixedu.bennu.core.presentationTier.component.OrganizationChart"%>
<%@page import="module.organization.domain.Unit"%>
<%@page import="module.organization.domain.Accountability"%>
<%@page import="module.organization.domain.Party"%>
<%@page import="module.organization.domain.UnconfirmedAccountability"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/organization/CSS/organization.css" media="screen"/>

<%@page import="module.organization.domain.AccountabilityType"%><bean:define id="partyChart" name="partyChart" type="module.organization.presentationTier.actions.OrganizationModelAction.PartyChart"/>
<logic:notEmpty name="partyChart">
	<logic:present name="partyChart" property="unit">
		<bean:define id="unit" name="partyChart" property="unit"/>
		<logic:present role="#managers">
			<bean:define id="url">/organizationModel.do?method=prepareEditUnit&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
			<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="externalId">
				<bean:message key="label.unit.edit" bundle="ORGANIZATION_RESOURCES"/>
			</html:link>
			|
			<bean:define id="url">/organizationModel.do?method=managePartyPartyTypes&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
			<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="externalId">
				<bean:message key="label.unit.partyTypes.manage" bundle="ORGANIZATION_RESOURCES"/>
			</html:link>
			|
		</logic:present>
		<bean:define id="url">/organizationModel.do?method=prepareAddUnit&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
		<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="externalId">
			<bean:message key="label.unit.child.add" bundle="ORGANIZATION_RESOURCES"/>
		</html:link>
		|
		<bean:define id="url">/organizationModel.do?method=prepareCreateUnit&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
		<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="externalId">
			<bean:message key="label.unit.child.create" bundle="ORGANIZATION_RESOURCES"/>
		</html:link>
		<logic:present role="#managers">
			|
			<bean:define id="url">/organizationModel.do?method=prepareManageChildAccountabilities&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
			<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="externalId">
				<bean:message key="label.unit.manage.children" bundle="ORGANIZATION_RESOURCES"/>
			</html:link>
			|
			<bean:define id="url">/organizationModel.do?method=prepareDeleteAccountability&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
			<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="externalId">
				<bean:message key="label.unit.remove.child" bundle="ORGANIZATION_RESOURCES"/>
			</html:link>
			|
			<bean:define id="url">/organizationModel.do?method=deleteUnit&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %></bean:define>
			<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="externalId">
				<bean:message key="label.unit.delete" bundle="ORGANIZATION_RESOURCES"/>
			</html:link>
		</logic:present>
	</logic:present>

	<%
		boolean passedElement = false;
		boolean hasUnconfirmed = false;
	%>
	<bean:define id="sortedAccountabilityTypes" type="java.util.SortedSet" name="organizationalModel" property="sortedAccountabilityTypes"/>
	<table width="100%" align="center">
		<tr>
			<td align="center" width="70%">
				<chart:orgChart id="object" name="partyChart" type="java.lang.Object">
					<%
						if (partyChart.getElement() == object) {
						    passedElement = true;
					%>
							<div class="orgTBox orgTBoxLight">
								<bean:write name="object" property="partyName.content"/>
							</div>
					<%
						} else {
						    Party party = null;
						    String styleCass = "orgTBox orgTBoxLight";
						    String title = null;
						    AccountabilityType accountabilityType = null;
						    int accountabilityTypeIndex = 0;
						    if (object instanceof Accountability) {
								final Accountability accountability = (Accountability) object;
								party = passedElement ? accountability.getChild() : accountability.getParent();
								if (object instanceof UnconfirmedAccountability) {
								    UnconfirmedAccountability unconfirmedAccountability = (UnconfirmedAccountability) accountability;
								    styleCass = "orgTBox orgTBoxRed";
								    accountabilityType = unconfirmedAccountability.getUnconfirmedAccountabilityType();
								    hasUnconfirmed = true;
								} else {
								    accountabilityType = accountability.getAccountabilityType();
								}
								title = accountability.getDetailsString();
								String creationUser = accountability.getCreatorUser() == null ? "" : "&#013;Creation user: " + accountability.getCreatorUser().getPresentationName();
								String justification = accountability.getJustification() == null ? "" : "&#013; Justification: " + accountability.getJustification();
								title += "&#013;Creation date: " + accountability.getCreationDate() + creationUser + justification;
								
								for (final Object at : sortedAccountabilityTypes) {
								    accountabilityTypeIndex++;
								    if (at == accountabilityType) {
										break;
								    }
								}
						    } else {
								party = (Party) object;
						    }
					%>
							<div style="position: relative;" class="<%= styleCass %>" <% if (title != null) { %>title="<%= title %>"<% } %>>
								<% if (accountabilityTypeIndex > 0) { %>
									<span style="position: absolute; top: -4px; right: 3px; color: #999999; font-size: 8px;"><%= accountabilityTypeIndex %></span>
								<% } %>
								<bean:define id="url">/organizationModel.do?method=viewModel&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %>&amp;partyOid=<%= party.getExternalId() %></bean:define>
								<html:link action="<%= url %>" paramId="organizationalModelOid" paramName="organizationalModel" paramProperty="externalId">
									<%= party.getPartyName().getContent() %>
								</html:link>
							</div>
					<%
						}
					%>
				</chart:orgChart>
			</td>
			<td>
				<logic:present name="partyChart" property="unit">
					<logic:equal name="unit" property="authorizedToManage" value="true">
						<% if (hasUnconfirmed) { %>
							<div class="postit">
								<div class="tipBox">
									<bean:message key="label.unit.has.unconfirmed.accountabilities" bundle="ORGANIZATION_RESOURCES"/>
									<bean:message key="label.unit.has.unconfirmed.accountabilities.click.prefix" bundle="ORGANIZATION_RESOURCES"/>
									<bean:define id="url">/organizationModel.do?method=reviewUnconfirmedAccountabilities&amp;viewName=<%= module.organization.presentationTier.actions.OrganizationModelAction.UNIT_CHART_VIEW_NAME %>&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/></bean:define>
									<html:link action="<%= url %>" paramId="partyOid" paramName="unit" paramProperty="externalId">
										<bean:message key="label.unit.has.unconfirmed.accountabilities.click.infix" bundle="ORGANIZATION_RESOURCES"/>
									</html:link>
									<bean:message key="label.unit.has.unconfirmed.accountabilities.click.suffix" bundle="ORGANIZATION_RESOURCES"/>
								</div>
							</div>
						<% } %>
					</logic:equal>
				</logic:present>
			<td/>
		</tr>
	</table>
</logic:notEmpty>
