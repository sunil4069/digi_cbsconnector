<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ui:header/>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading">
                <div class="panel-actions">
                    <a href="#" class="fa fa-caret-down"></a>
                </div>

                <h2 class="panel-title">Create User</h2>
            </header>
            <div class="panel-body">
                <form class="form-horizontal form-bordered" method="post" name="userform" id="userform">
                    <input type="hidden" id="id" value="0">
                    <div class="row">
                        <div class="col-md-4">
                            <label class="control-label" for="staffid">Staff ID</label>
                            <select class="form-control" id="staffid" name="staffid" required>
                                <c:forEach items="${staffs }" var="s">
                                    <option value="${s.code }">${s.code }-${s.firstName } ${s.lastName }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="control-label" for="uname">User Name</label>
                            <input type="text" class="form-control" id="uname" name="uname">
                        </div>
                        <div class="col-md-4">
                            <label class="control-label" for="uname">Roles</label>
                            <select class="form-control roles" id="roles" name="roles" multiple>
                                <option value="1">SUPER ADMIN</option>
                                <option value="2">ADMIN</option>
                                <option value="3">INPUTTER</option>
                                <option value="4">AUTHORIZER</option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label class="control-label" for="password">Password</label>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                        <div class="col-md-4">
                            <label class="control-label" for="repass">Confirm Password</label>
                            <input type="password" class="form-control" id="repass" name="repass">
                        </div>
                        <div class="col-md-4">
                            <label class="control-label" for="txnlimit">Txn Limit (for authorizer only)</label>
                            <input type="text" value="0.00" class="form-control" id="txnlimit" name="txnlimit">
                        </div>
                    </div>
                    <br/>
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9 col-sm-offset-3">
                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="reset" class="btn btn-default">Reset</button>
                            </div>
                        </div>
                    </footer>
                </form>
            </div>
        </section>
    </div>
</div>
<section class="panel">
    <header class="panel-heading">
        <div class="panel-actions">
            <a href="#" class="fa fa-caret-down"></a>
        </div>

        <h2 class="panel-title">User Details</h2>
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
<section class="panel">
    <header class="panel-heading">
        <div class="panel-actions">
            <a href="#" class="fa fa-caret-down"></a>
        </div>

        <h2 class="panel-title">Update User Role</h2>
    </header>
    <div class="panel-body">
        <form class="form-horizontal form-bordered" method="post" name="userform" id="userroleform">
            <input type="hidden" id="id" value="0">
            <div class="row">
                <div class="col-md-4">
                    <label class="control-label" for="uname">User Name</label>
                    <input type="text" class="form-control" id="uname" name="uname" readonly>
                </div>
                <div class="col-md-4">
                    <label class="control-label" for="uname">Roles</label>
                    <select class="form-control roles" id="roles" name="roles" multiple>
                        <option value="1">SUPER ADMIN</option>
                        <option value="2">ADMIN</option>
                        <option value="3">INPUTTER</option>
                        <option value="4">AUTHORIZER</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="control-label" for="txnlimit">Txn Limit (for authorizer only)</label>
                    <input type="text" class="form-control" id="txnlimit" name="txnlimit" >
                </div>
            </div>
            <br/>
            <footer class="panel-footer">
                <div class="row">
                    <div class="col-sm-9">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                </div>
            </footer>
        </form>
    </div>
</section>
<ui:footer/>
<script>
    $(document).ready(function () {
        loadUserTable();
        $('#staffid').select2();
        $('.roles').select2();

        // SUBMIT FORM
        $("#userform").submit(function (event) {
            // Prevent the form from submitting via the browser.
            event.preventDefault();
            let password = $('#password').val();
            let repass = $('#repass').val();
            let txnlimit = $("#txnlimit").val();
            //pass values only when the passwords match
            if (password == repass) {
                //get multiple values from roles select option
                let roles_list = $("#roles").val();
                let array = [];
                //convert roles selected values to json array object
                $(roles_list).each(function (index, element) {
                    let data = {"id": element};
                    array.push(data);
                });

                let formData = {
                    "username": $("#uname").val(),
                    "password": password,
                    "confirmpassword": repass,
                    "txnlimit":txnlimit,
                    "staffs": {
                        "code": $('#staffid').val()
                    }
                };
                formData['roles'] = array;
                //formDara.roles=array;
                console.log(formData);
                let url = "${pageContext.request.contextPath }/users";
                ajaxPost(url, formData);
            } else {
                alert('Password Mismatch!');
            }
        });

    });

    function loadUserTable() {
        let url = "${pageContext.request.contextPath }/users/authorized";
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
                            var username = "'" + row.username + "'";
                            let edit = '<a class="modal-with-form btn btn-primary btn-sm" onclick="edit(' + username + ')"><i class="fa fa-edit"></i></a>    ';
                            let activate = '<a class="modal-with-form btn btn-success btn-sm" data-toggle="tooltip" title="Activate ' + username + '" onclick="activateUser(' + username + ')"><i class="fa fa-check"></i></a>    ';
                            let deactivate = '<a class="modal-with-form btn btn-danger btn-sm" data-toggle="tooltip" title="Deactivate ' + username + '" onclick="deactivateUser(' + username + ')"><i class="fa fa-times"></i></a>';
                            return edit + activate + deactivate; // row object contains the row data
                        },

                    }
                ]
            });
        })
            .done(function (data) {
            })
            .fail(function (jqxhr, settings, ex) {
                $('#userTable').DataTable();
            });
    }

    function edit(username) {
        $("#userroleform #uname").val(username);
        let url = "${pageContext.request.contextPath }/users/" + username;
        var roleIds = [];
        $.get(url, function (data, status) {
            var roles = data.roles;
            let txnlimit = data.txnlimit;
            $.each(roles, function (key, value) {
                roleIds.push(value.id);
            });
            $("#userroleform #roles").val(roleIds);
            $("#userroleform #txnlimit").val(txnlimit);
            $("#userroleform #roles").select2();
        });

    }

    $("#userroleform").submit(function (event) {
        event.preventDefault();
        let roles_list = $("#userroleform #roles").val();
        let username = $("#userroleform #uname").val();
        let txnlimit =$("#userroleform #txnlimit").val();
        let json = {
            "roles":roles_list,
            "txnlimit":txnlimit
        };
        let url = "${pageContext.request.contextPath }/users/roles/" + username;
        ajaxPost(url, json);

    });

    function activateUser(username) {
        let url = "${pageContext.request.contextPath}/users/" + username + "/activate";
        ajaxPatchUrlCall(url);
    }

    function deactivateUser(username) {
        let url = "${pageContext.request.contextPath}/users/" + username + "/deactivate";
        ajaxPatchUrlCall(url);
    }


</script> 
