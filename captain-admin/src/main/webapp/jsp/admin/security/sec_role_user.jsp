<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<html>
<head>
<link rel="icon" type="image/ico" href="${ctx}/favicon.ico" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${pagetitle }, Welcome <sec:authentication property="name"/> !</title>


<style type="text/css">
body {
padding-top: 60px;
padding-bottom: 40px;
/*background-color: #efefef;*/
}
.asterisk_red {color:#FF0000 !important;font-weight:600;}
</style>

<script src="${ctx}/lookfor/common/jquery/jquery-1.9.1.min.js" type="text/javascript" language="javascript"></script>

<!-- bootstrap styles -->
<link href="${ctx}/lookfor/common/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/lookfor/common/bootstrap/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="${ctx}/lookfor/common/bootstrap/js/bootstrap.js"></script>


<script type="text/javascript">

var ctx = '${ctx}';

$(document).ready(function(){
	<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_OPERATOR">
	// do something ...
	</sec:authorize>
	
});
</script>


</head>

<body>

<form class="form-inline">

<div class="span8 well">
  <input type="text" class="input-small" placeholder="Email">
  <input type="password" class="input-small" placeholder="Password">
  <label class="checkbox">
    <input type="checkbox"> Remember me
  </label>
  <button type="submit" class="btn">Sign in</button>
</div>

<div class="span8">
  <table class="table">
    <thead>
      <tr>
        <th>#</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Username</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>1</td>
        <td>Mark</td>
        <td>Otto</td>
        <td>@mdo</td>
      </tr>
      <tr>
        <td>2</td>
        <td>Jacob</td>
        <td>Thornton</td>
        <td>@fat</td>
      </tr>
      <tr>
        <td>3</td>
        <td>Larry</td>
        <td>the Bird</td>
        <td>@twitter</td>
      </tr>
    </tbody>
  </table>
</div>

</form>


</body>
</html>