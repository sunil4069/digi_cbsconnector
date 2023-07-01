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

                <h2 class="panel-title">Enter Staff Details</h2>
            </header>
            <div class="panel-body">

                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="code"> Code</label>
                            <input type="text" class="form-control" id="code" name="code">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="fname">First
                                Name</label>
                            <input type="text" class="form-control" id="fname" name="fname">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="lname">Last
                                Name</label>
                            <input type="text" class="form-control" id="lname" name="lname">
                        </div>
                    </div>

                </div>
                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="gender">Gender</label>
                            <select class="form-control" id="gender" name="gender">
                                <option value="">Select Gender</option>
                                <option value="m">Male</option>
                                <option value="f">Female</option>
                                <option value="o">Other</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="phone">
                                Phone Number
                            </label>
                            <input type="text" class="form-control" id="phone" name="phone">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="post">
                                Post
                            </label>
                            <input type="text" class="form-control" id="post" name="post">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="office">Office</label>
                            <select class="form-control" id="office" name="office">
                                <option value="1">Select Office</option>
                                <c:forEach items="${offices }" var="o">
                                    <option value="${o.id }">${o.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

                <br/>
                <footer class="panel-footer">
                    <div class="row">
                        <div class="col-sm-9 ">
                            <button type="button" id="saveStaffBtn" class="btn btn-primary">Save
                                Staffs
                            </button>
                            <button type="reset" class="btn btn-default">Reset</button>
                        </div>
                    </div>
                </footer>
            </div>
        </section>
    </div>
</div>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <div class="panel-body">
                <form id="imgform">
                    <div class="row">
                        <div class="col-md-4">
                            <label class="control-label" for="pic">Upload Photo</label>
                            <input type="file" class="form-control" id="pic" name="pic">
                        </div>
                    </div>
                    <br>
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9 ">
                                <button type="submit" class="btn btn-primary">Upload</button>
                            </div>
                        </div>
                    </footer>
                </form>
            </div>
        </section>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading">
                <div class="panel-actions">
                    <a href="#" class="fa fa-caret-down"></a>
                </div>

                <h2 class="panel-title">Enter Staffs Family Details</h2>
            </header>
            <div class="panel-body">
                <input type="hidden" id="fid" value="0">
                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="ffname">
                                First Name
                            </label>
                            <input type="text" class="form-control" id="ffname" name="ffname">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="flname">
                                Last Name
                            </label>
                            <input type="text" class="form-control" id="flname" name="flname">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="fgender">Gender</label>
                            <select class="form-control" id="fgender" name="fgender">
                                <option value="">Select Gender</option>
                                <option value="m">Male</option>
                                <option value="f">Female</option>
                                <option value="o">Other</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="fphone">
                                Phone Number
                            </label>
                            <input type="text" class="form-control" id="fphone" name="fphone">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="foccupation">
                                Occupation
                            </label>
                            <input type="text" class="form-control" id="foccupation" name="foccupation">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="relation">
                                Relation
                            </label>
                            <select class="form-control" id="relation" name="relation">
                                <option value="">Select Relation</option>
                                <option value="father">Father</option>
                                <option value="mother">Mother</option>
                                <option value="sibling">Sibling</option>
                                <option value="grand father">Grand Father</option>
                                <option value="grand mother">Grand Mother</option>
                                <option value="spouse">Spouse</option>
                            </select>
                        </div>
                    </div>
                </div>
                <br/>
                <footer class="panel-footer">
                    <div class="row">
                        <div class="col-sm-9 ">
                            <button type="button" id="saveFamilyBtn" class="btn btn-primary">Save
                                Family
                            </button>
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
<!-- manage script to save values -->
<script>
    $(document).ready(function () {

        // SUBMIT First FORM
        $("#saveStaffBtn").click(function (event) {
            let formData = {
                "code": $('#code').val(),
                "firstName": $("#fname").val(),
                "lastName": $("#lname").val(),
                "gender": $("#gender").val(),
                "phoneNumber": $("#phone").val(),
                "post": $("#post").val(),
                "office": {
                    "id": $("#office").val()
                }
            };
            let url = "${pageContext.request.contextPath }/staffs";
            ajaxPost(url, formData);
        });

        //submit second form
        $("#saveFamilyBtn").click(function (event) {
            let formData = {
                "id": $('#id').val(),
                "firstName": $('#ffname').val(),
                "lastName": $('#flname').val(),
                "gender": $("#fgender").val(),
                "occupation": $('#foccupation').val(),
                "phoneNumber": $('#fphone').val(),
                "relation": $('#relation').val(),
                "staffs": {
                    "code": $('#code').val()
                }
            };
            let url = "${pageContext.request.contextPath }/staffsFamily";
            ajaxPost(url, formData);
        });
    });

    //handle image upload separately
    //change file to base64 and send JSON
    $("#imgform").submit(function (event) {
        event.preventDefault();
        let data = new FormData();
        let json = {
            "code": $('#code').val()
        };
        let jsonData = JSON.stringify(json);
        data.append("file", $("input[name=pic]")[0].files[0]);
        data.append("jsondata", jsonData);
        let url = "${pageContext.request.contextPath }/staffs/image";
        imagePost(url, data);
    });
</script>