var wrongQuestionTable;//表格对象
var studentValidator;
var exportValidator;
var $table = "#wrongQuestion_table";
var studentId = '';
$(document).ready(function(){
    studentId = $('#studentId').val();
    initKnowledgeTree();
    initExportKnowledgeTree();
    initFuzzySearch();
    validateData();
    initTable();
});
/**
 * 初始化表格
 */
function initTable(){
	wrongQuestionTable = $($table).DataTable({
        dom: '<"html5buttons"B>lTfgitp',
        "serverSide": true,     // true表示使用后台分页
        "ajax": {
            "url": contextPath+"wrongQuestion/getTableJson",  // 异步传输的后端接口url
            "type": "POST",      // 请求方式
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            data:{studentId:studentId},
        },
        "columns": [
            { "data": "id",
                "visible": false ,
                "searchable":false,
            },
            { "data": "name",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                'orderable' : false ,
                width: '40%'

            },
            { "data": "file_save_name",
                "searchable":false,
                'orderable' : false ,
                width: '5%',
                'render':function (data, type, row, meta) {
                    //data  和 row  是数据
                    let img = '';
                    if(data != null && data != '' && data != 'undefined'){
                        img =  '<img style="width: 100px;height:90px:" src="/wrongQuestionImage/'+data+'" >';
                    }
                    return img;
                }
            },
            { "data": "level",
                "searchable":false,
                render : function(data,type, row, meta) {
                    var subject = '';
                    if(data == 0){
                        subject = '简单';
                    }else if(data == 1){
                        subject = '普通';
                    }else{
                        subject = '复杂';
                    }
                    return subject;
                },
                width: '10%'
            },
            { "data": "knowledge_name",
                'orderable' : false ,
                width: '10%'
            },
            { "data": "time",
                "searchable":false,
                width: '10%'
            },
            { "data": null,
                "searchable":false,
                'orderable' : false ,
                width: '5%',
                'render':function (data, type, row, meta) {
                //data  和 row  是数据
                    var buttons = '';
                    buttons+='<button type="button" onclick="deleteWrongQuestion('+data.id+')" class="btn btn-primary btn-xs" >删除</button>&nbsp;&nbsp;';
                    // buttons+='<button type="button" onclick="downloadWrongQuestion('+data.id+')" class="btn btn-primary btn-xs" >下载</button>&nbsp;&nbsp;';
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

    studentValidator= $("#wrongQuestionForm").validate({
        rules: {
            name: {
                required: true,
                // maxlength: 500
            },
            knowledge_name:{
                required: true,
                maxlength: 100
            },
            level:{
                required: true,
            },
            // file:{
            //     required: true,
            // }

        },
        messages : {

            name : {
                required: "不能为空",
                // maxlength : "不超过500个字符",
            },
            knowledge_name:{
                required: "不能为空",
                maxlength : "不超过100个字符",
            },
            level:{
                required: "请选择错题级别",
            },
            // file:{
            //     required: "请上传文件",
            // }
        },
        submitHandler : function(form) {
            saveWrongQuestion();

        }
    });


    exportValidator= $("#exportForm").validate({
        rules: {
            // code:{
            //     required: true,
            //     maxlength: 100
            // },
            // level:{
            //     required: true,
            // },
            export_start_time:{
                required: true,
            },
            export_end_time:{
                required: true,
            },


        },
        messages : {

            // code:{
            //     required: "不能为空",
            //     maxlength : "不超过100个字符",
            // },
            // level:{
            //     required: "请选择错题级别",
            // },
            export_start_time:{
                required: "不能为空",
            },
            export_end_time:{
                required: "不能为空",
            },
        },
        submitHandler : function(form) {
            var formData = new FormData();
            formData.append("code",$('#export_form_code').val());
            formData.append("studentId",$('#export_form_student_id').val());
            formData.append("level",$('#export_form_level').val());
            formData.append("export_start_time",$('#export_start_time').val());
            formData.append("export_end_time",$('#export_end_time').val());
            $.ajax({
                type : "POST",
                data : formData,
                contentType : false, //必须
                processData : false,
                dataType:"json",
                url : contextPath+"wrongQuestion/hasStudentWrongQuestion",
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function(result){
                    if(result){
                        exportZip();
                        hideModal('exportModal');
                        clearExportForm();
                    }else{
                        showAlert("没有可导出的错题文件",'error');
                    }
                }
            });
        }
    });
    $("#uploadForm").validate({
        rules: {
            upload_file:{
                required: true,
            }

        },
        messages : {

            upload_file:{
                required: "请上传文件",
            }
        },
        submitHandler : function(form) {
            toastrTips("图片正在识别中,请稍后...");
            var formData = new FormData();
            formData.append("file",$("#upload_file")[0].files[0]);
            formData.append("ocrType",$("#form_ocrType").val());
            $.ajax({
                type : "POST",
                data : formData,
                contentType : false, //必须
                processData : false,
                dataType:"text",
                url : contextPath+"wrongQuestion/uploadOcr",
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function(result){
                    hideModal("uploadModal");

                    $('#form_name').val(result);
                    clearUploadForm();
                }
            });
        }
    });
}
/**
 * 上传错题
 */
function uploadWrongQuestion() {
    $('#form_student_id').val(studentId);
    showModal('wrongQuestionModal')
}

/**
 * ocr图片识别
 */
function ocrUpload() {
    showModal('uploadModal')
}

/**
 * 清空表单
 */
function clearWrongForm(){
    $('#wrongQuestionForm')[0].reset();
    $('#wrongQuestionForm').validate().resetForm();
    $('#knowledge_tree_div').hide();
}
/**
 * 下载错题
 */
function downloadWrongQuestion(id){
    var form = document.getElementById('downloadWrongQuestionForm');
    $('#download_id').val(id);
    form.submit();
}
/**
 * 保存错题
 */
function saveWrongQuestion() {


    var formData = new FormData();
    formData.append("file",$("#form_file")[0].files[0]);
    formData.append("name",$('#form_name').val());
    formData.append("studentId",$('#form_student_id').val());
    formData.append("knowledgeCode",$('#form_code').val());
    formData.append("knowledgeName",$('#form_knowledge_name').val());
    formData.append("level",$('#form_level').val());
    $.ajax({
        type : "POST",
        data : formData,
        contentType : false, //必须
        processData : false,
        dataType:"json",
        url : contextPath+"wrongQuestion/saveWrongQuestion",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            if(result == 1){
                hideModal('wrongQuestionModal');
                clearWrongForm();
                reloadTable();
                showAlert("保存成功",'success');
            }else{
                showAlert("保存失败",'error');
            }
        }
    });

}
/**
 * 刷新表格
 */
function reloadTable(){
    wrongQuestionTable.settings()[0].ajax.data={studentId:studentId,startTime:$('#start_time').val(),endTime:$('#end_time').val()};
    wrongQuestionTable.ajax.reload();
}
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}

/**
 * 删除
 * @param id
 */
function deleteWrongQuestion(id){
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
            url : contextPath+"wrongQuestion/deleteWrongQuestion",
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
 * 返回学生管理页面
 */
function backPrevious() {
    window.location.href=contextPath+"student/studentManage";
}

/**
 * 弹出导出配置表单
 */
function exportWrongQuestion(){
    $('#export_form_student_id').val(studentId);
    showModal('exportModal')
}

/**
 * 导出zip包
 */
function exportZip(){
    var form = document.getElementById('exportForm');
    form.submit();
}
function clearExportForm(){
    $('#exportForm')[0].reset();
    $('#exportForm').validate().resetForm();
    $('#export_form_code').val(null)
    $('#export_knowledge_tree_div').hide();
}
function clearUploadForm(){
    $('#uploadForm')[0].reset();
    $('#uploadForm').validate().resetForm();

}

/**
 * 初始化知识树
 */
function initKnowledgeTree(){
    var zNodes = []; //zTree的数据属性
    var setting = { //zTree的参数配置
        check : {
            enable : false,
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
            url : contextPath+"knowledge/getKnowledgeTree",

        },
        callback : {
            onClick: zTreeOnClick
        }
    };
    $.fn.zTree.init($("#knowledge_tree"), setting, zNodes);
}
/**
 * 树节点点击事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
    $('#form_code').val(treeNode.value);
    $('#form_knowledge_name').val(treeNode.value2);
    $('#knowledge_tree_div').hide();
}
/**
 * 初始化导出错题知识树
 */
function initExportKnowledgeTree(){
    var zNodes = []; //zTree的数据属性
    var setting = { //zTree的参数配置
        check : {
            enable : false,
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
            url : contextPath+"knowledge/getKnowledgeTree",

        },
        callback : {
            onClick: zTreeOnClickExport
        }
    };
    $.fn.zTree.init($("#export_knowledge_tree"), setting, zNodes);
}
function zTreeOnClickExport(event, treeId, treeNode) {
    $('#export_form_code').val(treeNode.value);
    $('#export_form_knowledge_name').val(treeNode.value2);
    $('#export_knowledge_tree_div').hide();
}

/**
 * 显示知识树
 */
function knowledgeShow(id){
    $('#'+id).show();
}

/**
 * 初始化模糊搜索方法
 */
function initFuzzySearch(){
    fuzzySearch('knowledge_tree','#search_tree',null,true); //初始化模糊搜索方法
    fuzzySearch('export_knowledge_tree','#export_search_tree',null,true); //初始化模糊搜索方法
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