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

    <!-- webuploader-->
    <link rel="stylesheet" href="${basePath}css/plugins/webuploader/webuploader.css">
    <link rel="stylesheet" href="${basePath}css/demo/webuploader-demo.css">
    <style>
        .uploadify ,.ke-container{
            float:left;
        }
    </style>

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>新闻资讯管理 / 更新</h5>
                </div>
                <div class="ibox-content">
                    <form:form method="post" action="saveOrUpdate" modelAttribute="newsInformation" class="form-horizontal">
                        <form:hidden path="id"/>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">标题:</label>

                            <div class="col-sm-10">
                                <form:input path="title" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">图片：</label>
                            <div class="col-sm-10">
                                <button class="btn btn-white" type="button" onclick="showUploadDialog('fileDelD','imageUrl')" data-toggle="modal" data-target="#myModal">
                                    <i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">上传</span>
                                </button>
                                <div id="fileDelD" class="uploader-list">
                                    <c:if test="${ !empty newsInformation.imageUrl }">
                                        <div id='titleImagePath'>
                                            <img height='100' width='100' style='margin-top: 10px;' src='${basePath}${newsInformation.imageUrl}' />
                                            <a href='#' class='uploadAct' onclick="del('fileDelD', 'imageUrl')">删除</a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <input type="hidden" name="imageUrl" id="imageUrl" value="${newsInformation.imageUrl}" style="width: 0;height: 0">
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">内容:</label>

                            <div class="col-sm-10">
                                <form:input path="content" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-3">
                                <input type="submit" class="btn btn-primary" value="保存" />
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="button" class="btn btn-white" onclick="window.location.href='${basePath}pages/contentInformation/newsInformation/list.jsp'" value="返回"/>
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

<!-- webuploader -->
<script src="${basePath}js/plugins/webuploader/webuploader.min.js"></script>
<script src="${basePath}js/demo/webuploader-demo.js"></script>

<script>
    $(document).ready(function () {
    });

    function showUploadDialog(fileDel,eid){
        picUpload({
            server: '${basePath}web/school/uploadFile;jsessionid=<%=session.getId()%>?param=pic',
            accept: {
                title: 'Images',//文字描述
                extensions: 'gif,jpg,jpeg,bmp,png',//允许的文件后缀
                mimeTypes: "image/*" //文件类型
            },
            formData:{},//文件上传请求的参数
            queueSizeLimit:'1',//上传数量限制，1：1张， 2：多张
            uploadBeforeSend:function(){//发送前触发

            },
            uploadSuccess:function(files,obj){//上传成功
                var showpath = "<div id='titleImagePath'><img height='100' width='100' style='margin-top: 10px;' src='${basePath}"+obj._raw + "'>";
                showpath += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                showpath += "<a href='#' class='uploadAct' onclick=\"del('"+fileDel+"','"+eid+"')\">删除</a></div>";
                $("#"+fileDel).html(showpath);
                $("#"+eid).val(obj._raw);
            },
            uploadError:function(){//文件上传
            }
        })
    }

    //删除上传文件
    function del(fileDel,eid){
        $('#' + fileDel).html("");
        $("#" + eid).val('');
    }
</script>

</body>
</html>
