$(function(){
	$("input[type='submit']").blur(function(){
		$("#msg").html("");
	});
	$("input[type='submit']").click(function(){
		if($("input[type='file']").val()=="") {
			$("#msg").html("你的文件呢？");
			return;
		}
		var formData = new FormData();
		formData.append("name",$("input[name='name']")[0].value);
		formData.append("file1",$("input[name='file1']")[0].files[0]);
		formData.append("file1",$("input[name='file2']")[0].files[0]);
		$.ajax({
			url:"uploadServlet",
			type:"post",
			data:formData,
			contentType:false,
			processData:false,
			xhr:function(){
				myXhr = $.ajaxSettings.xhr();
				if(myXhr.upload) {
					myXhr.upload.addEventListener('progress',progressHandlingFunction,false);
				}
				return myXhr;
			},
			success:function(data){
				$("#progress").html(data);
				var file1 = $("input[name='file1']");
				file1.after(file1.clone().val(""));
				file1.remove();
				var file2 = $("input[name='file2']");
				file2.after(file2.clone().val(""));
				file2.remove();
				
				$.ajax({
					url:"listFiles",
					type:"get",
					dataType:"json",
					success:function(data){
						var insertHTML = "";
						for(var i=0;i<data.length;i++) {
							insertHTML += "<tr><td>"+data[i].name+"</td><td><a href='${pageContext.request.contextPath}/downLoadServlet?filepath="+data[i].url+"'>下载</a></td></tr>";
						}
						$("table").html(insertHTML);
					}
				});
				
			},
			beforeSend:function(){
				$("input[type='submit']").attr("disabled","true");
				$("input[name='file1']").attr("disabled","true");
				$("input[name='file2']").attr("disabled","true");
			},
			complete:function(){
				$("input[type='submit']").removeAttr("disabled");
				$("input[name='file1']").removeAttr("disabled");
				$("input[name='file2']").removeAttr("disabled");
			},
		});
	});
});
function progressHandlingFunction(e) {
	if (e.lengthComputable) {
		$('progress').attr({value : e.loaded, max : e.total}); //更新数据到进度条
		var percent = e.loaded/e.total*100;
		$('#progress').html((e.loaded/1024/1024).toFixed(1) + "/" + (e.total/1024/1024).toFixed(1) +" MB " + percent.toFixed(2) + "%");
	}
}