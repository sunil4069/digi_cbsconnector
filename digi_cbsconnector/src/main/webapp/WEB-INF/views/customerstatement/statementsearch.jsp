<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<ui:header/>
<div class="row" style="font-weight:bold;">
	<div class="col-lg-12">
		<section class="panel">
			<header class="panel-heading">
				<div class="panel-actions">
					<a href="#" class="fa fa-caret-down"></a>
				</div>
			</header>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-3">
						<div class="form-group">
						<label class="control-label" for="accountType"> खाताको प्रकार   </label> 
							<select class="form-control" name="accountType" id="accountType">
<option value="0">Select Option </option>
    <option value="11101">  संचय कोष    </option>
    <option value="25101"> संचय कोष सापटी  </option>
    <option value="25102">  अवकाश कोष कर्जा  </option>
    <option value="25103"> सवारी कर्जा  </option>
    <option value="25104"> शैक्षिक कर्जा  </option>
						</select>
						</div>
					</div>
					
			<div class="col-sm-2">
						<div class="form-group">
							<label class="control-label" for="accountNumber"> खाता नं. </label> <input
								type="text" class="form-control" id="accountNumber" name="accountNumber" value="">
						</div>
					</div>		
					
					
					
					
					<div class="col-sm-1">
						<div class="form-group">
							<label class="control-label" for="startDate"> शुरु मिति </label> <input
								type="text" class="form-control" id="startDate" name="startDate" value="${strdate}">
						</div>
					</div>
					
					<div class="col-sm-1">
						<div class="form-group">
							<label class="control-label" for="endtDate"> अन्तिम मिति  </label> <input
								type="text" class="form-control" id="endDate" name="endDate" value="${strdate }">
						</div>
					</div>
					<!-- <div class="col-sm-4">
					<div class="form-group">
					<br>
						<button type="button" id="searchBtn"
									class="btn btn-primary">Search</button>
					</div>
					</div> -->
					
					<div class="col-sm-4">
					<div class="form-group">
					<br>
						<button type="button" id="searchBtn"
									class="btn btn-primary">Search</button>
									
									<button type="button" id="exporttopdfBtn"
									class="btn btn-primary">Export to pdf</button>
					</div>
					</div>
					
				</div>			
				
				
				
				
				

			</div>
		</section>
	</div>
</div>
<div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="table-responsive">
<table
	class="table jambo_table table-striped table-bordered display responsive wrap"
	id="lockertable" style="font-size: 90%; width: 100%;">
	<thead>
		<tr>
			<th> कार्यालय कोड  </th>
			<th> System id  </th>
			<th> ग्राहक नं.  </th>
			<th> लकर धनीको नाम  </th>
			<th>  लकर नं.   </th>
			<th>   लकर खोलेको मिति  </th>
			<th>   खाता नं.  </th>
			<th>   Lock Amount  </th>
			<th>   Fee/ Charge</th>
			
			
			
			
			
		</tr>
	</thead>
</table>
</div></div></div></div>
<ui:footer />




<script>
    $(document).ready(function () {
    	//$('#lockertable').DataTable().clear().draw();
    	//loaddatatable();

    });

    //show populated datatable
    function loaddatatable(month) {
        $('#lockertable').DataTable(); 
        let url = "${pageContext.request.contextPath }/lockers/readAllbymonth/"+month;
        $.get(url, function (datas, status) {
            let json = datas;
            let table = $('#lockertable').DataTable({
                "data": json,
                "columns": [
                	{data: "branchCode",defaultContent:""},
                	{data: "id",defaultContent:""},
                	{data: "customerId",defaultContent:""},
                    {data: "customerName",defaultContent:""},
                    {data: "lockerNumber",defaultContent:""},
                    {data: "openDate",defaultContent:""},
                    {data: "accountNumber",defaultContent:""},
                    {data: "netlockAmount",defaultContent:""},
                    {data: "netFeecharge",defaultContent:""},
                    
                ],
                "destroy": true
            });
        })
            .done(function (data) {
            })
            .fail(function (jqxhr, settings, ex) {
                alert('No Data Found!');
            });
        
    }
    
    
   
    
    $("#searchBtn").click(function(){
    	var month= $("#month").val();
    	$('#lockertable').DataTable().clear().draw();
    	loaddatatable(month);
    	
    });
    
     $("#exporttopdfBtn").click(function () {
        let month = $("#month").val();
        let url = "${pageContext.request.contextPath }/lockers/customerlockersfees?month=" + month;
        window.open(url, "_self");
       // window.open(url, "_blank"); 
     //  window.location.reload();
    });  
   
    
    
    
    
    
    
</script>
<!-- manage script to save values -->
