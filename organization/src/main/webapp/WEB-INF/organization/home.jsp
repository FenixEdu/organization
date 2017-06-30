<%@page import="org.fenixedu.commons.i18n.I18N"%>
<jsp:include page="models_common.jsp"/>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<% final String contextPath = request.getContextPath(); %>

<div class="page-body">
    <div>
        <table class="table">
            <tbody id="models">
                <tr>
                    <td>
                        <form id="addModelForm" class="form-horizontal" action="<%= contextPath + "/organization/createNewModel" %>" method="POST">
                            <input name="name" class="form-control" style="display: inline; width: 90%; margin-left: 10px;" required="required"/>
                            <button class="btn btn-primary">
                                <span class="glyphicon glyphicon-plus"></span>
                            </button>
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    var contextPath = '<%= contextPath %>';
    var models = <%= request.getAttribute("models")%>;
    $(document).ready(function() {
        document.getElementById("modelsTab").parentNode.classList.add("active");

        $(models).each(function(i, m) {
            var hrefModel = contextPath + '/organization/model/' + m.id;
            var row = $('<tr/>').prependTo($('#models'));
            row.append($('<td/>').html($('<a href="' + hrefModel + '">').text(m.name.<%= I18N.getLocale().getLanguage() %>)));
        });
    });
</script>