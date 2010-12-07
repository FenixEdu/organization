<%@page import="module.organization.domain.Person"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/collectionPager.tld" prefix="cp"%>
<script type="text/javascript">
function advancedClick(){
		if(jQuery("#divAdvancedSearch").is(':hidden')){
			//jQuery("#toolTip").fadeOut(150);
			jQuery("#divAdvancedSearch").show("slow");
			jQuery("#divBasicNameSearch").hide("slow");
		}else{
			jQuery("#divBasicNameSearch").show("slow");
			jQuery("#divAdvancedSearch").hide("slow");
			//jQuery("#adicionar").attr("src", srcExpand);
		}
	}
jQuery(document).ready(function($){

	var contextPath = "<%=request.getContextPath()%>";
	var srcExpand = contextPath + "/images/expand.png";
	var srcCollapse = contextPath + "/images/collapse.png";

	var cssGliderDiv = {
			'display' : 'none',
			'padding-bottom' : '25px',
			'padding-left' : '15px'
	}
	if (typeof resultsBasedOnDetails == 'undefined')
	{
		jQuery("#divAdvancedSearch").css(cssGliderDiv);
	}
	else 
	{
		jQuery("#divBasicNameSearch").css(cssGliderDiv);
	}
	var cssGliderButton = {
			'cursor' : 'pointer',
			'padding-top' : '5px'
	}
	//jQuery("#adicionar").css(cssGliderButton);
	//jQuery("#adicionar").attr("src", srcExpand);

	var cssGliderTab = {
			'display':'block'
	}
	//jQuery("#tabAdicionar").css(cssGliderTab);
	
	

	//jQuery("#adicionar").hover(
	//		function() {
	//			if(jQuery("#divAdvancedSearch").is(':hidden')){
	//				jQuery("#toolTip").fadeIn(400);
	//			}
	//		},
	//		function() {
	//			jQuery("#toolTip").fadeOut(150);
	//		}
	//);
});
</script>
<h1><bean:message key="search.person.contacts.title"
	bundle="CONTACTS_RESOURCES" /></h1>

<div id="divBasicNameSearch"><fr:edit
	action="/contacts.do?method=searchPersonsByName" id="personSearchBean"
	name="personSearchBean" schema="myorg.modules.contacts.search.byName">
	<fr:layout name="tabular">
		<fr:property name="classes" value="form" />
		<fr:property name="columnClasses" value="tderror" />
		<fr:property name="requiredMarkShown" value="true" />
	</fr:layout>
</fr:edit> <a href="javascript:advancedClick()"><bean:message
	key="search.person.advanced.link" bundle="CONTACTS_RESOURCES" /></a></div>
<div id=divAdvancedSearch><fr:edit
	action="/contacts.do?method=searchPersonsByDetails"
	id="personSearchByDetailsBean" name="personSearchBean">
	<fr:schema bundle="CONTACTS_RESOURCES"
		type="module.contacts.presentationTier.action.bean.PersonsBean">
		<fr:slot name="searchName" key="search.person.byname.label" />
		<fr:slot name="searchUsername"
			key="search.person.advanced.username.label" />
		<fr:slot name="searchPhone" key="search.person.advanced.phone.label" />
		<fr:slot name="searchPhoneType"
			key="module.contacts.domain.PhoneType.label" />
		<fr:slot name="searchEmail" key="search.person.advanced.email.label" />
		<fr:slot name="searchAddress"
			key="search.person.advanced.address.label" />
		<fr:slot name="searchWebAddress"
			key="search.person.advanced.webaddress.label" />
	</fr:schema>
	<fr:layout name="matrix">
		<fr:property name="classes"
			value="tstyle3 thlight thright mtop05 mbottom05 thmiddle" />
		<fr:property name="slot(searchName)" value="searchName" />
		<fr:property name="row(searchName)" value="0" />
		<fr:property name="column(searchName)" value="0" />

		<fr:property name="slot(searchUsername)" value="searchUsername" />
		<fr:property name="row(searchUsername)" value="1" />
		<fr:property name="column(searchUsername)" value="0" />

		<fr:property name="slot(searchPhone)" value="searchPhone" />
		<fr:property name="row(searchPhone)" value="2" />
		<fr:property name="column(searchPhone)" value="0" />

		<fr:property name="slot(searchPhoneType)" value="searchPhoneType" />
		<fr:property name="row(searchPhoneType)" value="2" />
		<fr:property name="column(searchPhoneType)" value="2" />

		<fr:property name="slot(searchEmail)" value="searchEmail" />
		<fr:property name="row(searchEmail)" value="3" />
		<fr:property name="column(searchEmail)" value="0" />

		<fr:property name="slot(searchAddress)" value="searchAddress" />
		<fr:property name="row(searchAddress)" value="4" />
		<fr:property name="column(searchAddress)" value="0" />

		<fr:property name="slot(searchWebAddress)" value="searchWebAddress" />
		<fr:property name="row(searchWebAddress)" value="5" />
		<fr:property name="column(searchWebAddress)" value="0" />
	</fr:layout>
</fr:edit></div>

<logic:present name="personSearchBean" property="searchResult">
	<h2><bean:message key="search.person.results.label"
		bundle="CONTACTS_RESOURCES" /></h2>
		<%-- If we searched using details, the details must appear alongside with the results
		this logic:equal takes care of that--%>
		
	<logic:equal name="personSearchBean" property="resultsByDetails" value="true">
		 <script type="text/javascript">
			var resultsBasedOnDetails = "indeed they are";
		</script>
	</logic:equal>
	
	<%-- TODO Dividing the output in several pages 	--%>
	<%-- TODO make the resultsByDetails depend on the isDefined and alter the url --%>
	<bean:define id="url">/teste/contacts.do?method=searchPersonsByDetails&amp;name=<bean:write name="personSearchBean" property="searchName"/></bean:define>			
	<p><bean:message key="label.pages" />:					
		<cp:collectionPages url="<%= url %>" numberOfVisualizedPages="11" pageNumberAttributeName="personSearchBean.pageNumber" numberOfPagesAttributeName="personSearchBean.numberOfPages"/>			
	</p> 
<%-- Showing each person --%>
	<logic:iterate id="person" name="personSearchBean" property="searchResult" type="module.organization.domain.Person">
		<div class="personInfo">
			 <fr:view name="person">
			 	<fr:schema bundle="CONTACTS_RESOURCES" type="module.organization.domain.Person">
			 		<fr:slot name="name"/>
			 		<fr:slot name="partyContacts"/>
			 	</fr:schema>
			 	<fr:layout name="person-table-contact">
			 	<%-- for now, putting all of the edit links active --%>
			 		<fr:property name="canEdit" value="true"/>
			 		<fr:property name="editLink" value="<%="/contacts.do?method=editContacts&personEId="+person.getExternalId()%>"/>
			 	</fr:layout>
			 </fr:view>
		</div>
	</logic:iterate>
</logic:present>

<br />
