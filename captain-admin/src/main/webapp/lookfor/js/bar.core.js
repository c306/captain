/**
 * 
 */

var finder0init = false;
var loadText = false;
var barinitW = 252;
var barinitH = 174;
var finder0W = 186;
var dragctxW = 238;
var dragctxH = 82;
var dragstpH = 174;
var selectW = 120;
var productH = 402;//760
var productDW = 544;
var productDH = 484;//838
var productShowH = 416;
var productDcdf = 200;//明细展示高度差异
var srcoh = 0;
window.onresize = function() {
	//bar.onChangeBarRight();
};
window.onscroll = function() {
	bar.onInitBarPosition();
};

function Bar() {}
Bar.prototype.init = function() {
	if (document.documentElement && document.documentElement.scrollTop) srcoh = document.documentElement.scrollTop;
	else if (document.body) srcoh = document.body.scrollTop;
	this.onChangeBarRight();//init bar right px
	
	var data = dataItems[0];
	var str = '<div id="finder00" class="bar_zone"><ul>';
	for(var i in data) {
		str += '<li id="' + data[i].id + '" objid="' + data[i].objid + '">' + data[i].name + '</li>';
	}
	str += '</ul></div><div id="finder01" class="bar_zone"><ul>';
	//default value
	data = dataItems[1];
	for(var i in data) {
		if(data[i].objid == 1) {
			str += '<li id="' + data[i].id + '" objid="' + data[i].objid + '">' + data[i].name + '</li>';
		}
	}
	str += '</ul></div>';
	$("#finder").append(str);
	this.onSelected("finder00", 1);//主页
	this.onSelectEvent("finder00", 1);
	this.onSelected("finder01", dflan);//默认语言
	this.onSelectEvent("finder01", 2);
	
	//drag
	$("div.bar").easydrag();
	$("div.bar").setHandler("__dragStrip");
};

Bar.prototype.onInitBarPosition = function() {
	var bodytop = 0;
	if($(".bar").height() == dragstpH || $(".bar").height() == 90) {
		if (document.documentElement && document.documentElement.scrollTop)
			bodytop = document.documentElement.scrollTop;
		else if (document.body)
			bodytop = document.body.scrollTop;
		var bartop = Math.abs($(".bar").position().top + bodytop - srcoh);
		$(".bar").css({'top':bartop});
		
		srcoh = bodytop;
	}
};

Bar.prototype.onChangeBarRight = function() {
	$(".bar").css({"right":(document.body.scrollWidth + 11 - $(".topzone").width()) / 2});
};

Bar.prototype.onNavigKey = function(level, objid, prtid) {
	if(objid != null) {
		var data = dataItems[level];
		var str = '';
		for(var i in data) {
			if(objid == data[i].objid) str += '<li id="' + data[i].id + '" objid="' + data[i].objid + '">' + data[i].name + '</li>';
		}
		for(var j=4; j>level; j--) {
			if($("#finder0"+j)) $("#finder0"+j).remove();
		}
		if(str != '') {
			if($("#finder0"+level) == null || $("#finder0"+level).attr("id") == undefined) {
				$("#finder").append('<div id="finder0' + level + '" class="bar_zone"></div>');
			} else {
				$("#finder0"+level).removeClass("bar_zone01").removeClass("bar_zone02").addClass("bar_zone");
			}
			this.onDragTipHide();
			this.onChgSize(level, 0);
			$("#finder0").remove();
			$("#finder0"+level).empty().append("<ul>" + str + "</ul>");
			finder0init = false;
			loadText = false;
			this.onSelectEvent("finder0" + level, level+1);
			
			//Home back to top
			if(objid == '1') {
				var stop = $(document).scrollTop();
				var iv = Math.ceil(stop/10);
				var ij = 1;
				var scro = setInterval(function() {
					if(iv*ij < stop) {
						$(document).scrollTop(stop-iv*ij);
					} else {
						clearInterval(scro);
						$(document).scrollTop(0);
					}
					ij = ij+1;
				}, 10);
				$(".bar").css({"top":25});
				this.onChangeBarRight();
				
				//selected
				this.onSelected("finder01", dflan);//默认语言
				//this.onDragTipHide();
			}
		} else {
			if($("#finder0"+level) && level > 1) $("#finder0"+level).remove();
			if(!finder0init) {
				finder0init = true;
				$("#finder0").remove();
				$("#finder").append('<div id="finder0" class="bar_content"></div>');
			}
			loadText = true;
			queryInfo(level, objid, prtid);//query product info
		}
	}
};

