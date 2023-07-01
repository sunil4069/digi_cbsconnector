<%--
  Created by IntelliJ IDEA.
  User: shishir
  Date: 9/4/19
  Time: 10:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<ui:header/>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading">
                <div class="panel-actions">
                    <a href="#" class="fa fa-caret-down"></a>
                </div>

                <h2 class="panel-title">Enter Transaction Details</h2>
            </header>
            <div class="panel-body">
                <form class="form-horizontal form-bordered">


                    <div class="form-group">
                        <label class="col-md-3 control-label" for="debitaccountnumber">Debit Account Number</label>
                        <div class="col-md-6">
                            <input type="text" readonly class="form-control" id="debitaccountnumber"
                                   name="debitaccountnumber" value="${debitAccountNumber}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="creditaccountnumber">Saving Account Number</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="creditaccountnumber" name="creditaccountnumber">
                        </div>
                        <div class="col-md-3" id="creditAccountName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="loanAccountNumber">Loan Account Number</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="loanAccountNumber" name="loanAccountNumber">
                        </div>
                        <div class="col-md-3" id="loanAccountName">
                        </div>
                    </div>

                   <!--  <div class="form-group">
                        <label class="col-md-3 control-label" for="drNarrative">Narrative</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="drNarrative" name="drNarrative">
                        </div>
                    </div> -->
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="amount">Amount</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="amount" name="amount" value="0.00">
                        </div>
                    </div>
                    <br/>
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9 col-sm-offset-3">
                                <a class="mb-xs mt-xs mr-xs modal-basic btn btn-success"
                                   data-toggle="modal" data-target="#confirmModal">Save Transaction</a>
                                <button type="button" id="transactionFormValidateBtn" class="btn btn-info">Validate
                                </button>
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

        <h2 class="panel-title">Payment Requests
        </h2>
    </header>
    <div class="panel-body">
        <table class="table table-bordered table-striped mb-none"
               id="paymentRequestsDatatable">
            <thead>
            <tr>
                <th>.</th>
                <th>Tr No</th>
                <th>Day Tr No</th>
                <th>Tr Date(A.D.)</th>
                <th>Tr Date(B.S.)</th>
                <th>Staff Name</th>
                <th>Pay Account No</th>
                <th>Saving Account No</th>
                <th>Payment Amount</th>
                <th>Pay Type</th>
            </tr>
            </thead>
        </table>
    </div>
</section>
<ui:successFailureConfirmWarningInfo/>
<ui:footer/>
<script>
    $(document).ready(function () {

        // SUBMIT Transaction FORM
        $("#confirmBtn").click(function () {
            // Prevent the form from submitting via the browser.
            var str="ERF-";
            var str1=$("#loanAccountNumber").val();
            var str2=str.concat(str1);
            	
            let formData = {
                "drAccountNo": $("#debitaccountnumber").val(),
                "crAccountNo": $("#creditaccountnumber").val(),
                "drNarrative": str2,
                "crNarrative": "ERF LOAN",
                
                "amount": $("#amount").val(),
                "loanAccountNumber": $("#loanAccountNumber").val()
            };
            let url = "${pageContext.request.contextPath }/transactions";
            ajaxPost(url, formData);
            $("#loanAccountName").html("");
        });
    });
    $("#transactionFormValidateBtn").click(function () {
        getCreditAccountName();
        getLoanAccountName();
    });

    function getLoanAccountName() {
        let accountNumber = $("#loanAccountNumber").val();
        let url = "${pageContext.request.contextPath }/master/accounts/" + accountNumber;
        $.ajax({
            url: url,
            method: "get",
            contentType: "application/json",
            success: function (data) {
                let json = data.data;
                $("#loanAccountName").html(json.accountName);
            },
            error: function (err) {
                $("#loanAccountName").html("");
                toastNotification('Failure', "Loan Ac name not found.", 'error');
            }
        });
    }

    function getCreditAccountName() {
        let accountNumber = $("#creditaccountnumber").val();
        let url = "${pageContext.request.contextPath }/master/accounts/" + accountNumber;
        $.ajax({
            url: url,
            method: "get",
            contentType: "application/json",
            success: function (data) {
                let json = data.data;
                $("#creditAccountName").html(json.accountName);
            },
            error: function (err) {
                $("#creditAccountName").html("");
                toastNotification('Failure', "Cr Ac name not found.", 'error');
            }
        });
    }

    $.ajax({
        url: "${pageContext.request.contextPath}/paymentrequests",
        method: "get",
        contentType: "application/json",
        success: function (data) {
            $('#paymentRequestsDatatable').DataTable({
                "data": data.data,
                "columns": [
                    {
                        "data": "Action",
                        "orderable": false,
                        "searchable": false,
                        "render": function (data, type, row, meta) { // render event defines the markup of the cell text
                            let showOnePaymentRequest = "showOnePaymentRequest('" + row.id + "')";
                            let view = '<a class="modal-with-form btn btn-default input-sm btn-info" onclick="' + showOnePaymentRequest + '"><i class="fa fa-check"></i></a>';
                            return view;
                        },

                    },
                    {data: "transactionNumber", defaultContent: ""},
                    {data: "dayTransactionNumber", defaultContent: ""},
                    {data: "trDateEn", defaultContent: ""},
                    {data: "trDateNp", defaultContent: ""},
                    {data: "staffName", defaultContent: ""},
                    {data: "payAccountNumber", defaultContent: ""},
                    {data: "savingAccountNumber", defaultContent: ""},
                    {data: "paymentAmount", defaultContent: ""},
                    {data: "payType", defaultContent: ""}
                ],
                "destroy": true
            });
        },
        error: function (err) {
            $('#paymentRequestsDatatable').DataTable();
        }
    });

    function showOnePaymentRequest(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/paymentrequests/" + id,
            method: "get",
            contentType: "application/json",
            success: function (data) {
                let json = data.data;
                $("#creditaccountnumber").val(json.savingAccountNumber);
                $("#loanAccountNumber").val(json.payAccountNumber);
                $("#amount").val(json.paymentAmount);
                toastNotification('Success', "Payment Request Selected", 'success');
            },
            error: function (err) {
                $("#creditaccountnumber").val("");
                $("#loanAccountNumber").val("");
                $("#amount").val("");
                toastNotification('Failure', "Payment Request Error", 'error');
            }
        });
    }
</script>
