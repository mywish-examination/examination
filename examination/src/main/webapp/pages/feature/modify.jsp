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
                    <h5>特征管理 / 更新</h5>
                </div>
                <div class="ibox-content">
                    <form:form method="post" action="saveOrUpdate" modelAttribute="feature" class="form-horizontal">
                        <form:hidden path="id"/>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">特征名称:</label>

                            <div class="col-sm-10">
                                <form:input path="featureName" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">特征类型:</label>

                            <div class="col-sm-10">
                                <form:select path="featureType" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${sys_dict.dict_feature_type}" var="it"  >
                                        <form:option value="${it.dictNum }">${it.dictValue }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">特征编号:</label>

                            <div class="col-sm-10">
                                <form:select path="featureCode" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${sys_dict_name}" var="it"  >
                                        <form:option value="${it.key }">${it.value }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">特征选项:</label>

                            <div class="col-sm-10">
                                <form:select path="featureOption" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:if test="${ not empty feature.featureCode }">
                                        <c:forEach items="${featureOptionList}" var="it"  >
                                            <c:if test="${it.dictNum == feature.featureOption}">
                                                <form:option value="${it.dictNum }" selected="selected">${it.dictValue }</form:option>
                                            </c:if>
                                            <c:if test="${it.dictNum != feature.featureOption}">
                                                <form:option value="${it.dictNum }">${it.dictValue }</form:option>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </form:select>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-3">
                                <input type="submit" class="btn btn-primary" value="保存" />
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="button" class="btn btn-white" onclick="window.location.href='${basePath}pages/feature/list.jsp'" value="返回"/>
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
<script>
    $(document).ready(function () {
        $("#featureCode").change(function(){
            var value = $(this).val();
            $.ajax({
                //请求方式
                type : "GET",
                //请求的媒体类型
                contentType: "application/json;charset=UTF-8",
                //请求地址
                url : "${basePath}web/dataDictionary/listByCode?code=" + value,
                //请求成功
                success : function(result) {
                    var content = "<option =value=''>请选择</option>";
                    var dict;
                    for(dict in result) {
                        content += "<option value='"+result[dict].dictNum+"'>"+result[dict].dictValue+"</option>";
                    }
                    $("#featureOption").html("");
                    $("#featureOption").append(content);
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    console.log(e.status);
                    console.log(e.responseText);
                }
            });
        });

    });
</script>

</body>
</html>
