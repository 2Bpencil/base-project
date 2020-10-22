// 再使用Ajax的时候会报 403(ajax get  方式是没问题的 post 的时候会报)
// Spring Security 原本是 防止 CSRF 攻击 现在 ajax 被误伤了...
var header = $("meta[name='_csrf_header']").attr("content");
var token =$("meta[name='_csrf']").attr("content");
$(document).ready(function(){
    validatePasswordData();
});

/**
 * 关闭Modal
 */
function hideModal(id){
    $("#"+id ).modal('hide');
}

/**
 * 显示Modal
 * @param id
 */
function showModal(id){
    $("#"+id ).modal('show');
}

/**
 * 弹出提示框
 * @param msg
 * @param type  success,error
 */
function showAlert(msg,type){
    swal({
        title: msg,
        text: "",
        type: type
    });
}

/**
 * 提示
 * @param msg
 */
function toastrTips(msg) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": true,
        "preventDuplicates": false,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showDuration": "400",
        "hideDuration": "1000",
        "timeOut": "7000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    toastr.info(msg);
}

/**
 * 清除密码表单
 */
function clearPasswordForm(){
    $('#passwordForm')[0].reset();
    $('#passwordForm').validate().resetForm();
}

/**
 * 验证密码数据
 */
function validatePasswordData(){
    $("#passwordForm").validate({
        rules: {
            old_password: {
                required: true,
                maxlength: 20,
                minlength: 6,
                remote : {//远程地址只能输出"true"或"false"
                    url : contextPath + "user/checkPassword",
                    type : "POST",
                    dataType : "json",//如果要在页面输出其它语句此处需要改为json
                    beforeSend : function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    data : {

                    }
                },
            },
            modify_password: {
                required : true,
                maxlength: 20,
                minlength: 6,
            },
            repeat_password:{
                required: true,
                maxlength: 20,
                minlength: 6,
                equalTo: "#form_modify_password"
            },
        },
        messages : {
            old_password : {
                required : "请输入原密码",
                maxlength : "不超过20个字符",
                minlength: "必须超过6个字符",
                remote : "密码不正确",
            },
            modify_password : {
                required: "请输入新密码",
                maxlength : "不超过20个字符",
                minlength: "必须超过6个字符",
            },
            repeat_password:{
                required: "请重新输入新密码",
                maxlength : "不超过20个字符",
                minlength: "必须超过6个字符",
                equalTo:"请再次输入相同的密码"
            },
        },
        submitHandler : function(form) {

            //保存
            $.ajax({
                type : "POST",
                data : $("#passwordForm").serialize(),
                dataType:"json",
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                url : contextPath+"user/modifyPassword",
                success: function(result){
                    if(result == 1){
                        hideModal('passwordModal');
                        clearPasswordForm();
                        showAlert("修改成功",'success');
                    }else{
                        showAlert("修改失败",'error');
                    }
                }
            });


        }
    });
}