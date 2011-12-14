<%@page import="module.contacts.presentationTier.action.bean.ContactToCreateBean"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<html:xhtml/>
<h2><bean:message bundle="CONTACTS_RESOURCES" key="label.addContact"/></h2>
<%--
<logic:empty name="contactToCreateBean" property="partyContactKind">
 --%>
 <%-- Choose the kind of contact to create --%>
 <fr:form action="/contacts.do?method=applyPartyContactCreate">
<fr:edit id="contactToCreateBean" name="contactToCreateBean" >
		<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.ContactToCreateBean">
			<fr:slot name="partyContactKind" key="manage.contacts.create.kind.label" layout="menu-postback">
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
			</fr:slot>
			
		<logic:notEmpty name="contactToCreateBean" property="partyContactKind">
			<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="PHONE">
				<fr:slot name="phoneType" key="module.contacts.domain.PhoneType.label" layout="menu-postback" >
					<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
				</fr:slot>
			</logic:equal>
		</logic:notEmpty>
		</fr:schema>
		<fr:destination name="postback" path="/contacts.do?method=chooseKindOfContactPostBack"/>
		<fr:destination name="cancel" path="/contacts.do?method=cancelContactOperation"/>
		<fr:destination name="invalid" path="/contacts.do?method=createPartyContact"/>

		<logic:notEmpty name="contactToCreateBean" property="partyContactKind">
			<fr:layout name="tabular">
				<fr:property name="requiredMarkShown" value="true" />
				<fr:property name="requiredMessageShown" value="false" />
			</fr:layout>
		</logic:notEmpty>
		
		<logic:empty name="contactToCreateBean" property="partyContactKind">
			<fr:layout name="tabular">
				<fr:property name="requiredMarkShown" value="true" />
				<fr:property name="requiredMessageShown" value="true" />
			</fr:layout>
		</logic:empty>
		
		<%-- 
		<fr:destination name="invalid" path="/x"/>
		--%>
</fr:edit>
<%-- 
</logic:empty>
--%>
<%-- Given the type of contact, let's allow specific field to be chosen --%>
<logic:notEmpty name="contactToCreateBean" property="partyContactKind">
	<%-- The type of contact i.e. Institutional, Work, etc --%>
	<fr:edit id="contactToCreateBean" nested="true" id="contactToCreateType" name="contactToCreateBean">
		<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.ContactToCreateBean">
   			<fr:slot name="partyContactType" key="manage.contacts.edit.partyContactType.label">
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
   				<logic:equal name="contactToCreateBean" property="superEditor" value="false">
   				<%-- If we aren't a supereditor i.e. a person that can edit any kind of contacts, including the immutables, we won't allow the immutable type to be set --%>
					<logic:equal name="contactToCreateBean" property="partyContactType" value="IMMUTABLE">
						<fr:property name="disabled" value="true"/>
						<fr:property name="readOnly" value="true"/>
					</logic:equal>
   					<fr:property name="excludedValues" value="IMMUTABLE"/>
				</logic:equal>
   			</fr:slot>
   		</fr:schema>
   		
   		<logic:notEqual name="contactToCreateBean" property="partyContactKind.name" value="PHONE">
  			<fr:layout name="tabular">
			<fr:property name="classes" value="tview-middle" />
				<fr:property name="requiredMarkShown" value="true" />
				<fr:property name="requiredMessageShown" value="false" />
			</fr:layout>
   		</logic:notEqual>
   		
   		<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="PHONE">
   			<logic:notEmpty name="contactToCreateBean" property="phoneType">
				<fr:layout name="tabular">
					<fr:property name="classes" value="tview-middle" />
					<fr:property name="requiredMarkShown" value="true" />
					<fr:property name="requiredMessageShown" value="false" />
				</fr:layout>
			</logic:notEmpty>
		
			<logic:empty name="contactToCreateBean" property="phoneType">
				<fr:layout name="tabular">
					<fr:property name="classes" value="tview-middle" />
					<fr:property name="requiredMarkShown" value="true" />
					<fr:property name="requiredMessageShown" value="true" />
				</fr:layout>
			</logic:empty>
   		</logic:equal>
   		
   		
   	</fr:edit>
	<%-- *END* The type of contact i.e. Institutional, Work, etc *END* --%>
	<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="PHONE">
		<logic:notEmpty name="contactToCreateBean" property="phoneType" >
			<bean:define id="schemaSuffix" name="contactToCreateBean" property="schemaSuffix"/>
			<fr:edit id="contactToCreateBean" nested="true"  name="contactToCreateBean" schema="<%="myorg.modules.contacts.CreateContact" + schemaSuffix.toString()%>">
				<fr:layout name="tabular">
					<fr:property name="requiredMarkShown" value="true" />
					<fr:property name="requiredMessageShown" value="true" />
				</fr:layout>
			</fr:edit>
		</logic:notEmpty>
	</logic:equal>
