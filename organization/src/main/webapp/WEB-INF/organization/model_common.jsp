<%@page import="org.fenixedu.commons.i18n.I18N"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<% final String contextPath = request.getContextPath(); %>
<script src='<%= contextPath + "/webjars/jquery-ui/1.11.1/jquery-ui.js" %>'></script>
<script src='<%= contextPath + "/js/organization/utils.js" %>'></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="<%= contextPath %>/css/organization/model.css">

<div class="page-header">
    <h2>
        <spring:message code="label.organization.model" text="Organizational Model"/>: <span id="organizationModelName"></span>
    </h2>
</div>

<ul class="nav nav-tabs" style="margin-bottom: 25px;">
    <li>
        <a id="structureTab" class="glyphicon glyphicon-equalizer">
            <spring:message code="label.organization.model.structure" text="Structure"/> 
        </a>
    </li>
    <li>
        <a id="configurationTab" class="glyphicon glyphicon-wrench">
            <spring:message code="label.organization.configuration" text="Configuration"/>
        </a>
    </li>
</ul>

<script type="text/javascript">
    var contextPath = '<%= request.getContextPath() %>';
    var organizationModel = ${organizationModel};
    var basePagePath = contextPath + '/organization/model/' + organizationModel.id;
    $(document).ready(function() {
        $('#organizationModelName').html(organizationModel.name.<%= I18N.getLocale().getLanguage() %>);
        var today = new Date();
        document.getElementById("structureTab").href=basePagePath; 
        document.getElementById("configurationTab").href=basePagePath + "/configuration";
    });
</script>
