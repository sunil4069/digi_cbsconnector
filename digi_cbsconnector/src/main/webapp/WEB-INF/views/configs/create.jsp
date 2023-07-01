<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ui:header/>
<div class="row">
    <div class="col-md-12">
        <div class="tabs">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#masterAccounts" data-toggle="tab">Master Accounts</a></li>
                <li><a href="#telnetConfig" data-toggle="tab">Telnet Config</a></li>
            </ul>
            <div class="tab-content">
                <div id="masterAccounts" class="tab-pane active">
                    <jsp:include page="masterAccounts.jsp"/>
                </div>
                <div id="telnetConfig" class="tab-pane">
                    <jsp:include page="telnetConfig.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
<ui:footer/>
<script>
    $(document).ready(function () {
        loadAdminConfig();
        loadMasterAccounts();
    })
    $("#saveAdminConfigBtn").click(function () {
        let formData = {
            "telnetUsername": $("[name='telnetUsername']").val(),
            "telnetPassword": $("[name='telnetPassword']").val(),
            "telnetIp": $("[name='telnetIp']").val(),
            "telnetPort": $("[name='telnetPort']").val(),
            "fundTransferCommand": $("#fundTransferCommand").val(),
            "miniStatementCommand": $("#miniStatementCommand").val(),
            "statementCommand": $("#statementCommand").val()
        }
        let url = "${pageContext.request.contextPath }/admins/configs";
        ajaxPost(url, formData);
    });

    function loadAdminConfig() {
        let url = "${pageContext.request.contextPath }/admins/configs";
        $.get(url, function (data) {
            if (data.statusCode === '200') {
                let json = data.data;
                $("[name='telnetUsername']").val(json.telnetUsername);
                $("[name='telnetIp']").val(json.telnetIp);
                $("[name='telnetPort']").val(json.telnetPort);
                $("#fundTransferCommand").val(json.fundTransferCommand);
                $("#miniStatementCommand").val(json.miniStatementCommand);
                $("#statementCommand").val(json.statementCommand);
            }
        });
    }

    // MASTER ACCOUNTS
    $("#saveMasterAccountsBtn").click(function () {
        let formData = {
            "accountName": $("[name='accountName']").val(),
            "accountNumber": $("[name='accountNumber']").val(),
            "accountType": $("[name='accountType']").val()
        }
        let refStaffCode = $("[name='refStaffCode']").val();
        if (refStaffCode.length > 0) {
            formData['refStaffCode'] = refStaffCode;
        }
        let url = "${pageContext.request.contextPath }/admins/master/accounts";
        ajaxPost(url, formData);
    });

    function loadMasterAccounts(){
        let url = "${pageContext.request.contextPath }/admins/master/accounts";
        $.get( url, function( data ) {
            $('#masterAccountsTable').DataTable({
                "data": data.data,
                "columns": [
                    {data: "accountNumber", defaultContent: ""},
                    {data: "accountName", defaultContent: ""},
                    {data: "accountType", defaultContent: ""},
                    {data: "refStaffCode", defaultContent: ""},
                ],
                "destroy": true
            });
        });
    }

</script>