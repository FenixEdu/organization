<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<% final String contextPath = request.getContextPath(); %>
<script src='<%= contextPath + "/webjars/jquery-ui/1.11.1/jquery-ui.js" %>'></script>
<script src='<%= contextPath + "/js/organization/utils.js" %>'></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="<%= contextPath %>/css/organization/model.css">

<br/>

<ul class="nav nav-tabs" style="margin-bottom: 25px;">
    <li>
        <a id="modelsTab">
            <spring:message code="label.organization.models" text="Organizational Models"/> 
        </a>
    </li>
    <li>
        <a id="entityTypesTab">
            <spring:message code="label.organization.entities" text="Entity Types"/>
        </a>
    </li>
    <li>
        <a id="relationTypesTab">
            <spring:message code="label.organization.relations" text="Relation Types"/>
        </a>
    </li>
</ul>

<script type="text/javascript">
    var contextPath = '<%= request.getContextPath() %>';
    var basePagePath = contextPath + '/organization';
    $(document).ready(function() {
        document.getElementById("modelsTab").href=basePagePath; 
        document.getElementById("entityTypesTab").href=basePagePath + "/partyType/list"; 
        document.getElementById("relationTypesTab").href=basePagePath + "/accountabilityType/list"; 
    });
</script>
