
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
        page.dialogs.commands.create();
    }
})

page.dialogs.elements.frmUpdate.validate({
    rules: {
        fullNameUp: {
            required: true,
            minlength: 5,
            maxlength: 25
        },
        emailUp: {
            required: true,
        }
    },
    messages: {
        fullNameUp:{
            required: "Bắt buộc nhập Full Name",
            minlength: 'Full Name tối thiểu ${0} ký tự',
            maxlength: 'Full Name tối đa ${0} ký tự'
        },
        emailUp: {
            required: "Email là bắt buộc"
        }
    },
    errorLabelContainer: "#modalUpdate .error-area",
    errorPlacement: function (error) {
        error.appendTo("#modalUpdate .error-area");
    },
    showErrors: function(errorMap, errorList) {
        if (this.numberOfInvalids() > 0) {
            page.dialogs.elements.errorAreaUpdate.removeClass("hide").addClass("show");
        } else {
            page.dialogs.elements.errorAreaUpdate.removeClass("show").addClass("hide").empty();
            $("#frmUpdate input.error").removeClass("error");
        }
        this.defaultShowErrors();
    },
    submitHandler: function () {
        page.dialogs.commands.update();
    }
})

page.dialogs.elements.frmDeposit.validate({
    rules: {
        transactionAmount: {
            required: true
        }
    },
    messages: {
        transactionAmount:{
            required: "Vui lòng nhập số tiền cần nạp"
        }
    },
    errorLabelContainer: "#modalDeposit .error-area",
    errorPlacement: function (error) {
        error.appendTo("#modalDeposit .error-area");
    },
    showErrors: function(errorMap, errorList) {
        if (this.numberOfInvalids() > 0) {
            page.dialogs.elements.errorAreaDeposit.removeClass("hide").addClass("show");
        } else {
            page.dialogs.elements.errorAreaDeposit.removeClass("show").addClass("hide").empty();
            $("#frmDeposit input.error").removeClass("error");
        }
        this.defaultShowErrors();
    },
    submitHandler: function () {
        page.dialogs.commands.deposit();
    }
})

page.dialogs.elements.frmWithdraw.validate({
    rules: {
        transactionAmountWdr: {
            required: true
        }
    },
    messages: {
        transactionAmountWdr:{
            required: "Vui lòng nhập số tiền cần nạp"
        }
    },
    errorLabelContainer: "#modalWithdraw .error-area",
    errorPlacement: function (error) {
        error.appendTo("#modalWithdraw .error-area");
    },
    showErrors: function(errorMap, errorList) {
        if (this.numberOfInvalids() > 0) {
            page.dialogs.elements.errorAreaWithdraw.removeClass("hide").addClass("show");
        } else {
            page.dialogs.elements.errorAreaWithdraw.removeClass("show").addClass("hide").empty();
            $("#frmWithdraw input.error").removeClass("error");
        }
        this.defaultShowErrors();
    },
    submitHandler: function () {
        page.dialogs.commands.withdraw();
    }
})

page.dialogs.elements.frmTransfer.validate({
    rules: {
        transferMoney: {
            required: true
        }
    },
    messages: {
        transferMoney:{
            required: "Vui lòng nhập số tiền cần chuyển"
        }
    },
    errorLabelContainer: "#modalTransfer .error-area",
    errorPlacement: function (error) {
        error.appendTo("#modalTransfer .error-area");
    },
    showErrors: function(errorMap, errorList) {
        if (this.numberOfInvalids() > 0) {
            page.dialogs.elements.errorAreaTransfer.removeClass("hide").addClass("show");
        } else {
            page.dialogs.elements.errorAreaTransfer.removeClass("show").addClass("hide").empty();
            $("#frmTransfer input.error").removeClass("error");
        }
        this.defaultShowErrors();
    },
    submitHandler: function () {
        page.dialogs.commands.transfer();
    }
})
