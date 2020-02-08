<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <title>${title}</title>

    <link rel="shortcut icon" href="${basePath}favicon.ico"> <link href="${basePath}css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${basePath}css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <!-- jqgrid-->
    <link href="${basePath}css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">

    <link href="${basePath}css/animate.css" rel="stylesheet">
    <link href="${basePath}css/style.css?v=4.1.0" rel="stylesheet">
    <!-- webuploader-->
    <link rel="stylesheet" href="${basePath}css/plugins/webuploader/webuploader.css">
    <link rel="stylesheet" href="${basePath}css/demo/webuploader-demo.css">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>学科评估排名 / 列表</h5>
                </div>
                <div class="ibox-content">

                    <div class="jqGrid_wrapper">
                        <table id="subjectAssessmentRankingList"></table>
                        <div id="pager"></div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${basePath}js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}js/bootstrap.min.js?v=3.3.6"></script>

<!-- webuploader -->
<script src="${basePath}js/plugins/webuploader/webuploader.min.js"></script>
<script src="${basePath}js/demo/webuploader-demo.js"></script>

<!-- Peity -->
<script src="${basePath}js/plugins/peity/jquery.peity.min.js"></script>

<!-- jqGrid -->
<script src="${basePath}js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${basePath}js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>

<script src="${basePath}js/plugins/kindeditor/kindeditor.js"></script>

<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {

        $.jgrid.defaults.styleUI = 'Bootstrap';

        // Configuration for jqGrid Example 1
        $("#subjectAssessmentRankingList").jqGrid({
            url: "${basePath}web/subjectAssessmentRanking/listPage",
            ExpandColumn: 'name',
            ExpandColClick: true,
            height: 520,
            autowidth: true,
            shrinkToFit: true,
            datatype: 'json',
            rowNum: 10,
            prmNames: {
                page: "pager.current",
                rows: "pager.size",
            },
            mtype: "POST",
            colNames: ['', '学科门类', '学科', '院校名称', '全国排名', '等级', '档次', '操作'],
            colModel: [
                {name: 'id', index: 'id', hidden: true},
                {name: 'categoryName', index: 'categoryName', width: '15%', sortable: false},
                {name: 'subjectName', index: 'subjectName', width: '15%', sortable: false},
                {name: 'schoolName', index: 'schoolName', width: '10%', sortable: false},
                {name: 'nationalRankings', index: 'nationalRankings', width: '10%', sortable: false},
                {name: 'level', index: 'level', width: '10%', sortable: false},
                {name: 'grade', index: 'grade', width: '10%', sortable: false},
                {name: 'act', index: 'act', width: '15%', sortable: false}
            ],
            jsonReader : {
                root: "pager.records",
                page: "pager.current",
                total: "pager.pages",
                records: "pager.size",
                repeatitems: false
            },
            pager: "#pager",
            // viewrecords: true,
            // multiselect: true,
            caption: "学科评估排名列表",
            toolbar: [true,"top"],
            gridComplete: function() {
                var ids = jQuery("#subjectAssessmentRankingList").jqGrid('getDataIDs');
                for(var i=0;i < ids.length;i++){
                    var id = ids[i];
                    var content = "";
                    // 删除
                    content += "<a href='javascript:void(0);' title='删除' id='" + id + "' class='btn btn-link shortcut_delete' title='删除'>";
                    content += "<i class='fa fa-times'></i>删除";
                    content += "</a>";
                    jQuery("#subjectAssessmentRankingList").jqGrid('setRowData',ids[i],{act:"<div class='jqgridContainer'>" + content + "</div>"});
                }
            }
        });

        // Add responsive to jqGrid
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#subjectAssessmentRankingList').setGridWidth(width);
        });

        var $content = $("<a></a>").attr("href","javascript:void(0);")
            .attr("id","import")
            .attr("class","btn btn-sm btn-primary")
            .attr("data-target", "#myModal")
            .attr("data-toggle", "modal")
            .append("导入");
        $("#t_subjectAssessmentRankingList").append("&nbsp;&nbsp;").append($("<span></span>").attr("class","jqgridContainer").append($content));
        $("#import","#t_subjectAssessmentRankingList").click(function(){
            showUploadDialog();
        });

    });

    function showUploadDialog(){
        picUpload({
            server: '${basePath}web/subjectAssessmentRanking/uploadFileExcel;jsessionid=<%=session.getId()%>?param=pic',
            accept: {
                title: 'Images',//文字描述
                extensions: 'xls,xlsx',//允许的文件后缀
                mimeTypes: "*.xls;*.xlsx;" //文件类型
            },
            formData:{},//文件上传请求的参数
            queueSizeLimit:'1',//上传数量限制，1：1张， 2：多张
            uploadBeforeSend:function(){//发送前触发

            },
            uploadSuccess:function(files,obj){//上传成功
                $("#subjectAssessmentRankingList").trigger("reloadGrid");
            },
            uploadError:function(){//文件上传
            }
        })
    }
</script>

</body>
</html>
