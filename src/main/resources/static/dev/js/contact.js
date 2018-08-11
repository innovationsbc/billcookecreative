var onContactSubmit = function(response) {

  $("#nameError").hide().html("");
  $("#emailError").hide().html("");
  $("#subjectError").hide().html("");
  $("#messageError").hide().html("");
  $("#sendmessage").hide();
  $("#errormessage").hide().html("");

  var serverContext = window.location.protocol + "//" + window.location.host + "/";

  if (typeof grecaptcha !== 'undefined') {
    var resp = grecaptcha.getResponse();
    if (resp.length == 0) {
      $("#errormessage").show().html("Please verify that you are not a robot.");
      return;
    }
  }

  var formData = $('form').serialize();
  $.post(serverContext + "contact", formData, function(data) {
    if(data.message == "success"){
        $("#sendmessage").show();
    }
  })
  .fail(function(data) {
    grecaptcha.reset();
    if(data.responseJSON.error == "Bad Request")
    {
      $("#errormessage").show().html("Form Error");
      $.each(data.responseJSON.errors, function(index, item) {
        $("#"+item.field+"Error").show().html(item.defaultMessage);
      });
    }
    else 
    {
      $("#errormessage").show().html(data.responseJSON.message);
    }      
  });  
}
