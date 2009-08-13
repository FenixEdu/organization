<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>
<%@ taglib uri="/WEB-INF/taglibs-datetime.tld" prefix="dt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/chart.tld" prefix="chart" %>

<h2>
	<bean:message key="label.model" bundle="ORGANIZATION_RESOURCES"/>
	:
	<bean:write name="organizationalModel" property="name"/>
</h2>

<logic:present name="party">
	<jsp:include page="viewPartyDetails.jsp"/>
</logic:present>

<h3>
	<bean:message key="label.unit.review.unconfirmed.accountabilities" bundle="ORGANIZATION_RESOURCES"/>
</h3>

<html:messages id="message" message="true" bundle="ORGANIZATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>

<logic:iterate id="unconfirmedAccountability" name="unconfirmedAccountabilities">
	<fr:view name="unconfirmedAccountability" schema="module.organization.domain.UnconfirmedAccountability">
	</fr:view>
	<bean:message key="label.unconfirmedAccountability.proposed.by" bundle="ORGANIZATION_RESOURCES"/>:
	<bean:write name="unconfirmedAccountability" property="user.presentationName"/>
	<bean:message key="label.unconfirmedAccountability.proposed.date" bundle="ORGANIZATION_RESOURCES"/>
	<dt:format pattern="yyyy-MM-dd HH:mm">
		<bean:write name="unconfirmedAccountability" property="submited.millis"/>
	</dt:format>
	<br/>

	<bean:define id="urlConfirm">/organizationModel.do?method=confirmAccountability&amp;viewName=default&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;partyOid=<bean:write name="party" property="externalId"/></bean:define>
	<html:link action="<%= urlConfirm %>" paramId="unconfirmedAccountabilityOid" paramName="unconfirmedAccountability" paramProperty="externalId">
		<bean:message key="label.confirm" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	<bean:define id="urlReject">/organizationModel.do?method=rejectAccountability&amp;viewName=default&amp;organizationalModelOid=<bean:write name="organizationalModel" property="externalId"/>&amp;partyOid=<bean:write name="party" property="externalId"/></bean:define>
	<html:link action="<%= urlReject %>" paramId="unconfirmedAccountabilityOid" paramName="unconfirmedAccountability" paramProperty="externalId">
		<bean:message key="label.reject" bundle="ORGANIZATION_RESOURCES"/>
	</html:link>
	<br/>
	<br/>
</logic:iterate>