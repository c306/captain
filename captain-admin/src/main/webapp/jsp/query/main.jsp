<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><s:text name="main.main.jsp.title" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- keywords.jsp -->
	<jsp:include page="../../common/keywords.jsp" />
	<link rel="shortcut icon" href="${ctx}/favicon.ico" />
	<link rel="icon" type="image/ico" href="${ctx}/favicon.ico" />
	
	<link type="text/css" href="${ctx}/lookfor/css/index-songs-cp-grid.css" rel="stylesheet" />
    <link rel="stylesheet" href="${ctx}/lookfor/css/index-cs-vanilla-grid.css" />
	
    <script type="text/javascript" src="${ctx}/lookfor/js/jquery-1.9.1.min.js"></script>
	<link type="text/css" href="${ctx}/lookfor/common/jquery/jquery-ui/css/ui-lightness/jquery-ui-1.10.2.custom.min.css" rel="stylesheet" />
	<script src="${ctx}/lookfor/common/jquery/jquery-ui/js/jquery-ui-1.10.2.custom.min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=7a0c732e47d34e1b2246257d790fd5ab"></script>
	<s:if test="#session['WW_TRANS_I18N_LOCALE'].toString() == 'en_US'">
		<script type="text/javascript" src="http://ditu.google.com/maps?file=api&v=2&key=AIzaSyAcJD6EZFneLrThCn2NRM-MPi1lCtRJzHo&hl=en-US"></script>
	</s:if>
	<s:else>
		<script type="text/javascript" src="http://ditu.google.com/maps?file=api&v=2&key=AIzaSyAcJD6EZFneLrThCn2NRM-MPi1lCtRJzHo&hl=zh-CN"></script>
	</s:else>
	
	<!-- bar start -->
	<link rel="stylesheet" type="text/css" href="${ctx}/lookfor/css/bar.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/lookfor/css/banner.css"></link>
	<script type="text/javascript" src="${ctx}/lookfor/js/bar.core.js"></script>
	<script type="text/javascript" src="${ctx}/lookfor/js/jquery.easydrag.js"></script>
	<script type="text/javascript" src="${ctx}/lookfor/js/main.js"></script>
	<script type="text/javascript" src="${ctx}/lookfor/js/escapechar.js"></script>
	
	<script type="text/javascript">
	var dataItems = ${dataItems};
	var dflan = '<s:property value="#session[\'WW_TRANS_I18N_LOCALE\'].toString()" />';
	var draft = '<s:text name="main.main.jsp.emain.content" />';
	dflan = dflan == 'en_US' ? '6' : '5';
	var ctx = '${ctx}';
	var itemobj = "";
	
	var dargctext = '<div class="drag_tip">[<s:text name="main.main.jsp.drtx.content" />]</div>';
	var bar = new Bar();//初始化bar
	$(document).ready(function(){
		setTimeout("bar.init()", 1);
	});

	$(function($) {
		$("#dragctx").droppable({
			accept: "#gridcontainer .item",
			// activeClass: "custom-state-active",,
			//tolerance: "touch",
			activate: function( event, ui ) {
				$("#dragctx").css({backgroundColor: ''}).html(dargctext);
				$masonrycontainer.data("accepted", "no");
				resetInitialState(ui.draggable, true);
			},
			deactivate: function( event, ui ) {
				$("#dragctx").css({backgroundColor: ''});
				$(document.body).css({cursor: 'auto'});
    			if($masonrycontainer.data("accepted") !== "success") { // if success droped not needs exec this 
					bar.onDragTipHide();
				}
			},
			over: function( event, ui ) {
				$("#dragctx").css({backgroundColor: '#FFFFB4'});
				//$("#bar").animate({width: barW + ui.helper.width(), height: barH + ui.helper.height()});
			},
			out: function( event, ui ) {
				$("#dragctx").css({backgroundColor: ''});
			},
			drop: function( event, ui ) {
				//alert(ui.draggable == ui.helper);
				loadBarContent(ui.draggable);
			}
		});
	});

	// 初始状态
	function resetInitialState(dragitem, draggableStart) {
		if($(dragitem).hasClass('logo')) {
			bar.onConvertSelected(2, null, null);
		} else if(draggableStart) {
			bar.onConvertSelected(3, null, null);
		} else {
			bar.onClearSelect();
		}
		clearBarCurrentItem();
		if(dragitem == null || dragitem == "undefined") {//close button execute
			bar.onDragTipHide();
		} else {
			bar.onDragctxInit();
		}
		if(draggableStart) {
			return;
		}
		//相关高亮
		selectedCategoryHighlight();
		// all highlight
		// $masonrycontainer.data( 'dragitems' ).addClass("transparencyzero");
	}

	function clearBarCurrentItem() {
		var citem = $masonrycontainer.data("currentItem");
		if(citem !== undefined) {
			citem.removeClass( 'mouseover' ).fadeIn("slow", function(){
				//$('#gridcontainer .transparencyzero').removeClass("transparencyzero");
			});
			$masonrycontainer.removeData("currentItem");
		}
	}

	// 加载 bar 显示内容
	function loadBarContent(dragitem) {
		$masonrycontainer.data("accepted", "success");
		clearBarCurrentItem();
		$masonrycontainer.data("currentItem", dragitem);
		
		if($(dragitem).hasClass('logo')){
			// if logo '000000000001'
			bar.onConvertSelected(2, 7, null);
			//bar.onQuery(2, 7, 2);
			return;
		} else {
			var ids = findNavigationIds($(dragitem).attr('type'));
			var url = "";
			var type = "";
			if(ids[1] == '15') {
				url = ctx + "/query/artle!research?keys=" + $(dragitem).attr("aid") + "&" + Math.random();
				type = 2;
				itemobj = findDirectSuperiorNavigationId($(dragitem).attr('type'));
			} else {
				url = ctx + "/query/artle!queryByOid?keys=" + $(dragitem).attr("aid") + "&" + Math.random();
				type = 1;
				itemobj = $(dragitem).attr("aid");
			}
			bar.onConvertSelected(ids[0], ids[1], itemobj);//3:13:201305260032
			$("#finder0").hide();
			setTimeout(function(){
				bar.onQueryStart(3, 2, type, type);
				$("#dragctx").load(url, function() {
					bar.onQueryFinish();
				});
			},200);
		}
	}
	
	/**
	 * 1. selected category's icons should be highlightly showed.
	 * 2. Only the same article icons will be highlight that if the selected item is article
	 */
	function selectedCategoryHighlight(navId, artId) {
		// if event target is bar's li click
		var evn = GetEvent();
		var isBarClick = ( evn !== undefined && evn !== null ) && $( evn.srcElement || evn.target ).parents('.bar_finder').length === 1;
		if( isBarClick ) {
			clearBarCurrentItem();
		} else if( !artId ) {
			var citem = $masonrycontainer.data("currentItem");
			if(citem !== undefined) {
				navId = findDirectSuperiorNavigationId($(citem).attr('type'));
	    		artId = $(citem).attr("aid");
			}
		}
		
		var navSelected = $('#finder').find('li[jsvalue="selected"]');
		if(navId === undefined)
			navId = navSelected.last().attr('id');
		if(artId)
			artId = "[aid='" + artId + "']";
		else
			artId = '';
		
		var npath = findCategoryNodepath(navId);
		if( npath === '' ) {// maybe a articleId if npath === ''
			navId = navSelected.eq(-2).attr('id');
			npath = findCategoryNodepath(navId);
			artId = navSelected.eq(-1).attr('id');
		}
		
		var dragitems = $('#gridcontainer').find(".item");
		// npath '':home, 001:about, 002:work, 003:writing
		if( 
				//isBarClick && 
				( npath === '' || npath.indexOf('|002|') !== 0 || npath === '|001|' || npath === '|002|' || npath === '|003|' )
		){ // if click the firt bar list or resetInitialState()
			// all highlight
			dragitems.addClass("transparencyzero");
			return;
		}
		// 
		dragitems.removeClass("transparencyzero");
		//
		dragitems.filter( ".item[type^='" + npath + "']" + artId ).addClass("transparencyzero");
	}

	// 返回 event 对象
	function GetEvent()
	{
	    if(document.all) // IE
	    {
	        return window.event;
	    }

	    func = GetEvent.caller; // 返回调用本函数的函数

	    while(func != null)
	    {
	        // Firefox 中一个隐含的对象 arguments，第一个参数为 event 对象
	        var arg0 = func.arguments[0];
	        // alert('参数长度：' + func.arguments.length);
	        if(arg0)
	        {
	            if((arg0.constructor == Event || arg0.constructor == MouseEvent) ||(typeof(arg0) == "object" && arg0.preventDefault && arg0.stopPropagation))
	            {
	                return arg0;
	            }
	        }

	        func = func.caller;
	    }

	    return null;
	}
	
	$(function(){
		// logo 图标加draggable
		setupDraggableForMasonry($('#gridcontainer>logo'));
		return;
		
	});
	</script>
	<!-- bar end -->

