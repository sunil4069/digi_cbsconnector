<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-06-15
  Time: 8:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ui:header/>
<section class="panel">
    <header class="panel-heading">
        <div class="panel-actions">
            <a href="#" class="fa fa-caret-down"></a>
        </div>

        <h2 class="panel-title">Unauthorized User Details</h2>
    </header>
    <div class="panel-body">
        <table class="table table-bordered table-striped mb-none"
               id="userTable">
            <thead>
            <tr>
                <th>User name</th>
                <th>Staff code</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>
        </table>
    </div>
</section>
<ui:footer/>
<script>
    $(document).ready(function () {
        loadUserTable();
    });

    function loadUserTable() {
        let url = "${pageContext.request.contextPath }/users/unauthorized";
        $.get(url, function (data, status) {
            let json = data.data;
            $('#userTable').DataTable({
                "data": json,
                "columns": [
                    {data: "username", "defaultContent": ""},
                    {data: "staffs.code", "defaultContent": ""},
                    {data: "staffs.firstName", "defaultContent": ""},
                    {data: "staffs.lastName", "defaultContent": ""},
                    {data: "status", "defaultContent": ""},
                    {
                        "data": "Action",
                        "orderable": false,
                        "searchable": false,
                        "render": function (data, type, row, meta) { // render event defines the markup of the cell text
                            let username = "'" + row.username + "'";
                            let edit = '<a class="modal-with-form btn btn-success btn-sm" onclick="authorizeUser(' + username + ')" data-toggle="tooltip" title="Authorize ' + username + '"><i class="fa fa-check"></i></a>';
                            let a = edit; // row object contains the row data
                            return a;

                        },

                    }
                ],
                destroy: true
            });
        })
            .done(function (data) {
            })
            .fail(function (jqxhr, settings, ex) {
                $('#userTable').DataTable();
            });
    }

    function authorizeUser(username) {
        if (confirm("Confirm " + username + " authorization?")) {
            let url = '${pageContext.request.contextPath}/users/' + username + '/authorize';
            $.ajax({
                url: url,
                method: "PUT",
                contentType: "application/json",
                beforeSend: function () {
                    $('.loading').css('z-index', '999');
                },
                success: function (data) {
                    toastNotification('Success', data.message, 'success');
                },
                error: function (err) {
                    toastNotification('Failure', "Authorization Failed.", 'error');
                },
                complete: function () {
                    $('.loading').css('z-index', '-1');
                }
            });
            loadUserTable();
        }
    }
</script>
