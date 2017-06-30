<%@page import="org.fenixedu.bennu.core.util.CoreConfiguration"%>
<%@page import="org.fenixedu.commons.i18n.I18N"%>
<jsp:include page="models_common.jsp"/>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<% final String contextPath = request.getContextPath(); %>

<div class="page-body">
    <div>
        <form id="addAccountabilityTypeForm" class="form-horizontal" action="<%= contextPath + "/organization/createNewAccountabilityType" %>" method="POST">
            <table class="table">
                <tbody id="accountabilityTypes">
                    <tr>
                        <td>
                            <input name="name" class="form-control" style="display: inline; width: 90%; margin-left: 10px;" required="required"/>
                        </td>
                        <td>
                            <input name="type" class="form-control" style="display: inline; width: 90%; margin-left: 10px;" required="required"/>
                        </td>
                        <td>
                            <button class="btn btn-primary">
                                <span class="glyphicon glyphicon-plus"></span>
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
    var contextPath = '<%= contextPath %>';
    var partyTypes = <%= request.getAttribute("accountabilityTypes")%>;
    var supportedLocales = '<%= CoreConfiguration.getConfiguration().supportedLocales().trim() %>'.split(',');
    $(document).ready(function() {
        document.getElementById("relationTypesTab").parentNode.classList.add("active");

        $(partyTypes).each(function(i, p) {
        	var hrefType = contextPath + '/organization/accountabilityType/' + p.id;
        	var row = $('<tr/>').prependTo($('#accountabilityTypes'));
        	row.append($('<td/>').html($('<a href="' + hrefType + '">').text(p.type)));
        	row.append($('<td/>').html(p.name.<%= I18N.getLocale().getLanguage() %>));
        	row.append($('<td/>').html(Utils.checkI18NCompleteString(supportedLocales, p.name)));
        });

        var row = $('<tr/>').prependTo($('#accountabilityTypes'));
        row.append($('<th/>').html('<spring:message code="label.organization.type" text="Type"/>'));
        row.append($('<th/>').html('<spring:message code="label.organization.name" text="Name"/>'));
        row.append($('<th/>').html(""));
    });
</script>