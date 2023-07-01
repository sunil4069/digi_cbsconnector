<%--
  Created by IntelliJ IDEA.
  User: shishir
  Date: 9/4/19
  Time: 10:37 PM
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

        <h2 class="panel-title">Download Transaction Details (Csv)</h2>
    </header>
    <div class="panel-body">
        <form class="form-horizontal form-bordered">
            <div class="form-group">
                <label class="col-md-3 control-label" for="dateFrom">Date From</label>
                <div class="col-md-6">
                    <input type="text" class="form-control" id="dateFrom" name="dateFrom">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 control-label" for="dateTo">Date To</label>
                <div class="col-md-6">
                    <input type="text" class="form-control" id="dateTo" name="dateTo">
                </div>
            </div>
            <br/>
            <footer class="panel-footer">
                <div class="row">
                    <div class="col-sm-9 col-sm-offset-3">
                        <button type="button" class="mb-xs mt-xs mr-xs modal-basic btn btn-primary"
                                id="searchBtn"><i class="fa fa-search"></i>Search
                        </button>
                        
                         <button type="button" class="mb-xs mt-xs mr-xs modal-basic btn btn-primary"
                                id="printBtn"><i class="fa fa-search"></i>Print
                        </button>
                        <button type="button" class="mb-xs mt-xs mr-xs modal-basic btn btn-primary"
                                id="vprintBtn"><i class="fa fa-search"></i>Voucher Print
                        </button>
                        
                        <a class="mb-xs mt-xs mr-xs modal-basic btn btn-primary"
                           id="downloadBtn"><i class="fa fa-download"></i> Download</a>
                    </div>
                </div>
            </footer>
        </form>
    </div>
</section>
<section class="panel">
    <header class="panel-heading">
        <%--        <div class="panel-actions">--%>
        <%--            <button type="button" class="mb-xs mt-xs mr-xs modal-basic btn btn-primary pull-right"--%>
        <%--                    id="todayTransactionBtn"><i class="fa fa-search"></i>Today's Transaction--%>
        <%--            </button>--%>
        <%--        </div>--%>

        <h2 class="panel-title">Transaction Details</h2>

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
                <th>Dr Narrative</th>
                <th>Cr Narrative</th>
                <th>Transaction Date</th>
                <th>Created By</th>
                <th>Authorized By</th>
            </tr>
            </thead>
        </table>
    </div>
</section>
<ui:footer/>
<script>

    $("#downloadBtn").click(function () {
        let dateFrom = $("#dateFrom").val();
        let dateTo = $("#dateTo").val();
        let url = "${pageContext.request.contextPath }/reports/transactions/downloads?dateFrom=" + dateFrom + "&dateTo=" + dateTo;
        window.open(url, "_blank");
        window.location.reload();
    });
    $("#searchBtn").click(function () {
        loadTransactionReport();
    });

    function loadTransactionReport() {
        let dateFrom = $("#dateFrom").val();
        let dateTo = $("#dateTo").val();
        let url = "${pageContext.request.contextPath }/reports/transactions?dateFrom=" + dateFrom + "&dateTo=" + dateTo;
        $.get(url, function (data, status) {
            $("#transactionDatatable").DataTable().clear().destroy();
            $('#transactionDatatable').DataTable({
                "data": data.data,
                "columns": [
                    {data: "digiTransactionId", defaultContent: ""},
                    {data: "amount", defaultContent: ""},
                    {data: "crAccountNo", defaultContent: ""},
                    {data: "drAccountNo", defaultContent: ""},
                    {data: "drNarrative", defaultContent: ""},
                    {data: "crNarrative", defaultContent: ""},
                    {data: "transactionDate", defaultContent: ""},
                    {data: "createdBy", defaultContent: ""},
                    {data: "authorizedBy", defaultContent: ""}
                ],
                "destroy": true
            });
        });
    }
    
    $("#printBtn").click(function () {
        let dateFrom = $("#dateFrom").val();
        let dateTo = $("#dateTo").val();
        let url = "${pageContext.request.contextPath }/reports/jaspers/transactions?dateFrom=" + dateFrom + "&dateTo=" + dateTo;
        window.open(url, "_blank");
     //  window.location.reload();
    }); 
    
    $("#vprintBtn").click(function () {
        let dateFrom = $("#dateFrom").val();
     //   let dateTo = $("#dateTo").val();
        let url = "${pageContext.request.contextPath }/reports/jaspers/vouchertransactions?dateFrom=" + dateFrom ;
        window.open(url, "_blank");
     //  window.location.reload();
    }); 
    
    
    
</script>
