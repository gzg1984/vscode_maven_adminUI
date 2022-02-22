<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="navbar" class="navbar navbar-default">
	<div class="navbar-container" id="navbar-container">
		<div class="navbar-header pull-left">
			<!-- #section:basics/navbar.layout.brand -->
			<a href="<%=request.getContextPath() %>/fakepage/index.do" class="navbar-brand"> <small> <i
					class="fa fa-leaf"></i> CHINA_LXR 管理后台首页
			</small>
			</a>
		</div>

		<!-- #section:basics/navbar.dropdown -->
		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">

				<!-- #section:basics/navbar.user_menu -->
				<li class="light-blue"><a data-toggle="dropdown" href="#"
					class="dropdown-toggle">  <span
						class="user-info"> <small>欢迎,</small> ${sessionScope.loginAdmin.username }
					</span> <i class="ace-icon fa fa-caret-down"></i>
				</a>
					<ul
						class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<li><a href="<%=request.getContextPath() %>/index.html"> <i class="ace-icon fa fa-home"></i>
								前往前台首页
						</a></li>
						<li><a href="<%=request.getContextPath() %>/admin/user/updatePassword.do"> <i class="ace-icon fa fa-cog"></i>
								修改密码
						</a></li>

<!-- 						<li><a href="profile.html"> <i -->
<!-- 								class="ace-icon fa fa-user"></i> Profile -->
<!-- 						</a></li> -->

						<li class="divider"></li>

						<li><a href="<%=request.getContextPath() %>/admin/logout.do"> <i class="ace-icon fa fa-power-off"></i>
								注销
						</a></li>
					</ul></li>

				<!-- /section:basics/navbar.user_menu -->
			</ul>
		</div>
		<!-- /section:basics/navbar.dropdown -->
	</div>
	<!-- /.navbar-container -->
</div>