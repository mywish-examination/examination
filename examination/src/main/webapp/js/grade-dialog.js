
var gradeDialog = function(baseUrl,title,options,callback,requestUrl){
	var dialogHtml = [
	      			"<div class='modal in' id='gradeModal' tabindex='-1' role='dialog' aria-hidden='true' style='display: block'>",
	      				"<div class='modal-dialog'>",
	      					"<div class='modal-content'>",
								"<div class='modal-header'><b>" + title + "</b><button type='button' class='close' data-dismiss='modal'>×</button></div>",
	      						"<div class='modal-body'>",
									"<div>",
										"<label for='name'  class='col-sm-2' style='padding-top:4px'>评级:</label>",
										"<a class='score-demo col-sm-8'>",
											"<img src='"+baseUrl+"/img/star-on.png' alt='1'>",
											"<img src='"+baseUrl+"/img/star-on.png' alt='2'>",
											"<img src='"+baseUrl+"/img/star-on.png' alt='3'>",
        									"<img src='"+baseUrl+"/img/star-on.png' alt='4'>",
        									"<img src='"+baseUrl+"/img/star-on.png' alt='5'>",
											"<input type='hidden' id='grade_score' value='5'>",
										"</a>",
									"</div>",
									"<div style='width:100%;height:auto;overflow:hidden;padding-top:10px'>",
										"<label for='email' class='col-sm-2'>备注:</label>",
										"<div class='col-sm-8'><textarea row='3' class='form-control' id='grade_remark' name='grade_remark' ></textarea></div>",
									"</div>",
									"<input type='hidden' id='reqNo' />",
								"</div>",
								"<div class='modal-footer'>",
									"<a class='btn btn-default' data-dismiss='modal'>取消</a>",
	    							"<a class='btn btn-primary' id='_dialogConfirm'>确定</a>",
								"</div>",
	      					"</div>",
	      				"</div>",
	      			"</div>"
	      		].join("");

	if($("#gradeModal")){
		$("#gradeModal").remove();
	};
	var dialog = $(dialogHtml).appendTo(document.body);
    dialog.css('top','20px');
	dialog.find("#_dialogConfirm").on("click", function(){
		var grade_score = $("#grade_score").val();//星星
		var grade_remark = $("#grade_remark").val();//备注
		var paramss = {operateFlag:options.operateFlag,grade_orgType:options.grade_orgType,reqNo:options.reqNo,grade_score:grade_score,grade_remark:grade_remark};
		var url = "./evaluate/saveOrUpdate";
		if(requestUrl!='' && requestUrl!=null && requestUrl!=undefined){
            url = requestUrl;
		}
		if(options.flag == '1'){
            $.ajax({
                url:url,
                async:false,   //是否为异步请求
                cache:false,  //是否缓存结果
                type:"post",
                data:{"score":grade_score,"toOrgID":options.toOrgID,"sourceType":"1","sourceID":options.sourceID},
                dataType:"json",
                success : function(data){
                    if(data.status == "success"){
                        parent.layer.msg("评价成功");
                        $('#gradeModal').modal('hide');
                        callback&&callback();
                    }
                }
            });
		}else if(options.flag == '2'){

		}
	});
	$('.score-demo img').each(function(){
		  $(this).hover(function(){
		  		$('.score-demo img').attr('src',baseUrl+'/img/star-off.png');
		  		for(var i=0;i<$(this).attr('alt');i++){
		  			$('.score-demo img').eq(i).attr('src',baseUrl+'/img/star-on.png');
		  		}
		  		$('#grade_score').val($(this).attr('alt'));
		  })
	})
}