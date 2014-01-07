<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('LYNCH + SONG')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>


<!-- song css -->
<link type="text/css" href="${ctx}/lookfor/css/songs-cp-grid-admin.css" rel="stylesheet" />
<!-- masonry css -->
<link rel="stylesheet" href="${ctx}/lookfor/css/cs-vanilla-grid-admin.css" />
<!-- masonry js -->
<script src="${ctx}/lookfor/common/jquery/masonry/modernizr-transitions.js"></script>
<script src="${ctx}/lookfor/common/jquery/masonry/jquery.masonry.js"></script>
<%--image resize--%>
<script src="${ctx}/lookfor/common/jquery/jquery.ae.image.resize.min.js"></script>



        <ul class="nav nav-tabs" id="myTab">
          <li class="active"><a href="#tab1" data-toggle="tab">Loading ...</a></li>
        </ul>
        <div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
          <div class="tab-pane fade in active" id="tab1">






<!-- user script base on masonry  -start -->
<script type="text/javascript">
masonryHandler = function(){
	
}
  $(function() {
    //alert(new MasonryCoordinate(1,1).xy());
    //alert($(new Object()).data("asdf","haha").data("asdf"));

    //var global
    $masonrycontainer = $('#gridcontainer');

    if ( $masonrycontainer.hasClass("textcontent") )
      initParams = {
        picWidth: 96,
        picHeight: 92,
        widthSpacing: 15,
        heightSpacing: 14,
        grid: [ 111, 106 ],
        snapTolerance: 90
      }
    if ( $masonrycontainer.hasClass("textcontent_half") )
      initParams = {
        picWidth: 48,
        picHeight: 46,
        widthSpacing: 4,
        heightSpacing: 6,
        grid: [ 54, 52 ],
        snapTolerance: 40,
        originalSize: { width: 432, height: 676 }
      }

    

    //alert($('#gridcontainer .item').size()); // find all children
    //alert($('#gridcontainer > .item').size()); // find first level
    
    function initializeDraggables(){
      var $masonrydragitems = $masonrycontainer.find(".item");
      $masonrydragitems.each(function(i,item){
    	  if($(item).data('init'))
    		  return;
    	  $(item)
    	  .data('init', 1)
    	  .click(function(e){// 鼠标点击事件
        	/*
        	var me = this, $me = $(this);
            if($me.hasClass("selected")) {
            	$me.removeClass("selected").css({opacity: 0.3});
            } else {
            	$me.addClass("selected").css({opacity: 1});
            }
            if(!e.ctrlKey) {
            	// Ctrl+Click
            	return;
            }
            $masonrydragitems.each(function(i,n){
            	if(me !== this) {
            		$(this).removeClass("selected").css({opacity: 0.3});
            	}
            });
        	*/
          }).dblclick(function(){
        	  // clear coordinates
        	  var coords = $(this).data('coordinates')||new Array(0);
        	  $(this).data('coordinates',[]);//clear coordinates
        	  clearOldCoveredCoord(coords, $(this));
        	  // removeClass overlapped
        	  $(this).stop().removeClass('overlapped').animate({ opacity: 1 }, 1);
        	  $(this).stop().fadeOut('', function(){ $(this).remove(); })
        	  recycleCandidate($(this));
          }).bind("overlapEvent", overlapListener)
          .draggable({
              grid: initParams.grid,
              //snap: ".snapbox",
              snapMode: "inner",
              snapTolerance: initParams.snapTolerance,
              opacity: 0.55,
              containment: $masonrycontainer,
              addClasses: false,
              helper: "original",
              revert: "invalid",
              cursor: "move",
              stack: $masonrydragitems,
              stop: function(event, ui){}
            });
      });
    }
    //
    initializeDraggables();

    $masonrycontainer.droppable({
      accept: "#gridcontainer .item, #candidatecontainer .item",
      // activeClass: "custom-state-active",
      drop: function( event, ui ) {
        //alert(ui.draggable);
        //$container.masonry( 'reload' );
        //alert(ui.draggable == ui.helper);
        draggStop(ui.helper);
      }
    });
    
    $masonrycontainer.masonry({
      itemSelector: '.item',
      columnWidth: initParams.grid[0],
      isAnimated: false,
      //isAnimated: !Modernizr.csstransitions,
      animationOptions: function(){
    	  // ie9 下动画效果是animate，每个brick完成动画后会执行此方法
      }
    });

    masonryHandler.BuildingBlocks = function (ignoreXY){
        //
        initializeAxis();
        sortSlices(ignoreXY);
    }
    //
    masonryHandler.BuildingBlocks();



function moveLeft(ui, unit){
  ui.css({ left: ui.position().left - initParams.grid[0] * unit });
}
function moveRight(){}
function moveTop(ui, unit){
  ui.css({ top: ui.position().top - initParams.grid[1] * unit });
}
function moveBottom(){}
function exchange(){}
function revert(){}


function caculateMoveLeft(){}
function caculateMoveRight(){}
function caculateMoveTop(){}
function caculateMoveBottom(){}
function caculateExchange(){}

/*数组中是否包含某个element*/
function contain(arr, e){
  var c = false;
  $.each(arr, function(i,n){ if($(n)[0]===$(e)[0]){c=true;return false;} });
  return c;
}
// equation formula
function pointX(n){
  //if(n===0) return 0;
  if(n===0) return 4;
  //return 96*n+15*(n-1);
  //return initParams.picWidth*n+initParams.widthSpacing*(n-1);
  return 4 + initParams.grid[0]*n;
}
function pointY(n){
  if(n===0) return 0;
  //return 92*n+14*(n-1);
  //return initParams.picHeight*n+initParams.heightSpacing*(n-1);
  return initParams.grid[1]*n;
}
function calcUnitW(width){
  //return Math.round((width + 15 + 96/2)/111);
  return Math.round((width + initParams.widthSpacing + initParams.picWidth/2)/initParams.grid[0]);
}
function calcUnitH(height){
  //return Math.round((height + 14 + 92/2)/106);
  return Math.round((height + initParams.heightSpacing + initParams.picHeight/2)/initParams.grid[1]);
}
//
function findCoordinateByUi(ui){
  // var offset = $(ui).offset();
  // p.html( "left: " + offset.left + ", top: " + offset.top );
  var position = $(ui).position();
  // 计算ui坐标
  var x = calcUnitW(position.left)-1, y = calcUnitH(position.top)-1;
  // 计算ui的单位长度
  var unitW=calcUnitW($(ui).width()), unitH=calcUnitH($(ui).height());
  /*
  if(x+unitW > axis.length || y+unitH > axis[0].length ){
    //alert('This position is out of bounds !');
    return null;
  }
  */
  if( x+unitW > axis.length ){
    moveLeft(ui, x+unitW - axis.length );
    x = axis.length - unitW;
  }
  if( y+unitH > axis[0].length ){
    moveTop(ui, y+unitH - axis[0].length);
    y = axis[0].length - unitH;
  }
  return axis[x<0?0:x][y<0?0:y];
}
function isUiOutOfBounds(ui){
  // 计算ui的单位长度
  var unitW=calcUnitW($(ui).width()), unitH=calcUnitH($(ui).height());
  if(x+unitW > axis.length || y+unitH > axis[0].length ){
    //alert('This position is out of bounds !');
    return null;
  }
}
function findLeftElements(x){
  
}
// Masonry Coordinate
function MasonryCoordinate(w,h) {

  this.x = pointX(w);
  this.y = pointY(h);

  this.idxX = w;
  this.idxY = h;

  this.xy = function(){return this.x+','+this.y;}

  this.val = function(){return this.idxX+','+this.idxY;}

  this.clear = function(){
    $.extend(this, {
      isEmpty: true,
      isCovered: false,

      // item: null,

      // reference boj
      innerElement: null,
      coverdElements: new Array(0)
    });
  }

  this.clear();
}
// global variable
axis;
// initialize
function initializeAxis(){
  // init data x,y bounds width:8, height:13
  var stepW=8, stepH=1000;
  axis = new Array(stepW);
  for(var w=0;w<stepW;w++){
    axis[w] = new Array(stepH);
    for(var h=0;h<stepH;h++){
      axis[w][h] = new MasonryCoordinate(w,h);
    }
  }
}
// positioning
function sortSlices(ignoreXY){
  // set $masonrycontainer size
  //$masonrycontainer.css({ width: initParams.originalSize.width, height: initParams.originalSize.height });
  // mansory() reload 会改变container高度，so need remember the original height.
  if(!initParams.originalSize) {
    initParams.originalSize = { width: $masonrycontainer.width(), height: $masonrycontainer.height() };
  }
  $masonrycontainer.height(initParams.originalSize.height);
  // 
  var maxHight = 0;
  $masonrycontainer.find(".item").each(function(i,el){
	// 定位
    positioningSlice($(el), ignoreXY);
    // 计算高度
  	var top = pointY(Number($(el).attr('y')));
  	$(el).css({left: pointX(Number($(el).attr('x'))), top: top});
  	if(top+pointY(Number($(el).attr('h'))) > maxHight) {
  		maxHight = top + pointY(Number($(el).attr('h')));
  	}
  });
  if( maxHight > 676 - pointY(4) ) $masonrycontainer.height(maxHight+pointY(4));// add 4*unit height
}
/**
 * ignoreXY参数表示是否忽略ui中的x,y属性
 */
function positioningSlice(ui, ignoreXY){
  var x = +$(ui).attr('x'), y = +$(ui).attr('y');
  //var x,y;
  if(ignoreXY === true || isNaN(x) || isNaN(y)) {
    // get MasonryCoordinate
    var coordinate = findCoordinateByUi(ui);
    if(coordinate===null){ alert('This position is out of bounds !'); return; }
    $(ui).attr({x: x=coordinate.idxX, y: y=coordinate.idxY});
  }
  var coord = axis[x][y];
  coord.isEmpty = false;
  coord.innerElement = ui;
  //$(ui).offset({ top: coord.y, left: coord.x });
  //readonly $(ui).position({ top: coord.y, left: coord.x });
  var origs = $(ui).data('coordinates')||new Array(0);
  /*
  $.each(origs, function(i,newCoord){
    alert(newCoord.val());
  });
  */
  $(ui).data('coordinates', new Array(0));
  //alert($.isArray($(ui).data('coordinates')));
  var isOverlapped = false;
  function isCoveredOnlyByUi(coordi, ui){
    // only covered by itself
    return coordi.coverdElements.length===1 && $(coordi.coverdElements[0])[0] === $(ui)[0];
  }
  // 计算ui的单位长度
  var unitW=calcUnitW($(ui).width()), unitH=calcUnitH($(ui).height());
  // axis.slice(x, x+unitW);
  for(var w=x;w<x+unitW;w++){
    for(var h=y;h<y+unitH;h++){
      if( !isOverlapped && axis[w][h].isCovered && !isCoveredOnlyByUi(axis[w][h], ui)) {
        isOverlapped = true;
      }
      axis[w][h].isCovered = true;
      if(!contain(axis[w][h].coverdElements, $(ui)))  axis[w][h].coverdElements.push($(ui));
      $(ui).data('coordinates').push(axis[w][h]);
    }
  }
  // clear old overlapped coordinates
  clearOldCoveredCoord(origs, $(ui));
  //
  $(ui).data('isOverlapped', isOverlapped).trigger("overlapEvent", [ui]);
  // unique
  coord.coverdElements = $.unique(coord.coverdElements);
  // 
}
//clear old overlapped coordinates
function clearOldCoveredCoord(originCoordinates, ui) {
  $.each(originCoordinates, function(i,oldCoveredCoord){
    var hasSame = false;
    hasSame = contain($(ui).data('coordinates'), oldCoveredCoord);
    if(!hasSame){
      // 新坐标已不含原坐标，则原坐标应清除ui
      $.each(oldCoveredCoord.coverdElements, function(i,oldUi){
        if(ui[0] === oldUi[0]){
          // find original ui idx and remove it
          oldCoveredCoord.coverdElements.splice(i,1);
          return false;// break loop
        }
      });
      // clear this coordinate if it is not covered any ui
      if(oldCoveredCoord.coverdElements.length === 0) {
        //var b1 = oldCoveredCoord.isCovered;
        oldCoveredCoord.clear();
      }
      // validate old ui
      $.each(oldCoveredCoord.coverdElements, function(i,oldUi){
        validateOverlaps(oldUi);
      });
    }
  });
}

// validate overlaps
function validateOverlaps(ui){
  if($(ui).data('isOverlapped')) {
    $(ui).data('isOverlapped', false);
    $.each($(ui).data('coordinates'), function(i,coordinate){
      if(coordinate.coverdElements.length > 1){
        $(ui).data('isOverlapped', true);
        return false;
      }
    });
    if(!$(ui).data('isOverlapped'))
      $(ui).trigger("overlapEvent", [ui]);
  }
}

// warn
function overlapListener(evn, ui){
  if($(ui).data('isOverlapped')) {
    $(ui).stop().addClass('overlapped').fadeTo(800, +$(ui).css('opacity')===1?0.33:1,
      function(){
        overlapListener(evn, ui);
      });
  } else {
    $(ui).stop().removeClass('overlapped').animate({
        opacity: 1
      }, 1,
      function(){});
  }
}


function draggStop(ui){
  positioningSlice(ui, true);
  //alert($(ui).position().top);
  var maxHight = $(ui).position().top + pointY(Number($(ui).attr('h'))) + pointY(4);
  if( maxHight > 676 && maxHight > $masonrycontainer.height() ) $masonrycontainer.height( maxHight );// add 4*unit height
}

/**********************************/
/**************#candidatecontainer********************/
/**********************************/
  var $candidatecontainer = $('#candidatecontainer');

  // setup function
  function setupDraggableForCandiate(item) {
	  $(item).draggable({
		    //grid: initParams.grid,
		    //snap: ".snapbox",
		    //snapMode: "inner",
		    //snapTolerance: 40,
		    opacity: 0.55,
		    //containment: $masonrycontainer,
		    addClasses: false,
		    cursor: "move",
		    cursorAt: { top: 35, left: 35 },
		    appendTo: $masonrycontainer,
		    //appendTo: $masonrycontainer.parent(),
		    //helper: function( event ) { return $( "<div class='ui-widget-header'>I'm a custom helper</div>" ); },
		    helper: function( a, b, c ) {
		    	var h = $(this).clone();
		    	h.children('div').remove();
		    	$(this).find('img').hide();//hide org ui
		    	$masonrycontainer.css({overflow:'visible'});//将overflow置为可见，当stop时再置为hidden
		    	return $( h.prop('outerHTML').replace(/style=".*?"/g, '') );
		    },
		    //helper: "clone",
		    zIndex: 100,
		    revert: "invalid",
		    cursor: "move",
		    //scope: "tasks",
		    //stack: "#candidatecontainer .item",
		    start: function( event, ui ) {$masonrycontainer.data('mouseincontainer','no')},
		    stop: function(event, ui){// ui.draggable不存在，可直接使用$(this)
		        //
		        if($masonrycontainer.data('mouseincontainer') === 'yes') {
		          ui.helper.removeClass('ui-draggable-dragging').remove();
		          var coordinate = findCoordinateByUi(ui.helper);
		          if(coordinate===null){ alert('STOP! This position is out of bounds !'); return; }
		          // insert ui
		          insertAppropriatePosition(ui);
		          // remove candidate ui from #candidatecontainer
		          $(this).remove();
		          //return false;
		        } else {
		        	$(this).find('img').show();
		        }
		    	$masonrycontainer.css({overflow:'hidden'});
		        //alert($masonrycontainer.find('.ui-draggable-dragging').css({backgroundColor: '#FF0000'}));
		      }
		  });
  }

  var $candidatedragitems = $candidatecontainer.find(".item");
  $candidatedragitems.each(function(i,n){setupDraggableForCandiate(n);});

  // when mouse inter grid container
  $masonrycontainer
  //.mouseenter(function(){$masonrycontainer.data('mouseincontainer','yes')})
  //.mouseleave(function(){$masonrycontainer.data('mouseincontainer','no')})
  .mousemove(function(evn){
	  var helper = $masonrycontainer.children().last().filter('.ui-draggable-dragging');// the draggable helper element: ui-draggable-dragging
	  if( helper.length < 1 ) return;
	  if( (helper.position().left - $masonrycontainer.position().left) < 420 ) {// 偏移大于420，表明candidate在gallery container之外
		  if( $masonrycontainer.data('mouseincontainer') !== 'yes' )
			  $masonrycontainer.data('mouseincontainer','yes');
	  } else {
		  if( $masonrycontainer.data('mouseincontainer') !== 'no' )
			  $masonrycontainer.data('mouseincontainer','no');
	  }
  });
  
  // add new image item
  function insertAppropriatePosition(ui){
    $masonrycontainer.prepend( ui.helper.prop('outerHTML') );//.masonry( 'reload' );
    initializeDraggables();
    //
    masonryHandler.BuildingBlocks(true);
  }
  // move into candidate
  function recycleCandidate(ui){
	  var cntr = $candidatecontainer;
	  cntr.prepend( ui.removeClass('masonry-brick').prop('outerHTML').replace(/style=".*?"/, 'style="display: none;"') ); //替换style="" //非贪婪模式匹配
	  setupDraggableForCandiate(cntr.find('div:first-child'));
	  cntr.find('div:first-child').append( function(){
		  return '<div><code>' + $(this).attr('w') + ' x ' + $(this).attr('h') + '</code></div>';
	  } ).find('img').resizeImage().centerImage();
	  //alert(123);
	  cntr.find('div:first-child').fadeIn('slow');
  }

  
  // fix candiate on scroll
  var $win = $(window)
    , navTop = $candidatecontainer.length && $candidatecontainer.offset().top - 40
    , isFixed = 0;
  
  $candidatecontainer.css({ height: $win.height() - 170 });

  /*// hack sad times - holdover until rewrite for 2.1
  $nav.on('click', function () {
    if (!isFixed) setTimeout(function () {  $win.scrollTop($win.scrollTop() - 47) }, 10)
  })
  */

  $win.on('scroll', processScroll);

  function processScroll() {
    var scrollTop = $win.scrollTop()
    if (scrollTop >= navTop && !isFixed) {
      isFixed = 1
      $candidatecontainer.addClass('candidate-fixed').css({ height: $win.height() - 196 });
    } else if (scrollTop <= navTop && isFixed) {
      isFixed = 0
      $candidatecontainer.removeClass('candidate-fixed').css({ height: $win.height() - 170 });
    }
  }
  
  
  });
