<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message key="user.label.view" bundle="CONTACTS_RESOURCES"/> <bean:write name="person" property="user"/></h2>
</br>
<logic:equal name="person" property="partyContacts.empty" value="true">
	<h3 class="mtop2 mbottom05"><bean:message key="no.contacts.label" bundle="CONTACTS_RESOURCES"/></h3>
</logic:equal>
<logic:equal name="person" property="partyContacts.empty" value="false">
	<h3 class="mtop2 mbottom05"><bean:message key="contactListing.label.view" bundle="CONTACTS_RESOURCES"/></h3>
	<fr:view name="person" property="partyContacts">
		<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.domain.PartyContact">
		<fr:slot name="description" key="label.description"></fr:slot>
		<fr:slot name="party.partyName"></fr:slot>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2"/>
		</fr:layout>
	</fr:view>
</logic:equal>
<div id="xpto">
	<script type="text/javascript">
				$("#xpto").attr("class","infobox_dotted");
	</script>
	<fr:edit name="emailBean" id="xpto" action="/contacts.do?method=createCustomEmail">
		<fr:schema type="module.contacts.presentationTier.action.bean.ContactBean" bundle="CONTACTS_RESOURCES">
			<fr:slot name="value" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
				<fr:property name="value" value="256"/>
			</fr:slot>
		</fr:schema> 
	</fr:edit>
	<html:link action="/contacts.do?method=modifyContacts" paramId="personOid" paramName="person" paramProperty="externalId">
					<bean:message key="user.create.contacts.label" bundle="CONTACTS_RESOURCES"/>
	</html:link>
</div>
</br>

<%--   
<bean:define id="person" name="USER_SESSION_ATTRIBUTE"	property="user.person"/>

<h2>
	<bean:message bundle="MOBILITY_RESOURCES" key="label.module.mobility.frontPage"/>
</h2>

<fr:form action="/mobility.do?method=frontPage">
	<fr:edit id="offerSearch" name="offerSearch">
		<fr:schema type="module.mobility.domain.util.OfferSearch" bundle="MOBILITY_RESOURCES">
			<fr:slot name="processNumber" key="label.mobility.processIdentification" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
				<fr:property name="size" value="10"/>
			</fr:slot>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="MOBILITY_RESOURCES" key="label.mobility.submit"/>
	</html:submit>
</fr:form>
<br/>
<logic:notEmpty name="offerSearch" property="processNumber">
	<bean:message bundle="MOBILITY_RESOURCES" key="message.mobility.invalid.processIdentification"/>
</logic:notEmpty>
--%>



