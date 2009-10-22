<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/chart.tld" prefix="chart" %>
<%@page import="myorg.presentationTier.component.OrganizationChart"%>
<%@page import="module.organization.domain.Unit"%>
<%@page import="module.organization.domain.Accountability"%>
<%@page import="module.organization.domain.Party"%>
<%@page import="module.organization.domain.UnconfirmedAccountability"%>


<%@page import="module.organization.domain.AccountabilityType"%><bean:define id="partyChart" name="partyChart" type="module.organization.presentationTier.actions.OrganizationModelAction.PartyChart"/>
<logic:notEmpty name="partyChart">
	<logic:present name="partyChart" property="unit">
		<bean:define id="unit" name="partyChart" property="unit"/>
		<logic:present role="myorg.domain.RoleType.MANAGER">
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
		<logic:present role="myorg.domain.RoleType.MANAGER">
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
								<bean:write name="object" property="partyName"/>
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
