//post method with validation error in list displayed in modal
function ajaxPost(url, formData) {
    // PREPARE FORM DATA
    $.ajax({
        url: url,
        method: "post",
        data: JSON.stringify(formData),
        contentType: "application/json",
        beforeSend: function () {
            $('.loading').css('z-index', '999');
        },
        success: function (data) {
            let json=data.data;
            let id=json.id;
            $("[name='id']").val(id);
            toastNotification('Success', data.message, 'success');
        },
        error: function (err) {
            triggerOnError(err);
        },
        complete: function () {
            $('.loading').css('z-index', '-1');
        }
    });
}

function ajaxPostOnlyUrl(url) {
    $.ajax({
        url: url,
        method: "post",
        contentType: "application/json",
        beforeSend: function () {
            $('.loading').css('z-index', '999');
        },
        success: function (data) {
            toastNotification('Success', data.message, 'success');
        },
        error: function (err) {
            triggerOnError(err);
        },
        complete: function () {
            $('.loading').css('z-index', '-1');
        }
    });
}
function ajaxGetOnlyUrl(url) {
    $.ajax({
        url: url,
        method: "get",
        contentType: "application/json",
        beforeSend: function () {
            $('.loading').css('z-index', '999');
        },
        success: function (data) {

        },
        error: function (err) {
            triggerOnError(err);
        },
        complete: function () {
            $('.loading').css('z-index', '-1');
        }
    });
}
function ajaxGetUrlCall(url) {
    $.ajax({
        url: url,
        method: "get",
        contentType: "application/json",
        beforeSend: function () {
            $('.loading').css('z-index', '999');
        },
        success: function (data) {
            doWork(data);
        },
        error: function (err) {
            let data = JSON.parse(err.responseText);
            $("#failureModel .modal-text p").html(data.message);
            $("#failureModel .modal-text h4").html(data.statusCode);
            $("#failureModel").modal('show');
        },
        complete: function () {
            $('.loading').css('z-index', '-1');
        }
    });
}
function ajaxPatchUrlCall(url) {
    $.ajax({
        url: url,
        method: "PATCH",
        contentType: "application/json",
        beforeSend: function () {
            $('.loading').css('z-index', '999');
        },
        success: function (data) {
            toastNotification('Success', data.message, 'success');
        },
        error: function (err) {
            let data = JSON.parse(err.responseText);
            $("#failureModel .modal-text p").html(data.message);
            $("#failureModel .modal-text h4").html(data.statusCode);
            $("#failureModel").modal('show');
        },
        complete: function () {
            $('.loading').css('z-index', '-1');
        }
    });
}
function imagePost(url, data) {

    // PREPARE FORM DATA

    $.ajax({
        url: url,
        method: "post",
        data: data,
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        timeout: 600000,
        cache: false,
        beforeSend: function () {
            $('.loading').css('z-index', '999');
        },
        success: function (data) {
            toastNotification('Success', data.message, 'success');
        },
        error: function (err) {
            triggerOnError(data);
        },
        complete: function () {
            $('.loading').css('z-index', '-1');
        }
    });
}


//DELETE DATA

function deletedata(url) {
    $.ajax({
        url: url,
        type: 'DELETE',
        beforeSend: function () {
            $('.loading').css('z-index', '999');
        },
        success: function (data) {
            toastNotification('Success', data.message, 'success');
        },
        error: function (err) {
            toastNotification('Failed', data.message, 'failure');
        },
        complete: function () {
            $('.loading').css('z-index', '-1');
        }
    });
}

function triggerOnSuccess(data) {
    if (data.statusCode === '201') {
        $("#successMessageModal .modal-text p").html(data.message);
        $("#successMessageTriggerBtn").trigger('click');
    } else  if (data.statusCode === '200') {
        $("#successMessageModal .modal-text p").html(data.message);
        $("#successMessageTriggerBtn").trigger('click');
    } else {
        $("#failureModel .modal-text p").html(data.statusCode);
        $("#failureModel .panel-title").html(data.message);
        $("#failureMessageTriggerBtn").trigger('click');
    }
}

function triggerOnError(err) {
    let data = JSON.parse(err.responseText);
    $("#failureModel .modal-text p").html("");
    if (data.statusCode === '422' || data.statusCode === '400') {
        if(data.errors!=null) {
            $.each(data.errors, function (index, value) {
                $("#failureModel .modal-text p").append($("<li>" + value + "</li>"));
            });
        }
        $("#failureModel #failureTitle").html(data.message);
    } else {
        $("#failureModel .modal-text p").html(data.message);
    }
    $("#failureModel").modal('show');
}
function toastNotification(heading, message, type) {
    $.toast({
        heading: heading,
        text: message,
        icon: type,
        hideAfter: 5000,
        position: 'bottom-center'
    })
}