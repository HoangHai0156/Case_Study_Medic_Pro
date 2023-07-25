page.dialogs.elements.frmCreate.validate({
    rules: {
        fullNameCre: {
            required: true,
            minlength: 5,
            maxlength: 25
        },
        emailCre: {
            required: true
        }

    },
    messages: {
        fullNameCre:{
            required: "Bắt buộc nhập Full Name",
            minlength: 'Full Name tối thiểu ${0} ký tự',
            maxlength: 'Full Name tối đa ${0} ký tự'
        },
        emailCre: {
            required: "Email là bắt buộc"
        }
    },
    errorLabelContainer: "#modalCreate .error-area",
    errorPlacement: function (error) {
        error.appendTo("#modalCreate .error-area");
    },
    showErrors: function(errorMap, errorList) {
        if (this.numberOfInvalids() > 0) {
            page.dialogs.elements.errorAreaCreate.removeClass("hide").addClass("show");
        } else {
            page.dialogs.elements.errorAreaCreate.removeClass("show").addClass("hide").empty();
            $("#frmCreate input.error").removeClass("error");
        }
        this.defaultShowErrors();
    },
    submitHandler: function () {
        page.dialogs.command.create();
    }
})