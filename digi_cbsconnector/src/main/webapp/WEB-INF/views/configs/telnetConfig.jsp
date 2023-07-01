<%--
  Created by IntelliJ IDEA.
  User: shishir
  Date: 4/12/20
  Time: 1:58 PM
  To change this template use File | Settings | File Templates.
--%>
<section class="panel">
    <header class="panel-heading">
        <h2 class="panel-title">Telnet Config Details</h2>
    </header>
    <div class="panel-body">
        <div class="row">
            <div class="col-sm-3">
                <div class="form-group">
                    <label class="control-label">Telnet Username</label>
                    <input type="text" name="telnetUsername" class="form-control">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="form-group">
                    <label class="control-label">Telnet Password</label>
                    <input type="password" name="telnetPassword" class="form-control">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="form-group">
                    <label class="control-label">Telnet Ip</label>
                    <input type="text" name="telnetIp" class="form-control">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="form-group">
                    <label class="control-label">Telnet Port</label>
                    <input type="text" name="telnetPort" class="form-control">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="control-label">Fund Transfer Command</label>
                    <textarea class="form-control" rows="4" id="fundTransferCommand"></textarea>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="control-label">Mini Statement Command</label>
                    <textarea class="form-control" rows="4" id="miniStatementCommand"></textarea>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="control-label">Statement Command</label>
                    <textarea class="form-control" rows="4" id="statementCommand"></textarea>
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
                        <button class="btn btn-success" id="saveAdminConfigBtn">Submit Data</button>
                    </div>
                </div>
            </footer>
        </section>
    </div>
</div>