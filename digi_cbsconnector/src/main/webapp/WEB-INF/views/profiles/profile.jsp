<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ui:header/>
<!-- start: page -->

<div class="row">
    <div class="col-md-4 col-lg-3">

        <section class="panel">
            <div class="panel-body">
                <div class="thumb-info mb-md">
                    <!-- users image -->
                    <img
                            src="data:image/jpeg;base64,${profileimage}" id="profileimage"
                            class="rounded img-responsive" alt="${cuser.staffs.firstName }">
                    <br>
                    <br>
                    <div class="thumb-info-title">
						<span class="thumb-info-inner">${cuser.staffs.firstName}
							&nbsp
							${cuser.staffs.lastName}
						</span> <span class="thumb-info-type">${cuser.staffs.post}</span>
                    </div>
                </div>

                <hr class="dotted short">

                <h6 class="text-muted">ADBL</h6>
                <p>Agricultural Development Bank Ltd.</p>
                <div class="clearfix">
                    <a class="text-uppercase text-muted pull-right"
                       href="${pageContext.request.contextPath }/userprofiles/colleagues-page">(COLLEAGUES)</a>
                </div>

                <hr class="dotted short">

                <div class="social-icons-list">
                    <a rel="tooltip" data-placement="bottom" target="_blank"
                       href="http://www.facebook.com" data-original-title="Facebook"><i
                            class="fa fa-facebook"></i><span>Facebook</span></a> <a rel="tooltip"
                                                                                    data-placement="bottom"
                                                                                    href="http://www.twitter.com"
                                                                                    data-original-title="Twitter"><i
                        class="fa fa-twitter"></i><span>Twitter</span></a>
                    <a rel="tooltip" data-placement="bottom"
                       href="http://www.linkedin.com" data-original-title="Linkedin"><i
                            class="fa fa-linkedin"></i><span>Linkedin</span></a>
                </div>

            </div>
        </section>


    </div>
    <div class="col-md-8 col-lg-6">

        <div class="tabs">
            <ul class="nav nav-tabs tabs-primary">
                <li class="active"><a href="#overview" data-toggle="tab">Overview</a>
                </li>
                <li><a href="#edit" data-toggle="tab">Edit</a></li>
            </ul>
            <div class="tab-content">
                <div id="overview" class="tab-pane active">
                    <h3 class="mb-md">Basic Information</h3>

                    <section>
                        <table class="table table-hover">
                            <tr>
                                <th>Username</th>
                                <td>${cuser.username}
                                </td>
                            </tr>
                            <tr>
                                <th>Name</th>
                                <td>${cuser.staffs.firstName}
                                    &nbsp
                                    ${cuser.staffs.lastName}</td>
                            </tr>
                            <tr>
                                <th>Gender</th>
                                <td>${cuser.staffs.gender}
                                </td>
                            </tr>
                            <tr>
                                <th>Phone Number</th>
                                <td>${cuser.staffs.phoneNumber}
                                </td>
                            </tr>
                            <tr>
                                <th>Post</th>
                                <td>${cuser.staffs.post}
                                </td>
                            </tr>

                        </table>

                    </section>

                </div>
                <div id="edit" class="tab-pane">

                    <form class="form-horizontal" method="get" id="profileform">
                        <h4 class="mb-xlg">Personal Information</h4>
                        <fieldset>
                            <div class="form-group">
                                <label class="col-md-3 control-label" for="profileFirstName">First
                                    Name</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="profileFirstName"
                                           value="${cuser.staffs.firstName }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label" for="profileLastName">Last
                                    Name</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="profileLastName"
                                           value="${cuser.staffs.lastName }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label" for="profilePhone">Phone
                                    Number</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="profilePhone"
                                           value="${cuser.staffs.phoneNumber }">
                                </div>
                            </div>
                        </fieldset>

                        <div class="panel-footer">
                            <div class="row">
                                <div class="col-md-9 col-md-offset-3">
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                    <button type="reset" class="btn btn-default">Reset</button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <!-- allow changing passwords here -->
                    <hr class="dotted tall">
                    <form class="form-horizontal" method="post" id="profilechangepass">
                        <h4 class="mb-xlg">Change Password</h4>
                        <fieldset class="mb-xl">
                            <div class="form-group">
                                <label class="col-md-3 control-label"
                                       for="password">Old Password</label>
                                <div class="col-md-8">
                                    <input type="password" class="form-control"
                                           id="password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label" for="profileNewPassword">New
                                    Password</label>
                                <div class="col-md-8">
                                    <input type="password" class="form-control" value="${staffdetail.staffs.code }"
                                           id="profileNewPassword">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"
                                       for="profileNewPasswordRepeat">Repeat New Password</label>
                                <div class="col-md-8">
                                    <input type="password" class="form-control"
                                           id="profileNewPasswordRepeat">
                                </div>
                            </div>
                        </fieldset>
                        <div class="panel-footer">
                            <div class="row">
                                <div class="col-md-9 col-md-offset-3">
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                    <button type="reset" class="btn btn-default">Reset</button>
                                </div>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- end: page -->
<ui:footer/>


<script>

    //SUBMIT FORM
    $("#profileform").submit(function (event) {
        // Prevent the form from submitting via the browser.

        event.preventDefault();
        var formData = {
            "firstName": $("#profileFirstName").val(),
            "lastName": $("#profileLastName").val(),
            "phoneNumber": $("#profilePhone").val()
        };
        var url = "${pageContext.request.contextPath }/userprofiles";
        ajaxPost(url, formData);

    });


    //SUBMIT FORM
    $("#profilechangepass").submit(function (event) {
        // Prevent the form from submitting via the browser.

        event.preventDefault();
        var formData = {
            "password": $("#password").val(),
            "newpassword": $("#profileNewPassword").val(),
            "confirmpassword": $("#profileNewPasswordRepeat").val()
        };
        var url = "${pageContext.request.contextPath }/userprofiles/change-password";
        ajaxPost(url, formData);

    });
</script>