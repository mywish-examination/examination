var $parentNode = window.parent.document;

function $childNode(name) {
    return window.frames[name]
}
Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
// tooltips
$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
if (top == this) {
    var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.html?v=4.0" title="返回首页"><i class="fa fa-home"></i></a></div>';
    $('body').append(gohome);
}

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}
//得到日期，可以对年进行增减  yyyy-MM-dd
function getYearDate(year){
    var pre = new Date();
    pre.setFullYear(pre.getFullYear()+year);
    var years = pre.getFullYear();
    var month = pre.getMonth()+1;
    if(month<10){
        month = "0"+month;
    }
    var day = pre.getDate();
    if(day<10){
        day="0"+day;
    }
    return years+"-"+month+"-"+day;
}
//得到日期，可以对月进行增减  yyyy-MM-dd
function getMonthDate(month) {
    var date = new Date();
    date.setMonth(date.getMonth()+month);
    return date.format('yyyy-MM-dd');
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8,
        })
        .disableSelection();
};

function initSummernote(b,s){
	$('.summernote').summernote({
		lang: 'zh-CN',
		height: 300,
		width: '90%',
		onImageUpload: function(files,editor,welEditable) {
			var index = top.layer.load(1, {
				  shade: [0.5,'#fff'] //0.1透明度的白色背景
				});
			var data = new FormData();
		    data.append("file", files[0]);
		 	$.ajax({
				data: data,
				async:false,
				type: "POST",
				url: b+"/summernote/uploadimg",
				cache: false,
				contentType: false,
				processData: false,
				success: function (data) {
					console.log("111"+data.responseText);
					editor.insertImage(welEditable, s+data.responseText); 
					top.layer.close(index);
				}, 
				error:function(data){
					console.log("222"+data.responseText);
					editor.insertImage(welEditable, s+data.responseText); 
					top.layer.close(index);
				}
			});
	    }
	});
	
}
