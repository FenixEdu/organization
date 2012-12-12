<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<logic:notPresent name="party" property="personalInformation">
	<bean:message key="label.manage.information.none" bundle="PERSONAL_INFORMATION_RESOURCES"/>
</logic:notPresent>
<logic:present name="party" property="personalInformation">
	
</logic:present>