</head><!--  onselectstart="return false;" -->
<%--背景、网格线设置--%>
<s:set var="topgrid" value="@com.c306.comm.sysargs.SysArgsUtil@getVal(@com.c306.code.utils.SongConstans@KEY_BGLINE) eq 1 ? 'topzone_bg_grid' : ''" />
<s:set var="showgrid" value="@com.c306.comm.sysargs.SysArgsUtil@getVal(@com.c306.code.utils.SongConstans@KEY_BGLINE) eq 1 ? 'textzone_bg_grid' : ''" />
<s:set var="bottomgrid" value="@com.c306.comm.sysargs.SysArgsUtil@getVal(@com.c306.code.utils.SongConstans@KEY_BGLINE) eq 1 ? 'bottomzone_bg_grid' : ''" />
<s:set var="showbgimg" value="@com.c306.comm.sysargs.SysArgsUtil@getVal(@com.c306.code.utils.SongConstans@KEY_BGIMAGE) == 1 ? 'body_bg_noise' : ''" />
<body oncontextmenu="return false;" onselect="document.selection.empty();" 
	oncopy="document.selection.empty();" onbeforecopy="return false;"
  class="<s:property value="showbgimg" />"
  >

<!-- top start -->
<div class="topzone <s:property value="topgrid" />">
	<div class="logozone"><!-- <div class="logo"></div> --></div>
