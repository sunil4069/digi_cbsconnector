$(document).ready( function () {

	 var table = $('#employeesTable').DataTable({

			"sAjaxSource": "/employees",

			"sAjaxDataProp": "",

			"order": [[ 0, "asc" ]],

			"aoColumns": [

			    { "mData": "ro_code"},

		      { "mData": "name" },

				  { "mData": "address" },

				  { "mData": "office_level" }

			]

	 })

});
