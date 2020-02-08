<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${title}</title>

    <link rel="shortcut icon" href="${basePath}favicon.ico">
    <link href="${basePath}css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${basePath}css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${basePath}css/animate.css" rel="stylesheet">
    <link href="${basePath}css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>我的收藏 / 更新</h5>
                </div>
                <div class="ibox-content">
                    <form:form method="post" action="saveOrUpdate" modelAttribute="myCollection" class="form-horizontal">
                        <form:hidden path="id"/>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校代码:</label>

                            <div class="col-sm-10">
                                <form:input path="educationalCode" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">专业名称:</label>

                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="majorName" value="${myCollection.majorName}" data-id="${myCollection.majorId}">
                                    <form:hidden path="majorId" />
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                        </ul>
                                    </div>
                                    <!-- /btn-group -->
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">用户名称:</label>

                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="userName" value="${myCollection.userName}" data-id="${myCollection.userId}">
                                    <form:hidden path="userId" />
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                        </ul>
                                    </div>
                                    <!-- /btn-group -->
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-3">
                                <input type="button" class="btn btn-primary" value="保存" id="save"/>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="button" class="btn btn-white" onclick="window.location.href='${basePath}pages/myCollection/list.jsp'" value="返回"/>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${basePath}js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}js/bootstrap.min.js?v=3.3.6"></script>

<!-- SUMMERNOTE -->
<script src="${basePath}js/plugins/summernote/summernote.min.js"></script>
<script src="${basePath}js/plugins/summernote/summernote-zh-CN.js"></script>
<!-- iCheck -->
<script src="${basePath}js/plugins/iCheck/icheck.min.js"></script>
<script src="${basePath}js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script>
    $(document).ready(function () {
        $("#save").click(function() {
            $("#majorId").val($("#majorName").attr("data-id"));
            $("#userId").val($("#userName").attr("data-id"));
            $("form#myCollection").submit();
        });
    });

    /**
     * 专业
     */
    var testBsSuggest2 = $("#majorName").bsSuggest({
        //url: "/rest/sys/getuserlist?keyword=",
        url: "${basePath}web/major/listSuggest",
        /*effectiveFields: ["userName", "shortAccount"],
        searchFields: [ "shortAccount"],
        effectiveFieldsAlias:{userName: "姓名"},*/
        showBtn: false,
        idField: "id",
        keyField: "name",
        effectiveFields: ["name"],
    }).on('onDataRequestSuccess', function (e, result) {
        console.log('onDataRequestSuccess: ', result);
    }).on('onSetSelectValue', function (e, keyword) {
        console.log('onSetSelectValue: ', keyword);
    }).on('onUnsetSelectValue', function (e) {
        console.log("onUnsetSelectValue");
    });

    /**
     * 用户
     */
    var testBsSuggest3 = $("#userName").bsSuggest({
        //url: "/rest/sys/getuserlist?keyword=",
        url: "${basePath}web/user/listSuggest",
        /*effectiveFields: ["userName", "shortAccount"],
        searchFields: [ "shortAccount"],
        effectiveFieldsAlias:{userName: "姓名"},*/
        showBtn: false,
        idField: "id",
        keyField: "trueName",
        effectiveFields: ["trueName"],
    }).on('onDataRequestSuccess', function (e, result) {
        console.log('onDataRequestSuccess: ', result);
    }).on('onSetSelectValue', function (e, keyword) {
        console.log('onSetSelectValue: ', keyword);
    }).on('onUnsetSelectValue', function (e) {
        console.log("onUnsetSelectValue");
    });
</script>

</body>
</html>
