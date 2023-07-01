<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<ui:header/>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading">
                <div class="panel-actions">
                    <a href="#" class="fa fa-caret-down"></a>
                </div>

                <h2 class="panel-title">Enter Office Details</h2>
            </header>
            <div class="panel-body">
                <form class="form-horizontal form-bordered" method="post" name="officeform" id="officeform">
                    <input type="hidden" id="id" value="0">
                    <div class="row">
                        <div class="col-md-4">
                            <label class="control-label" for="ro_code">RO
                                Code</label>
                            <input type="text" class="form-control" id="ro_code" name="ro_code">
                        </div>

                        <div class="col-md-4">
                            <label class="control-label" for="name">Name</label>
                            <input type="text" class="form-control" id="name" name="name">
                        </div>

                        <div class="col-md-4">
                            <label class="control-label" for="address">Address</label>
                            <input type="text" class="form-control" id="address" name="address">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label class="control-label" for="officeLevel">Office
                                Level</label>
                            <select class="form-control input-sm mb-md" name="officeLevel" id="officeLevel">
                                <option value="ho">Head</option>
                                <option value="ro">Regional</option>
                                <option value="br">Branch</option>
                            </select>
                        </div>
                    </div>
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9">
                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="reset" class="btn btn-default">Reset</button>
                            </div>
                        </div>
                    </footer>


                </form>
            </div>
        </section>
    </div>
</div>
<ui:footer/>
<script>
    $(document).ready(function () {

        // SUBMIT FORM
        $("#officeform").submit(function (event) {
            // Prevent the form from submitting via the browser.

            event.preventDefault();
            var formData = {
                "id": $('#id').val(),
                "roCode": $("#ro_code").val(),
                "name": $("#name").val(),
                "address": $("#address").val(),
                "officeLevel": $("#officeLevel").val()
            };
            var url = "${pageContext.request.contextPath }/office";
            ajaxPost(url, formData);
        });

    });
</script>