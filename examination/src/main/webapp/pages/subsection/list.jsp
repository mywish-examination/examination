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
                    <h5>分段管理 / 列表</h5>
                </div>
                <div class="ibox-content">

                    <div class="jqGrid_wrapper">
                        <table id="subsectionList"></table>
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

<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {

        $.jgrid.defaults.styleUI = 'Bootstrap';

        // Configuration for jqGrid Example 1
        $("#subsectionList").jqGrid({
                url: "${basePath}web/subsection/listPage",
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
            colNames: ['', '分数', '人数', '位次', '年份', '科类', '操作'],
            colModel: [
                {name: 'id', index: 'id', hidden: true},
                {name: 'score', index: 'score', width: '15%', sortable: false},
                {name: 'num', index: 'num', width: '15%', sortable: false},
                {name: 'rank', index: 'rank', width: '10%', sortable: false},
                {name: 'year', index: 'year', width: '10%', sortable: false},
                {name: 'subjectTypeName', index: 'subjectTypeName', width: '10%', sortable: false},
                {name: 'act', index: 'act', width: '15%', sortable: false}
            ],
            jsonReader : {
                root: "pager.records",
                page: "pager.current",
                total: "pager.pages",
                records: "pager.size",
                repeatitems: false
            },
            multiselect: true,//复选框
            pager: "#pager",
            // viewrecords: true,
            // multiselect: true,
            caption: "分段列表",
            toolbar: [true,"top"],
            gridComplete: function() {
                var ids = jQuery("#subsectionList").jqGrid('getDataIDs');
                for(var i=0;i < ids.length;i++){
                    var id = ids[i];
                    var content = "";
                    // 删除
                    content += "<a href='javascript:void(0);' title='删除' id='" + id + "' class='btn btn-link shortcut_delete' title='删除'>";
                    content += "<i class='fa fa-times'></i>删除";
                    content += "</a>";
                    jQuery("#subsectionList").jqGrid('setRowData',ids[i],{act:"<div class='jqgridContainer'>" + content + "</div>"});
                }
            }
        });

        // Add responsive to jqGrid
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#subsectionList').setGridWidth(width);
        });

        var $content = $("<a></a>").attr("href","javascript:void(0);")
            .attr("id","deleteBatch")
            .attr("class","btn btn-sm btn-primary")
            .append("删除");
        $("#t_subsectionList").append("&nbsp;&nbsp;").append($("<span></span>").attr("class","jqgridContainer").append($content));
        $("#deleteBatch","#t_subsectionList").click(function(){
            //获取多选到的id集合
            var ids = $("#subsectionList").jqGrid("getGridParam", "selarrrow");
            if(ids == null || ids == "") return;

            var prompt = "确定要删除所选择的记录吗？";
            var url = "${basePath}web/subsection/deleteBatch?ids=" + ids;
            index = top.layer.confirm(prompt, {
                btn: ["确认", "取消"] //按钮
            }, function(){
                $.ajax({
                    url:url,
                    type:'POST',
                    timeout:'60000',
                    dataType:'json',
                    success:function(jsonData){
                        if(jsonData.status == 'success') {
                            top.layer.close(index);
                            $("#subsectionList").trigger("reloadGrid");
                        }
                    }
                });
            }, function(){
            });
        });

        $content = $("<a></a>").attr("href","javascript:void(0);")
            .attr("id","deleteAll")
            .attr("class","btn btn-sm btn-primary")
            .append("清空全部");
        $("#t_subsectionList").append("&nbsp;&nbsp;").append($("<span></span>").attr("class","jqgridContainer").append($content));
        $("#deleteAll","#t_subsectionList").click(function(){
            var prompt = "确定要删除所选择的记录吗？";
            var url = "${basePath}web/subsection/deleteAll";
            index = top.layer.confirm(prompt, {
                btn: ["确认", "取消"] //按钮
            }, function(){
                $.ajax({
                    url:url,
                    type:'POST',
                    timeout:'60000',
                    dataType:'json',
                    success:function(jsonData){
                        if(jsonData.status == 'success') {
                            top.layer.close(index);
                            $("#subsectionList").trigger("reloadGrid");
                        }
                    }
                });
            }, function(){
            });
        });

        $content = $("<a></a>").attr("href","javascript:void(0);")
            .attr("id","import")
            .attr("class","btn btn-sm btn-primary")
            .attr("data-target", "#myModal")
            .attr("data-toggle", "modal")
            .append("导入");
        $("#t_subsectionList").append("&nbsp;&nbsp;").append($("<span></span>").attr("class","jqgridContainer").append($content));
        $("#import","#t_subsectionList").click(function(){
            showUploadDialog();
        });

    });

    function showUploadDialog(){
        picUpload({
            server: '${basePath}web/subsection/uploadFileExcel;jsessionid=<%=session.getId()%>?param=pic',
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
                $("#subsectionList").trigger("reloadGrid");
            },
            uploadError:function(){//文件上传
            }
        })
    }
</script>

</body>
</html>