<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="WEB_ADDRESS">
			<bean:define id="schemaSuffix" name="contactToCreateBean" property="schemaSuffix"/>
			<fr:edit name="contactToCreateBean" nested="true" schema="<%="myorg.modules.contacts.CreateContact" + schemaSuffix.toString()%>">
				<fr:layout name="tabular">
					<fr:property name="requiredMarkShown" value="true" />
					<fr:property name="requiredMessageShown" value="true" />
				</fr:layout>
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
	
			<fr:layout name="tabular">
				<fr:property name="requiredMarkShown" value="true" />
				<fr:property name="requiredMessageShown" value="true" />
			</fr:layout>
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
							<fr:slot name="<%="geographicLevels(" + ((Entry)geographicLevel).getKey() + ")"%>" bundle="CONTACTS_RESOURCES" layout="menu-select-postback" key="module.contacts.empty.label" >
								 <fr:property name="providerClass" value="module.contacts.presentationTier.renderers.providers.GeographicLocationsProvider"/> 
								 <fr:property name="eachSchema" value="myorg.modules.contacts.GeographicLocation"/>
								 <fr:property name="format" value="${name}"/>
								 <fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
							</fr:slot>
						</fr:schema>
						<fr:layout name="matrix">
							<fr:property name="classes" value="tstyle3 thlight thright mtop05 mbottom05 thmiddle" />
			                <fr:property name="<%="slot(geographicLevels(" + ((Entry)geographicLevel).getKey() + "))"%>" value="<%="geographicLevels(" + ((Entry)geographicLevel).getKey() + ")"%>" />
			                <fr:property name="<%="labelHidden(geographicLevels(" + ((Entry)geographicLevel).getKey() + "))" %>" value="true"/>
			                <fr:property name="<%="row(geographicLevels(" + ((Entry)geographicLevel).getKey() + "))"%>"  value="0" />
			                <fr:property name="<%="column(geographicLevels(" + ((Entry)geographicLevel).getKey() + "))"%>" value="0" />
						
						</fr:layout>
						<fr:destination name="postback" path="/contacts.do?method=chooseGeographicLevelPostBack"/>
					
						<fr:layout name="tabular">
							<fr:property name="requiredMarkShown" value="true" />
							<fr:property name="requiredMessageShown" value="true" />
						</fr:layout>
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
		<%-- The visibility groups to which the contact should be added to --%>
		<fr:edit name="contactToCreateBean">
			<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.ContactToCreateBean">
			   <fr:slot name="visibilityGroups" bundle="CONTACTS_RESOURCES" key="manage.contacts.edit.visibilityGroups.label" layout="option-select">
        		<fr:property name="providerClass" value="module.contacts.presentationTier.renderers.providers.VisibilityGroupsProvider" />
        		<fr:property name="eachSchema" value="myorg.modules.contacts.Groups.selectItemByAlias"/>
        		<fr:property name="eachLayout" value="values"/>
        		<fr:property name="classes" value="nobullet noindent"/>
        		<fr:property name="sortBy" value="name"/>
        		<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
	  		  </fr:slot>
			</fr:schema>
			
			<fr:layout name="tabular">
				<fr:property name="requiredMarkShown" value="true" />
				<fr:property name="requiredMessageShown" value="true" />
			</fr:layout>
		</fr:edit>
	</logic:notEmpty>
	</logic:present>
	
</logic:equal>
<logic:equal name="contactToCreateBean" property="partyContactKind.name" value="EMAIL_ADDRESS">
			<bean:define id="schemaSuffix" name="contactToCreateBean" property="schemaSuffix"/>
			<fr:edit id="contactToCreateSpecific" name="contactToCreateBean" schema="<%="myorg.modules.contacts.CreateContact" + schemaSuffix.toString()%>">
				<fr:layout name="tabular">
					<fr:property name="requiredMarkShown" value="true" />
					<fr:property name="requiredMessageShown" value="true" />
				</fr:layout>
			</fr:edit>
</logic:equal>
</logic:notEmpty>

<br>
	<html:submit styleClass="inputbutton"><bean:message key="renderers.form.submit.name" bundle="RENDERER_RESOURCES"/></html:submit>
	<html:cancel styleClass="inputbutton cancel"><bean:message key="renderers.form.cancel.name" bundle="RENDERER_RESOURCES"/></html:cancel>
</fr:form>