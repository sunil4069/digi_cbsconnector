<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav id="menu" class="nav-main" role="navigation">
    <ul class="nav nav-main">
        <li class="nav-active"><a href="<c:url value="/dashboard"/>">
            <i class="fa fa-home" aria-hidden="true"></i> <span>Dashboard</span>
        </a></li>

        <%--SETTINGS--%>
        <li class="nav-parent"><a> <i class="fa fa-cogs"
                                      aria-hidden="true"></i> <span>Settings</span>
        </a>
            <sec:authorize access="hasAnyRole('ADMIN', 'SUPERADMIN')">
                <%--                Admin Configurations--%>
                <ul class="nav nav-children">
                    <li><a href="<c:url value="/admins/forms"/>"><i
                            class="fa fa-cogs" aria-hidden="true"></i> Admin Configurations</a></li>
                </ul>
                <%--                ./Admin Configurations--%>
                <!-- office settings -->
                <ul class="nav nav-children">
                    <li class="nav-parent"><a><i class="fa fa-briefcase"
                                                 aria-hidden="true"></i> Office</a>
                        <ul class="nav nav-children">

                            <li><a href="<c:url value="/office/create-page"/>"><i
                                    class="fa fa-edit" aria-hidden="true"></i> Add/Update Office</a></li>
                            <li><a href="<c:url value="/office/view-page"/>"><i
                                    class="fa fa-search" aria-hidden="true"></i> View Office</a></li>
                        </ul>
                    </li>
                </ul>
                <!-- staff settings -->
                <ul class="nav nav-children">

                    <li class="nav-parent"><a><i
                            class="fa fa-users" aria-hidden="true"></i> Staff</a>
                        <ul class="nav nav-children">

                            <li><a href="<c:url value="/staffs/create-page"/>"><i
                                    class="fa fa-edit" aria-hidden="true"></i> Add/Update
                                Staff</a></li>
                            <li><a href="<c:url value="/staffs/view-page"/>"><i
                                    class="fa fa-search" aria-hidden="true"></i> View
                                Staff</a></li>
                        </ul>
                    </li>
                </ul>
                <%-- ./STAFF SETTINGS--%>
                <!-- User settings -->
                <ul class="nav nav-children">
                    <li class="nav-parent"><a><i
                            class="fa fa-user" aria-hidden="true"></i> User</a>
                        <ul class="nav nav-children">
                            <li><a href="<c:url value="/users/create-page"/>"><i
                                    class="fa fa-edit" aria-hidden="true"></i> Manage
                                Users</a></li>
                            <li><a href="<c:url value="/users/unauthorized-page"/>"><i
                                    class="fa fa-edit" aria-hidden="true"></i> Unauthorized Users</a></li>
                        </ul>
                    </li>
                </ul>
                <%--./USER SETTINGS--%>
            </sec:authorize>
            <ul class="nav nav-children">
                <li><a href="<c:url value="/master/accounts/savings/create-form"/>"><i
                        class="fa fa-cogs" aria-hidden="true"></i> Saving Accounts</a></li>
                <li><a href="<c:url value="/master/accounts/erfloans/create-form"/>"><i
                        class="fa fa-cogs" aria-hidden="true"></i> ERF Loan Accounts</a></li>
                <li><a href="<c:url value="/master/accounts/pfloans/create-form"/>"><i
                        class="fa fa-cogs" aria-hidden="true"></i> PF Loan Accounts</a></li>
            </ul>
        </li>
        <%--./SETTINGS--%>
        <%--TRANSACTION--%>
        <li class="nav-parent"><a><i
                class="fa fa-external-link" aria-hidden="true"></i> Transaction</a>
            <ul class="nav nav-children">

                <sec:authorize access="hasAnyRole('INPUTTER')">
                    <li><a href="<c:url value="/transactions/create-form"/>"><i
                            class="fa fa-edit" aria-hidden="true"></i> Add
                        Transaction</a></li>
                </sec:authorize>
                <li><a href="<c:url value="/transactions/unauthorized"/>"><i
                        class="fa fa-eye" aria-hidden="true"></i>Unauthorized
                    Transactions</a></li>
                <li><a href="<c:url value="/transactions/authorized"/>"><i
                        class="fa fa-eye" aria-hidden="true"></i>Authorized
                    Transactions</a></li>
                <sec:authorize access="hasAnyRole('INPUTTER')">
                    <li><a href="<c:url value="/accounts/transfers/create-form"/>"><i
                            class="fa fa-edit" aria-hidden="true"></i> Account Transfer Transaction</a></li>
                </sec:authorize>

            </ul>
        </li>
        <%--./TRANSACTION--%>
        <%--STATEMENT--%>
        <li class="nav-parent"><a><i
                class="fa fa-table" aria-hidden="true"></i> Statement</a>
            <ul class="nav nav-children">

                <li><a href="<c:url value="/statements/search-form?type=MINI_STATEMENT"/>"><i
                        class="fa fa-search" aria-hidden="true"></i> Search Mini Statement</a></li>
                <li><a href="<c:url value="/statements/search-form?type=STATEMENT"/>"><i
                        class="fa fa-search" aria-hidden="true"></i> Search Statement</a></li>

            </ul>
        </li>
        <%--./STATEMENT--%>
        <%--REPORT--%>
        <li class="nav-parent"><a><i
                class="fa fa-book" aria-hidden="true"></i> Report</a>
            <ul class="nav nav-children">
                <li><a href="<c:url value="/reports/transactions/search"/>"><i
                        class="fa fa-search" aria-hidden="true"></i> Fund Transfer Transaction</a></li>

            </ul>
            
            
            <ul class="nav nav-children">
                    <li class="nav-parent"><a><i
                            class="fa fa-user" aria-hidden="true"></i> Financial Reports</a>
                        <ul class="nav nav-children">
                            <li><a href="<c:url value="/erf/printerfbl"/>"><i
                                    class="fa fa-edit" aria-hidden="true"></i> Print BL
                                </a></li>
                            <li><a href="<c:url value="#"/>"><i
                                    class="fa fa-edit" aria-hidden="true"></i> Profit Loss Account</a></li>
                        </ul>
                    </li>
                </ul>
                
                <ul class="nav nav-children">
                    <li class="nav-parent"><a><i
                            class="fa fa-user" aria-hidden="true"></i> Individual Reports </a>
                        <ul class="nav nav-children">
                            <li><a href="<c:url value="/erf/printerfloans"/>"><i
                                    class="fa fa-edit" aria-hidden="true"></i> Erf Loan Balances
                                </a></li>
                            <li><a href="<c:url value="/erf/printerfpfandloans"/>"><i
                                    class="fa fa-edit" aria-hidden="true"></i> Provident Fund And PF Loan</a></li>
                        </ul>
                    </li>
                </ul>


    </ul>
            
            
        </li>
        <%--./REPORT--%>




</nav>