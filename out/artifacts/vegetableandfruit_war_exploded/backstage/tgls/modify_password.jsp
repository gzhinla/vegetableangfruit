<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
		<!-- Google Chrome Frame也可以让IE用上Chrome的引擎: -->
		<meta name="renderer" content="webkit">
		<!--国产浏览器高速模式-->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="author" content="gok商城" />
		<!-- 作者 -->
		<meta name="revised" content="gok商城.v3, 2019/05/01" />
		<!-- 定义页面的最新版本 -->
		<meta name="description" content="网站简介" />
		<!-- 网站简介 -->
		<meta name="keywords" content="搜索关键字，以半角英文逗号隔开" />
		<title>果蔬商城</title>

		<!-- 公共样式 开始 -->
		<link rel="stylesheet" type="text/css" href="../css/base.css">
		<link rel="stylesheet" type="text/css" href="../css/iconfont.css">
		<script type="text/javascript" src="../framework/jquery-1.11.3.min.js"></script>
		<link rel="stylesheet" type="text/css" href="../layui/css/layui.css">
		<script type="text/javascript" src="../layui/layui.js"></script>
		<!-- 滚动条插件 -->
		<link rel="stylesheet" type="text/css" href="../css/jquery.mCustomScrollbar.css">
		<script src="../framework/jquery-ui-1.10.4.min.js"></script>
		<script src="../framework/jquery.mousewheel.min.js"></script>
		<script src="../framework/jquery.mCustomScrollbar.min.js"></script>
		<script src="../framework/cframe.js"></script><!-- 仅供所有子页面使用 -->
		<!-- 公共样式 结束 -->

	</head>

	<body>
		<div class="cBody">
			<form id="addForm" class="layui-form" action="">
				<div class="layui-form-item">
					<label class="layui-form-label">原始密码</label>
					<div class="layui-input-inline shortInput">
						<input type="password" id="oldpassword" name="oldpassword" onblur="checkOldPassword()" required lay-verify="required" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">新密码</label>
					<div class="layui-input-inline shortInput">
						<input type="password" id="password1" name="password" required lay-verify="required" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">确认新密码</label>
					<div class="layui-input-inline shortInput">
						<input type="password" id="password2" name="password2" required lay-verify="required" autocomplete="off" class="layui-input">
					</div>
				</div>
			</form>
			
			
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" onclick="updatePassword()">确认修改</button>
				</div>
			</div>
			
			
			<script>
				var flag = false;
				
				function checkOldPassword() {
					var password = $("#oldpassword").val();
					
					$.ajax({
						url:'<%=basePath %>/backstage_userServlet?action=checkOldPassword',
						method:'post',
						data:{'password':password},
						dataType:'JSON',
						success:function(result){
							if(result.status==200){
								flag = true;
							}else{
								layer.open({
									  title: '提示信息',
									  content: result.msg
									});
							}
						}
					});
				}
			
				function updatePassword() {
					if(!flag){
						layer.open({
							  title: '提示信息',
							  content: '原始密码错误，请重新输入'
							});
					}else{
						var newPassword1 = $("#password1").val();
						var newPassword2 = $("#password2").val();
						
						if(newPassword1!=newPassword2){
							layer.open({
								  title: '提示信息',
								  content: '两次密码不一致，请重新输入'
								});
						}else{
							$.ajax({
								url:'<%=basePath %>/backstage_userServlet?action=modifyPassword',
								method:'post',
								data:{'newPassword':newPassword2},
								dataType:'JSON',
								success:function(result){
									if(result.status==200){
										alert("修改成功");
										window.location.href="<%=basePath %>backstage_userServlet?action=afterModifyPassword";
									
									}else{
										layer.open({
											  title: '提示信息',
											  content: result.msg
											});
									}
								}
							});
						}
					}
				}
			</script>

		</div>
	</body>
</html>