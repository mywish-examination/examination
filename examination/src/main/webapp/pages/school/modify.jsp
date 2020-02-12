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
                    <h5>学院管理 / 更新</h5>
                </div>
                <div class="ibox-content">
                    <form:form method="post" action="saveOrUpdate" modelAttribute="school" class="form-horizontal">
                        <form:hidden path="id"/>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校代码:</label>

                            <div class="col-sm-10">
                                <form:input path="educationalCode" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学校名称:</label>

                            <div class="col-sm-10">
                                <form:input path="name" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学校主类型:</label>

                            <div class="col-sm-10">
                                <form:select path="mainType" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${sys_dict.dict_school_main_type}" var="it"  >
                                        <form:option value="${it.dictNum }">${it.dictValue }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学校子类型:</label>

                            <div class="col-sm-10">
                                <form:select path="childrenType" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${sys_dict.dict_school_children_type}" var="it"  >
                                        <form:option value="${it.dictNum }">${it.dictValue }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">曾用名:</label>

                            <div class="col-sm-10">
                                <form:input path="onceName" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注:</label>

                            <div class="col-sm-10">
                                <form:input path="remark" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">主管部门:</label>

                            <div class="col-sm-10">
                                <form:select path="mainManagerDepartment" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${sys_dict.dict_school_main_manager_department}" var="it"  >
                                        <form:option value="${it.dictNum }">${it.dictValue }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校隶属:</label>

                            <div class="col-sm-10">
                                <form:select path="educationalInstitutionsSubjection" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${sys_dict.dict_school_educational_institutions_subjection}" var="it"  >
                                        <form:option value="${it.dictNum }">${it.dictValue }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学历层次:</label>

                            <div class="col-sm-10">
                                <form:select path="educationLevel" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${sys_dict.dict_school_education_level}" var="it"  >
                                        <form:option value="${it.dictNum }">${it.dictValue }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校官网链接:</label>

                            <div class="col-sm-10">
                                <form:input path="educationalInstitutionsWebsite" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校属性:</label>

                            <div class="col-sm-10">
                                <form:checkboxes path="educationalInstitutionsAttribute" items="${sys_dict.dict_school_educational_institutions_attribute}" itemValue="${dictValue}" />
                                <form:select path="educationalInstitutionsAttribute" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${sys_dict.dict_school_educational_institutions_attribute}" var="it"  >
                                        <form:option value="${it.dictNum }">${it.dictValue }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">基本信息:</label>

                            <div class="col-sm-10">
                                <form:input path="baseInfo" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校招办链接:</label>

                            <div class="col-sm-10">
                                <form:input path="educationalInstitutionsRecruitUrl" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">招生章程链接:</label>

                            <div class="col-sm-10">
                                <form:input path="recruitConstitutionUrl" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">双一流学科:</label>

                            <div class="col-sm-10">
                                <form:input path="doubleFirstClassSubject" class="form-control" maxlength="250" onchange="this.value=$.trim(this.value)"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校图片：</label>
                            <div class="col-sm-10">
                                <button class="btn btn-white" type="button" onclick="showUploadDialog('fileDelD','educationalInstitutionsIconUrl')" data-toggle="modal" data-target="#myModal">
                                    <i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">上传</span>
                                </button>
                                <div id="fileDelD" class="uploader-list">
                                    <c:if test="${ !empty school.educationalInstitutionsIconUrl }">
                                        <div id='titleImagePath'>
                                            <img height='100' width='100' style='margin-top: 10px;' src='${basePath}${school.educationalInstitutionsIconUrl}' />
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <a href='#' class='uploadAct' onclick="del('fileDelD', 'educationalInstitutionsIconUrl')">删除</a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <input type="hidden" name="educationalInstitutionsIconUrl" id="educationalInstitutionsIconUrl" value="${school.educationalInstitutionsIconUrl}" style="width: 0;height: 0">
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">办学层次:</label>

                            <div class="col-sm-10">
                                <form:select path="schoolRunningLevel" class="form-control valid-control">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${sys_dict.dict_school_school_running_level}" var="it"  >
                                        <form:option value="${it.dictNum }">${it.dictValue }</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">省份:</label>

                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="provinceName" value="${school.provinceName}" data-id="${school.provinceId}">
                                    <form:hidden path="provinceId" />
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
                                <input type="button" class="btn btn-white" onclick="window.location.href='${basePath}pages/school/list.jsp'" value="返回"/>
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

<!-- iCheck -->
<script src="${basePath}js/plugins/iCheck/icheck.min.js"></script>

<script src="${basePath}js/content.js?v=1.0.0"></script>
<script src="${basePath}js/plugins/kindeditor/kindeditor.js"></script>
<script src="${basePath}js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script>
    $(document).ready(function () {
        $("#save").click(function() {
            $("#provinceId").val($("#provinceName").attr("data-id"));
            $("form#school").submit();
        });
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

    /**
     * 城市
     */
    var testBsSuggest = $("#provinceName").bsSuggest({
        //url: "/rest/sys/getuserlist?keyword=",
        url: "${basePath}web/city/listSuggest",
        /*effectiveFields: ["userName", "shortAccount"],
        searchFields: [ "shortAccount"],
        effectiveFieldsAlias:{userName: "姓名"},*/
        showBtn: false,
        idField: "id",
        keyField: "cityName",
        effectiveFields: ["cityName"],
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
