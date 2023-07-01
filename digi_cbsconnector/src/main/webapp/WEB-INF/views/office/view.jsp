<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ui:header/>
<section class="panel">
    <header class="panel-heading">
        <div class="panel-actions">
            <a href="#" class="fa fa-caret-down"></a>
        </div>

        <h2 class="panel-title">Office Details</h2>
    </header>
    <div class="panel-body">
        <table class="table table-bordered table-striped mb-none"
               id="officeTable">
            <thead>
            <tr>
                <th>RO CODE</th>
                <th>NAME</th>
                <th>ADDRESS</th>
                <th>OFFICE LEVEL</th>
                <th>Action</th>
            </tr>
            </thead>
        </table>
    </div>
</section>

<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <form class="form-horizontal form-bordered" id="officeform">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">EDIT OFFICE</h4>
                </div>

                <div class="modal-body">

                    <input type="hidden" id="id" value="0">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="roCode">RO
                            Code</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="roCode"
                                   name="roCode">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="name">Name</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="name" name="name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="address">Address</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="address"
                                   name="address">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="officeLevel">Office
                            Level</label>
                        <div class="col-md-6">
                            <select class="form-control input-sm mb-md" name="officeLevel"
                                    id="officeLevel">
                                <option value="ho">Head</option>
                                <option value="ro">Regional</option>
                                <option value="br">Branch</option>
                            </select>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9 col-sm-offset-3">
                                <a type="button" id="deleteBtn"
                                   class="mb-xs mt-xs mr-xs modal-basic btn btn-danger"><i
                                        class="fa fa-trash-o"></i></a>
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
        $('#officeTable').DataTable();
        loaddatatable();
    });

    function loaddatatable() {
        let url = "${pageContext.request.contextPath }/office/";
        ajaxGetUrlCall(url);
    }

    function doWork(data) {
        let json = data.datas;
        let table = $('#officeTable').DataTable({
            "data": json,
            "columns": [
                {data: "roCode"},
                {data: "name"},
                {data: "address"},
                {data: "officeLevel"},
                {
                    "data": "Action",
                    "orderable": false,
                    "searchable": false,
                    "render": function (data, type, row, meta) { // render event defines the markup of the cell text
                        let a = '<a class="modal-with-form btn btn-default" onclick="edit(' + row.id + ')"><i class="fa fa-edit"></i> EDIT</a>'; // row object contains the row data
                        return a;
                    }
                }

            ],
            "destroy": true
        });
    }

    //ON EDIT BUTTON CLICK
    function edit(id) {
        let url = "${pageContext.request.contextPath }/office/" + id;
        $.get(url, function (data, status) {
            let json = data.datas;
            $("#id").val(json.id);
            $("#roCode").val(json.roCode);
            $("#name").val(json.name);
            $("#address").val(json.address);
            $("#officeLevel").val(json.officeLevel);
            $("#deleteBtn").attr("onclick", "deleteOffice('" + url + "')");
            $("#myModal").modal('show');
        })
            .done(function (data) {
            })
            .fail(function (jqxhr, settings, ex) {
                alert('No Data Found!');
            });

    }

    function deleteOffice(url) {
        deletedata(url);
        loaddatatable();
        $("#myModal").modal('hide');
    }

    //SUBMIT FORM
    $("#officeform").submit(function (event) {
// Prevent the form from submitting via the browser.

        event.preventDefault();
        let formData = {
            "id": $('#id').val(),
            "roCode": $("#roCode").val(),
            "name": $("#name").val(),
            "address": $("#address").val(),
            "officeLevel": $("#officeLevel").val()
        };
        let url = "${pageContext.request.contextPath }/office";
        ajaxPost(url, formData);

    });

    //RELOAD ON CLOSE MODAL
    $("#myModal").on("hidden.bs.modal", function () {
        loaddatatable();
    });


</script>