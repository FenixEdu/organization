<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h1><bean:message key="configure.contacts.module.label" bundle="CONTACTS_RESOURCES"/></h1>
<br/>
<h2><bean:message key="manage.visibility.groups.label" bundle="CONTACTS_RESOURCES"/></h2>
<p><bean:message key="manage.visibility.groups.instructions" bundle="CONTACTS_RESOURCES" /></p>

<fr:edit action="/contacts.do?method=setVisibilityGroups" id="groupsSelectorBean" name="groupsSelectorBean" schema="pt.ist.bennu.core.modules.contacts.ListPersistentGroups">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle5 thright thlight mtop05 ulnomargin"/>
			<fr:property name="columnClasses" value=",,tdclear tderror1"/>
		</fr:layout>

		<fr:destination name="cancel" path="/contacts.do?method=frontPage"/>
</fr:edit>
<%-- Used to notify of correct submission of visibility changes!:--%>
<logic:present name="changesSubmitted"> 
	<bean:message key="manage.visibility.groups.successfullyChanged" bundle="CONTACTS_RESOURCES"/>
</logic:present>
<%-- Visibility groups alias management: --%>
<br/>
<br/>
<h3><bean:message key="manage.visibility.groups.alias.title" bundle="CONTACTS_RESOURCES"/></h3>
<bean:message key="manage.visibility.groups.alias.explanation" bundle="CONTACTS_RESOURCES" /><br/>
<bean:message key="manage.visibility.groups.alias.explanation.further" bundle="CONTACTS_RESOURCES" />
<br/>
<br/>
	<fr:view name="groupsSelectorBean" property="groups">
		<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.GroupsSelectorBean">
			<fr:slot name="name" bundle="CONTACTS_RESOURCES" key="manage.visibility.groups.alias.groupNameLabel" />
			<fr:slot name="groupAlias" layout="null-as-label" bundle="CONTACTS_RESOURCES" key="manage.visibility.groups.alias.groupAliasLabel">
				<fr:property name="subLayout" value="values"/>
				<fr:property name="subSchema" value="pt.ist.bennu.core.modules.contacts.Groups.listGroupAlias"/>
			</fr:slot>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="name" value="tstyle4 thlight"/>
			<fr:property name="linkFormat(edit)" value="/contacts.do?method=editAlias&groupId=${externalId}" />
			<fr:property name="key(edit)" value="manage.visibility.groups.alias.editLabel"/>
			<fr:property name="bundle(edit)" value="CONTACTS_RESOURCES"/>
			<%-- <fr:property name="visibleIf(edit)" value="alias"/> --%>
			<fr:property name="moduleRelative(edit)" value="true"/> 		
		</fr:layout>
	</fr:view>
<br/>
<%-- SuperEditor role management: --%>
<h2><bean:message key="manage.supereditor.role.label" bundle="CONTACTS_RESOURCES"/></h2>
<p><bean:message key="manage.supereditor.role.instructions" bundle="CONTACTS_RESOURCES"/></p>
<%-- <br/>--%>
<fr:edit action="/contacts.do?method=assignSuperEditorRoleToGroup" id="roleSelectorGroupsBean" name="roleSelectorGroupsBean" schema="pt.ist.bennu.core.modules.contacts.ListPersistentGroups">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle5 thright thlight mtop05 ulnomargin"/>
			<fr:property name="columnClasses" value=",,tdclear tderror1"/>
		</fr:layout>
		<fr:destination name="cancel" path="/contacts.do?method=frontPage"/>
</fr:edit>
<br/>
<fr:edit action="/contacts.do?method=assignSuperEditorRoleToPerson" id="personsBean" name="personsBean" schema="pt.ist.bennu.core.modules.contacts.configure.SuperEditorRole.listPersons" >
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
</fr:edit>
<br/>
<fr:view name="usersWithSuperEditorRoleBean" property="users">
	<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.GroupsSelectorBean">
		<fr:slot name="person.name" bundle="CONTACTS_RESOURCES" key="manage.supereditor.role.listUsers.label">
		</fr:slot>
		<fr:slot name="username" bundle="CONTACTS_RESOURCES" key="manage.supereditor.role.listUsers.label.username">
		</fr:slot>
	</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="name" value="tstyle4 thlight"/>
			<fr:property name="linkFormat(edit)" value="/contacts.do?method=removeUserFromSuperEditorRole&userId=${externalId}" />
			<fr:property name="key(edit)" value="manage.supereditor.role.removeUser.label"/>
			<fr:property name="bundle(edit)" value="CONTACTS_RESOURCES"/>
			<%-- <fr:property name="visibleIf(edit)" value="alias"/> --%>
			<fr:property name="moduleRelative(edit)" value="true"/> 		
		</fr:layout>
		<%-- <fr:layout name="tabular">
			<fr:property name="name" value="tstyle4 thlight"/>
			<fr:property name="linkFormat(edit)" value="/contacts.do?method=editAlias&groupId=${externalId}" />
			<fr:property name="key(edit)" value="manage.visibility.groups.alias.editLabel"/>
			<fr:property name="bundle(edit)" value="CONTACTS_RESOURCES"/>
			--%>
			<%-- <fr:property name="visibleIf(edit)" value="alias"/> --%>
			<%-- %><fr:property name="moduleRelative(edit)" value="true"/> 		
		</fr:layout> --%>
</fr:view>
