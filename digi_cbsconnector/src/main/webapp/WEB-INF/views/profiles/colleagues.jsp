<%@ taglib prefix="ui" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<ui:header />

<div class="row">
	<div class="col-md-12">
		<section class="panel">
			<header class="panel-heading">
				<div class="panel-actions">
					<a href="#" class="fa fa-caret-down"></a>
				</div>

				<h2 class="panel-title">Colleagues Details</h2>
			</header>
			<div class="panel-body">
				<table class="table table-bordered table-striped mb-none"
					id="staffTable">
					<thead>
						<tr>
							<th>Code</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Action</th>
						</tr>
					</thead>
				</table>
			</div>
		</section>
	</div>

</div>


<ui:footer />

<script>
	$(document).ready(function() {
		loaddatatable();
		loadfamilydatatable();
		$('#staffcode').select2();

	});

	//show populated datatable
	function loaddatatable() {
		$('#staffFamilyTable').DataTable();
		var url = "${pageContext.request.contextPath }/userprofiles/colleagues";
		$
				.get(
						url,
						function(data, status) {
							var json = data.data;
							var table = $('#staffTable')
									.DataTable(
											{
												"data" : json,
												"columns" : [
														{
															data : "code"
														},
														{
															data : "firstName"
														},
														{
															data : "lastName"
														},
														{
															"data" : "Action",
															"orderable" : false,
															"searchable" : false,
															"render" : function(
																	data, type,
																	row, meta) { // render event defines the markup of the cell text 
																var a = '<a class="modal-with-form btn btn-default" href="${pageContext.request.contextPath }/userprofiles/user-profile/'+row.code+'" ><i class="fa fa-edit"></i> PROFILE</a>'; // row object contains the row data
																return a;
															}
														} ]
											});
						}).done(function(data) {
				}).fail(function(jqxhr, settings, ex) {
					alert('No Data Found!');
				});
	}
</script>