</script>
<!-- user script base on masonry  //end -->

<!-- resize image -->
<script type="text/javascript">
var candidateImageDimension = {width: 96, height: 92};
jQuery.fn.extend({
	resizeImage : function() {
		return this.each(function() {
			if(this.tagName !== 'IMG')alert('error: not a img tag, but a ' + this.tagName);
			var iwidth = candidateImageDimension.width, iheight = candidateImageDimension.height;
			var image = new Image();
			image.src = this.src;
			if(image.width>0 && image.height>0){
				if(image.width <= iwidth && image.height<= iheight){
					$(this).css({width: image.width, height: image.height});
				} else if(image.width/image.height >= iwidth/iheight){
					if(image.width > iwidth){
						$(this).css({width: iwidth, height: (image.height*iwidth)/image.width});
					}
				}else{
					if(image.height > iheight){
						$(this).css({width: (image.width*iheight)/image.height, height: iheight});
					}
				}
			}
		});
	},
	centerImage: function(){
		var w = $(this).width(), h = $(this).height();
		if( candidateImageDimension.width === w )
			$(this).css({marginTop: ( candidateImageDimension.height-h ) / 2});
		else if( candidateImageDimension.height === h )
  			$(this).css({marginLeft: ( candidateImageDimension.width-w ) / 2});
	}
});
$(function(){
	$('#candidatecontainer img').aeImageResize(candidateImageDimension).one( "error", function() {
		this.src = '${ctx}/lookfor/images/common/emptypic.jpg';
		//$(this).resizeImage().centerImage();
	});
});
</script>




