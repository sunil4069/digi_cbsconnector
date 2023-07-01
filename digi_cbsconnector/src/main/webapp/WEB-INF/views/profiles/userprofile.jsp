<%@ taglib prefix="ui" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ui:header />
<!-- start: page -->

<div class="row">
	<div class="col-md-4 col-lg-3">

		<section class="panel">
			<div class="panel-body">
				<div class="thumb-info mb-md">
					<!-- users image -->
					<img
						src="" id="profileimage"
						class="rounded img-responsive" alt="${cuser.staffs.firstName }">
					<br>
					<br>
					<div class="thumb-info-title">
						<span class="thumb-info-inner name">
						</span> <span class="thumb-info-type post"></span>
					</div>
				</div>

				<hr class="dotted short">

				<h6 class="text-muted">ADBL</h6>
				<p>Agricultural Development Bank Ltd.</p>
				<div class="clearfix">
					<a class="text-uppercase text-muted pull-right" href="#">(View
						All)</a>
				</div>

				<hr class="dotted short">

				<div class="social-icons-list">
					<a rel="tooltip" data-placement="bottom" target="_blank"
						href="http://www.facebook.com" data-original-title="Facebook"><i
						class="fa fa-facebook"></i><span>Facebook</span></a> <a rel="tooltip"
						data-placement="bottom" href="http://www.twitter.com"
						data-original-title="Twitter"><i class="fa fa-twitter"></i><span>Twitter</span></a>
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
			</ul>
			<div class="tab-content">
				<div id="overview" class="tab-pane active">
					<h3 class="mb-md">Basic Information</h3>

					<section>
						<table class="table table-hover">
							<tr>
								<th>Name</th>
								<td class="name"></td>
							</tr>
							<tr>
								<th>Gender</th>
								<td id="gender">
								</td>
							</tr>
							<tr>
								<th>Phone Number</th>
								<td id="phone">
								</td>
							</tr>
							<tr>
								<th>Post</th>
								<td class="post">
								</td>
							</tr>

						</table>

					</section>

				</div>
			</div>
		</div>
	</div>

</div>
<!-- end: page -->
<ui:footer />
<script>
$(document).ready(function(){
	var code='${code}';
	var url="${pageContext.request.contextPath }/userprofiles/"+code;
	$.ajax({
		url:url,
		method:"get",
		success: function (data){
			var staffs=data.data;
			$(".name").html(staffs.firstName+" "+staffs.lastName);
			$("#gender").html(staffs.gender);
			$("#phone").html(staffs.phoneNumber);
			$(".post").html(staffs.post);
			$("#profileimage").attr("src","data:image/jpeg;base64,"+staffs.base64pic+"");
	    },
	    error: function (err){
	    	alert(err.status);    
	    }
	});
});

</script>