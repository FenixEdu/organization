<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<html:xhtml/>
<fr:edit id="contactToEditBean" name="contactToEditBean" action="/contacts.do?method=applyPartyContactEdit">
	<logic:equal name="contactToEditBean" property="className" value="module.contacts.domain.Phone">
		<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.ContactToEditBean">
			<fr:slot name="value" key="module.contacts.domain.Phone">
				<fr:property name="size" value="4" />
				<fr:property name="maxLength" value="4" />
				<logic:equal name="contactToEditBean" property="partyContactType" value="IMMUTABLE">
					<logic:equal name="contactToEditBean" property="superEditor" value="false">
						<fr:property name="disabled" value="true"/>
					</logic:equal>
				</logic:equal>
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
        		<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RegexpValidator">
        				<fr:property name="bundle" value="CONTACTS_RESOURCES"/>
            			<fr:property name="regexp" value="(\d{4})?" />
            			<fr:property name="message" value="manage.contacts.edit.error.phone.invalidFormat" />
        		</fr:validator>
   			</fr:slot>
<%-- 			<fr:slot name="phoneType" key="module.contacts.domain.PhoneType.label" layout="menu-postback" > --%>
<!-- 			if we aren't a supereditor and this is an institutional contact we can't change the content -->
<%-- 			<logic:equal name="contactToEditBean" property="partyContactType" value="IMMUTABLE"> --%>
<%-- 				<logic:equal name="contactToEditBean" property="superEditor" value="false"> --%>
<%-- 					<fr:property name="disabled" value="true"/> --%>
<%-- 				</logic:equal> --%>
<%-- 			</logic:equal> --%>
<%-- 			</fr:slot> --%>
   			<fr:slot name="partyContactType" key="manage.contacts.edit.partyContactType.label">
				<logic:equal name="contactToEditBean" property="superEditor" value="false">
					<logic:equal name="contactToEditBean" property="partyContactType" value="IMMUTABLE">
						<fr:property name="disabled" value="true"/>
					</logic:equal>
   					<fr:property name="excludedValues" value="IMMUTABLE"/>
				</logic:equal>
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
   			</fr:slot>
   			<fr:slot name="visibilityGroups" bundle="CONTACTS_RESOURCES" key="manage.contacts.edit.visibilityGroups.label" layout="option-select">
        		<fr:property name="providerClass" value="module.contacts.presentationTier.renderers.providers.VisibilityGroupsProvider" />
        		<fr:property name="eachSchema" value="myorg.modules.contacts.Groups.selectItem"/>
        		<fr:property name="eachLayout" value="values"/>
        		<fr:property name="classes" value="nobullet noindent"/>
        		<fr:property name="sortBy" value="name"/>
			</fr:slot>
   			<%-- 
   			<fr:slot name="visibilityGroups" key="manage.contacts.edit.visibilityGroups.label"/>
   				--%>
		</fr:schema>
		<fr:destination name="postback" path="/contacts.do?method=choosePhoneRegexpPostBack"/>
		<%-- 
		<fr:layout name="matrix">
		<fr:property name="classes"
			value="mtop05 mbottom05" />
		<fr:property name="slot(value)" value="value" />
		<fr:property name="row(value)" value="0" />
		<fr:property name="column(value)" value="0" />
		
		<fr:property name="slot(phoneType)" value="phoneType" />
		<fr:property name="row(phoneType)" value="0" />
		<fr:property name="column(phoneType)" value="1" />

		<fr:property name="slot(partyContactType)" value="partyContactType" />
		<fr:property name="row(partyContactType)" value="1" />
		<fr:property name="column(partyContactType)" value="0" />

		<fr:property name="slot(visibilityGroups)" value="visibilityGroups" />
		<fr:property name="row(visibilityGroups)" value="2" />
		<fr:property name="column(visibilityGroups)" value="0" />

		</fr:layout>
	--%>	
		<%-- 
		<fr:destination name="invalid" path="/contacts.do?method=editPartyContact"/>
		<fr:destination name="invalid" path="/x"/>
		--%>
	</logic:equal>
	
	<fr:layout name="tabular">
		<fr:property name="requiredMarkShown" value="true" />
		<fr:property name="requiredMessageShown" value="true" />
	</fr:layout>
</fr:edit>
<%-- select the visibility groups to which one should allow or deny the visibility. They groups should appear with the alias if it exists 
<fr:edit action="/contacts.do?method=setVisibilityGroups" id="visibilityGroupsConcededBean" name="visibilityGroupsConcededBean" schema="myorg.modules.contacts.ListVisibilityGroups">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle5 thright thlight mtop05 ulnomargin"/>
			<fr:property name="columnClasses" value=",,tdclear tderror1"/>
		</fr:layout>
		<fr:destination name="cancel" path="/contacts.do?method=frontPage"/>
</fr:edit>
--%>
