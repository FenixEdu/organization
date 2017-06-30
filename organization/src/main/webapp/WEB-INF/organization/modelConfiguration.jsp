${portal.toolkit()}
<%@page import="org.fenixedu.bennu.core.util.CoreConfiguration"%>
<%@page import="org.fenixedu.commons.i18n.I18N"%>
<jsp:include page="model_common.jsp"/>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<% final String contextPath = request.getContextPath(); %>

<div class="page-body">
    <div>
        <form id="addAccountabilityTypeForm" class="form-horizontal" method="POST">
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
                <table class="table">
                    <tbody id="accountabilityTypes">
                    </tbody>
                </table>
            </div>
            <div class="form-group">
                <button class="btn btn-primary">
                    <spring:message code="label.save" text="Save"/>
                </button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    var contextPath = '<%= contextPath %>';
    var accountabilityTypes = <%= request.getAttribute("accountabilityTypes")%>;
    var supportedLocales = '<%= CoreConfiguration.getConfiguration().supportedLocales().trim() %>'.split(',');
    var organizationModel = <%= request.getAttribute("organizationModel")%>;

    function containsAccountability(a) {
        var c = false;
    	$(organizationModel.accountabilityTypes).each(function(i, t) {
    	    if (t.id == a.id) {
    	        c = true;
    	    }
        });
    	return c;
    };

    function inputForAccountabilityType(a) {
        if (containsAccountability(a)) {
            return $('<input name="accountabilityTypes" class="form-control" style="display: inline;" type="checkbox" checked="checked" value="' + a.id + '"/>');
        } else {
            return $('<input name="accountabilityTypes" class="form-control" style="display: inline;" type="checkbox" value="' + a.id + '"/>');
        }
    };

    $(document).ready(function() {
        document.getElementById("configurationTab").parentNode.classList.add("active");

        var name = JSON.stringify(organizationModel.name);
        document.getElementById("name").value = name;

        $(accountabilityTypes).each(function(i, a) {
            var row = $('<tr/>').prependTo($('#accountabilityTypes'));
            row.append($('<td/>').html(a.type));
            row.append($('<td/>').html(a.name.<%= I18N.getLocale().getLanguage() %>));
            row.append($('<td/>').html(inputForAccountabilityType(a)));
        });

        var row = $('<tr/>').prependTo($('#accountabilityTypes'));
        row.append($('<th/>').html('<spring:message code="label.organization.relations" text="Relation Type"/>'));
        row.append($('<th/>').html('<spring:message code="label.organization.name" text="Name"/>'));
        row.append($('<th/>').html(""));
    });
</script>