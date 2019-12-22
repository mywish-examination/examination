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
                    <h5>学校专业管理 / 更新</h5>
                </div>
                <div class="ibox-content">
                    <form:form method="post" action="saveOrUpdate" modelAttribute="schoolMajor" class="form-horizontal">
                        <form:hidden path="id"/>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">学校名称:</label>

                            <div class="col-sm-10">
                                <form:select path="schoolId" class="form-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${schoolList}" var="it" >
                                        <form:option value="${it.id }">${it.name }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">专业名称:</label>

                            <div class="col-sm-10">
                                <form:select path="majorId" class="form-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${majorList}" var="it" >
                                        <form:option value="${it.id }">${it.name }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">招生人数:</label>

                            <div class="col-sm-10">
                                <form:input path="recruitNum" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">录取分数线:</label>

                            <div class="col-sm-10">
                                <form:input path="admissionScoreLine" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-3">
                                <input type="submit" class="btn btn-primary" value="保存" />
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="button" class="btn btn-white" onclick="window.location.href='${basePath}pages/examinationManager/schoolMajor/list.jsp'" value="返回"/>
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

<!-- 自定义js -->
<script src="${basePath}js/content.js?v=1.0.0"></script>

<script src="${basePath}js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script>
    $(document).ready(function () {

    });
</script>

</body>
</html>