</div>
<!-- top end -->


<!-- bar start unselectable="on" onselectstart="javascript:return false;" -->
<div class="bar">
	<!-- div loadding... -->
	<div id="__loadding" class="div_loadding pitem_transparent">
		<div class="loadding_image"></div>
	</div>
	
	<div class="shadelefttop"></div>
	<div class="shadecentertop"></div>
	<div class="shaderighttop"></div>
	<div class="shadeleft"></div>
	
	<!-- middle -->
	<div id="finder" class="bar_finder"></div>
	<div id="dragctx" class="drag_content"></div>
	
	<!-- drag zone -->
	<div id="__dragStrip" class="bar_leftcenter"></div>
	<!-- drag zone -->
	
	<div class="shaderight"></div>
	<div class="shadeleftbottom"></div>
	<div class="shadecenterbottom"></div>
	<div class="shaderightbottom"></div>
</div>
<!-- bar end -->



<div id="textzone" class="textzone <s:property value="showgrid" />" style="overflow:visible;">
<!-- grid div 
<div id="snapgride" style="position: absolute; top:0px; left:0px; clear:both; width:100%; height:100%; z-index:0;">
    <div class="snapbox item col1 row1"></div>
</div>-->
<!-- images -->
<div id="gridcontainer" class="textcontent" style="overflow:visible;">
<div class="logo item" aid="000000000001" type="|001|004" style="top: -47px; position: absolute; z-index: 21; left: 7px; display: block; filter:alpha(opacity=100); -moz-opacity:1; opacity:1; margin: 0px !important;"></div>
<s:action var="galleryPage" namespace="/admin" name="masonry!gallery" executeResult="true" />

</div><!-- /gridcontainer -->
<!-- #lazyItem -->
<div id="lazyItem" class="lazyItem"></div>

</div><!-- /textzone -->

<!-- bottom start -->
<div class="bottomzone <s:property value="bottomgrid" />"></div>
<div class="copy_right">
	<div class="copy_right_zone">Copyright 2013 All Rights Reserved 京ICP备13015097号</div>
	<div class="design_zone">Designed by <a href="http://metonym.co">Kimberly McCleary</a></div>
</div>

<!-- draggable helper -->
<div id="draggable_helper" class="textcontent" style="height:0px;"></div>

<script type="text/javascript">

