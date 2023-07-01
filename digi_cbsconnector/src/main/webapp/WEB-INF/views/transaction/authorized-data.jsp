<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-05-02
  Time: 1:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<ui:header/>
<section class="panel">
    <header class="panel-heading">
        <div class="panel-actions">
            <a href="#" class="fa fa-caret-down"></a>
        </div>

        <h2 class="panel-title"><a href="<c:url value="/transactions/create-form"/>">Authorized Transactions</a> &nbsp;&nbsp;&nbsp;
        </h2>
    </header>
    <div class="panel-body">
        <table class="table table-bordered table-striped mb-none"
               id="transactionDatatable">
            <thead>
            <tr>
                <th>Soft. Transaction Id</th>
                <th>T24 Transaction Id</th>
                <th>Amount</th>
                <th>Cr Account No</th>
                <th>Dr Account No</th>
                <th>Loan Account No</th>
                <th>Narrative</th>
                <th>Transaction Date</th>
                <th>Created By</th>
                <th>Authorized By</th>
                <th>Action</th>
            </tr>
            </thead>
        </table>
    </div>
</section>
<ui:footer/>
<script>
    $(document).ready(function () {
        $('#transactionDatatable').DataTable();
        loadData();
    });
    $("#currentUserDataBtn").click(function () {
        let url = "${pageContext.request.contextPath }/transactions/my/authorized/";
        ajaxGetUrlCall(url);
    });

    function loadData() {
        let url = "${pageContext.request.contextPath }/transactions/all/authorized/";
        ajaxGetUrlCall(url);
    }

    //doWork will use the response data from ajaxUrlCall and perform action on it
    function doWork(data) {
        let json = data.data;
        if (json.length === 0) {
            $('#transactionDatatable').DataTable();
        } else {
            let table = $('#transactionDatatable').DataTable({
                "data": json,
                "columns": [
                    {data: "digiTransactionId", defaultContent: ""},
                    {data: "transactionId", defaultContent: ""},
                    {data: "amount", defaultContent: ""},
                    {data: "crAccountNo", defaultContent: ""},
                    {data: "drAccountNo", defaultContent: ""},
                    {data: "loanAccountNumber", defaultContent: ""},
                    {data: "drNarrative", defaultContent: ""},
                    {data: "transactionDate", defaultContent: ""},
                    {data: "createdBy", defaultContent: ""},
                    {data: "authorizedBy", defaultContent: ""},
                    
                    {
                        "data": "Action",
                        "orderable": false,
                        "searchable": false,
                        "render": function (data, type, row, meta) { // render event defines the markup of the cell text
                           
                            var view = '<a class="modal-with-form btn btn-default input-sm" href="${pageContext.request.contextPath }/reports/voucher?digiTransactionId='+row.digiTransactionId+'"><i class="fa fa-search1"></i>Print</a>'; 
                            let a = view; // row object contains the row data
                          
                            return a;

                        },

                    }
                    
                    
                ],
                "destroy": true
            });
        }
    }

    function doWorkInFailure() {
        $('#transactionDatatable').DataTable();
    }
</script>
