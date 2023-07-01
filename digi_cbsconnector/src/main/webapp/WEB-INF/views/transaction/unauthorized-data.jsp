<%--
  Created by IntelliJ IDEA.
  User: shishir
  Date: 9/4/19
  Time: 10:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<ui:header/>
<section class="panel">
    <header class="panel-heading">
        <div class="panel-actions">
            <a href="#" class="fa fa-caret-down"></a>
        </div>

        <h2 class="panel-title"><a href="<c:url value="/transactions/create-form"/>">Unauthorized Transactions</a>
            &nbsp;&nbsp;&nbsp;<button
                    type="button" id="currentUserDataBtn" class="btn btn-primary">Your data
            </button>&nbsp;&nbsp;&nbsp;<button type="button" id="allUserDataBtn" class="btn btn-primary">All data
            </button>
        </h2>
    </header>
    <div class="panel-body">
        <table class="table table-bordered table-striped mb-none"
               id="transactionDatatable">
            <thead>
            <tr>
                <th>Soft. Transaction Id</th>
                <th>Amount</th>
                <th>Cr Account No</th>
                <th>Dr Account No</th>
                <th>Loan Account No</th>
                <th>Narrative</th>
                <th>Transaction Date</th>
                <th>Created By</th>
                <th>ACTION</th>
            </tr>
            </thead>
        </table>
    </div>
</section>
<ui:footer/>
<!-- Modal for transactions -->
<div id="transactionModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <form class="form-horizontal form-bordered" id="transctionform">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">TRANSACTION DETAIL</h4>
                </div>

                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="digiTransactionId">Soft. Transaction Id</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="digiTransactionId" name="digiTransactionId"
                                   readonly>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="drAccountNo">Debit Account Number</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="drAccountNo" name="drAccountNo" readonly>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="crAccountNo">Credit Account Number</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="crAccountNo" name="crAccountNo">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="loanAccountNumber">Loan Account Number</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="loanAccountNumber" name="loanAccountNumber">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="drNarrative">Narrative</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="drNarrative" name="drNarrative" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="amount">Amount</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="amount" name="amount">
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9 col-sm-offset-3">
                                <sec:authorize access="hasAnyRole('INPUTTER')">
                                    <a class="mb-xs mt-xs mr-xs modal-basic btn btn-primary" id="editBtn"><i
                                            class="fa fa-edit"></i> Edit</a>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('AUTHORIZER')">
                                    <a class="mb-xs mt-xs mr-xs modal-basic btn btn-success" id="authorizeBtn"><i
                                            class="fa fa-check-square"></i> Authorize</a>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('INPUTTER')">
                                    <a type="button" class="mb-xs mt-xs mr-xs modal-basic btn btn-danger"
                                       id="deleteBtn">Delete</a>
                                    <button type="button" class="btn btn-info"
                                            data-dismiss="modal">Close
                                    </button>
                                </sec:authorize>
                            </div>
                        </div>
                    </footer>
                </div>
            </form>
        </div>

    </div>
