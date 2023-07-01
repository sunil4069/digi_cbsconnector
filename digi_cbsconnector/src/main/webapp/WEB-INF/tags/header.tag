<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>

<html class="fixed">

	<head>

		<title>${pagetitle }</title>
		<ui:meta/>
		<ui:styles/>
	</head>
	<body>
	<ui:loadingScreen/>
		<section class="body">

			<!-- start: header -->
			<header class="header">

				<div class="logo-container">
					<a href=".." class="logo">
						<img src="${pageContext.request.contextPath }/resources/assets/adbllogo.jpg" height="35" alt="${username}" />
					</a>
					<div class="visible-xs toggle-sidebar-left" data-toggle-class="sidebar-left-opened" data-target="html" data-fire-event="sidebar-left-opened">
						<i class="fa fa-bars" aria-label="Toggle sidebar"></i>
					</div>
				</div>
			
				<!-- start: search & user box -->
				<div class="header-right">
			<h5 class="pull-left" style="color: green">Agricultural Development Bank Limited,
		Abakash Kosh Byabasthapan Karyalaya
			
			</h5>
<%--					<form action="pages-search-results.html" class="search nav-form">--%>
<%--						<div class="input-group input-search">--%>
<%--							<input type="text" class="form-control" name="q" id="q" placeholder="Search...">--%>
<%--							<span class="input-group-btn">--%>
<%--								<button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>--%>
<%--							</span>--%>
<%--						</div>--%>
<%--					</form>--%>
<%--			--%>
					<span class="separator"></span>
					<div id="userbox" class="userbox">
						<a href="#" data-toggle="dropdown">
							<figure class="profile-picture">
								<img src="${pageContext.request.contextPath }/resources/assets/userdummy.png" class="img-circle" />
							</figure>
							<div class="profile-info" data-lock-name="First Last" data-lock-email="username">
 								    <span class="">${username }</span><br />
 									<span class="">${post }</span>
							</div>
			
							<i class="fa custom-caret"></i>
						</a>
			
						<div class="dropdown-menu">
							<ul class="list-unstyled">
								<li class="divider"></li>
								<li>
									<a tabindex="-1" href="<c:url value="/userprofiles/user-profile"/> "><i class="fa fa-user"></i> My Profile</a>
								</li>
								<%--<li>
									<a role="menuitem" tabindex="-1" href="#" data-lock-screen="true"><i class="fa fa-lock"></i> Lock Screen</a>
								</li>--%>
								<li>
									<a role="menuitem" tabindex="-1" href="/logout"><i class="fa fa-power-off"></i> Logout</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- end: search & user box -->
			</header>
			<!-- end: header -->

			<div class="inner-wrapper">
				<!-- start: sidebar -->
				<aside id="sidebar-left" class="sidebar-left">
				
					<div class="sidebar-header">
						<div class="sidebar-title" style="color:white">
							ADBL ErfOnine
						</div>
						<div class="sidebar-toggle hidden-xs" data-toggle-class="sidebar-left-collapsed" data-target="html" data-fire-event="sidebar-left-toggle">
							<i class="fa fa-bars" aria-label="Toggle sidebar"></i>
						</div>
					</div>
				
					<div class="nano">
						<div class="nano-content">
							<ui:nav/>
				</div>
				</div>
				</aside>
				<!-- end: sidebar -->

				<section role="main" class="content-body">
					<header class="page-header">
						<h2>${pagetitle }</h2>
					
						<div class="right-wrapper pull-right">
							<ol class="breadcrumbs">
								<li>
									<a href="index.html">
										<i class="fa fa-home"></i>
									</a>
								</li>
								<li><span>${pagetitle }</span></li>
							</ol>
					
							<a class="sidebar-right-toggle" data-open="sidebar-right"><i class="fa fa-chevron-left"></i></a>
						</div>
					</header>
