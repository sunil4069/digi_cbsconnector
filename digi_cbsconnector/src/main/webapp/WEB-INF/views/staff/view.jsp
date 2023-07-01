<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ui:header/>

<div class="row">
    <div class="col-md-12">
        <div class="tabs">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#popular" data-toggle="tab"><i
                        class="fa fa-star"></i> Staff</a></li>
                <li><a href="#recent" data-toggle="tab">Staff Family</a></li>
            </ul>

            <div class="tab-content">
                <!-- Display staff -->
                <div id="popular" class="tab-pane active">
                    <section class="panel">
                        <header class="panel-heading">
                            <div class="panel-actions">
                                <a href="#" class="fa fa-caret-down"></a>
                            </div>

                            <h2 class="panel-title">Staff Details</h2>
                        </header>
                        <div class="panel-body">
                            <table class="table table-bordered table-striped mb-none"
                                   id="staffTable">
                                <thead>
                                <tr>
                                    <th>Code</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Gender</th>
                                    <th>Phone Number</th>
                                    <th>Post</th>
                                    <th>Office</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </section>
                </div>
                <!-- Display staff family -->
                <div id="recent" class="tab-pane">
                    <section class="panel">
                        <header class="panel-heading">
                            <div class="panel-actions">
                                <a href="#" class="fa fa-caret-down"></a>
                            </div>

                            <h2 class="panel-title">Staff Family Details</h2>
                            <br>
                            <div class="form-group">
                                <div class="col-md-4">
                                    <select class="form-control" id="staffcode" name="staffcode" required>
                                        <option value="">Select Staff</option>
                                        <c:forEach items="${staffs }" var="s">
                                            <option value="${s.code }">${s.code }-${s.firstName } ${s.lastName }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </header>
                        <div class="panel-body">
                            <table class="table table-bordered table-striped mb-none"
                                   id="staffFamilyTable">
                                <thead>
                                <tr>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Gender</th>
                                    <th>Occupation</th>
                                    <th>Phone Number</th>
                                    <th>Relation</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal for staffs -->
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <form class="form-horizontal form-bordered" id="staffform">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">EDIT STAFF</h4>
                </div>

                <div class="modal-body">

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="code"> Code</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="code" name="code" readonly>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="fname">First
                            Name</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="fname" name="fname">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="lname">Last
                            Name</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="lname" name="lname">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="gender">Gender</label>
                        <div class="col-md-6">
                            <select class="form-control" id="gender"
                                    name="gender">
                                <option value="">Select Gender</option>
                                <option value="m">Male</option>
                                <option value="f">Female</option>
                                <option value="o">Other</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="phone">Phone
                            Number</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="phone" name="phone">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="post">Post</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="post" name="post">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="office">Office</label>
                        <div class="col-md-6">
                            <select class="form-control" id="office" name="office">
                                <option value="">Select Office</option>
                                <c:forEach items="${offices }" var="o">
                                    <option value="${o.id }">${o.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9 col-sm-offset-3">
                                <a type="button" id="deletebtn" onclick="" class="btn btn-danger"><i class="fa fa-trash-o"></i></a>
                                <button type="submit" class="btn btn-primary">Save</button>
                                <button type="button" class="btn btn-info"
                                        data-dismiss="modal">Close
                                </button>

                            </div>
                        </div>
                    </footer>
                </div>
            </form>
        </div>

    </div>
</div>

<%-- <ui:successFailureConfirmWarningInfo/> --%>
<ui:footer/>

<script>
    $(document).ready(function () {
        loaddatatable();
       loadfamilydatatable(); 
        $('#staffcode').select2();

    });

    //show populated datatable
    function loaddatatable() {
        $('#staffFamilyTable').DataTable(); 
        let url = "${pageContext.request.contextPath }/staffs/";
        $.get(url, function (data, status) {
            let json = data.data;
            let table = $('#staffTable').DataTable({
                "data": json,
                "columns": [
                    {data: "code"},
                    {data: "firstName",defaultContent:""},
                    {data: "lastName",defaultContent:""},
                    {data: "gender",defaultContent:""},
                    {data: "phoneNumber",defaultContent:""},
                    {data: "post",defaultContent:""},
                     {data: "office.name",defaultContent:""},
                    {
                        "data": "Action",
                        "orderable": false,
                        "searchable": false,
                        "render": function (data, type, row, meta) { // render event defines the markup of the cell text
                            let profile = '<a class="modal-with-form btn btn-info btn-sm" href="${pageContext.request.contextPath }/userprofiles/user-profile/' + row.code + '" ><i class="fa fa-user"></i></a>'; // row object contains the row data
                            let edit = '<a class="modal-with-form btn btn-primary btn-sm" onclick="edit(' + row.code + ')"><i class="fa fa-edit"></i></a>';
                            let a = edit + " " + profile; // row object contains the row data
                            return a;

                        },

                    }
                ],
                "destroy": true
            });
        })
            .done(function (data) {
            })
            .fail(function (jqxhr, settings, ex) {
                alert('No Data Found!');
            });
    }

    //ON EDIT BUTTON CLICK
    function edit(code) {
        let url = "${pageContext.request.contextPath }/staffs/" + code;
        $.get(url, function (data, status) {
            let json = data.data;
            $("#code").val(json.code);
            $("#fname").val(json.firstName);
            $("#lname").val(json.lastName);
            $("#gender").val(json.gender);
            $("#phone").val(json.phoneNumber);
            $("#post").val(json.post);
            $("#office").val(json.office.id);
            $("#deletebtn").attr("onclick", "deletedata('" + url + "')");
            $("#myModal").modal('show');
        })
            .done(function (data) {
            })
            .fail(function (jqxhr, settings, ex) {
                alert('No Data Found!');
            });

    }

    // SUBMIT staffs
    $("#staffform").submit(function (event) {
        // Prevent the form from submitting via the browser.

        event.preventDefault();
        let formData = {
            "code": $('#code').val(),
            "firstName": $("#fname").val(),
            "lastName": $("#lname").val(),
            "gender": $("#gender").val(),
            "phoneNumber": $("#phone").val(),
            "post": $("#post").val(),
            "office": {
                "id": $("#office").val()
            }
        };
        let url = "${pageContext.request.contextPath }/staffs";
        ajaxPost(url, formData);
        $("#myModal").modal('hide');

    });

    $("#myModal").on("hidden.bs.modal", function () {
        loaddatatable();
    });

    //staff family
    function loadfamilydatatable() {
        $('#staffcode').change(function () {
            let staffcode = $(this).val();
            getfamily(staffcode);
        });

        function getfamily(staffcode) {
            let familytable = '#staffFamilyTable';
            let url = "${pageContext.request.contextPath }/staffsFamily/findByStaff/" + staffcode;
            $.get(url, function (data, status) {
                let json = data.datas;
                let table = $(familytable).DataTable({
                    "data": json,
                    "columns": [
                        {data: "firstName"},
                        {data: "lastName"},
                        {data: "gender"},
                        {data: "occupation"},
                        {data: "phoneNumber"},
                        {data: "relation"},
                        {
                            "data": "Action",
                            "orderable": false,
                            "searchable": false,
                            "render": function (data, type, row, meta) { // render event defines the markup of the cell text
                                let url = '${pageContext.request.contextPath }/staffsFamily/' + row.id;
                                let onclick = "deletedata('" + url + "')";
                                let a = '<a class="modal-with-form btn btn-danger btn-sm familydelete" onclick="' + onclick + '"><i class="fa fa-trash-o"></i></a>'; // row object contains the row data
                                return a;
                            }
                        }
                    ],
                    "destroy": true
                });
            })
                .done(function (data) {
                })
                .fail(function (jqxhr, settings, ex) {
                    $(familytable).empty();
                });
        }
    }


</script>