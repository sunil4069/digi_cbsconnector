<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<ui:header/>
<%-- <img src="${pageContext.request.contextPath }/resources/assets/adbl.jpg"> --%>

<body  oncontextmenu="return false">
<div class="row">
    <div class="col-md-6 col-lg-12 col-xl-6">
        <div class="row">
       <div class="col-md-12 col-lg-6 col-xl-6">
                <section class="panel panel-featured-left panel-featured-primary">
                    <div class="panel-body">
                        <div class="widget-summary">
                            <div class="widget-summary-col widget-summary-col-icon">
                                <div class="summary-icon bg-primary">
                                    <i class="fa fa-bank" style="font-size:48px;color:red"></i>
                                </div>
                            </div>
                            <div class="widget-summary-col">
                                <div class="summary">
                                    <h4 class="title"> 
                                    AUTHIRIZED ITEMS TODAY</h4>
                                     <div class="info">
                                        <strong class="amount" id="newEnquiry"></strong>
                                    </div> 
                                </div>
                                <div class="summary-footer">
                                    <a class="text-muted text-uppercase"
                                   href='<c:url value="/transactions/authorized"/>'> Click Here</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div> 
            
    <div class="col-md-12 col-lg-6 col-xl-6">
                <section class="panel panel-featured-left panel-featured-primary">
                    <div class="panel-body">
                        <div class="widget-summary">
                            <div class="widget-summary-col widget-summary-col-icon">
                                <div class="summary-icon bg-primary">
                                    <i class="fa fa-bank" style="font-size:48px;color:green"></i>
                                </div>
                            </div>
                            <div class="widget-summary-col">
                                <div class="summary">
                                    <h4 class="title"> 
                                    AUTHIRIZED ITEMS ALL</h4>
                                     <div class="info">
                                        <strong class="amount" id="newEnquiry"></strong>
                                    </div> 
                                </div>
                                <div class="summary-footer">
                                    <a class="text-muted text-uppercase"
                                   href='<c:url value="/reports/transactions/search"/>'> Click Here</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>         
            
            </div> 
            
            
            
        <div class="row">
        <sec:authorize access="hasAnyRole('INPUTTER')">
            <div class="col-md-12 col-lg-6 col-xl-6">
                <section class="panel panel-featured-left panel-featured-primary">
                    <div class="panel-body">
                        <div class="widget-summary">
                            <div class="widget-summary-col widget-summary-col-icon">
                                <div class="summary-icon bg-primary">
                                    <i class="fa fa-book"></i>
                                </div>
                            </div>
                            <div class="widget-summary-col">
                                <div class="summary">
                                    <h4 class="title"> INPUT LOAN TRANSACTION </h4>
                                    <div class="info">
                                        <strong class="amount" id="inEnquiry"> </strong>
                                    </div>
                                </div>
                                <div class="summary-footer">
                                    <a class="text-muted text-uppercase"
                                    href='<c:url value="/transactions/create-form"/>'> Click Here </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
           
     <div class="col-md-12 col-lg-6 col-xl-6">
                <section class="panel panel-featured-left panel-featured-primary">
                    <div class="panel-body">
                        <div class="widget-summary">
                            <div class="widget-summary-col widget-summary-col-icon">
                                <div class="summary-icon bg-primary">
                                    <i class="fa fa-exchange"></i>
                                </div>
                            </div>
                            <div class="widget-summary-col">
                                <div class="summary">
                                    <h4 class="title">   ACCOUNT TRANSFER (Current VS Call)  </h4>
                                    <div class="info">
                                        <strong class="amount" id="inRecommendbr"> </strong>
                                    </div>
                                </div>
                                <div class="summary-footer">
                                    <a class="text-muted text-uppercase"
                                    
                                     href='<c:url value="/accounts/transfers/create-form"/>'>Click Here</a> 
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>        
         </sec:authorize>   
   
   <div class="col-md-12 col-lg-6 col-xl-6">
                <section class="panel panel-featured-left panel-featured-secondary">
                    <div class="panel-body">
                        <div class="widget-summary">
                            <div class="widget-summary-col widget-summary-col-icon">
                                <div class="summary-icon bg-secondary">
                                    <i class="fa fa-check-square-o" style="font-size:48px;color:red"></i>
                                </div>
                            </div>
                            <div class="widget-summary-col">
                                <div class="summary">
                                    <h4 class="title">  AUTHORIZE TRANSACTION   </h4>
                                    <div class="info">
                                        <strong class="amount" id="inProcess"></strong>
                                    </div>
                                </div>
                                <div class="summary-footer">
                                    <a class="text-muted text-uppercase"
                                    href='<c:url value="/transactions/unauthorized"/>'>Click Here</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>         
            
            
            
             
            
            
            
      
             
               
                 
            
            
        </div>
    </div>
</div>

<ui:footer/>
</body>