function setupDraggableForMasonry(dragitems) {
	dragitems.each(function(i,item){
  	  if($(item).data('init'))
  		  return;
  	  $(item)
  	  	.data('init', 1)
      	.draggable({
    		//grid: initParams.grid,
    		//snap: ".snapbox",
    		//snapMode: "inner",
    		//snapTolerance: initParams.snapTolerance,
    		opacity: 0.55,
    		containment: document.body,
    		appendTo: $("#draggable_helper"),
    		addClasses: false,
    		helper: "clone",
    		revert: "invalid",
    		cursor: "move",
    		stack: "#gridcontainer .item",
    		start: function(event, ui){
    			$(this).hide();
    			ui.helper.css({opacity: 1, zIndex: 9999999});
    			
    			bar.onDragctxInit();
    			//相关高亮
    			//relatedObjectsHighlight($(this));
        		var navid = findDirectSuperiorNavigationId($(this).attr('type'));
        		var artid = $(this).attr("aid");
        		selectedCategoryHighlight(navid, artid);
	    		// all highlight
	    		// $masonrycontainer.data( 'dragitems' ).addClass("transparencyzero");
    		},
    		stop: function(event, ui){
    			if($masonrycontainer.data("accepted") !== "success") {
    	    		$(this).removeClass('mouseover').fadeIn("slow");
    	    		selectedCategoryHighlight();
    	    		// all highlight
    	    		// $masonrycontainer.data( 'dragitems' ).addClass("transparencyzero");
    			} else {
    				//$("#dragctx").html('');
    			}
    			// if logo
        		if($(this).hasClass('logo')){
    	    		$(this).removeClass('mouseover').fadeIn("slow");
        		}
    		}
    	})
    	// 双击显示
    	.dblclick(function(){
    		if(!$(this).hasClass('logo')){// not logo
        		$(this).fadeOut("slow");
    		}
    		var navid = findDirectSuperiorNavigationId($(this).attr('type'));
    		var artid = $(this).attr("aid");
    		selectedCategoryHighlight(navid, artid);
    		//
    		loadBarContent($(this));
    		//相关高亮
    		//relatedObjectsHighlight(this);
    		// all highlight
    		// $masonrycontainer.data( 'dragitems' ).addClass("transparencyzero");
    	}).mouseenter(function(){
    		if(!$(this).hasClass("transparencyzero")){$(this).addClass("mouseover");}
    	}).mouseleave(function(){
    		if(!$(this).hasClass("transparencyzero")){$(this).removeClass("mouseover");}
    	});
	});
}


//Related objects highlight
function relatedObjectsHighlight(curItem, yes){
	// 
	$masonrycontainer.data( 'dragitems' ).removeClass("transparencyzero");
	//
	if(yes===false || !curItem) return;
	$masonrycontainer.data( 'dragitems' ).filter(".item[type^='" + $(curItem).attr('type') + "']").addClass("transparencyzero");
}



	$(function() {

		if ( $('#gridcontainer').hasClass("textcontent") )
			initParams = {
				picWidth: 96,
				picHeight: 92,
				widthSpacing: 15,
				heightSpacing: 14,
				grid: [ 111, 106 ],
				snapTolerance: 90
			}

		$masonrycontainer = $('#gridcontainer'); // golbal
		var dragitems = $masonrycontainer.find(".item");
		$masonrycontainer.data('dragitems', dragitems); // add data
		//
		setupDraggableForMasonry(dragitems);

        // exec
        calculateHeight(dragitems);

	});

	// 计算高度
	var maxHeight = 0;
	
	// equation formula
	function pointX(n){
		if(n===0) return 0;
		//return 96*n+15*n;
		return initParams.picWidth*n+initParams.widthSpacing*n;
	}
	function pointY(n){
		if(n===0) return 0;
		//return 92*n+14*n;
		return initParams.picHeight*n+initParams.heightSpacing*n;
	}
	function calcWidth(n){
		if(n===0) return 0;
		//return 96*n+15*(n-1);
		return initParams.picWidth*n+initParams.widthSpacing*(n-1);
	}
	function calcHeight(n){
		if(n===0) return 0;
		//return 92*n+14*(n-1);
		return initParams.picHeight*n+initParams.heightSpacing*(n-1);
	}
	function calculateHeight(dragitems) {
		var containerPaddingTop = 7, containerPaddingLeft = 8;
		dragitems.not(".logo").each(function(idx, el){
			var top = pointY(Number($(el).attr('y'))) + containerPaddingTop;
			$(el).css({left: pointX(Number($(el).attr('x'))) + containerPaddingLeft, top: top});

			if(top+calcHeight(Number($(el).attr('h'))) > maxHeight) {
				maxHeight = top+calcHeight(Number($(el).attr('h')));
			}
		});
		$masonrycontainer.height(maxHeight+9);
	}
