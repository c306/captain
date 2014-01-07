<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<%--如果是普通用户转向
<sec:authorize ifAnyGranted="ROLE_MEMBER">
  <c:redirect url="/admin/secuser!userinfo.action" context="${ctx }" />
</sec:authorize>--%>
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
background-color: #efefef;
}
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

<!-- jquery-ui -->
<link href="${ctx}/lookfor/common/jquery/jquery-ui/css/ui-lightness/jquery-ui-1.10.2.custom.min.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/lookfor/common/jquery/jquery-ui/js/jquery-ui-1.10.2.custom.min.js" type="text/javascript" language="javascript"></script>

<!-- zTree -->
<link rel="stylesheet" href="${ctx}/lookfor/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script src="${ctx}/lookfor/common/jquery/ztree/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>




<s:if test="iscn">
	<script type="text/javascript" src="${ctx }/common/My97DatePicker/cn_WdatePicker.js"></script>
</s:if><s:else>
	<script type="text/javascript" src="${ctx }/common/My97DatePicker/en_WdatePicker.js"></script>
</s:else>

<script type="text/javascript">

var ctx = '${ctx}';

$(document).ready(function(){
	<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_OPERATOR">
	// do something ...
	</sec:authorize>
	
});
</script>




<!-- ztree start -->
<style type="text/css">
#treeMenu {
  padding: 0 0 0 0;
}
.ztree li a.level0 {
  margin-bottom: 2px;
  border: 1px solid #e5e5e5;
  -webkit-border-radius: 4px;
     -moz-border-radius: 4px;
          border-radius: 4px;
display: block;
height: 34px;
}
.ztree li a.level0:hover {
  background-color: #f5f5f5;
  filter:"";
}
.ztree li a.level0.cur {
  background-color: #eee;
}
.ztree li a.level0 span {
text-align: center;
background-color: transparent;
border-bottom-color: #333;
border-bottom-style: none;
border-bottom-width: 0px;
color: #333;
display: block;
font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
font-size: 13px;
line-height: 18px;
padding-bottom: 8px;
padding-left: 15px;
padding-right: 15px;
padding-top: 8px;

}

.ztree li a.level0 span.button { float:right; margin-left: 10px; visibility: visible;display:none;}
.ztree li span.button.switch.level0 {display:none;}

.ztree ul.level0 {
  margin-bottom: 2px;
  border: 1px solid #e5e5e5;
  -webkit-border-radius: 4px;
     -moz-border-radius: 4px;
          border-radius: 4px;

    border-top-width: 0px;
  border-top-left-radius: 0px;
  border-top-right-radius: 0px;

  position: relative;
  top: -4px;

  padding-top: 4px;
  padding-bottom: 4px;
}

[class^="ico_"],
[class*=" ico_"] {
  display: inline-block;
  width: 14px;
  height: 14px;
  *margin-right: .3em;
  line-height: 14px;
  vertical-align: text-top;
  background-image: url("./bootstrap/css/glyphicons-halflings.png") !important;
  background-position: 14px 14px;
  background-repeat: no-repeat;
}
.ztree li a span.ico_close.button {background-position: -384px -120px !important;
  background-image: url("./bootstrap/img/glyphicons-halflings.png") !important;}
.ztree li a span.ico_open.button {background-position: -408px -120px !important;
  background-image: url("./bootstrap/img/glyphicons-halflings.png") !important;}
.ztree li a span.ico_docu.button {background-position: -48px -120px !important;
  background-image: url("./bootstrap/img/glyphicons-halflings.png") !important;}
