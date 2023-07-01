<%--
  Created by IntelliJ IDEA.
  User: shishir
  Date: 4/12/20
  Time: 1:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ui:header/>
<section class="panel">
    <header class="panel-heading">
        <h2 class="panel-title">PF Loan Accounts Details</h2>
    </header>
    <div class="panel-body">
        <div class="row">
            <input type="hidden" id="id" value="0" name="id">
            <div class="col-sm-4">
                <div class="form-group">
                    <label class="control-label">Account Name</label>
                    <input type="text" name="accountName" class="form-control">
                </div>
            </div>
            <div class="col-sm-4">
                <div class="form-group">
                    <label class="control-label">Account Number</label>
                    <input type="text" name="accountNumber" class="form-control">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4">
                <div class="form-group">
                    <label class="control-label">Staff Code</label>
                    <input type="text" name="refStaffCode" class="form-control">
                </div>
            </div>
        </div>
    </div>
</section>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <footer class="panel-footer">
                <div class="row">
                    <div class="col-md-6"></div>
                    <div class="col-md-6">
                        <button class="btn btn-info" id="masterAccountsResetBtn">Reset Data</button>
                        <button class="btn btn-primary">Validate</button>
                        <button class="btn btn-success" id="saveMasterAccountsBtn">Submit Data</button>
                    </div>
                </div>
            </footer>
        </section>
    </div>
</div>
<section class="panel">
    <header class="panel-heading">
        <h2 class="panel-title">PF Loan Accounts Details</h2>
    </header>
    <div class="panel-body">
        <table class="table table-bordered table-striped mb-none"
               id="masterAccountsTable">
            <thead>
            <tr>
                <th>Account Number</th>
                <th>Account Name</th>
                <th>Staff Code</th>
                <th>Action</th>
            </tr>
            </thead>
        </table>
    </div>
</section>
<ui:footer/>
<script>
    $(document).ready(function () {
        loadMasterAccounts();
    })
    $("#saveMasterAccountsBtn").click(function () {
        let formData = {
            "accountName": $("[name='accountName']").val(),
            "accountNumber": $("[name='accountNumber']").val()
        }
        let refStaffCode = $("[name='refStaffCode']").val();
        if (refStaffCode.length > 0) {
            formData['refStaffCode'] = refStaffCode;
        }
        let id = $("[name='id']").val();
        if (id > 0) {
            formData['id'] = id;
        }
        let url = "${pageContext.request.contextPath }/master/accounts/pfloans";
        ajaxPost(url, formData);
        $("#masterAccountsTable").DataTable().clear();
        loadMasterAccounts();
    });

    function loadMasterAccounts() {
        let url = "${pageContext.request.contextPath }/master/accounts/pfloans";
        $.get(url, function (data) {
            $('#masterAccountsTable').DataTable({
                "data": data.data,
                "columns": [
                    {data: "accountNumber", defaultContent: ""},
                    {data: "accountName", defaultContent: ""},
                    {data: "refStaffCode", defaultContent: ""},
                    {
                        "data": "Action",
                        "orderable": false,
                        "searchable": false,
                        "render": function (data, type, row, meta) { // render event defines the markup of the cell text
                            let edit = '<a class="modal-with-form btn btn-primary btn-sm" onclick="edit(' + row.id + ')"><i class="fa fa-edit"></i></a>';
                            return edit;

                        },

                    }
                ],
                "destroy": true
            });
        });
    }

    $("#masterAccountsResetBtn").click(function () {
        $("[name='id']").val(0);
        $("[name='accountName']").val("");
        $("[name='accountNumber']").val("");
        $("[name='refStaffCode']").val("");
    })
    function edit(id){
        let url ="${pageContext.request.contextPath }/master/accounts/pfloans/"+id;
        $.get(url, function (data) {
          let json=data.data;
            $("[name='id']").val(json.id);
            $("[name='accountName']").val(json.accountName);
            $("[name='accountNumber']").val(json.accountNumber);
            $("[name='refStaffCode']").val(json.refStaffCode);
        });
    }
</script>