//父id
// paid = "";
// meid = "";
// mename = "";
var heads=['年级班级名称','排序'];
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
        url : contextPath + 'classes/getAllClassess',
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType : "json",
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
function addClasses(){
    if(meid == ""){
        showModal("classesModal");
    }else {
        if(paid != 0){
            swal({
                title: "提示",
                text: "不能新增第三级！"
            });
            return;
        }
        $("#form_pid").val(meid);
        $("#form_parent").val(mename);
        showModal("classesModal");
    }
}
/**
 * 保存
 */
function saveClasses(){
//保存
    $.ajax({
        type : "POST",
        data : $("#classesForm").serialize(),
        url : contextPath+"classes/saveOrEditEntity",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType : "json",
        success: function(result){
            if(result == 1){
                hideModal('classesModal');
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
function editClasses(){
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
        url : contextPath+"classes/getEditEntityInfo",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType : "json",
        success: function(result){
            $('#form_parent').val(result.parent);
            $('#form_id').val(result.id);
            $('#form_name').val(result.name);
            $('#form_pid').val(result.pid);
            $('#form_sort').val(result.sort);
            showModal("classesModal");
        }
    });
}

/**
 * 删除
 */
function deleteClasses(){
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
        url : contextPath+"classes/checkClassesUsed",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            if(result){
                swal({
                    title: "是否确定删除?",
                    text: "你将会删除这条记录以及其所有下级记录!",
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
                        url : contextPath+"classes/deleteClasses",
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
                ids="";
                swal("有班级被分配，不能删除!", "", "error");
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

/**
 * 知识配置
 */
function knowledgeSet() {
    if(meid == ""){
        swal({
            title: "提示",
            text: "请选中要配置的项！"
        });
        return;
    }
    if(mename.indexOf("班")==-1){
        swal({
            title: "提示",
            text: "请选择班级进行配置！"
        });
        return;
    }
    initTree();


}

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
                "Y" : "s",
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
            url : contextPath+"knowledge/getAllKnowledgeForTree",
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        },
        callback : {
            onAsyncSuccess : zTreeOnAsyncSuccess//异步加载树完成后回调函数
        }
    };
    $.fn.zTree.init($("#knowledgeGroup"), setting, zNodes);

}

/*
 * 异步加载树完成后回调函数
 */
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    //反填角色已有的菜单
    $.ajax({
        type : "POST",
        data : {
            id : meid
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        url : contextPath + "classes/getKnowledgeByClassId",
        dataType : "JSON",
        success : function(result){
            $("#check2").attr("checked",false);
            $("#check1").attr("checked",false);
            var  treeObj = $.fn.zTree.getZTreeObj("knowledgeGroup");
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

    showModal("knowledgeModal");
}
/**
 * 全选/取消全选
 */
function checkAll(boo){
    var treeObj = $.fn.zTree.getZTreeObj("knowledgeGroup");
    if(boo == "y"){
        $("#check2").attr("checked",false);
        treeObj.checkAllNodes(true);
    }else{
        $("#check1").attr("checked",false);
        treeObj.checkAllNodes(false);
    }
}

/**
 * 保存知识配置
 */
function saveKnowledgeSet(){

    var treeObj = $.fn.zTree.getZTreeObj("knowledgeGroup");
    var nodes = treeObj.getCheckedNodes(true);
    var knowledgeIds = "";
    for(var i=0;i<nodes.length;i++){
        if(i == nodes.length-1){
            knowledgeIds += nodes[i].id;
        }else{
            knowledgeIds += nodes[i].id+",";
        }
    }
    $.ajax({
        type : "POST",
        data : {
            id : meid,
            knowledgeIds : knowledgeIds,
        },
        url : contextPath + "classes/saveKnowledgeSet",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(result){
            hideModal("knowledgeModal");
            if(result == 1){
                showAlert("权限分配成功",'success');
            }else{
                showAlert("权限分配失败",'error');
            }
        }
    });


}

//******************************************************表单验证*****************************************************************
/**
 * 验证数据
 */
function validateData(){
     $("#classesForm").validate({
        rules: {
            name: {
                required: true,
                maxlength: 100,
                remote : {//远程地址只能输出"true"或"false"
                    url : contextPath + "classes/verifyTheRepeat",
                    type : "POST",
                    dataType : "json",//如果要在页面输出其它语句此处需要改为json
                    beforeSend : function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    data : {
                        id : function(){
                            return $("#form_id").val();
                        },
                        pid : function(){
                            return $("#form_pid").val();
                        }
                    }
                },
            },
            sort:{
                digits:true
            }

        },
        messages : {
            name : {
                required : "不能为空",
                maxlength : "不超过100个字符",
                remote : "班级名已存在",
            },
            sort:{
                digits:"请输入正整数"
            }
        },
        submitHandler : function(form) {
            saveClasses();
        }
    });
}
/**
 * 清空表单
 */
function clearForm(){
    $('#classesForm')[0].reset();
    $('#form_pid').val(null);
    $('#form_id').val(null);
    $('#classesForm').validate().resetForm();
    paid = "";
    meid = "";
    mename = "";
    ids = "";
}