</style>


  <SCRIPT type="text/javascript">
    <!--
    
    var treeLang = '<s:property value="iscn ? 'zh_CN' : 'en_US' "/>';
    
    var curMenu = null, zTree_Menu = null;
    var treesetting = {
      view: {
        showLine: false,
        selectedMulti: false,
        dblClickExpand: false
      },
      data: {
        key: {
          name: ( treeLang === 'zh_CN' ? 'name' : 'ename')
        },
        simpleData: {
          enable: true
        }
      },
      callback: {
        // onNodeCreated: this.onNodeCreated,
        onNodeCreated: function(event, treeId, nd){
          if (nd.level === 0) {
            $("#" + nd.tId + "_a").addClass("breadcrumb");
          }
        },
        // beforeExpand: zTreeBeforeExpand,
        //beforeClick: this.beforeClick,
        onClick: this.onClick
      }
    };


    var zNodes =[
      { id:1, pId:0, name:"首页", ename:"HOME", open:true},
      { id:11, pId:1, name:"图片排序", ename:"PHOTO GALLERY", uri:"/admin/"},

      { id:2, pId:0, name:"关于", ename:"ABOUT"},
      { id:21, pId:2, name:"公司", ename:"PRACTICE", uri:"/admin/aboutpractice!edit.action?oid=000000000001"},//query.type=4
      { id:22, pId:2, name:"人员", ename:"PEOPLE", uri:"/admin/aboutpeople!list.action?query.type=5"},
      { id:23, pId:2, name:"联系", ename:"CONTACT", uri:"/admin/aboutcontact!list.action?query.type=6"},
      /*{ id:231, pId:23, name:"ABOUT", ename:"BEIJING", uri:"/admin/aboutcontact!list.action?query.type=20"},
      { id:232, pId:23, name:"ABOUT", ename:"NEWYORK", uri:"/admin/aboutcontact!list.action?query.type=21"},*/
      { id:24, pId:2, name:"合作", ename:"COLLABORATION", uri:"/admin/aboutcollaboration!list.action?query.type=7"},

      { id:3, pId:0, name:"作品", ename:"WORK"},
      { id:31, pId:3, name:"建筑", ename:"ARCHITECTURE", uri:"/admin/products!list.action?query.type=8"},
      { id:32, pId:3, name:"室内", ename:"INTERIORS", uri:"/admin/products!list.action?query.type=9"},
      { id:34, pId:3, name:"城市设计", ename:"URBAN DESIGN", uri:"/admin/products!list.action?query.type=22"},
      { id:33, pId:3, name:"产品", ename:"PRODUCTS", uri:"/admin/products!list.action?query.type=10"},
      { id:331, pId:33, name:"椅子", ename:"Seating", uri:"/admin/products!list.action?query.type=12"},
      { id:332, pId:33, name:"桌子", ename:"Tables", uri:"/admin/products!list.action?query.type=13"},
      { id:333, pId:33, name:"储物", ename:"Storage", uri:"/admin/products!list.action?query.type=14"},
      { id:334, pId:33, name:"灯具", ename:"Lighting", uri:"/admin/products!list.action?query.type=15"},
      { id:335, pId:33, name:"洁具", ename:"Bath", uri:"/admin/products!list.action?query.type=16"},
      { id:336, pId:33, name:"装置", ename:"Installations", uri:"/admin/products!list.action?query.type=17"},
      { id:337, pId:33, name:"饰面", ename:"Finishes", uri:"/admin/products!list.action?query.type=18"},
      { id:338, pId:33, name:"金工", ename:"Hardware", uri:"/admin/products!list.action?query.type=19"},
      { id:35, pId:3, name:"研究", ename:"RESEARCH", uri:"/admin/products!list.action?query.type=11"},
      { id:36, pId:3, name:"档案", ename:"ARCHIVE", uri:"/admin/products!list.action?query.type=23"},

      { id:4, pId:0, name:"著述", ename:"WRITING"},
      { id:41, pId:4, name:"著述", ename:"WRITING", uri:"/admin/products!list.action?query.type=3"},
      /*{ id:411, pId:41, name:"ABOUT", ename:"新增"},
      { id:412, pId:41, name:"ABOUT", ename:"复核"},
      { id:42, pId:4, name:"ABOUT", ename:"职责管理"},
      { id:421, pId:42, name:"ABOUT", ename:"新增"},
      { id:422, pId:42, name:"ABOUT", ename:"复核"},
      { id:43, pId:4, name:"ABOUT", ename:"自动指令发送设置"},
      { id:44, pId:4, name:"ABOUT", ename:"对私参数设置"},

      { id:5, pId:0, name:"系统管理", ename:"SYSTEM MANAGEMENT"},
      { id:51, pId:5, name:"用户", ename:"USER ACCOUNTS"},
      { id:52, pId:5, name:"角色", ename:"USER ROLES"}
      */
    ];




    function beforeClick(treeId, node) {
      if (node.isParent) {
        if (node.level === 0) {
          var pNode = curMenu;
          while (pNode && pNode.level !==0) {
            pNode = pNode.getParentNode();
          }
          if (pNode !== node) {
            var a = $("#" + pNode.tId + "_a");
            a.removeClass("cur").addClass("breadcrumb");
            zTree_Menu.expandNode(pNode, false);
          }
          a = $("#" + node.tId + "_a");
          a.removeClass("breadcrumb").addClass("cur");

          var isOpen = false;
          for (var i=0,l=node.children.length; i<l; i++) {
            if(node.children[i].open) {
              isOpen = true;
              break;
            }
          }
          if (isOpen) {
            zTree_Menu.expandNode(node, true);
            curMenu = node;
          } else {
            zTree_Menu.expandNode(node.children[0].isParent?node.children[0]:node, true);
            curMenu = node.children[0];
          }
        } else {
          zTree_Menu.expandNode(node);
        }
      }
      return !node.isParent;
    }
    function onClick(e, treeId, node) {
      // alert("Do something!");
      if(node.uri)
    	location = "${ctx}" + node.uri + '&treeid=' + node.tId;
    }

    $(document).ready(function(){
      $.fn.zTree.init($("#treeMenu"), treesetting, zNodes);
      zTree_Menu = $.fn.zTree.getZTreeObj("treeMenu");
      zTree_Menu.expandAll(true);
      //
      curMenu = zTree_Menu.getNodeByTId('${param.treeid}');
      if( curMenu === null )
      	curMenu = zTree_Menu.getNodes()[0].children[0];
      zTree_Menu.selectNode(curMenu);
      
      //
      $('#myTab').find('.active a').text(treeLang === 'zh_CN' ? curMenu.name : curMenu.ename);
      
    });
    //-->
  </SCRIPT>
