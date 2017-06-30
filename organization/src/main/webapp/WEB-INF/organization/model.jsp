<%@page import="org.fenixedu.commons.i18n.I18N"%>
<jsp:include page="model_common.jsp"/>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<% final String contextPath = request.getContextPath(); %>

<div class="page-body">

<div class="col-lg-6">
    <div class="panel panel-default">
        <div class="panel-body">
            <div>
                <table class="table">
                    <thead><tr><th><spring:message code="label.organization.model.units" text="Units" /></th></tr></thead>
                    <tbody id="units"/>
                </table>
            </div>
            <div>
                <table class="table">
                    <thead><tr><th><spring:message code="label.organization.model.people" text="People" /></th></tr></thead>
                    <tbody id="people"/>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="col-lg-6">
    <div class="panel panel-default">
        <div class="panel-body">
            <div>
                <table class="table">
                    <thead><tr><th><spring:message code="label.organization.relations" text="Relation Types"/></th></tr></thead>
                    <tbody id="accountabilityTypes"/>
                </table>
            </div>
        </div>
    </div>
</div>

</div>

<script type="text/javascript">
    var contextPath = '<%= contextPath %>';
    var organizationModel = <%= request.getAttribute("organizationModel")%>;
    var basePagePath = contextPath + '/organization/model/' + organizationModel.id;
    $(document).ready(function() {
    	document.getElementById("structureTab").parentNode.classList.add("active");

        $(organizationModel.units).each(function(i, u) {
            var hrefModel = contextPath + '/organization/model/' + u.id;
            var row = $('<tr/>').prependTo($('#units'));
            row.append($('<td/>').html($('<a href="' + hrefModel + '">').text(u.name)));
        });

        $(organizationModel.accountabilityTypes).each(function(i, t) {
            var row = $('<tr/>').prependTo($('#accountabilityTypes'));
            row.append($('<td/>').html(t.name.<%= I18N.getLocale().getLanguage() %>));
        });
    });
</script>