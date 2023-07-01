<!-- Vendor -->
<script src="${pageContext.request.contextPath }/resources/assets/vendor/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath }/resources/assets/vendor/bootstrap/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath }/resources/assets/vendor/nanoscroller/nanoscroller.js"></script>
<script src="${pageContext.request.contextPath }/resources/assets/vendor/magnific-popup/magnific-popup.js"></script>

<!-- Specific Page Vendor -->
<script src="${pageContext.request.contextPath }/resources/assets/vendor/jquery-ui/js/jquery-ui-1.10.4.custom.js"></script>
<!-- Specific Page Vendor -->
<script src="${pageContext.request.contextPath }/resources/assets/vendor/select2/select2.js"></script>
<script src="${pageContext.request.contextPath }/resources/assets/vendor/jquery-datatables/media/js/jquery.dataTables.js"></script>
<script src="${pageContext.request.contextPath }/resources/assets/vendor/jquery-datatables/extras/TableTools/js/dataTables.tableTools.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/assets/vendor/jquery-datatables-bs3/assets/js/datatables.js"></script>


<!-- Theme Base, Components and Settings -->
<script src="${pageContext.request.contextPath }/resources/assets/javascripts/theme.js"></script>
<script src="${pageContext.request.contextPath }/resources/assets/external/toast/jquery.toast.min.js"></script>

<!-- Examples -->
<script src="${pageContext.request.contextPath }/resources/assets/external/customDigi.js"></script>

<script>

function printData(elementid) {

				var divToPrint = document.getElementById(elementid);
				/*var newWin = window.open('', '', 'height=200,width=200');*/

				var htmlToPrint = '' +
			'<style>' +
			'@media print {'+
			
			'h1 {page-break-before: always;}'+
			
			'}'+
			
			'@page : {'+
			' margin: 0mm 0mm 0mm 0mm;'+

			'}'+
			'table td,table td, table th {' +
			'border:1px solid #000000;' +
			'padding-left:10px;font-size:12px;' +
			'} table{'+
			'border-collapse: collapse; width:100%;'+
			'}' +
			'.inputpreeti{font-family:preeti;'+
			'font-size:16px;'+
			' font-weight:bold;'+
			'}'+
			'.inputsunil{font-family:sunil_1;'+
			'font-size:16px;'+
				

			'}'+
			'footer { font-size: 12px; color: #000000; text-align: center;}'+
			'</style>';
				htmlToPrint += divToPrint.outerHTML;
				newWin = window.open("");
				newWin.document.write(htmlToPrint);
				/*newWin.document.write(divToPrint.outerHTML);*/
				newWin.print();
				newWin.close();
			}	
			</script>