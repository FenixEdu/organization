<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<logic:notPresent name="party" property="personalInformation">
	<bean:message key="label.manage.information.none" bundle="PERSONAL_INFORMATION_RESOURCES"/>
</logic:notPresent>
<logic:present name="party" property="personalInformation">
	
</logic:present>
