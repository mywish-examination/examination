
var gradeDialog = function(title,options,callback){
	var dialogHtml = [
	      			"<div class='modal in' id='gradeModal' tabindex='-1' role='dialog' aria-hidden='true'>",
	      				"<div class='modal-dialog'>",
	      					"<div class='modal-content'>",
								"<div class='modal-header'><b>" + title + "</b><button type='button' class='close' data-dismiss='modal'>×</button></div>",
	      						"<div class='modal-body'>",
									"<div>",
										"<label for='name'  class='col-sm-2' style='padding-top:4px'>评级:</label>",
										"<a class='score-demo col-sm-8'>",
											"<img src='./img/star-on.png' alt='1'>",
											"<img src='./img/star-on.png' alt='2'>",
											"<img src='./img/star-on.png' alt='3'>",
											"<img src='./img/star-on.png' alt='4'>",
											"<img src='./img/star-on.png' alt='5'>",
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
	dialog.css('top',$(window).scrollTop()+'px');
	dialog.find("#_dialogConfirm").on("click", function(){
		var grade_score = $("#grade_score").val();
		var grade_remark = $("#grade_remark").val();
		var paramss = {operateFlag:options.operateFlag,grade_orgType:options.grade_orgType,reqNo:options.reqNo,grade_score:grade_score,grade_remark:grade_remark};
		var url = "";
		if(options.flag == '1'){
			url = "gradeCompanyToOrg.do";
		}else if(options.flag == '2'){
			url = "gradeOrgToCompany.do";
		}
		App.request(url, {
			data:paramss,
			success:function(result){
				if(result.ec == "0"){
					Nuui.utils.alert("评级成功",{},function(){
						callback && callback();
					});
					$('#gradeModal').modal('hide');
				}else{
					Nuui.utils.alert(result.em);
					$('#gradeModal').modal('hide');
				}
			}
		});
	});
	$('.score-demo img').each(function(){
		  $(this).hover(function(){
		  		$('.score-demo img').attr('src','./img/star-off.png');
		  		for(var i=0;i<$(this).attr('alt');i++){
		  			$('.score-demo img').eq(i).attr('src','./img/star-on.png');
		  		}
		  		$('#grade_score').val($(this).attr('alt'));
		  })
	})
}