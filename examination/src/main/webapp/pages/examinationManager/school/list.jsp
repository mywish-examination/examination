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
                    <h5>学院管理 / 列表</h5>
                </div>
                <div class="ibox-content">

                    <div class="jqGrid_wrapper">
                        <table id="schoolList"></table>
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
        // Examle data for jqGrid
        var mydata = [
            {
                id: "1",
                invdate: "2010-05-24",
                name: "test",
                note: "note",
                tax: "10.00",
                total: "2111.00"
            }
        ];

        // Configuration for jqGrid Example 1
        $("#schoolList").jqGrid({
            data: mydata,
            datatype: "local",
            height: 250,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 14,
            rowList: [10, 20, 30],
            colNames: ['', '学校名称', '学校主类型', '学校子类型', '学历层次', '创建时间', '操作'],
            colModel: [
                {name: 'id', index: 'id', width: '10%', sortable: false, hidden: true},
                {name: 'invdate', index: 'invdate', width: '10%', sortable: false},
                {name: 'name', index: 'name', width: '10%'},
                {name: 'name', index: 'name', width: '10%'},
                {name: 'name', index: 'name', width: '10%'},
                {name: 'name', index: 'name', width: '10%'},
                {name: 'act', index: 'act', width: '10%', sortable: false}
            ],
            pager: "#pager",
            viewrecords: true,
            caption: "学院列表",
            hidegrid: false,
            toolbar: [true,"top"],
            gridComplete: function() {
                var ids = jQuery("#schoolList").jqGrid('getDataIDs');
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
                    jQuery("#schoolList").jqGrid('setRowData',ids[i],{act:"<div class='jqgridContainer'>" + content + "</div>"});
                }
            },
            loadComplete: function(){
                //删除
                $(".shortcut_delete").click(function(){
                    var rowid = $(this).attr("id");
                    var rowdata = jQuery("#schoolList").jqGrid('getRowData',rowid);
                    var prompt = "删除失败";
                    var url = "${basePath}delete.json?id=" + rowid;
                    index = top.layer.confirm(prompt, {
                        btn: ["<msg:message code='button.confirm'/>", "<msg:message code='button.cancel'/>"] //按钮
                    }, function(){
                        $.ajax({
                            url:url,
                            type:'post',
                            timeout:'60000',
                            dataType:'json',
                            success:function(jsonData,textStatus){
                                if(textStatus == "success"){
                                    if(jsonData.status == "success"){
                                        top.layer.close(index);
                                        $("#schoolList").trigger("reloadGrid");
                                    }
                                }
                            }
                        });
                    }, function(){

                    });
                });
                //修改
                $(".shortcut_modify").click(function() {
                    var rowid = $(this).attr("id");
                    window.location.href = "${basePath}pages/examinationManager/school/modify.jsp?id=" + rowid;
                });
            }
        });

        // Add responsive to jqGrid
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#schoolList').setGridWidth(width);
        });

        var $content = $("<a></a>").attr("href","javascript:void(0)")
            .attr("id","create")
            .attr("class","btn btn-sm btn-primary")
            .append("创建");
        $("#t_schoolList").append("&nbsp;&nbsp;").append($("<span></span>").attr("class","jqgridContainer").append($content));
        $("#create","#t_schoolList").click(function(){
            window.location.href = "${basePath}pages/examinationManager/school/modify.jsp";
        });

    });
</script>

</body>
</html>