<div id="textzone" class="textzone"><!-- textzone start -->
<!-- grid div 
<div id="snapgride" style="position: absolute; top:0px; left:0px; clear:both; width:100%; height:100%; z-index:0;">
    <div class="snapbox item col1 row1"></div>
</div>-->

<div id="toolbar" style="margin-left: 8px; margin-right: 8px;">
  <div style="width: 452px; float: left; margin: 0 0 0px 0;" class="">
    <div class="btn-group">
      <a id="savebtn" class="btn" href="javascript:void(0);" onclick="saveMasonry()"><s:text name="global.button.label.save"/></a>
      <a id="previewbtn" class="btn" href="javascript:void(0);" onclick="previewMasonry()"><s:text name="global.button.label.preview"/></a>
      <a id="autosortbtn" class="btn" href="javascript:void(0);" onclick="autoSortMasonry()"><s:text name="global.button.label.autobuild"/></a>
      <a class="btn btn-small" href="javascript:void(0);" onclick="showSettingsDialog()"><i class="icon-cog"></i><s:text name="global.button.label.Settings"/></a>
      <div class="btn-group pull-right">
        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
          Action
          <span class="caret"></span>
        </a>
        <ul class="dropdown-menu">
          <li><a id="clearbtn" href="javascript:void(0);" onclick="clearMasonry()"><s:text name="global.button.label.clear"/></a></li>
          <li><a id="reloadbtn" href="${ctx}/admin/masonry.action" onclick=""><s:text name="global.button.label.reload"/></a></li>
          <!-- 
          <li class="divider"></li>
          <li><a id="autosortbtn" href="javascript:void(0);" onclick="autoSortMasonry()"><s:text name="global.button.label.autobuild"/></a></li>
          -->
        </ul>
      </div>
    </div>
  </div>
  <div style="width: 350px; float: right; margin: 0 0 0px 0;" class="">
    <div class="btn-group">
      <div class="btn-group pull-right">
        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
          Action
          <span class="caret"></span>
        </a>
        <ul class="dropdown-menu">
          <li><a id="clearbtn" href="javascript:void(0);" onclick="clearCandidates()"><s:text name="global.button.label.clear"/></a></li>
          <!-- 
          <li><a id="reloadcandidatebtn" href="javascript:void(0);" onclick=""><s:text name="global.button.label.reload"/></a></li>
          -->
        </ul>
      </div>
    </div>
  </div>
