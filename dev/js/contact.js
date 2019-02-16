$("#submitContactForm").submit(function() {
  onContactSubmit();
});

var onContactSubmit = function(response) {

  $("#spinner").show();
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
  // $.post(serverContext + "contact", formData, function(data) {
  //   $("#spinner").hide();
  //   if(data.message == "success") {
  //       $("#sendmessage").show();
  //   }
  //   else {
  //     $("#errormessage").show().html(data.error);
  //   }
  // })
  // .fail(function(data) {
  //   $("#spinner").hide();
  //   grecaptcha.reset();
  //   console.log(data)
  //   if(data.responseJSON.error == "Bad Request")
  //   {
  //     $("#errormessage").show().html("Form Error");
  //     $.each(data.responseJSON.errors, function(index, item) {
  //       $("#"+item.field+"Error").show().html(item.defaultMessage);
  //     });
  //   }
  //   else 
  //   {
  //     $("#errormessage").show().html(data.responseJSON.message);
  //   }
  // });


  $.ajax({
    type: 'POST',
    url: serverContext + "contact",
    data: formData,
    dataType: 'json',
    success: function(data, textStatus, jqXHR) {
      $("#spinner").hide();
      if(data.message == "success") {
          $("#sendmessage").show();
      }
      else {
        $("#errormessage").show().html(data.message);
      }
    },
    error: function (data, textStatus, errorThrown) {
      $("#spinner").hide();
      grecaptcha.reset();
      console.log(data)
      if(data.status == 500)
      {
        $("#errormessage").show().html("Internal Server Error");
      }
      if(data.responseJSON != null)
      {
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
      }
      else
      {
        $("#errormessage").show().html("Server Error");
      }
    }
  });

}
