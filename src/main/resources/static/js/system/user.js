var userTable;//表格对象
var userValidator;//表单验证
var $table = "#user_table";
$(document).ready(function(){
    initTable();
    validateData();
});

/**
 * 初始化表格
 */
function initTable(){
    userTable = $($table).DataTable({
        dom: '<"html5buttons"B>lTfgitp',
        "serverSide": true,     // true表示使用后台分页
        "ajax": {
            "url": contextPath+"user/getTableJson",  // 异步传输的后端接口url
            "type": "POST",      // 请求方式
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        },
        "columns": [
            { "data": "id",
                "visible": false ,
                "searchable":false,
            },
            { "data": "username",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: '25%'

            },
            { "data": "real_name",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: '25%'

            },
            { "data": "phone",
                'orderable' : false ,
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: '20%'
            },
            { "data": "sex",
                render : function(data,type, row, meta) {
                    return ( parseInt(data) === 1?"男":"女");
                },
                'orderable' : false ,
                width: '10%'
            },
            {   "data": null,
                "searchable":false,
                'orderable' : false ,
                width: '20%',
                'render':function (data, type, row, meta) {
                    //data  和 row  是数据
                    var buttons = '';
                    buttons+='<button type="button" onclick="editUser('+data.id+')" class="btn btn-primary btn-xs" >编辑</button>&nbsp;&nbsp;';
                    buttons+='<button type="button" onclick="deleteUser('+data.id+')" class="btn btn-primary btn-xs" >删除</button>&nbsp;&nbsp;';
                    buttons+='<button type="button" onclick="assignmentRole('+data.id+')" class="btn btn-primary btn-xs" >分配角色</button>&nbsp;&nbsp;';
                    buttons+='<button type="button" onclick="reSetPassword('+data.id+')" class="btn btn-primary btn-xs" >重置密码</button>';
                    return buttons;
                }
            },
        ],
        "order": [[ 0, 'desc' ]],
        buttons: [
            { extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'ExampleFile'},
            {extend: 'pdf', title: 'ExampleFile'},

            {extend: 'print',
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ],
        language:CONSTANT.DATA_TABLES.DEFAULT_OPTION.language,
        autoWidth:false,
        processing: false,
    });
    $($table).on( 'error.dt', function ( e, settings, techNote, message ){
        //这里可以接管错误处理，也可以不做任何处理
    }).DataTable();
}
/**
 * 验证数据
 */
function validateData(){
    jQuery.validator.addMethod("cellPhone", function(value, element) {
        return this.optional(element)
            || /^1[0-9]\d{1}\d{4}\d{4}( x\d{1,6})?$/.test(value);
    }, "联系方式无效");
    userValidator= $("#userForm").validate({
        rules: {
            username: {
                required: true,
                maxlength: 50,
                remote : {//远程地址只能输出"true"或"false"
                    url : contextPath + "user/verifyTheRepeat",
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
            phone: {
                cellPhone : 'required'
            },
            sex:{
                required: true,
                },
            realName:{
                required: true,
            }
        },
        messages : {
            username : {
                required : "不能为空",
                maxlength : "不超过50个字符",
                remote : "用户名已存在",
            },
            phone : {
                required: "不能为空",
                maxlength : "不超过50个字符",
            },
            sex:{
                required: "请选择性别",
            },
            realName:{
                required: "不能为空",
            }
        },
        submitHandler : function(form) {
            saveUser();

        }
    });
}

/**
 * 保存用户
 */
function saveUser(){
    //保存
    $.ajax({
        type : "POST",
        data : $("#userForm").serialize(),
        url : contextPath+"user/saveOrEditEntity",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            if(result == 1){
                hideModal('userModal');
                clearForm();
                reloadTable();
                showAlert("保存成功",'success');
            }else{
                showAlert("保存失败",'error');
            }
        }
    });
}

/**
 * 编辑用户
 * @param id
 */
function editUser(id){
    $.ajax({
        type : "POST",
        data : {id:id},
        dataType:"json",
        url : contextPath+"user/getEntityInfo",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            $('#form_id').val(result.id);
            $('#form_username').val(result.username);
            $('#form_phone').val(result.phone);
            $('#form_sex').val(result.sex);
            $('#form_realName').val(result.realName);
            showModal("userModal");
        }
    });
}

/**
 * 刷新表格
 */
function reloadTable(){
    userTable.ajax.reload();
}

/**
 * 删除用户
 * @param id
 */
function deleteUser(id){
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
            data : {id:id},
            dataType:"json",
            url : contextPath+"user/deleteUser",
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                if(result == 1){
                    reloadTable();
                    swal("删除成功!", "", "success");
                }else{
                    swal("删除失败!", "", "error");
                }
            }
        });
    });
}



/**
 * 分配角色
 * @param id
 */
function assignmentRole(id){
    userId = id;
    initTree();

}
var userId;
/**
 * 加载分配菜单页面
 */
