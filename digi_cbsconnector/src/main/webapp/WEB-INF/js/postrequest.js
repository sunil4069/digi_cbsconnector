$( document ).ready(function() {
  
  // SUBMIT FORM
    $("#officeform").submit(function(event) {
    // Prevent the form from submitting via the browser.
    event.preventDefault();
    ajaxPost();
  });
    
    
    function ajaxPost(){
      
      // PREPARE FORM DATA
      var formData = {
        ro_code : $("#ro_code").val(),
        name :  $("#name").val(),
        address : $("#address").val()
      }
      
      // DO POST
      $.ajax({
      type : "POST",
      contentType : "application/json",
      url : window.location + "views/office/create",
      data : JSON.stringify(formData),
      dataType : 'json',
      success : function(result) {
        if(result.status == "Done"){
          console.log("Successful");
        }else{
          console.log("failed");
        }
        console.log(result);
      },
      error : function(e) {
        alert("Error!")
        console.log("ERROR: ", e);
      }
    });
      
      // Reset FormData after Posting
      resetData();
 
    }
    
    function resetData(){
      $("#ro_code").val("");
      $("#name").val("");
      $("address").val("");
    }
})