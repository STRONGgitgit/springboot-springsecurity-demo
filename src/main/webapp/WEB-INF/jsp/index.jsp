<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="common/common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

	<title>用户管理</title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="stylesheet" href="${ss }/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ss }/css/jquery.dataTables.min.css" />
	<link rel="stylesheet" href="${ss }/css/matrix-style.css" />
	<link rel="stylesheet" href="${ss }/css/matrix-media.css" />
	<link href="${ss }/font-awesome/css/font-awesome.css" rel="stylesheet" />
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>
	<meta name="_csrf" content="${_csrf.token}"/><meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>

<body>

<!--Header-part-->
<div id="header">
	<h1><a href="dashboard.html">Matrix Admin</a></h1>
</div>
<!--close-Header-part-->

<!--top-Header-menu-->
<%@include file="common/top.jsp"%>
<%@include file="common/menu.jsp"%>

<div id="content">
	<div id="content-header">
		<div id="breadcrumb"> <a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a> <a href="#" class="current">Tables</a> </div>
		<h1>Tables</h1>
	</div>
	<br/>
	<div align="center">
		<h1>这就是传说中的欢迎页面</h1>
	</div>
</div>

<%--弹框--%>
<div class="modal fade bs-example-modal-sm"  id="selectRole" tabindex="-1" role="dialog" aria-labelledby="selectRoleLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="selectRoleLabel">分配角色</h4>
			</div>
			<div class="modal-body">
				<form id="boxRoleForm" >
					<%--<div class='checkbox'>
                        <label><input type='checkbox' id=''/>First One</label>
                    </div>
                  --%>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" onclick="saveUserRoles();" class="btn btn-primary">Save</button>
			</div>
		</div>
	</div>
</div>

<!--Footer-part-->
<div class="row-fluid">
	<div id="footer" class="span12"> 2017 &copy; yqj <a href="http://themedesigner.in/">Themedesigner.in</a> </div>
</div>
<!--end-Footer-part-->

<script src="${ss }/js/bootstrap.min.js"></script>
<script src="${ss }/js/jquery.dataTables.min.js"></script>
<script src="${ss }/js/layer.js"></script>
<script type="text/javascript">

</script>

</body>
</html>