Bar.prototype.onChgBarSize = function(level, selectid) {
	this.onDragTipHide();
	if(selectid == '4') {//4writing
		this.onChgSize(level+1, finder0W);
	} else if(selectid == '9') {//9contact
		this.onChgSize(level, 0);
	} else {
		this.onChgSize(level, finder0W);
	}
	if($("#finder0"+level) == null || $("#finder0"+level).attr("id") == undefined) {
		if(selectid == '9') {//9contact
			$("#finder").append('<div id="finder0' + level + '" class="bar_zone"></div>');
		} else if(selectid == '4') {//4writing
			//
		} else {
			$("#finder").append('<div id="finder0' + level + '" class="bar_zone01"></div>');
		}
	}
	$("#finder0").remove();
	$("#finder0"+level).empty();
	if(selectid == '9') {//9contact
		//$("#finder").append('<div id="finder0" class="bar_content"></div>');
		//$("#finder0").css({'width':selectW+64}).show();
	} else if(selectid == '4') {//4writing
		$("#finder0"+level).css({'width':selectW*3+64});
	}
	finder0init = false;
	loadText = false;
};

Bar.prototype.onShowTitle = function(level, data) {
	var str = '';
	for(var i in data) {
		str += '<li id="' + data[i].id + '" objid="' + data[i].objid + '">' + data[i].name + '</li>';
	}
	$("#finder0"+level).append("<ul>" + str + "</ul>");
	this.onSelectEvent("finder0" + level, level+1);
};

Bar.prototype.onChgSize = function(level, dwidth) {
	var barW = barinitW + selectW * (level-1) + dwidth;
	$(".bar").css({"width":barW});
	$(".shadecentertop").css({"width":barW-27});
	$(".shadecenterbottom").css({"width":barW-27});
	$("#dragctx").css({"width":dragctxW+selectW*(level-1)+dwidth});
};

Bar.prototype.onQueryStart = function(level, btn, type, btype) {
	var productW = (level<3?3-level:1) * selectW + finder0W;
	var _findW = (level<3?3-level:0) * selectW + finder0W;
	/*var _barRight = document.body.scrollWidth - barinitW - (level-2)*selectW - $(".bar").offset().left;
	_barRight = _barRight<0?0:_barRight;
	if(_barRight > document.body.scrollWidth - barW) _barRight = document.body.scrollWidth - barW - 20;
	$(".bar").css({"right":_barRight, "left":""});*/
	if(type == 1) {
		$(".product_list").css({"height":productDH}).show();
		$(".product_title").html("").hide();
		$(".bar").css({"height":barinitH+productH});
		$(".shadeleft").css({"height":barinitH+productH-27});
		$(".shaderight").css({"height":barinitH+productH-27});
		$("#dragctx").css({"height":dragctxH+productH});
		$(".product").css({"height":productDH});
	} else if(type == 2) {
		this.onProductShow(5);
	}
	$(".product").css({"width":productDW});
	$("#dragctx").css({"width":dragctxW+productW});
	$(".bar").css({"width":barinitW+productW});
	$(".shadecentertop").css({"width":barinitW+productW-27});
	$(".shadecenterbottom").css({"width":barinitW+productW-27});
	//13architecture 14interiors 29urban design 16research 19Archive 10collaboration 4writings
	$("#finder0").remove();
	if(btype == 1) {
		//
	} else {
		$("#finder").append('<div id="finder0" class="bar_content"></div>');
		$("#finder0").css({"width":_findW-2}).show();
	}
	$("#dragctx").empty().html(dargctext).show();
	$(".drag_tip").css({"line-height":dragctxH+productH+"px"});
	$("#__loadding").show();
};

Bar.prototype.onQueryFinish = function(btn) {
	$("#__loadding").hide();
	$(".product_list").show();
	$(".product_list_01").show();
	$(".product_title").show();
	$("#dragctx").show().prepend('<div onclick="resetInitialState();" class="product_detailclose"></div>');
};

Bar.prototype.onProductShow = function(btn) {
	$("#dragctx").css({"height":dragctxH+productH+productShowH-productDcdf});
	$(".bar").css({"height":barinitH+productH+productShowH-productDcdf});
	$(".shadeleft").css({"height":barinitH+productH+productShowH-productDcdf-27});
	$(".shaderight").css({"height":barinitH+productH+productShowH-productDcdf-27});
	$(".product").css({"height":productDH+productShowH-productDcdf});
	$(".product_list").css({"height":productDH-productDcdf}).show();
	$(".product_title").show();
};

Bar.prototype.onProductClose = function(btn) {
	$(".product_list").css({"height":productDH}).show();
	$("#dragctx").css({"height":dragctxH+productH});
	$(".bar").css({"height":barinitH+productH});
	$(".shadeleft").css({"height":barinitH+productH-27});
	$(".shaderight").css({"height":barinitH+productH-27});
	$(".product").css({"height":productDH});
	$(".product_title").html("").hide();
};

