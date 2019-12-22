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

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>学校专业管理 / 列表</h5>
                </div>
                <div class="ibox-content">

                    <div class="jqGrid_wrapper">
                        <table id="majorList"></table>
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
        $("#majorList").jqGrid({
            url: "${basePath}web/schoolMajor/listPage",
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
            colNames: ['', '学校名称', '专业名称', '招生人数', '录取分数线', '操作'],
            colModel: [
                {name: 'id', index: 'id', width: '10%', sortable: false, hidden: true},
                {name: 'schoolId', index: 'schoolId', width: '10%', sortable: false},
                {name: 'majorId', index: 'majorId', width: '10%', sortable: false},
                {name: 'recruitNum', index: 'recruitNum', width: '10%', sortable: false},
                {name: 'admissionScoreLine', index: 'admissionScoreLine', width: '10%', sortable: false},
                {name: 'act', index: 'act', width: '10%', sortable: false}
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
            caption: "学院列表",
            toolbar: [true,"top"],
            gridComplete: function() {
                var ids = jQuery("#majorList").jqGrid('getDataIDs');
                for(var i=0;i < ids.length;i++){
                    var id = ids[i];
                    var content = "";
                    // 修改
                    content += "<a href='javascript:void(0);' title='修改' id='" + id + "' class='btn btn-link shortcut_modify' title='修改'>";
                    content += "<i class='fa fa-pencil-square-o'></i>修改";
                    content += "</a>";
                    // 删除
                    content += "<a href='javascript:void(0);' title='删除' id='" + id + "' class='btn btn-link shortcut_delete' title='删除'>";
                    content += "<i class='fa fa-times'></i>删除";
                    content += "</a>";
                    jQuery("#majorList").jqGrid('setRowData',ids[i],{act:"<div class='jqgridContainer'>" + content + "</div>"});
                }
            },
            loadComplete: function(){
                //删除
                $(".shortcut_delete").click(function(){
                    var rowid = $(this).attr("id");
                    var prompt = "确定要删除所选择的记录吗？";
                    var url = "${basePath}web/schoolMajor/delete?id=" + rowid;
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
                                    $("#majorList").trigger("reloadGrid");
                                }
                            }
                        });
                    }, function(){
                    });
                });
                //修改
                $(".shortcut_modify").click(function() {
                    var rowid = $(this).attr("id");
                    window.location.href = "${basePath}web/schoolMajor/detail?id=" + rowid;
                });
            }
        });

        // Add responsive to jqGrid
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#majorList').setGridWidth(width);
        });

        var $content = $("<a></a>").attr("href","javascript:void(0)")
            .attr("id","create")
            .attr("class","btn btn-sm btn-primary")
            .append("创建");
        $("#t_majorList").append("&nbsp;&nbsp;").append($("<span></span>").attr("class","jqgridContainer").append($content));
        $("#create","#t_majorList").click(function(){
            window.location.href = "${basePath}web/schoolMajor/detail";
        });

    });
</script>

</body>
</html>
