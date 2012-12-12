<%@page import="module.contacts.domain.ContactsConfigurator"%>
<%@page import="pt.ist.fenixWebFramework.renderers.utils.RenderUtils"%>
<%@page import="module.contacts.presentationTier.action.bean.ContactToEditBean"%>
<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
asdf
<html:xhtml/>
<bean:define id="contactToEditClassName" name="contactToEditBean" property="className"/>
<bean:define id="personEId" name="contactToEditBean" property="wrappedContact.party.OID"/>
<fr:edit id="contactToEditBean" name="contactToEditBean" action="/contacts.do?method=applyPartyContactEdit">

		<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.ContactToEditBean">
			<fr:slot name="value" key="<%=contactToEditClassName.toString()%>">
				<fr:property name="size" value="50" />
				<logic:equal name="contactToEditBean" property="partyContactType" value="IMMUTABLE">
						<logic:equal name="contactToEditBean" property="superEditor" value="false">
							<fr:property name="disabled" value="true"/>
							<fr:property name="readOnly" value="true"/>
						</logic:equal>
				</logic:equal>
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
        		<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RegexpValidator">
        				<fr:property name="bundle" value="CONTACTS_RESOURCES"/>
            			<fr:property name="regexp" value="<%=ContactsConfigurator.getJSRegExp(((String)contactToEditClassName))%>" />
            			<fr:property name="message" value="manage.contacts.edit.error.generic.invalidFormat" />
        		</fr:validator>
   			</fr:slot>
   			<fr:slot name="partyContactType" key="manage.contacts.edit.partyContactType.label">
   				<logic:equal name="contactToEditBean" property="superEditor" value="false">
					<logic:equal name="contactToEditBean" property="partyContactType" value="IMMUTABLE">
						<fr:property name="disabled" value="true"/>
						<%--
						<fr:property name="readOnly" value="true"/>
						--%>
   						
					</logic:equal>
				</logic:equal>
   				<logic:equal name="contactToEditBean" property="superEditor" value="false">
					<logic:notEqual name="contactToEditBean" property="partyContactType" value="IMMUTABLE">
   						<fr:property name="excludedValues" value="IMMUTABLE"/>
					</logic:notEqual>
				</logic:equal>
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
   			</fr:slot>
   			<fr:slot name="visibilityGroups" bundle="CONTACTS_RESOURCES" key="manage.contacts.edit.visibilityGroups.label" layout="option-select">
        		<fr:property name="providerClass" value="module.contacts.presentationTier.renderers.providers.VisibilityGroupsProvider" />
        		<fr:property name="eachSchema" value="pt.ist.bennu.core.modules.contacts.Groups.selectItemByAlias"/>
        		<fr:property name="eachLayout" value="values"/>
        		<fr:property name="classes" value="nobullet noindent"/>
        		<fr:property name="sortBy" value="name"/>
			</fr:slot>
		</fr:schema>
		<fr:destination name="postback" path="/contacts.do?method=choosePhoneRegexpPostBack"/>
		<fr:destination name="cancel" path="<%="/contacts.do?method=editContacts&personEId="+ personEId%>"/>
		<%-- 
		<fr:destination name="invalid" path="<%="/contacts.do?method=editContacts&personEId="+ personEId%>"/>
		<fr:destination name="invalid" path="/x"/>
		--%>
		
		<fr:layout name="tabular">
			<fr:property name="requiredMarkShown" value="true" />
			<fr:property name="requiredMessageShown" value="true" />
		</fr:layout>
</fr:edit>
