var knowledgeWrongNumEchart;
var  knowledgeFrequencyEchart;
var keyKnowledgeEchart;
var studentId;
var startDate;//当前日期
var endDate;//前七天日期
$(document).ready(function(){
    studentId = $('#studentId').val();
    knowledgeWrongNumEchart = echarts.init(document.getElementById('knowledgeWrongNum'));
    knowledgeFrequencyEchart = echarts.init(document.getElementById('knowledgeFrequency'));
    keyKnowledgeEchart = echarts.init(document.getElementById('keyKnowledge'));
    //当前时间
    var curDate = new Date();
    //前七天
    var preDate = new Date(curDate.getTime() - 24*60*60*1000*7);
    endDate = curDate.format("yyyy-MM-dd");
    startDate = preDate.format("yyyy-MM-dd");
    $("#start_time").val(startDate);
    $("#end_time").val(endDate);

    initKnowledgeSelect();
    knowledgeWrongNum();
    keyKnowledge();



});



/**
 * 返回学生管理页面
 */
function backPrevious() {
    window.location.href=contextPath+"student/studentManage";
}

/**
 * 初始化下拉选
 */
function initKnowledgeSelect(){
    $.ajax({
        type : "POST",
        data : {studentId:studentId},
        url : contextPath + "wrongQuestion/getAllKnowledge",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType:"json",
        success : function(result){
            let options = '';
            for (let i = 0; i < result.length; i++) {
                options += '<option value="'+result[i].knowledge_code+'">'+result[i].knowledge_name+'</option>';
            }
            $("#knowledgeSelect").html(options)
            $("#knowledgeSelect").select2();
            knowledgeFrequency();
        }
    });
}
function changeKnowledgeSelect(){
    knowledgeFrequency();
}
/**
 * 知识点错误次数统计
 */
function knowledgeWrongNum() {
    startDate = $("#start_time").val();
    endDate = $("#end_time").val();
    if((startDate==''||startDate==null)||(endDate==''||endDate==null)){
        swal("时间不能为空!", "", "error");
        return;
    }
    $.ajax({
        type : "POST",
        data : {studentId:studentId,startDate:startDate,endDate:endDate},
        url : contextPath + "wrongQuestion/knowledgePointErrorNum",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType:"json",
        success : function(result){
            knowledgeWrongNumOption.xAxis[0].data = result.xAxis;
            knowledgeWrongNumOption.series[0].data=result.data;
            knowledgeWrongNumEchart.setOption(knowledgeWrongNumOption);
        }
    });
}
var knowledgeWrongNumOption = {
    color: ['#3398DB'],
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : [],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'错题数',
            type:'bar',
            barWidth: '60%',
            data:[]
        }
    ]
};

/**
 * 知识点频次分析
 */
function knowledgeFrequency(){
    $.ajax({
        type : "POST",
        data : {studentId:studentId,code:$("#knowledgeSelect").val()},
        url : contextPath + "wrongQuestion/knowledgeFrequency",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType:"json",
        success : function(result){
            knowledgeFrequencyOption.xAxis.data = result.xAxis;
            knowledgeFrequencyOption.series[0].data = result.data;
            knowledgeFrequencyEchart.setOption(knowledgeFrequencyOption);
        }
    });

}

var knowledgeFrequencyOption = {
    backgroundColor: '#FFF',
    grid: {
        top: '9%',
        bottom: '19%',
        left: '6%',
        right: '4%'
    },
    tooltip: {
        trigger: 'axis',
        label: {
            show: true
        }
    },
    xAxis: {
        boundaryGap: true, //默认，坐标轴留白策略
        axisLine: {
            show: false
        },
        splitLine: {
            show: false
        },
        axisTick: {
            show: false,
            alignWithLabel: true
        },
        data: [

        ]
    },
    yAxis: {
        minInterval: 1, //设置成1保证坐标轴分割刻度显示成整数。
        axisLine: {
            show: false
        },
        splitLine: {
            show: true,
            lineStyle: {
                type: 'dashed',
                color: 'rgba(33,148,246,0.2)'
            }
        },
        axisTick: {
            show: false
        },
        splitArea: {
            show: true,
            areaStyle: {
                color: 'rgb(245,250,254)'
            }
        }
    },
    series: [{
        type: 'line',
        symbol: 'circle',
        name:'错题数',
        symbolSize: 7,
        lineStyle: {
            color: 'rgb(33,148,246)',
            shadowBlur: 12,
            shadowColor: 'rgb(33,148,246,0.9)',
            shadowOffsetX: 1,
            shadowOffsetY: 1
        },
        itemStyle: {
            color: 'rgb(33,148,246)',
            borderWidth: 1,
            borderColor: '#FFF'
        },
        label: {
            show: false,
            distance: 1,
        },
        data: [

        ]
    }]
};

/**
 * 重点知识统计
 */
function keyKnowledge(){

    $.ajax({
        type : "POST",
        data : {studentId:studentId},
        url : contextPath + "wrongQuestion/keyKnowledge",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType:"json",
        success : function(result){
            keyKnowledgeOption.legend.data = result.legend;
            keyKnowledgeOption.series[0].data = result.data;
            keyKnowledgeEchart.setOption(keyKnowledgeOption);
        }
    });
}
var keyKnowledgeOption = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data:['重点知识1','重点知识2','重点知识3','非重点知识']
    },
    series: [
        {
            name:'错题数',
            type:'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '20',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:25, name:'重点知识1'},
                {value:310, name:'重点知识2'},
                {value:234, name:'重点知识3'},
                {value:135, name:'非重点知识'}
            ]
        }
    ]
};


//时间格式化
Date.prototype.format = function (format) {
    var args = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter

        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var i in args) {
        var n = args[i];

        if (new RegExp("(" + i + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
    }
    return format;
};