</div>
<ui:successFailureConfirmWarningInfo/>
<ui:footer/>
<script>
    $(document).ready(function () {
    	
        $('#transactionDatatable').DataTable();
        loadAllData();
    });

    function loadAllData() {
    	
        let url = "${pageContext.request.contextPath }/transactions/all/unauthorized";
        ajaxGetUrlCall(url);
    }

    //ON VIEW BUTTON CLICK
    function showOneTransaction(digiTransactionId) {
        let url = "${pageContext.request.contextPath }/transactions/" + digiTransactionId;
        let authorizeUrl = "${pageContext.request.contextPath }/transactions/authorize/" + digiTransactionId;
        $.get(url, function (data, status) {
            let json = data.data;
            $("#digiTransactionId").val(json.digiTransactionId);
            $("#amount").val(json.amount);
            $("#crAccountNo").val(json.crAccountNo);
            $("#drAccountNo").val(json.drAccountNo);
            $("#loanAccountNumber").val(json.loanAccountNumber);
            $("#drNarrative").val(json.drNarrative);
            $("#deleteBtn").attr("onclick", "deleteOneTransaction('" + url + "')");
            $("#authorizeBtn").attr("onclick", "authorizeOneTransaction('" + authorizeUrl + "')");
            $("#transactionModal").modal('show');
        })
            .done(function (data) {
            })
            .fail(function (jqxhr, settings, ex) {
                console.log('No Data Found for id=' + digiTransactionId);
            });

    }

    $("#editBtn").click(function () {
        let formData = {
            "digiTransactionId": $("#digiTransactionId").val(),
            "drAccountNo": $("#drAccountNo").val(),
            "crAccountNo": $("#crAccountNo").val(),
            "drNarrative": $("#drNarrative").val(),
            "crNarrative": "ERF LOAN",
            "amount": $("#amount").val(),
            "loanAccountNumber": $("#loanAccountNumber").val()
        };
        let url = "${pageContext.request.contextPath }/transactions";
        $.ajax({
            url: url,
            method: "post",
            data: JSON.stringify(formData),
            contentType: "application/json",
            success: function (data) {
                alert("Edit Successful");
                location.reload();
            },
            error: function (err) {
                alert("Edit Failure");
            },
            complete: function () {
            }
        });
        loadAllData();
    });

    function deleteOneTransaction(url) {
        deletedata(url);
        location.reload();
    }

    function authorizeOneTransaction(url) {
        $("#transactionModal").modal('hide');
        $.ajax({
            url: url,
            method: "post",
            contentType: "application/json",
            beforeSend: function () {
                $('.loading').css('z-index', '999');
            },
            success: function (data) {
                toastNotification('Success', data.message, 'success');
            },
            error: function (err) {
            	
	if (err.responseJSON.errors != null) {
					let errorMsg = err.responseJSON.errors[0];
					if (errorMsg != null || errorMsg != '') {
						toastNotification('Failure', errorMsg, 'error');
					}
				} else {
					toastNotification('Failure', "Authorization Failed.",
							'error');
				}
			},
			complete : function() {
				$('.loading').css('z-index', '-1');
			}
		});
		loadAllData();
	}

	$("#currentUserDataBtn")
			.click(
					function() {
						let url = "${pageContext.request.contextPath }/transactions/my/unauthorized";
						ajaxGetUrlCall(url);
					});
	$("#allUserDataBtn").click(function() {
		loadAllData();
	});

	//doWork will use the response data from ajaxUrlCall and perform action on it
	function doWork(data) {
		let json = data.data;
		let table = $('#transactionDatatable')
				.DataTable(
						{

							"data" : json,
							"columns" : [
									{
										data : "digiTransactionId",
										defaultContent : ""
									},
									{
										data : "amount",
										defaultContent : ""
									},
									{
										data : "crAccountNo",
										defaultContent : ""
									},
									{
										data : "drAccountNo",
										defaultContent : ""
									},
									{
										data : "loanAccountNumber",
										defaultContent : ""
									},
									{
										data : "drNarrative",
										defaultContent : ""
									},
									{
										data : "transactionDate",
										defaultContent : ""
									},
									{
										data : "createdBy",
										defaultContent : ""
									},
									{
										"data" : "Action",
										"orderable" : false,
										"searchable" : false,
										"render" : function(data, type, row,
												meta) { // render event defines the markup of the cell text
											let showOneTransaction = "showOneTransaction('"
													+ row.digiTransactionId
													+ "')";
											let printvoucher = "printvoucher('"
													+ row.digiTransactionId
													+ "')";
											let view = '<a class="modal-with-form btn btn-default input-sm" onclick="' + showOneTransaction + '"><i class="fa fa-edit1"></i>Edit/Authorize</a>';
											var view1 = '<a class="modal-with-form btn btn-default input-sm" href="${pageContext.request.contextPath }/reports/voucher?digiTransactionId='
													+ row.digiTransactionId
													+ '"><i class="fa fa-sear1"></i>PrintVoucher</a>';
											let a = view; // row object contains the row data
											let b = view1;
											return a + b;

										},

									} ],
							"destroy" : true
						});
	}

	function doWorkInFailure() {
		$('#transactionDatatable').DataTable();
	}

	function printvoucher(digiTransactionId) {
		let url = "${pageContext.request.contextPath }/reports/voucher?digiTransactionId="
				+ digiTransactionId;
		ajaxGetUrlCall(url);
	}
</script>