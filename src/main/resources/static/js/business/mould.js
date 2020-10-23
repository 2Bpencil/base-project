var mouldTable;//表格对象
var userValidator;//表单验证
var $table = "#mould_table";
$(document).ready(function(){

    initTable();
    validateData();





    $(".checkall").click(function () {
        if (this.checked) {
            $(this).attr('checked', 'checked')
            $("input[name='datatablecheckbox']").each(function () {
                this.checked = true;
            });
        } else {
            $(this).removeAttr('checked')
            $("input[name='datatablecheckbox']").each(function () {
                this.checked = false;
            });
        }
    });
});

/**
 * 初始化表格
 */
function initTable(){
    mouldTable = $($table).DataTable({
        dom: '<"html5buttons"B>lTfgitp',
        "serverSide": true,     // true表示使用后台分页
        "ajax": {
            "url": contextPath+"mould/getTableJson",  // 异步传输的后端接口url
            "type": "POST",      // 请求方式
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        },
        "columns": [
            { "data": "id",
                "visible": false ,
                "searchable":false,
                'orderable' : false ,
            },
            {
                "sClass": "text-center",
                "data": null,
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox" name="datatablecheckbox"  class="checkchild"  value="' + data.id + '" />';
                },
                'orderable' : false ,
                "searchable":false,
                width: '5%'
            },

            { "data": "name",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: '25%'

            },
            { "data": "project",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                width: '25%'

            },
            {   "data": null,
                "searchable":false,
                'orderable' : false ,
                width: '20%',
                'render':function (data, type, row, meta) {
                    //data  和 row  是数据
                    var buttons = '';
                    buttons+='<button type="button" onclick="editMould('+data.id+')" class="btn btn-primary btn-xs" >编辑</button>&nbsp;&nbsp;';
                    buttons+='<button type="button" onclick="deleteMould('+data.id+')" class="btn btn-primary btn-xs" >删除</button>&nbsp;&nbsp;';
                    return buttons;
                }
            },
        ],
        select : {
            style : 'multi',
            selector : 'td:first-child',
            info:false
        },
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
    userValidator= $("#mouldForm").validate({
        rules: {
            name:{
                required: true,
            },
            project:{
                required: true,
            },
            content:{
                required: true,
            }
        },
        messages : {
            name : {
                required : "不能为空",
            },
            project : {
                required: "不能为空",
            },
            content:{
                required: "不能为空",
            },
        },
        submitHandler : function(form) {
            saveMould();

        }
    });
}

/**
 * 保存用户
 */
function saveMould(){
    //保存
    $.ajax({
        type : "POST",
        data : $("#mouldForm").serialize(),
        url : contextPath+"mould/saveOrEditEntity",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            if(result == 1){
                hideModal('mouldModal');
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
function editMould(id){
    $.ajax({
        type : "POST",
        data : {id:id},
        dataType:"json",
        url : contextPath+"mould/getEntityInfo",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            $('#form_id').val(result.id);
            $('#form_name').val(result.name);
            $('#form_project').val(result.project);
            $('#form_content').val(result.content);
            showModal("mouldModal");
        }
    });
}

/**
 * 刷新表格
 */
function reloadTable(){
    mouldTable.ajax.reload();
}

/**
 * 删除用户
 * @param id
 */
function deleteMould(id){
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
            url : contextPath+"mould/deleteEntity",
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
 * 初始化数据表
 */
function initTables() {

    if($('#database_ip').val()==''| $('#database_name').val()=='' || $('#database_username').val()=='' || $('#database_password').val()==''){
        swal("数据库连接信息不能为空!", "", "error");
        return;
    }
    $.ajax({
        type : "POST",
        data : {
            databaseIp:$('#database_ip').val(),
            databaseName:$('#database_name').val(),
            username:$('#database_username').val(),
            password:$('#database_password').val(),
        },
        dataType:"json",
        url : contextPath+"mould/getTables",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            console.log(result);
            var options = '';
            for (var i = 0;i<result.length;i++){
                options+='<option value="'+result[i]+'">'+result[i]+'</option>';
            }
            $('#table_select').html(options);
            $("#table_select").select2();
        }
    });
}
function createCode(){
    var checkedBox = $("input[name='datatablecheckbox']:checked");
    var ids = '';
    checkedBox.each(function () {
        if(ids == ''){
            ids = $(this).val();
        }else{
            ids += ',' + $(this).val();
        }
    });
    console.log(ids);
    console.log($('#table_select').val());
}



/**
 * 清空表单
 */
function clearForm(){
    $('#mouldForm')[0].reset();
    $('#form_id').val(null);
    $('#mouldForm').validate().resetForm();
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