<!-- ztree /end -->

</head>

<body>


<%@ include file="/common/messages.inc"%>
<s:iterator var="ferr" value="fieldErrors">
            <div style="padding: 0pt 0.7em;" class="ui-state-error ui-corner-all"> 
        <p><span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span> 
        <strong>Alert:</strong> ${ferr}</p>
      </div>
</s:iterator>

<%--
<s:if test="'zh_CN' == #session['WW_TRANS_I18N_LOCALE'].toString()">
	<a href="${ctx }/admin/?request_locale=en_US"><span style="margin-right:45px;color:#000000;">EN</span></a>
  	<a href="${ctx }/admin/?request_locale=zh_CN"><span style="margin-right:45px;color:#e00065;">中文</span></a>
</s:if>
<s:else>
	<a href="${ctx }/admin/?request_locale=en_US"><span style="margin-right:45px;color:#e00065;">EN</span></a>
  	<a href="${ctx }/admin/?request_locale=zh_CN"><span style="margin-right:45px;color:#000000;">中文</span></a>
</s:else>
--%>
      


    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="${ctx}/<s:property value="%{iscn ? 'cn' : 'en'}" />">Lynch + Song</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li class="active"><a href="${ctx}/<s:property value="%{iscn ? 'cn' : 'en'}" />">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>

          <div class="btn-group pull-right">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
              <i class="icon-user"></i> <s:property value="loginUser.username"/>
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
<%--              <li><a href="#">Profile</a></li>--%>
<%--              <li class="divider"></li>--%>
              <li><a href="${ctx }/j_spring_security_logout"><span style="color:#000000;"><s:text name="admin.index.logout"/></span></a></li>
            </ul>
          </div>


          </div><!--/.nav-collapse -->
          
          <ul class="nav nav-pills pull-right">
          <li class="dropdown" id="menu1">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#menu1">
              Language
              <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
              <li><a href="${ctx }/admin/?request_locale=en_US">English</a></li>
              <li class="divider"></li>
              <li><a href="${ctx }/admin/?request_locale=zh_CN">Chinese</a></li>
            </ul>
          </li>
          </ul>
          
        </div>
      </div>
    </div><!--/.navbar -->



    <div class="container"><!-- container start -->



      <!-- CONTENT -->
      <div class="row">

        <div class="span3">
          <div class="well" style="min-height: 500px;">
            <!-- 左边菜单 start -->
            <ul id="treeMenu" class="ztree"></ul>
            <!-- / 左边菜单 -->
          </div>
        </div><!-- / span3 -->


        <div class="span9"><!-- span9 start -->


