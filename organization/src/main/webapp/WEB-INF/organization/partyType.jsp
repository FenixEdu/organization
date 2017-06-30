${portal.toolkit()}
<%@page import="org.fenixedu.bennu.core.util.CoreConfiguration"%>
<jsp:include page="models_common.jsp"/>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% final String contextPath = request.getContextPath(); %>

<div class="page-body">
    <form method="POST" action="#" class="form-horizontal" id="editForm">
        <div class="form-group">
            <label class="control-label col-sm-2" for="type">
                <spring:message code="label.organization.type" text="Type"/>
            </label>
            <div class="col-sm-10">
                <input name="type" type="text" class="form-control" id="type" required="required"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="name">
                <spring:message code="label.organization.name" text="Name"/>
            </label>
            <div class="col-sm-10">
                <input bennu-localized-string type="text" name="name" id="name" required/>
            </div>
            <div class="col-sm-10 col-sm-offset-2" id="warings">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2">
                <button id="editButton" class="btn btn-primary">
                    <spring:message code="label.save" text="Save"/>
                </button>
                <button id="deleteButton" class="btn btn-danger" onclick="document.getElementById('deleteForm').submit(); return false;">
                    <spring:message code="label.delete" text="Delete"/>
                </button>
            </div>
        </div>
    </form>
    <form method="POST" action="#" class="form-horizontal" id="deleteForm" name="deleteForm" style="display: none;">
    </form>
</div>

<script type="text/javascript">
    var contextPath = '<%= contextPath %>';
    var partyType = <%= request.getAttribute("partyType")%>;

    document.getElementById("entityTypesTab").parentNode.classList.add("active");

    var name = JSON.stringify(partyType.name);
    document.getElementById("type").value = partyType.type;
    document.getElementById("name").value = name;

    var supportedLocales = '<%= CoreConfiguration.getConfiguration().supportedLocales().trim() %>'.split(',');
    var warings = Utils.checkI18NCompleteString(supportedLocales, partyType.name);
    $('#warings').html(warings);

    document.getElementById("deleteForm").action = contextPath + '/organization/partyType/' + partyType.id + "/delete";

</script>