function initTree(){
    var zNodes = []; //zTree的数据属性
    var setting = { //zTree的参数配置
        check : {
            enable : true,
            chkStyle : 'checkbox',
            chkboxType : {
                "Y" : "ps",
                "N" : "ps"
            }
        },
        data : {
            simpleData : {
                enable : true
            }
        },
        async : {
            enable : true,
            type : "GET",
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            url : contextPath+"role/getAllRoleTree",

        },
        callback : {
            onAsyncSuccess : zTreeOnAsyncSuccess//异步加载树完成后回调函数
        }
    };
    $.fn.zTree.init($("#roleGroup"), setting, zNodes);

}

/*
 * 异步加载树完成后回调函数
 */
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    //反填用户已有的菜单
    $.ajax({
        type : "POST",
        data : {
            id : userId
        },
        url : contextPath + "user/getRolesByUserId",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType : "JSON",
        success : function(result){
            $("#check2").attr("checked",false);
            $("#check1").attr("checked",false);
            var  treeObj = $.fn.zTree.getZTreeObj("roleGroup");
            treeObj.expandAll(true);
            for (var i = 0; i < result.length; i++) {
                var node =treeObj.getNodeByParam("id",result[i].id);
                treeObj.checkNode(node,true,false);
                treeObj.expandNode(node, true, false, false);
            }
            var boole = true;
            var nodes = treeObj.getNodes();
            for(var i=0;i<nodes.length;i++){
                if(!nodes[i].checked){
                    boole = false;
                    return;
                }
            }
            if(boole){
                document.getElementById("check1").checked='checked';
            }
        }
    });

    showModal("roleModal");
}
/**
 * 全选/取消全选
 */
function checkAll(boo){
    var treeObj = $.fn.zTree.getZTreeObj('roleGroup');
    if(boo == "y"){
        $("#check2").attr("checked",false);
        treeObj.checkAllNodes(true);
    }else{
        $("#check1").attr("checked",false);
        treeObj.checkAllNodes(false);
    }
}

/**
 * 保存权限
 */
function saveUserAndRole(){
    var treeObj = $.fn.zTree.getZTreeObj("roleGroup");
    var nodes = treeObj.getCheckedNodes(true);
    var roleIds = "";
    for(var i=0;i<nodes.length;i++){
        if(i == nodes.length-1){
            roleIds += nodes[i].id;
        }else{
            roleIds += nodes[i].id+",";
        }
    }
    $.ajax({
        type : "POST",
        data : {
            userId : userId,
            roleIds : roleIds,
        },
        dataType:'json',
        url : contextPath + "user/saveUserAndRole",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(result){
            hideModal("roleModal");
            if(result == '1'){
                showAlert("权限分配成功",'success');
            }else{
                showAlert("权限分配失败",'error');
            }
        }
    });
}

/**
 * 清空表单
 */
function clearForm(){
    $('#userForm')[0].reset();
    $('#form_sex').val('');
    $('#form_id').val(null);
    $('#userForm').validate().resetForm();
}

/**
 * 重置密码
 * @param id
 */
function reSetPassword(id) {
    $.ajax({
        type : "POST",
        data : {
            userId : id,
        },
        dataType:'json',
        url : contextPath + "user/reSetPassword",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(result){
            if(result == '1'){
                showAlert("重置密码成功",'success');
            }else{
                showAlert("重置密码失败",'error');
            }
        }
    });
}

/*常量*/
var CONSTANT = {
    DATA_TABLES : {
        DEFAULT_OPTION : { //DataTables初始化选项
            language: {
                "sProcessing":   "处理中...",
                "sLengthMenu":   "每页 _MENU_ 项",
                "sZeroRecords":  "没有匹配结果",
                "sInfo":         "当前显示第 _START_ 至 _END_ 项，查询到 _TOTAL_ 项，共_MAX_项。",//共 _TOTAL_ 项  搜索到_TOTAL_/_MAX_条
                "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix":  "",
                "sSearch":       "搜索:",
                "sUrl":          "",
                "sEmptyTable":     "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands":  ",",
                "oPaginate": {
                    "sFirst":    "首页",
                    "sPrevious": "上页",
                    "sNext":     "下页",
                    "sLast":     "末页",
                    "sJump":     "跳转"
                },
                "oAria": {
                    "sSortAscending":  ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            autoWidth: false,	//禁用自动调整列宽
            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
            order: [],			//取消默认排序查询,否则复选框一列会出现小箭头
            processing: false,	//隐藏加载提示,自行处理
            serverSide: true,	//启用服务器端分页
            searching: false	//禁用原生搜索
        },
        COLUMN: {
            CHECKBOX: {	//复选框单元格
                className: "td-checkbox",
                orderable: false,
                width: "30px",
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            }
        },
        RENDER: {	//常用render可以抽取出来，如日期时间、头像等
            ELLIPSIS: function (data, type, row, meta) {
                data = data||"";
                return '<span title="' + data + '">' + data + '</span>';
            }
        }
    }
};