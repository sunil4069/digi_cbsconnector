<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<ui:header/>
<section class="panel">
    <div class="panel-heading">
        <div class="panel-actions">
            <a href="#" class="fa fa-caret-down"></a>
        </div>

        <h2 class="panel-title">Search Mini-Statement</h2>
        <br>
    </div>
    <div class="panel-body">
        <div class="form-group">
            <div class="col-md-8">
                <form class="form-horizontal form-bordered"
                      id="searchStatementForm">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="accountnumber">
                            Account Number</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="accountnumber"
                                   name="accountnumber">
                        </div>
                    </div>
                    <br/>
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9 col-sm-offset-3">
                                <a class="mb-xs mt-xs mr-xs modal-basic btn btn-primary"
                                   data-toggle="modal" data-target="#confirmModal"><i class="fa fa-search"
                                                                                      aria-hidden="true"></i> Search</a>
                                <a class="mb-xs mt-xs mr-xs modal-basic btn btn-primary" id="export"><i
                                        class="fa fa-download" aria-hidden="true"></i> Export</a>
                            </div>
                        </div>
                    </footer>
                </form>
            </div>
        </div>

    </div>
</section>
<section class="panel">
    <header class="panel-heading">
        <div class="panel-actions">
            <a href="#" class="fa fa-caret-down"></a>
        </div>

        <h2 class="panel-title">Mini Statement Table</h2>
    </header>
    <div class="panel-body">
        <table class="table table-bordered table-striped mb-none"
               id="statementTable">
            <thead>
            <tr>
                <th>Booking Date</th>
                <th>Transaction Id</th>
                <th>Transaction Type</th>
                <th>Cheque No</th>
                <th>Amount</th>
                <th>Narrative</th>
            </tr>
            </thead>
        </table>
    </div>
</section>
<ui:confirmModal/>
<ui:failureResponseModal/>
<ui:footer/>
<script>
    let $table = $('#statementTable');
    $table.dataTable({
        sDom: "<'text-right mb-md'T>" + $.fn.dataTable.defaults.sDom,
        oTableTools: {
            sSwfPath: $table.data('swf-path'),

            aButtons: [

                {
                    sExtends: 'xls',
                    sButtonText: 'Excel',
                },


                {
                    sExtends: 'print',
                    sButtonText: 'Print',
                    sInfo: 'Please press CTR+P to print or ESC to quit'

                }
            ]
        }
    });
    $("#confirmBtn").click(function () {
        loadDataTable();
    });

    function loadDataTable() {
        let accountNumber = $("#accountnumber").val();
        let dateFrom = $("#datefrom").val();
        let dateTo = $("#dateto").val();
        let statementType = "MINI_STATEMENT";
        let param = "?accountNumber=" + accountNumber + "&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&statementTypeEnum=" + statementType;
        let url = "${pageContext.request.contextPath }/statements/telnetStatement" + param;
        let json = ajaxGetUrlCall(url);
    }

    function doWork(data) {

        $table.dataTable({
            sDom: "<'text-right mb-md'T>" + $.fn.dataTable.defaults.sDom,
            oTableTools: {
                sSwfPath: $table.data('swf-path'),
                aButtons: [
                    {
                        sExtends: 'xls',
                        sButtonText: 'Excel'
                    },
                    {
                        sExtends: 'print',
                        sButtonText: 'Print',
                        sInfo: 'Please press CTR+P to print or ESC to quit'
                    }
                ]
            },
            "data": data.data,
            "columns": [
                {data: "bookingDateEn"},
                {data: "transactionId"},
                {data: "transactionType"},
                {data: "chequeNo"},
                {data: "amount"},
                {data: "narrative"},
            ],
            "destroy": true
        });
    }

    $("#export").click(function () {
        let accountNumber = $("#accountnumber").val();
        let dateFrom = $("#datefrom").val();
        let dateTo = $("#dateto").val();
        let statementType = "MINI_STATEMENT";
        let param = "?accountNumber=" + accountNumber + "&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&statementTypeEnum=" + statementType;
        let url = "${pageContext.request.contextPath }/reports/statements/downloads" + param;
        window.open(url, "_blank");
        window.location.reload();
    });
</script>