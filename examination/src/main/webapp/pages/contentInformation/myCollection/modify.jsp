<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link href="${basePath}css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${basePath}css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
    <link href="${basePath}css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>我的收藏 / 创建</h5>
                </div>
                <div class="ibox-content">
                    <form method="post" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学校名称:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学校主类型:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学校子类型:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">曾用名:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">主管部门:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校隶属:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学历层次:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校官网链接:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校属性:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">基本信息:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校招办链接:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">招生章程链接:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">双一流学科:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">院校图标:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">办学层次:</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">省份:</label>

                            <div class="col-sm-10">
                                <select class="form-control m-b" name="account">
                                    <option>选项 1</option>
                                    <option>选项 2</option>
                                    <option>选项 3</option>
                                    <option>选项 4</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <button class="btn btn-primary" type="button"
                                        onclick="javascript: window.location.href='${basePath}pages/examinationManager/school/list.jsp'">保存</button>
                                <button class="btn btn-white" type="button"
                                        onclick="javascript: window.location.href='${basePath}pages/examinationManager/school/list.jsp'">取消</button>
                            </div>
                        </div>
                    </form>
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
        $('.summernote').summernote({
            lang: 'zh-CN'
        });
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    });
</script>

</body>
</html>
