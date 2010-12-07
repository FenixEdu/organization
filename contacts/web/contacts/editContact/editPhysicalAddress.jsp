<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

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