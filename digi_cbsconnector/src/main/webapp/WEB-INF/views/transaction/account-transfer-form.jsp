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
                            <select class="form-control" id="debitaccountnumber" name="debitaccountnumber">
                                <option value="">Select Account Number</option>
                                <c:forEach items="${masterAccounts}" var="m">
                                    <option value="${m.accountNumber}">${m.accountNumber}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="creditaccountnumber">Credit Account Number</label>
                        <div class="col-md-6">
                            <select class="form-control" id="creditaccountnumber" name="creditaccountnumber">
                                <option value="">Select Account Number</option>
                                <c:forEach items="${masterAccounts}" var="m">
                                    <option value="${m.accountNumber}">${m.accountNumber}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="drNarrative">Narrative</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="drNarrative" name="drNarrative">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="amount">Amount</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="amount" name="amount">
                        </div>
                    </div>
                    <br/>
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9 col-sm-offset-3">
                                <a class="mb-xs mt-xs mr-xs modal-basic btn btn-primary"
                                   data-toggle="modal" data-target="#confirmModal">Save Transaction</a>
                                <button type="reset" id="transactionFormResetBtn" class="btn btn-default">Reset</button>
                            </div>
                        </div>
                    </footer>
                </form>
            </div>
        </section>
    </div>
</div>
<ui:successFailureConfirmWarningInfo/>
<ui:footer/>
<script>
    $(document).ready(function () {

        // SUBMIT Transaction FORM
        $("#confirmBtn").click(function () {
            // Prevent the form from submitting via the browser.
            let formData = {
                "drAccountNo": $("#debitaccountnumber").val(),
                "crAccountNo": $("#creditaccountnumber").val(),
                "drNarrative": $("#drNarrative").val(),
                "amount": $("#amount").val()
            };
            let url = "${pageContext.request.contextPath }/accounts/transfers";
            ajaxPost(url, formData);
            $("#transactionFormResetBtn").trigger('click');
        });
    });
</script>