</script>


<div id="preloading" style="display:none;"></div>
<script type="text/javascript">

<s:action var="categoriesPage" namespace="/admin" name="products" executeResult="false" />
// Categories map navigation
CatMapNav = [
  <s:iterator var="item" value="#categoriesPage.categories" status="stat">
  	{cid: <s:property value="oid" />, pid: <s:property value="( pid == null || pid == '' ) ? '-1': pid " />, nid: <s:property value="%{( objtype == null || objtype == '' ) ? '-1': objtype }" />}<s:if test="#stat.last == false ">,</s:if>
  </s:iterator>
];
/**
 * find Category Nodepath
 */
function findCategoryNodepath(nid){
	var cid = findCategoryId( nid ), nodepath = '';
	while( cid !== '-1' && cid !== '' ) {
		nodepath = '|' + cid + nodepath;
		cid = findParentCategoryId( cid );
	}
	if( nodepath === '' ) return '';
	return nodepath + '|';
}
/**
 * find parent Category Id by Navigation Id
 */
function findParentCategoryId(cid){
	var rs = '';
	$.each(CatMapNav, function(i, n){
		if(cid == n.cid) {
			rs = n.pid;
			return false;
		}
	});
	rs = '' + rs;
	while( rs != '-1' && rs !== '' && rs.length < 3 ){
		rs = '0' + rs;
	}
	return rs;
}
/**
 * find Category Id by Navigation Id
 */
function findCategoryId(nid){
	var rs = '';
	$.each(CatMapNav, function(i, n){
		if(nid == n.nid) {
			rs = n.cid;
			return false;
		}
	});
	rs = '' + rs;
	while( rs != '-1' && rs !== '' && rs.length < 3 ){
		rs = '0' + rs;
	}
	return rs;
}
/**
 * find Direct superior Navigation Id
 */
function findDirectSuperiorNavigationId(nodepath){
	var cids = nodepath.split('|');
	if(cids<2)return null;
	return findNavigationId(cids[cids.length-2]);
}
/**
 * Category oid 转 navigation oid
 '|002|010|012|' -> [id1, id2]
 */
function findNavigationIds(nodepath){
	var cids = nodepath.split('|');
	return [findNavigationId(cids[1]), findNavigationId(cids[2])];
}
function findNavigationId(cid){
	var rs = '';
	$.each(CatMapNav, function(i, n){
		if(cid == n.cid) {
			rs = n.nid;
			return false;
		}
	});
	return rs;
}

$(document).ready(function() {
	// init load
	var scrollLoadStatus = 0;
	$(window).scroll(function() {
		if( scrollLoadStatus === 0 ) {
			//获取网页的完整高度(fix)
			var h = $(document.body).height();

			//获取浏览器高度(fix)
			var c = $(window).height();

			//获取网页滚过的高度(dynamic)
			var t = $(window).scrollTop();
			
			//当 t+c = h 的时候就说明到底儿了
			if( h < (100 + t + c) ){
				// loading...
				scrollLoadStatus = 1;
				// do something ...
				scrollLoad();
			}
		}
	});
	
	var galleryPagenation = {pageSize: 20, pageNo: <s:property value="#galleryPage.page == null ? 0 : #galleryPage.page.pageNo" />, pageCount: <s:property value="#galleryPage.page == null ? 0 : #galleryPage.page.getPageCount()"/>};
	function scrollLoad() {
        if(galleryPagenation.pageNo >= galleryPagenation.pageCount) {
            // loading image
            $('#lazyItem').hide();
            // there's no more pages
            scrollLoadStatus = 2;
            return;
        }
        else
        	galleryPagenation.pageNo++;
        
        var url = '${ctx}/admin/masonry!gallery.action';
        //
        $("#preloading").load(url, {
        	"query.pageSize": galleryPagenation.pageSize,
        	"query.pageNo": galleryPagenation.pageNo
        	}, function(){
            //
        	var items = $(this).find('.item');
        	calculateHeight(items);
        	items.appendTo($masonrycontainer);
        	// 
        	dragitems = $masonrycontainer.find(".item");
    		$masonrycontainer.data('dragitems', dragitems); // add data
        	setupDraggableForMasonry(dragitems);
    		//
        	selectedCategoryHighlight();
            
            // init load status when completed
            scrollLoadStatus = 0;
        });
	}
});
</script>

</body>
</html>
