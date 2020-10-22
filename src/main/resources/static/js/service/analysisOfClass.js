var distributionEchart;
var wrongKnowledgeTop5Echart;
var wrongProportionEchart;
var classId;
var startDate;//当前日期
var endDate;//前七天日期
$(document).ready(function(){
    //当前时间
    var curDate = new Date();
    //前七天
    var preDate = new Date(curDate.getTime() - 24*60*60*1000*7);
    endDate = curDate.format("yyyy-MM-dd");
    startDate = preDate.format("yyyy-MM-dd");
    $("#start_time").val(startDate);
    $("#end_time").val(endDate);
    classId = $('#classId').val();

    distributionEchart = echarts.init(document.getElementById('distribution'));
    wrongKnowledgeTop5Echart = echarts.init(document.getElementById('wrongKnowledgeTop5'));
    wrongProportionEchart = echarts.init(document.getElementById('wrongProportion'));

    initWrongQuestionDistribution();
    initTop5();
    initKnowledgeSelect();

});



/**
 * 返回学生管理页面
 */
function backPrevious() {
    window.location.href=contextPath+"student/studentManage";
}

/**
 * 初始化  错题分布图
 */
function initWrongQuestionDistribution(){
    $.ajax({
        type : "POST",
        data : {classId:classId,startDate:startDate,endDate:endDate},
        url : contextPath + "classes/wrongQuestionDistribution",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType:"json",
        success : function(result){
            distributionData = result.data;
            distributionOption.legend.data = result.legend;
            var seriesData = [];
            for (var i = 0; i < result.legend.length; i++) {
                var seriesObj = {};
                seriesObj.name = result.legend[i];
                seriesObj.data = result.data[i];
                seriesObj.type= 'scatter';
                seriesObj.symbolSize = function (data) {
                    return 20;
                };
                seriesObj.itemStyle ={
                    normal: {
                        shadowBlur: 10,
                            shadowColor: 'rgba(25, 100, 150, 0.5)',
                            shadowOffsetY: 5,
                    }
                };
                seriesData.push(seriesObj);
            }
            distributionOption.series = [];
            distributionOption.series = seriesData;
            distributionEchart.setOption(distributionOption);
        }
    });


}
var distributionData = [];

var distributionOption = {

    legend: {
        right: 5,
        data: []
    },
    xAxis: {
        name:'错题间隔时间（天）',
        splitLine: {
            lineStyle: {
                type: 'dashed'
            }
        },
        minInterval: 1, //设置成1保证坐标轴分割刻度显示成整数。
    },
    yAxis: {
        name:'错题个数',
        splitLine: {
            lineStyle: {
                type: 'dashed'
            }
        },
        minInterval: 1, //设置成1保证坐标轴分割刻度显示成整数。
        scale: true
    },
    tooltip: {
        padding: 10,
        backgroundColor: '#222',
        borderColor: '#777',
        borderWidth: 1,
        formatter: function (obj) {
            var value = obj.value;
            // return value;
            return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">'
                + '学生：'+ value[2]
                + '</div>'
                + '错题数：'+value[1]+ '<br>'
                + '错误间隔时长：'+value[0]+ '天<br>';

        }
    },
    series: []
};
//top 5
function initTop5(){
    $.ajax({
        type : "POST",
        data : {classId:classId,startDate:startDate,endDate:endDate},
        url : contextPath + "classes/wrongQuestionTop5",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType:"json",
        success : function(result){
            wrongKnowledgeTop5Option.yAxis[0].data = result.yAxisData;
            wrongKnowledgeTop5Option.series[0].data = result.seriesData;
            wrongKnowledgeTop5Echart.setOption(wrongKnowledgeTop5Option);
        }
    });

}
var wrongKnowledgeTop5Option = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: null // 默认为直线，可选为：'line' | 'shadow'
        },
        formatter: '<div style="text-align: center;">犯错学生数量</div>{b} : {c}'
    },
    grid: {
        left: '3%',
        right: '4%',
        top: '10%',
        height: 280, //设置grid高度
        containLabel: true
    },
    xAxis: [{
        type: 'value',
        axisLabel: {
            show: false
        },
        axisTick: {
            show: false
        },
        axisLine: {
            show: false
        },
        splitLine: {
            show: false
        }

    }],
    yAxis: [{
        type: 'category',
        boundaryGap: true,
        axisTick: {
            show: true
        },
        axisLabel: {
            interval: null
        },
        data: ['北京', '长沙', '天津', '太原', '石家庄', '广州', '重庆'],
        splitLine: {
            show: false
        }
    }],
    series: [{
        name: '流量',
        type: 'bar',
        barWidth: 25,
        label: {
            normal: {
                show: true,
                position: 'right'
            }

        },
        data: [160, 170, 240, 264, 300, 520, 610]
    }]
};

/**
 * 初始化图表
 */
function initChart(){
    startDate = $("#start_time").val();
    endDate = $("#end_time").val();
    if((startDate==''||startDate==null)||(endDate==''||endDate==null)){
        swal("时间不能为空!", "", "error");
        return;
    }
    initWrongQuestionDistribution();
    initTop5();
    initKnowledgeSelect();
}

/**
 * 初始化下拉选
 */
function initKnowledgeSelect(){
    $.ajax({
        type : "POST",
        data : {classId:classId,startDate:startDate,endDate:endDate},
        url : contextPath + "classes/getAllWrongKnowledge",
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
            initWrongProportion();
        }
    });
}

/**
 * 选择
 */
function changeKnowledgeSelect(){
    initWrongProportion();
}
/**
 * 初始化知识点错题人数占比
 */
function initWrongProportion(){
    var code = $("#knowledgeSelect").val();
    $.ajax({
        type : "POST",
        data : {classId:classId,code:code,startDate:startDate,endDate:endDate},
        url : contextPath + "classes/wrongProportion",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType:"json",
        success : function(result){
            wrongProportionOption.title.text = '错误人数：'+result.wrongNum +' / 全班人数：'+result.allNum;
            wrongProportionOption.series[0].data = [result.proportion];
            wrongProportionEchart.setOption(wrongProportionOption);
        }
    });
}
var wrongProportionOption = {
    title: {
        text: '2017全球手机市场份额',
    },
    series: [{
        type: 'liquidFill',
        radius: '80%',
        data: [0.5, 0.45, 0.4],
        label: {
            normal: {
                // textStyle: {
                color: 'red',
                insideColor: 'yellow',
                fontSize: 50
                // }
            }
        }
    }]
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
