<%--
  Created by IntelliJ IDEA.
  User: shishir
  Date: 4/12/20
  Time: 1:58 PM
  To change this template use File | Settings | File Templates.
--%>
<section class="panel">
    <header class="panel-heading">
        <h2 class="panel-title">Master Accounts Details</h2>
    </header>
    <div class="panel-body">
        <div class="row">
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
            <div class="col-sm-4">
                <div class="form-group">
                    <label class="control-label">Account Type</label>
                    <select name="accountType" class="form-control">
                        <option value="CURRENT">CURRENT</option>
                        <option value="CALL">CALL</option>
                        <option value="LOAN">LOAN</option>
                    </select>
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
                        <button class="btn btn-info">Reset Data</button>
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
        <h2 class="panel-title">Master Accounts Details</h2>
    </header>
    <div class="panel-body">
        <table class="table table-bordered table-striped mb-none"
               id="masterAccountsTable">
            <thead>
            <tr>
                <th>Account Number</th>
                <th>Account Name</th>
                <th>Account Type</th>
                <th>Staff Code</th>
            </tr>
            </thead>
        </table>
    </div>
</section>