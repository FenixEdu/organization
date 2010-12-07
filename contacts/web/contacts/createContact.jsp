<%@page import="module.contacts.presentationTier.action.bean.ContactToCreateBean"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<html:xhtml/>
<%--
<logic:empty name="contactToCreateBean" property="partyContactKind">
 --%>
 <fr:form action="/contacts.do?method=applyPartyContactCreate">
<fr:edit id="contactToCreateBean" name="contactToCreateBean" >
		<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.ContactToCreateBean">
			<fr:slot name="partyContactKind" key="manage.contacts.create.kind.label" layout="menu-postback">
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
			</fr:slot>
			
		<logic:notEmpty name="contactToCreateBean" property="partyContactKind">
			<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="PHONE">
				<fr:slot name="phoneType" key="module.contacts.domain.PhoneType.label" layout="menu-postback" >
				</fr:slot>
			</logic:equal>
		</logic:notEmpty>
		</fr:schema>
		<fr:destination name="postback" path="/contacts.do?method=chooseKindOfContactPostBack"/>
		<%-- 
		<fr:destination name="invalid" path="/contacts.do?method=editPartyContact"/>
		<fr:destination name="invalid" path="/x"/>
		--%>
</fr:edit>
<%-- 
</logic:empty>
--%>
<logic:notEmpty name="contactToCreateBean" property="partyContactKind">
	<fr:edit id="contactToCreateType" name="contactToCreateBean">
		<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.ContactToCreateBean">
   			<fr:slot name="partyContactType" key="manage.contacts.edit.partyContactType.label">
   				<logic:equal name="contactToCreateBean" property="superEditor" value="false">
					<logic:equal name="contactToCreateBean" property="partyContactType" value="IMMUTABLE">
						<fr:property name="disabled" value="true"/>
						<fr:property name="readOnly" value="true"/>
					</logic:equal>
   					<fr:property name="excludedValues" value="IMMUTABLE"/>
				</logic:equal>
   			</fr:slot>
   		</fr:schema>
   	</fr:edit>
	<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="PHONE">
		<logic:notEmpty name="contactToCreateBean" property="phoneType" >
			<bean:define id="schemaSuffix" name="contactToCreateBean" property="schemaSuffix"/>
			<fr:edit id="contactToCreateSpecific" name="contactToCreateBean" schema="<%="myorg.modules.contacts.CreateContact" + schemaSuffix.toString()%>">
			</fr:edit>
		</logic:notEmpty>
	</logic:equal>
<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="WEB_ADDRESS">
			<bean:define id="schemaSuffix" name="contactToCreateBean" property="schemaSuffix"/>
			<fr:edit id="contactToCreateSpecific" name="contactToCreateBean" schema="<%="myorg.modules.contacts.CreateContact" + schemaSuffix.toString()%>">
			</fr:edit>
</logic:equal>
<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="PHYSICAL_ADDRESS">
	<%-- if we don't have a country selected, let us select one first--%>
		<fr:edit name="contactToCreateBean" action="/contacts.do?method=Gotcha!">
			<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.ContactToCreateBean">
				<fr:slot name="physicalAddressBean.country" layout="menu-select-postback" key="module.contacts.Country.label">
					<fr:property name="providerClass" value="module.contacts.presentationTier.renderers.providers.CountriesProvider"/>				
					<fr:property name="sortBy" value="name"/>
					<fr:property name="eachSchema" value="myorg.modules.contacts.PhysicalAddress.countryList"/>
					<fr:property name="eachLayout" value="values"/>
					<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				</fr:slot>
			</fr:schema>
			<fr:destination name="postback" path="/contacts.do?method=chooseGeographicLevelPostBack"/>
			</fr:edit>
	<logic:present name="contactToCreateBean" property="physicalAddressBean.country">
	<%-- If we indeed have a country already selected--%>
	<table>
		<tbody>
		<logic:notEmpty name="contactToCreateBean" property="physicalAddressBean.geographicLevels">
		<logic:iterate id="geographicLevel" name="contactToCreateBean" property="physicalAddressBean.geographicLevels" indexId="index">
			<tr>
				<th><%=((Entry)geographicLevel).getKey()%></th>
				<td>
				<fr:edit id="<%= "id-"+index%>" name="contactToCreateBean" property="physicalAddressBean">
						<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.PhysicalAddressBean">
							<fr:slot name="<%="geographicLevels(" + ((Entry)geographicLevel).getKey() + ")"%>" layout="menu-select-postback" key="module.contacts.empty.label" >
								 <fr:property name="providerClass" value="module.contacts.presentationTier.renderers.providers.GeographicLocationsProvider"/> 
								 <fr:property name="eachSchema" value="myorg.modules.contacts.GeographicLocation"/>
							</fr:slot>
						</fr:schema>
						<fr:destination name="postback" path="/contacts.do?method=chooseGeographicLevelPostBack"/>
					</fr:edit>
				</td>
			</tr>
		</logic:iterate>
		</logic:notEmpty>
		</tbody>
	</table>
	<logic:notEmpty name="contactToCreateBean" property="physicalAddressBean.addressBean">
		<bean:define id="schema" name="contactToCreateBean" property="physicalAddressBean.addressBean.schema"/>
		<fr:edit name="contactToCreateBean" property="physicalAddressBean.addressBean" schema="<%=schema.toString()%>"/>
	</logic:notEmpty>
	</logic:present>
	
</logic:equal>
<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="EMAIL_ADDRESS">
			<bean:define id="schemaSuffix" name="contactToCreateBean" property="schemaSuffix"/>
			<fr:edit id="contactToCreateSpecific" name="contactToCreateBean" schema="<%="myorg.modules.contacts.CreateContact" + schemaSuffix.toString()%>">
			</fr:edit>
</logic:equal>
</logic:notEmpty>
	<html:submit styleClass="inputbutton"><bean:message key="renderers.form.submit.name" bundle="RENDERER_RESOURCES"/></html:submit>
	<html:cancel styleClass="inputbutton"><bean:message key="renderers.form.cancel.name" bundle="RENDERER_RESOURCES"/></html:cancel>
</fr:form>