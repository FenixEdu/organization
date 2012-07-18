<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<html:xhtml/>

<logic:equal name="contactToEditBean" property="className" value="module.contacts.domain.Phone">
<%-- 
<jsp:include page="editOneFieldContact.jsp" /> 
--%>
<fr:edit id="contactToEditBean" name="contactToEditBean" action="/contacts.do?method=applyPartyContactEdit">
	<logic:equal name="contactToEditBean" property="className" value="module.contacts.domain.Phone">
		<fr:schema bundle="CONTACTS_RESOURCES" type="module.contacts.presentationTier.action.bean.ContactToEditBean">
			<fr:slot name="phoneType" key="module.contacts.domain.PhoneType.label" layout="menu-postback" />
			<fr:slot name="value" key="module.contacts.domain.Phone">
        		<logic:equal name="contactToEditBean" property="isVoip" value="true">
					<fr:property name="size" value="50" />
				</logic:equal>
        		<logic:equal name="contactToEditBean" property="isVoip" value="false">
					<fr:property name="size" value="15" />
					<fr:property name="maxLength" value="15" />
        		</logic:equal>
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" />
        		<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RegexpValidator">
        			<logic:equal name="contactToEditBean" property="isVoip" value="false">
            			<fr:property name="regexp" value="(\+?\d{4,15})?" />
            			<fr:property name="message" value="manage.contacts.edit.error.phone.invalidFormat" />
        			</logic:equal>
        			<logic:equal name="contactToEditBean" property="isVoip" value="true">
            			<fr:property name="regexp" value="(.*@(\w+\.\w+)+){1}" />
            			<fr:property name="message" value="manage.contacts.edit.error.phone.invalidFormat.voip" />
        			</logic:equal>
        		</fr:validator>
   			</fr:slot>
		</fr:schema>
		<fr:destination name="postback" path="/contacts.do?method=choosePhoneRegexpPostBack"/>
		<%-- 
		<fr:destination name="invalid" path="/contacts.do?method=editPartyContact"/>
		<fr:destination name="invalid" path="/x"/>
		--%>
	</logic:equal>
</fr:edit>
It's a phone!!
</logic:equal>
<logic:equal name="contactToEditBean" property="className" value="module.contacts.domain.EmailAddress">
<jsp:include page="editOneFieldContact.jsp"/>
It's an Email!!
</logic:equal>
<logic:equal name="contactToEditBean" property="className" value="module.contacts.domain.PhysicalAddress">
<jsp:include page="editPhysicalAddress.jsp"/>
It's a PhysicalAddress!!
</logic:equal>
<logic:equal name="contactToEditBean" property="className" value="module.contacts.domain.WebAddress">
<jsp:include page="editOneFieldContact.jsp"/>
It's a WebAddress!!
</logic:equal>
TODO make a control here so that if it catches a contact which isn't of any kind, it sends a message
Or nothing?!?!
