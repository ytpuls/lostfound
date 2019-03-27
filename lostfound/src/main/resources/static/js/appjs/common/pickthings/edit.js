$().ready(function() {
	laydate({
		elem : '#picktime'
	});
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
layui.use('upload', function () {
	var upload = layui.upload;
	//执行实例
	var uploadInst = upload.render({
		elem: '#test1', //绑定元素
		url: '/common/sysFile/upload', //上传接口
		size: 1000,
		accept: 'file',
		done: function (r) {
			$("#img").attr("src",r.fileName);
			$("#thingsimg").val(r.fileName);
			layer.msg(r.msg);
		},
		error: function (r) {
			layer.msg(r.msg);
		}
	});
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/pickthings/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入名字"
			}
		}
	})
}