</div>

<!-- images -->
<div id="gridcontainer" class="textcontent_half">

  <s:iterator var="item" value="items" status="stat">
    <div class="item col${item.w } row${item.h }" w="${item.w}" h="${item.h}" x="${item.x}" y="${item.y}" type="${item.type}" aid="${item.articleId}" picid="${item.objid}" ordinal="" url="${item.url }" >
      <img alt="" src="${ctx}${item.url }">
    </div>
  </s:iterator>
  
</div>
<!-- candidatecontainer div  -->
<div id="candidatecontainer" class="">

    <!-- start page.datas -->
    <s:iterator var="item" value="page.datas" status="stat">
      <div class="item col${item.width} row${item.heigh}" w="${item.width}" h="${item.heigh}" x="" y="" type="${item.nodepath}" aid="${item.objid}" picid="${item.oid}" ordinal="" url="${ctx}/${item.uri }" >
        <img title="${item.width}x${item.heigh}" src="${ctx}/${item.uri }" />
        <div><code>${item.width} x ${item.heigh}</code></div>
      </div>
    </s:iterator>

</div><!-- candidatecontainer div end  onerror="javascript:this.src='${ctx}/lookfor/images/common/emptypic.jpg'"-->

</div><!-- textzone end -->


<form id="previewfrm" action="${ctx}/query/bar!first.action" method="post" target="_blank">
<input type="hidden" name="keys" id="previewfrm_keys">
</form>

          </div> <!-- /tab1 -->

        </div> <!-- /tab-content -->



