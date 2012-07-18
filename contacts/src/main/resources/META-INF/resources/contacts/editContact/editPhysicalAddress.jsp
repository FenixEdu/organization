<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<html:xhtml/>

<logic:equal name="contactToEdit" property="class.name" value="module.contacts.domain.Phone">
<jsp:include page="editOneFieldContact.jsp"/>
It's a phone!!
</logic:equal>
<logic:equal name="contactToEdit" property="class.name" value="module.contacts.domain.EmailAddress">
It's an Email!!
</logic:equal>
<logic:equal name="contactToEdit" property="class.name" value="module.contacts.domain.PhysicalAddress">
It's a PhysicalAddress!!
</logic:equal>
<logic:equal name="contactToEdit" property="class.name" value="module.contacts.domain.WebAddress">
It's a WebAddress!!
</logic:equal>
Or nothing?!?!
