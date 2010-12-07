<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

	<h2><bean:message bundle="CONTACTS_RESOURCES" key="manage.visibility.groups.alias.edit.groupLabel"/>:</h2>
<h3>
	<fr:view name="groupAliasBean" property="groupToEdit.name">
	</fr:view>
</h3>
<fr:edit name="groupAliasBean"
	id="myorg.modules.contacts.edit.setGroupAliasBean"
	action="/contacts.do?method=modifyGroupAlias">
	<fr:schema
		type="module.contacts.presentationTier.action.bean.GroupAliasBean"
		bundle="CONTACTS_RESOURCES">
		<fr:slot name="alias" bundle="CONTACTS_RESOURCES"
			key="manage.visibility.groups.alias.edit.aliasLabel">
			<fr:validator
				name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator">
				<fr:property name="requiredLanguages" value="pt,en" />
			</fr:validator>
			<fr:property name="value" value="40" />
		</fr:slot>
	</fr:schema>
	<fr:destination name="cancel"
		path="/contacts.do?method=configContactsModule" />
</fr:edit>
<%-- 
	<html:link action="/contacts.do?method=modifyContacts" paramId="personOid" paramName="person" paramProperty="externalId">
					<bean:message key="user.create.contacts.label" bundle="CONTACTS_RESOURCES"/>
	</html:link>--%>