Bar.prototype.onConvertSelected = function(levelid1, levelid2, levelid3) {
	if(levelid1 != null) {
		this.onNavigKey(1, levelid1, 0)//show level 2
		//$("#finder00").val(levelid1);
		$("#finder00 li").each(function() {
			if($(this).attr("id") == levelid1) 
				$(this).attr("jsvalue","selected").css({"background-color":"#FFFFB4"});
			else
				$(this).attr("jsvalue","").css({"background-color":""});
		});
	}
	if(levelid2 != null) {
		this.onNavigKey(2, levelid2, 0)//show level 3
		//$("#finder01").val(levelid2);
		$("#finder01 li").each(function() {
			if($(this).attr("id") == levelid2) 
				$(this).attr("jsvalue","selected").css({"background-color":"#FFFFB4"});
			else
				$(this).attr("jsvalue","").css({"background-color":""});
		});
	}
	if(levelid3 != null) {
		this.onNavigKey(3, levelid3, 0)//show level 4
		//$("#finder02").val(levelid3);
		$("#finder02 li").each(function(i) {
			if($(this).attr("id") == levelid3) {
				$(this).attr("jsvalue","selected").css({"background-color":"#FFFFB4"});
				$("#finder02").scrollTop($(this).height() * i);
			} else {
				$(this).attr("jsvalue","").css({"background-color":""});
			}
		});
	}
};

Bar.prototype.onSelected = function(objnme, objid) {
	$("#"+objnme+" li").each(function(i) {
		if($(this).attr("id") == objid) {
			$(this).attr("jsvalue","selected").css({"background-color":"#FFFFB4"});
			$("#"+objnme).scrollTop($(this).height() * i);
		} else {
			$(this).attr("jsvalue","").css({"background-color":""});
		}
	});
};

Bar.prototype.onClearSelect = function() {
	if($("#finder02 li").size() > 0) {
		$("#finder02 li").each(function() {
			$(this).attr("jsvalue","").css({"background-color":""});
		});
		if($("#finder02").width() > selectW) $("#finder0").hide();
	} else if($("#finder01 li").size() > 0) {
		$("#finder01 li").each(function() {
			$(this).attr("jsvalue","").css({"background-color":""});
		});
	}
};

Bar.prototype.onDragctxInit = function() {
	$("#dragctx").html(dargctext).css({'height':dragctxH}).show();//text init
	$(".bar").css({"height":dragstpH});
	$(".shadeleft").css({"height":dragstpH-27});
	$(".shaderight").css({"height":dragstpH-27});
	$("#finder01").css({'width':selectW-2});//writings
};

Bar.prototype.onDragTipHide = function() {
	$("#dragctx").html(dargctext).css({'height':barinitH-dragctxH-2}).hide();//text init
	$(".bar").css({"height":dragstpH-dragctxH-2});
	$(".shadeleft").css({"height":dragstpH-dragctxH-27-2});
	$(".shaderight").css({"height":dragstpH-dragctxH-27-2});
	
	if($("#finder0") != null && $("#finder0").attr("id") == "finder0") {
		var fdW = $("#finder0").width();
		$(".product").css({"width":$(".product").width()-fdW});
		$("#dragctx").css({"width":$("#dragctx").width()-fdW});
		$(".bar").css({"width":$(".bar").width()-fdW-2});
		$(".shadecentertop").css({"width":$(".shadecentertop").width()-fdW-2});
		$(".shadecenterbottom").css({"width":$(".shadecenterbottom").width()-fdW-2});
	}
	$("#finder0").remove();
	if($($("#finder00 li").get(3)).attr("jsvalue") == "selected") {//writings
		//$("#finder0").remove();
	} else {
		$("#finder01").css({'width':selectW-2});
	}
	/*$("#finder0").html("");
	//writings
	if($($("#finder00 li").get(3)).attr("jsvalue") == "selected") {//writings
		$("#finder0").remove();
	} else {
		$("#finder01").css({'width':selectW-2});
	}*/
};

Bar.prototype.onSelectEvent = function(objnme, level) {
	$("#"+objnme+" li").click(function() {
		obj = this;
		$(obj).attr("jsvalue","selected").css({"background-color":"#FFFFB4"});
		$("#"+objnme+" li").each(function() {
			if(obj != this) $(this).attr("jsvalue","").css({"background-color":""});
		});
		bar.onNavigKey(level, $(obj).attr("id"), $(obj).attr("objid"));
		
		var objid = $(obj).attr("id"), prtid = $(obj).attr("objid");
		//objid 3 work
		//prtid 3 work第二级
		//  15 product下一级
		//  8,9,11,19,22 architecture,interiors,research,archive,urban design下一级
		if(objid == '3' || prtid == '3' || prtid == '15') {
			selectedCategoryHighlight(objid);
		} else if(prtid == '8' || prtid == '9' || prtid == '11' || prtid == '23' || prtid == '22') {
			var type = 0;
			if(prtid == '8') type = '13';//architecture
			else if(prtid == '9') type = '14';//interiors
			else if(prtid == '11') type = '16';//research
			else if(prtid == '23') type = '19';//archive
			else if(prtid == '22') type = '29';//urban design
			selectedCategoryHighlight(type, objid);
		} else {
			selectedCategoryHighlight(objid);
		}
	}).mouseenter(function() {
		if($(this).attr("jsvalue") != "selected") $(this).css({"background-color":"#FFFFB4"});
	}).mouseleave(function(obj) {
		if($(this).attr("jsvalue") != "selected") $(this).css({"background-color":""});
	});
};
