<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<html:xhtml/>

<logic:notEmpty name="person" >
<h1><bean:message key="edit.person.information.and.contacts.main.label" bundle="CONTACTS_RESOURCES"/></h1>
<br/>
<h2><bean:message key="edit.person.information.and.contacts.photo.label" bundle="CONTACTS_RESOURCES"/></h2>
<br/>
<html:img src="https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&amp;contentContextPath_PATH=/homepage"
                    paramId="uuid" paramName="person" paramProperty="user"
                    align="middle" styleClass="float: right; border: 1px solid #aaa; padding: 3px;" /> 

<logic:notEmpty name="sortedContacts" >
<%-- Visibility of the contacts--%>
<h2><bean:message key="edit.person.information.and.contacts.visibility.label" bundle="CONTACTS_RESOURCES"/></h2>

<table class="tstyle3">
	<tr>
		<th/>
		<th/>
		<%-- Print the visibility group name --%>
		<logic:iterate id="visibilityGroup" name="visibilityGroups">
		<%-- Print something depending on the existence of a group alias or not--%>
			<th>
			<logic:empty name="visibilityGroup" property="groupAlias">
				<bean:write name="visibilityGroup" property="name"/>
			</logic:empty>
			<logic:notEmpty name="visibilityGroup" property="groupAlias">
				<logic:notEmpty name="visibilityGroup" property="groupAlias.groupAlias">
					<bean:write name="visibilityGroup" property="groupAlias.groupAlias"/>
				</logic:notEmpty>
				<logic:empty name="visibilityGroup" property="groupAlias.groupAlias">
					<bean:write name="visibilityGroup" property="name"/>
				</logic:empty>
			</logic:notEmpty>
			</th>
		</logic:iterate>
	</tr>
	<logic:iterate id="sortedContact" name="sortedContacts">
	<logic:present name="sortedContact">
		<tr>
		<%-- Use the class to write the bean:message --%>
			<bean:define id="keyDependingOnClass" name="sortedContact" property="class.name"/>
			<td><bean:message key="<%=keyDependingOnClass.toString()%>" bundle="CONTACTS_RESOURCES"/> (<bean:write name="sortedContact" property="type.localizedName"/>)</td>
			<%-- showing of the contact information --%>
			<td><bean:write name="sortedContact" property="description"/></td>
			<%-- mark all visibilities with the given image or - case they aren't visible--%>
					<logic:iterate id="visibilityGroup" name="visibilityGroups">
					<td class="acenter">
					<%if (((module.contacts.domain.PartyContact)sortedContact).isVisibleTo((myorg.domain.groups.PersistentGroup)visibilityGroup)) {%>
                		<%-- <img src="<%request.getContextPath();%>/images/accept.gif"/>--%>
                		T
					<%}else {%>
						-
					<%}%>
					</td>
					</logic:iterate>
					<td class="tdclear">
					<html:link action="/contacts.do?method=editPartyContact" paramId="partyContactOid" paramName="sortedContact" paramProperty="externalId">
						<bean:message bundle="CONTACTS_RESOURCES" key="manage.contacts.edit.label"/>
					</html:link>,
					<html:link action="/contacts.do?method=deletePartyContact" paramId="partyContactOid" paramName="sortedContact" paramProperty="externalId">
						<bean:message bundle="CONTACTS_RESOURCES" key="manage.contacts.remove.label"/>
					</html:link>
					</td>
	</logic:present>
	</logic:iterate>
</table>

</logic:notEmpty>
<logic:empty name="sortedContacts">
<h3><bean:message key="edit.person.information.and.contacts.no.available.contacts" bundle="CONTACTS_RESOURCES"/></h3>
</logic:empty>
<html:link action="/contacts.do?method=createPartyContact" paramId="personOid" paramName="person" paramProperty="externalId">
<br/>
	<h3><bean:message bundle="CONTACTS_RESOURCES" key="manage.contacts.addContact.label"/></h3>
</html:link>
</logic:notEmpty>


<%--
TODO Talvez meter aqui em baixo um erro ou uma mensagem para que isto seja reportado. Basicamente só pode acontecer quando aparece um utilizador que não é uma pessoa
--%>
<logic:empty name="person">
<h1>Teste, null TODO assert if it's  possible to get here</h1>
</logic:empty>