<script type="text/javascript">
///////////////////////////////////////////////////
////////////masonry js//////////////////////////
///////////////////////////////////////////////////
function showSettingsDialog() {
	var showgrid = '<s:property value="@com.c306.comm.sysargs.SysArgsUtil@getVal(@com.c306.code.utils.SongConstans@KEY_BGLINE)" />';
	var showbgimg = '<s:property value="@com.c306.comm.sysargs.SysArgsUtil@getVal(@com.c306.code.utils.SongConstans@KEY_BGIMAGE)" />';
	
	if(showgrid === '1') {
		$('#boxshowgrid').prop('checked', true);
		$('#setting_grid').addClass('setting_grid');
	} else {
		$('#boxshowgrid').prop('checked', false);
		$('#setting_grid').removeClass('setting_grid');
	}
	if(showbgimg === '1') {
		$('#boxshowbgimg').prop('checked', true);
		$('#setting_bg_img').addClass('setting_bg_img');
	} else {
		$('#boxshowbgimg').prop('checked', false);
		$('#setting_bg_img').removeClass('setting_grid');
	}
	// open dialog
	$( '#dialog-settings' ).dialog( 'open' );
}
function appliySettings() {
	var keys = '';
	if($('#boxshowgrid')[0].checked){
		keys += '<s:property value="@com.c306.code.utils.SongConstans@KEY_BGLINE" />=1;';
	} else {
		keys += '<s:property value="@com.c306.code.utils.SongConstans@KEY_BGLINE" />=0;';
	}
	keys += '<s:property value="@com.c306.code.utils.SongConstans@KEY_BGIMAGE" />=1;';
	if($('#boxshowbgimg')[0].checked){
		keys += '<s:property value="@com.c306.code.utils.SongConstans@KEY_BGIMAGE" />=1;';
	} else {
		keys += '<s:property value="@com.c306.code.utils.SongConstans@KEY_BGIMAGE" />=0;';
	}
	//
	showProgressbar();
	$.post("${ctx}/admin/sysargs!updateAppParams.action", { "keys": keys },
			   function(data){
				 showProgressbar(1);
				 //
				 if(data.success === true)
				 	 showMsg("Background settings have changed successfully.", 1, 1);
				 else
					 showMsg("Failed to change bachground.", 1, 2);
			   }, "json");
}
function autoSortMasonry() {
	$masonrycontainer.masonry( 'reload' , function(){
		setTimeout( function(){
			masonryHandler.BuildingBlocks(true);
	        }, 300 );
	} );

}
function clearMasonry() {
	if(!confirm('<s:text name="global.hintinfo.operateconfirm"/>')){
		return false;
	}
	$('#gridcontainer').html('');
	showProgressbar();
	$.post("${ctx}/admin/masonry!save.action", { "keys": assembleJson() },
			   function(data){
				 //reload
				 document.location = "${ctx}/admin/masonry.action";
			   }, "json");
}
function previewMasonry() {
	$('#previewfrm_keys').val(assembleJson());
	$('#previewfrm').submit();
}
function saveMasonry() {
	if(!confirm('<s:text name="global.hintinfo.operateconfirm"/>')){
		return false;
	}
	showProgressbar();
	$.post("${ctx}/admin/masonry!save.action", { "keys": assembleJson() },
			   function(data){
				 showProgressbar(1);
				 //
				 if(data.success === true)
				 	 showMsg("Your changes have saved successfully.", 1, 1);
				 else
					 showMsg("Failed to save.", 1, 2);
			   }, "json");
	
}
function assembleJson() {
	var json = [];
	var items = $('#gridcontainer').children('.item');
	items.each(function(i, n){
		json[json.length] = convertJson(i, n.attributes);
	});
	/*
	$.each(axis, function(i){
		//axis[w][h].coverdElements
		$.each(axis[i], function(j, coordinate){
			json[json.length] = convertJson(i+''+j, coordinate.innerElement.attributes);
		});
	});
	*/
	json = '[' + json.join(',') + ']';
	return json;
}
function convertJson(idx, obj) {
	var uri = '${ctx}' === '' ? obj.url.nodeValue : ( obj.url.nodeValue.replace('${ctx}','') );
	return '{"w":' + obj.w.nodeValue + ',"h":' + obj.h.nodeValue + ',"x":' + obj.x.nodeValue + ',"y":' + obj.y.nodeValue + ',"url":"' + uri + '","type":"' + obj.type.nodeValue + '","articleId":"' + obj.aid.nodeValue + '","objid":"' + obj.picid.nodeValue + '","ordinal":' + obj.y.nodeValue + obj.x.nodeValue + '}';//,"ordinal":' + obj.ordinal + '
}

