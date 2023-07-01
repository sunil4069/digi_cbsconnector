<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<ui:header />
<ui:styles /> 
 <html>
 	<head> <title>ADBLERF</title>
 </head>

 
 <body  oncontextmenu="return false">
 
 
 <div id="printable1">
 <div class="container">
 	<div class="container-fluid">
 <p Style="text-align:center;font-size:20px;">कृषि विकास बैंक लि. </p>
     <p Style="text-align:center;font-size:16px;">
कर्मचारी अवकाश कोष व्यवस्थापन कार्यालय
<br>
लेखा भौचर

 </p>
</div>
  <table Style="width:20%;float: right;margin-right:10%;">
  <tr>
  <td>  क </td><td>   </td>
  </tr>
   <tr>
  <td>  ख  </td><td>  ${voucher.transactionDate } </td>
  </tr>
   <tr>
  <td>  ग   </td><td>  </td>
  </tr>
  </table>
  
  <br><br><br><br><br>
   

	<table style="width:60%;align:left;margin-left: 10%;margin-right: 20%;">
	<thead>
	<tr>
	<th style="width:5%;">
	</th><th style="width:20%;">  
	हिसाव नम्बर   </th><th style="width:20%;"></th>
	</tr>
	</thead>
	<tbody>
	<tr>
	<td rowspan="7"> 
	घ
	</td>
	<td>
	${drac }

</td><td> ${voucher.amount } </td>
	</tr>
	<tr>
	<td style="height:25px;">
	कर्जा खाता नं.(
	<label>${voucher.loanAccountNumber }</label>
	)
	
	</td><td></td>
	</tr>
	<tr>
	<td style="height:25px;">System ID:${voucher.digiTransactionId }</td>  <td></td>
	</tr>
	<tr>
	<td style="height:25px;">T24 Id:${voucher.transactionId }</td>  <td></td>
	</tr>
	<tr>
	<td style="height:25px;"></td><td></td>
	</tr>
	<tr>
	<td style="height:25px;"></td><td></td>
	</tr>
	<tr>
	<td>   जम्मा  </td><td> ${voucher.amount } </td>
	</tr>
	</tbody>
	</table>
	
	<table style="width:85%;align:left;margin-left: 10%;margin-right: 20%;">
	<thead>
	<tr>
	<th style="width:5%;">
	</th><th style="width:20%;">  
	हिसाव नम्बर   </th><th style="width:20%;"></th>
	</tr>
	</thead>
	<tbody>
	<tr>
	<td rowspan="7"> 
	ङ
	</td>
	<td >1281000020202</td><td> ${voucher.amount } </td>
	</tr>
	<tr>
	<td style="height:25px;"></td><td></td>
	</tr>
	<tr>
	<td style="height:25px;"></td><td></td>
	</tr>
	<tr>
	<td style="height:25px;"></td><td></td>
	</tr>
	<tr>
	<td style="height:25px;"></td><td></td><td style="width:4%;border:0;"></td><td style="width:5%;">  च </td><td style="width:10%;"></td>
	</tr>
	<tr>
	<td style="height:25px;"></td><td></td><td  style="border:0;"></td><td>  छ   </td><td></td>
	</tr>
	<tr>
	<td>   जम्मा     </td><td> ${voucher.amount } </td><td style="border:0;"></td>  <td>  ज   </td><td></td>
	</tr>
<tr>
<td colspan="6" style="border:0;">.</td>
</tr>
<tr>
<td>  झ  </td>	<td colspan="5"> </td>
	</tr>	
	<tr>
<td>  ञ  </td>	<td colspan="5"> </td>
	</tr>	
	<tr>
<td>  ट  </td><td> </td><td style="width:10px;" colspan="4"> 
   ठ  
</td>
	</tr>	
	<tr>
<td>  विवरण  </td>	

<td colspan="5"> 
संलग्न आ.प. अनुसार कर्जा लगानी
</td>
	</tr>
	
	
	</tbody>
	</table>
	
	
	
	
	
	
	</div>
	</div>
	
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4"></div>
		<div class="col-sm-4">
			<div class="form-group">
				<br> <input type="button" id="printdata" onclick="printData('printable1')"
					class="btn btn-danger" value="Print Data">
			</div>
		</div>
	</div>
<ui:footer />
</body>


</html>


