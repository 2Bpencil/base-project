//父id
// paid = "";
// meid = "";
// mename = "";
var heads=['菜单名称','菜单代码','路径','菜单类型','图标','排序'];
$(document).ready(function(){
    showTreeTable();
    validateData();


});

/**
 * 初始化菜单树表格
 *
 */
function showTreeTable() {
    $("#treeTable").empty();
    $.ajax({
        type : 'POST',
        url : contextPath + 'menu/getAllMenus',
        dataType : "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(result) {
            $.TreeTable("treeTable",heads,result);
        }
    });
}

/**
 * 清空pid
 */
function clearPid(){
    paid = "";
    meid = "";
    mename = "";

}

/**
 * 新增
 */
function addMenu(){

    if(meid == ""){
        showModal("menuModal");
    }else {
        $.ajax({
            type : "POST",
            data : {id:meid},
            url : contextPath+"menu/getEntityInfo",
            dataType : "json",
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                if(result.type == 1){
                    swal("叶子菜单下不能新建菜单!", "", "error");
                    return;
                }else {
                    $("#form_pid").val(meid);
                    $("#form_parent").val(mename);
                }
                showModal("menuModal");
            }
        });
    }
}
/**
 * 保存
 */
function saveMenu(){
//保存
    $.ajax({
        type : "POST",
        data : $("#menuForm").serialize(),
        url : contextPath+"menu/saveOrEditEntity",
        dataType : "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            if(result == 1){
                hideModal('menuModal');
                showTreeTable();
                clearForm();
                showAlert("保存成功",'success');
            }else{
                showAlert("保存失败",'error');
            }
        }
    });
}

/**
 * 编辑
 */
function editMenu(){
    if(meid==""){
        swal({
            title: "提示",
            text: "请选中要编辑的项！"
        });
        return;
    }
    $.ajax({
        type : "POST",
        data : {id:meid},
        url : contextPath+"menu/getEditEntityInfo",
        dataType : "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            $('#form_parent').val(result.parent);
            $('#form_id').val(result.id);
            $('#form_pid').val(result.pid);
            $('#form_name').val(result.name);
            $('#form_code').val(result.code);
            $('#form_url').val(result.url);
            $('#form_type').val(result.type);
            $('#form_sort').val(result.sort);
            $('#form_icon').val(result.icon);
            showModal("menuModal");
        }
    });
}

/**
 * 删除
 */
function deleteMenu(){
    if(meid == ""){
        swal({
            title: "提示",
            text: "请选中要删除的项！"
        });
        return;
    }
    var node = jQuery('#treeTable').treetable('childs', meid);
    getNodes(node);
    $.ajax({
        type : "POST",
        data : {ids:ids},
        dataType:"json",
        url : contextPath+"menu/checkMenuUsed",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            if(result){
                swal({
                    title: "是否确定删除?",
                    text: "你将会删除这条记录!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "Yes, delete it!",
                    closeOnConfirm: false
                }, function () {
                    $.ajax({
                        type : "POST",
                        data : {ids:ids},
                        dataType:"json",
                        url : contextPath+"menu/deleteMenu",
                        beforeSend : function(xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function(result){
                            if(result == 1){
                                ids="";
                                showTreeTable();
                                swal("删除成功!", "", "success");
                            }else{
                                swal("删除失败!", "", "error");
                            }
                        }
                    });
                });
            }else{
                ids = "";
                swal("有菜单被分配，不能删除!", "", "error");
            }
        }
    });
}
//删除用
var ids="";
function getNodes(node){
    if(ids==""){
        ids += node.id;
    }else {
        ids += ","+ node.id;
    }
    if(node.children.length > 0){
        for(var i = 0;i < node.children.length;i++){
            getNodes(node.children[i]);
        }
    }
}
//******************************************************表单验证*****************************************************************
/**
 * 验证数据
 */
function validateData(){
     $("#menuForm").validate({
        rules: {
            code: {
                required: true,
                maxlength: 20,
                remote : {//远程地址只能输出"true"或"false"
                    url : contextPath + "menu/verifyTheRepeat",
                    type : "POST",
                    dataType : "json",//如果要在页面输出其它语句此处需要改为json
                    beforeSend : function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    data : {
                        id : function(){
                            return $("#form_id").val();
                        }
                    }
                },
            },
            name: {
                required: true,
                maxlength: 20
            },
            url:{
                required: true,
                maxlength: 100
            },
            type:{
                required: true,
            },
            sort:{
                digits:true
            }

        },
        messages : {
            code : {
                required : "不能为空",
                maxlength : "不超过20个字符",
                remote : "角色名已存在",
            },
            name : {
                required: "不能为空",
                maxlength : "不超过20个字符",
            },
            url:{
                required: "不能为空",
                maxlength: "不超过100个字符",
            },
            type:{
                required: "请选择类型",
            },
            sort:{
                digits:"请输入正整数"
            }
        },
        submitHandler : function(form) {
            if($('#form_pid').val()!=0 && $('#form_pid').val()!='' && $('#form_pid').val()!=null){
                var type = $('#form_type').val();
                if(type == 0){
                    swal("父级菜单下只能选择叶子菜单!", "", "error");
                    return;
                }
            }
            saveMenu();

        }
    });
}
/**
 * 清空表单
 */
function clearForm(){
    $('#menuForm')[0].reset();
    $('#form_type').val("");
    $('#form_id').val(null);
    $('#form_pid').val(null);
    $('#menuForm').validate().resetForm();
    paid = "";
    meid = "";
    mename = "";
    ids = "";
}