///////////////////////////////////////////////////
////////////candidates js//////////////////////////
///////////////////////////////////////////////////
function clearCandidates() {
	if(!confirm('<s:text name="global.hintinfo.operateconfirm"/>')){
		return false;
	}
	showProgressbar();
	$.post("${ctx}/admin/masonry!removeCandidates.action", { "keys": assembleCandidatesIds() },
			   function(data){
          		 showProgressbar(1);
          		 //
          		 if(data.success === true) {
          			 $('#candidatecontainer').html('');
          		 	 showMsg("Candidate icons have cleared successfully.", 1, 1);
          		 }
          		 else
          			 showMsg("Failed to clear.", 1, 2);
			   }, "json");
}
function assembleCandidatesIds() {
	var ids = [];
	$('#candidatecontainer').children('.item').each(function(i, n){
		ids[i] = $(n).attr('picid');
	});
	return ids.join(',');
}

///////////////////////////////////////////////////
//////////// common js
///////////////////////////////////////////////////
//dialog-message
function showMsg(msg, isModal, success) {
	var icon = '', clas = '';
	if(success && success === 1){
		icon = '<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>';
		//clas = 'ui-state-error ui-corner-all';
	} else if(success) {
		icon = '<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>';
		clas = 'ui-state-error ui-corner-all';
	}
	$( "#dialog-message" ).find('#dialog-message-cnt').html('<div class="' + clas + '">' + icon + msg + '</div>').parent().dialog( "open" );
}
function showProgressbar(hide) {
	if(hide) {
		$("#ui-progressbar").remove();
		$("#overlay_div").remove();
		return;
	}
	$(document.body).append('<div id="ui-progressbar" class="progress progress-striped active ui-state-highlight" style="position:fixed;width:350px;z-index:1030;top:'+($(window).height()-30)/2+'px;left:'+($(window).width()-350)/2+'px;"><div class="bar" style="width: 100%;"></div></div>');
	//$(document.body).append('<div id="ui-progressbar" style="position:fixed;width:350px;z-index:1030;top:'+($(window).height()-30)/2+'px;left:'+($(window).width()-350)/2+'px;"></div>');
	$(document.body).append('<div id="overlay_div" class="bar" style="position:absolute;top:0px;left:0px;z-index:1000;0px;width:'+$(document).width()+'px;height:'+$(document).height()+'px;"></div>');
	return;
}
$(function() {
  $( "#dialog-settings" ).dialog({
    modal: true,
    autoOpen: false,
    minHeight: 300,
    maxHeight: 600,
    buttons: {
      <s:text name="global.button.label.apply"/>: function() {
        $( this ).dialog( "close" );
        appliySettings();
      },
      <s:text name="global.button.label.cancel"/>: function() {
        $( this ).dialog( "close" );
      }
    }
  });
  $( "#dialog-message" ).dialog({
    modal: true,
    autoOpen: false,
    maxHeight: 300,
    buttons: {
      Ok: function() {
        $( this ).dialog( "close" );
      }
    }
  });
});
</script>

<div style="display:none;">
<div id="dialog-settings" title="<s:text name="global.button.label.Settings"/>" style="padding-top:15px;">
  <div class="control-group">
    <label class="checkbox">
      <input type="checkbox" id="boxshowgrid" onclick="$('#setting_grid').toggleClass('setting_grid')">
      <s:text name="admin.label.settings.showgrid"/>
    </label>
    <div class="controls">
      <div id="setting_grid" class="setting_block"></div>
    </div>
  </div>
  <div class="control-group">
    <label class="checkbox">
      <input type="checkbox" id="boxshowbgimg" onclick="$('#setting_bg_img').toggleClass('setting_bg_img')">
      <s:text name="admin.label.settings.showbackgroundimage"/>
    </label>
    <div class="controls">
      <div id="setting_bg_img" class="setting_block"></div>
    </div>
  </div>
</div>
<div id="dialog-message" title="Information" style="padding-top:15px;">
  <div id="dialog-message-cnt"></div>
</div>
</div>

<%@ include file="/jsp/admin/include/foot.inc"%>