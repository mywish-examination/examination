<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    session.setAttribute("basePath", basePath);

    String title = "报考之家";
    session.setAttribute("title", title);
%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>报考之家 - 主页</title>

    <link rel="shortcut icon" href="favicon.ico">
    <link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="img-circle" src="img/profile_small.jpg" /></span>
                        <span class="block m-t-xs"><strong class="font-bold">${cookie.loginName.value}</strong></span>
                        <span class="text-muted text-xs block">${cookie.trueName.value}</span>
                    </div>
                    <div class="logo-element">H+
                    </div>
                </li>
                <li>
                    <a class="J_menuItem" href="${basePath}pages/school/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">学院管理</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/major/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">专业管理</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/schoolMajor/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">学校专业管理</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/volunteer/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">志愿档案</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/historyAdmissionData/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">历年录取数据</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/schoolPlan/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">院校计划</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/historyDataControlLine/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">历史数据控制线</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/subjectAssessmentRanking/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">学科评估排名</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/subsection/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">分段管理</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/newsInformation/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">新闻资讯</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/feedback/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">意见反馈</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/myCollection/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">我的收藏</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/user/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">用户管理</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/dataDictionary/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">数据字典</span>
                        <span class="fa "></span>
                    </a>
                    <a class="J_menuItem" href="${basePath}pages/feature/list.jsp">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">特征管理</span>
                        <span class="fa "></span>
                    </a>
                </li>

            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i
                        class="fa fa-bars"></i> </a>
                </div>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id=""></a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="${basePath}web/user/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="index_v1.html?v=4.0" frameborder="0"
                    data-id="index_v1.html" style="display: none;" seamless></iframe>
<%--            <iframe class="J_iframe" name="iframe1" width="100%" height="100%" src="layouts.html" frameborder="0"--%>
<%--                    data-id="layouts.html" seamless></iframe>--%>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2019-2020 <a href="http://www.zi-han.net/" target="_blank">zihan's blog</a>
            </div>
        </div>
    </div>
    <!--右侧部分结束-->
</div>

<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="js/hplus.js?v=4.1.0"></script>
<script type="text/javascript" src="js/contabs.js"></script>

<!-- 第三方插件 -->
<script src="js/plugins/pace/pace.min.js"></script>
</body>
</html>
