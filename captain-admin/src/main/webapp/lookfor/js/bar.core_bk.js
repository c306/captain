/**
 * 
 */

var dragctx = true;
var finder = true;
var finder0 = true;
var finder0init = false;
var barW = 0;
var barH = 0;
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
var loadText = false;
function Bar() {}

window.onresize = function() {
	//bar.onChangeBarRight();
};
var srcoh = 0;
window.onscroll = function() {
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

Bar.prototype.init = function() {
	if (document.documentElement && document.documentElement.scrollTop)
		srcoh = document.documentElement.scrollTop;
	else if (document.body)
		srcoh = document.body.scrollTop;
	this.onChangeBarRight();//init bar right px
	
	var data = dataItems[0];
	/*var str = '<select size="6" id="finder00" class="bar_select" onchange="bar.onNavigKey(1, $(this).val());">';
	for(var i in data) {
		str += '<option onmouseover="bar.onSelectMouseOver(this);" onmouseout="bar.onSelectMouseOut(this);" value="' + data[i].id + '">' + data[i].name + '</option>';
	}
	str += '</select><select size="6" id="finder01" class="bar_select" onchange="bar.onNavigKey(2, $(this).val());"></select>';*/
	
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
	this.onDragStart();
};

Bar.prototype.onChangeBarRight = function() {
	$(".bar").css({"right":(document.body.scrollWidth + 11 - $(".topzone").width()) / 2});
};

Bar.prototype.onDragStart = function() {
	// add drag and drop functionality to #dragitem
	$("div.bar").easydrag();
	$("div.bar").setHandler("__dragStrip");
	// set a function to be called on a drop event
	//$("#dragitem").ondrop(function(e, element){ alert(element + " Dropped"); });
};

Bar.prototype.onNavigKey = function(level, objid, prtid) {
	if(objid != null) {
		var data = dataItems[level];
		var str = '';
		for(var i in data) {
			if(objid == data[i].objid) {
				//str += '<option onmouseover="bar.onSelectMouseOver(this);" onmouseout="bar.onSelectMouseOut(this);" value="' + data[i].id + '">' + data[i].name + '</option>';
				str += '<li id="' + data[i].id + '" objid="' + data[i].objid + '">' + data[i].name + '</li>';
			}
		}
		for(var j=4; j>level; j--) {
			if($("#finder0"+j)) $("#finder0"+j).remove();
		}
		if(str != '') {
			if($("#finder0"+level) == null || $("#finder0"+level).attr("id") == undefined) {
				//$("#finder").append('<select size="6" id="finder0' + level + '" class="bar_select" onchange="bar.onNavigKey(' + (level + 1) + ', $(this).val());"></select>');
				$("#finder").append('<div id="finder0' + level + '" class="bar_zone"></div>');
			} else {
				//if($("#finder0"+level).attr("class") == "bar_zone01")
				$("#finder0"+level).removeClass("bar_zone01").removeClass("bar_zone02").addClass("bar_zone");
			}
			if(finder0init) {
				$("#finder0").remove();
				finder0init = false;
			}
			$("#finder0"+level).empty();
			$("#finder0"+level).append("<ul>" + str + "</ul>");
			this.onSelectEvent("finder0" + level, level+1);
			
			this.onDragTipHide();
			this.onChgSize(level, 0);
			//$("#finder0"+level).animate({width: selectW});
			$("#finder0").remove();
			finder0init = false;
			loadText = false;
			
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
			if($("#finder0"+level) && level > 1) {
				//if(prtid == '8' || prtid == '9' || prtid == '11' || prtid == '22' || prtid == '6' || prtid == '7') {//8architecture 9interiors 22urban design 11research 6people 7collaboration
				//} else if(prtid == '3') {//writing
				//} else {
				//	this.onChgSize(level-1, 0);
				//}
				
				//$("#finder0"+level).animate({width: 0});
				//setTimeout(function() {
				$("#finder0"+level).remove();
				//}, 400);
			}
			this.onQuery(level, objid, prtid);
			loadText = true;
		}
		//objid 3 work
		//prtid 3 work第二级
		//  15 product下一级
		//  8,9,11,22 architecture,interiors,research,urban design下一级
		if(objid == '3' || prtid == '3' || prtid == '15') {
			selectedCategoryHighlight(objid);
		} else if(prtid == '8' || prtid == '9' || prtid == '11' || prtid == '22') {
			var type = 0;
			if(prtid == '8') type = '13';//architecture
			else if(prtid == '9') type = '14';//interiors
			else if(prtid == '11') type = '16';//research
			else if(prtid == '22') type = '29';//urban design
			selectedCategoryHighlight(type, objid);
		}
	}
};

Bar.prototype.onShowTitle = function(level, data, selectid) {
	var str = '';
	for(var i in data) {
		//str += '<option onmouseover="bar.onSelectMouseOver(this);" onmouseout="bar.onSelectMouseOut(this);" value="' + data[i].id + '">' + data[i].name + '</option>';
		str += '<li id="' + data[i].id + '" objid="' + data[i].objid + '">' + data[i].name + '</li>';
	}
	
	if($("#finder0"+level) == null || $("#finder0"+level).attr("id") == undefined) {
		//$("#finder").append('<select size="6" id="finder0' + level + '" class="bar_select" onchange="bar.onNavigKey(' + (level + 1) + ', $(this).val());"></select>');
		if(selectid == '9') {//9contact
			$("#finder").append('<div id="finder0' + level + '" class="bar_zone"></div>');
		} else if(selectid == '4') {//4writing
			
		} else {
			$("#finder").append('<div id="finder0' + level + '" class="bar_zone01"></div>');
		}
	}
	if(finder0init) {
		$("#finder0").remove();
		finder0init = false;
	}
	$("#finder0"+level).empty();
	$("#finder0"+level).append("<ul>" + str + "</ul>");
	this.onSelectEvent("finder0" + level, level+1);
	
	this.onDragTipHide();
	if(selectid == '4') {//4writing
		this.onChgSize(level+1, finder0W);
	} else {
		this.onChgSize(level, finder0W);
	}
	//$("#finder0"+level).animate({width: selectW});
	$("#finder0").remove();
	if(selectid == '9') {//9contact
		$("#finder").append('<div id="finder0" class="bar_content"></div>');
		$("#finder0").css({'width':selectW+64}).show();
	} else if(selectid == '4') {//4writing
		$("#finder0"+level).css({'width':selectW*3+64});
	}
	finder0init = false;
	loadText = false;
};

Bar.prototype.onChgSize = function(level, dwidth) {
	//$("div.bar").animate({width: 134+selectW*level});
	//$("div.drag_content").animate({width: 118+selectW*level});
	barW = barinitW + selectW * (level-1) + dwidth;
	$(".bar").css({"width":barW});
	$(".shadecentertop").css({"width":barW-27});
	$(".shadecenterbottom").css({"width":barW-27});
	$("#dragctx").css({"width":dragctxW+selectW*(level-1)+dwidth});
};

Bar.prototype.onQuery = function(level, objid, prtid) {
	if(!finder0init) {
		finder0init = true;
		$("#finder0").remove();
		$("#finder").append('<div id="finder0" class="bar_content"></div>');
	}
	//if(prtid == '3') {//writtings
	//} else {
	//	this.onDragTipHide();
	//}
	//$("#finder0").html(objid);
	queryInfo(level, objid, prtid);//query product info
};

Bar.prototype.onQueryFinish = function(level, btn, type) {
	//var _barDH = $(".bar").height();
	var _barEvH = Math.ceil(productH/btn);
	barH = barinitH + productH;
	//var _barDW = $(".bar").width();
	var productW = (level<3?3-level:1) * selectW + finder0W;
	barW = barinitW + productW;
	var _barEvW = Math.ceil(productW/btn);
	var _findW = (level<3?3-level:0) * selectW + finder0W;
	var _findEvW = Math.ceil(_findW/btn);
	/*var _barRight = document.body.scrollWidth - barinitW - (level-2)*selectW - $(".bar").offset().left;
	_barRight = _barRight<0?0:_barRight;
	if(_barRight > document.body.scrollWidth - barW) _barRight = document.body.scrollWidth - barW - 20;
	$(".bar").css({"right":_barRight, "left":""});*/
	
	var i = 1;
	$(".product_list").hide();
	if(type == 1) $(".product_title").html("").hide();
	var _dragItl = setInterval(function() {
		if(_barEvH*i < productH) {
			//$("#__dragStrip").css({"height":dragstpH+_barEvH*i});
			$(".bar").css({"height":barinitH+_barEvH*i});
			$(".shadeleft").css({"height":barinitH+_barEvH*i-27});
			$(".shaderight").css({"height":barinitH+_barEvH*i-27});
			$("#dragctx").css({"height":dragctxH+_barEvH*i});
			$(".product").css({"height":dragctxH+_barEvH*i});
			
			//$(".product_submit").css({"width":dragctxW+_barEvW*i});
			//$(".product_sbtn").css({"width":dragctxW+_barEvW*i});
			//$(".product_smsg").css({"width":dragctxW+_barEvW*i});
			$(".product").css({"width":dragctxW+_barEvW*i});
			$("#dragctx").css({"width":dragctxW+_barEvW*i});
			$(".bar").css({"width":barinitW+_barEvW*i});
			$(".shadecentertop").css({"width":barinitW+_barEvW*i-27});
			$(".shadecenterbottom").css({"width":barinitW+_barEvW*i-27});
			$("#finder0").css({"width":_findEvW*i});
		} else {
			clearInterval(_dragItl);
			$(".product_list").show();
			//$("#__dragStrip").css({"height":dragstpH+productH});
			$(".bar").css({"height":barinitH+productH});
			$(".shadeleft").css({"height":barinitH+productH-27});
			$(".shaderight").css({"height":barinitH+productH-27});
			$("#dragctx").css({"height":dragctxH+productH});
			$(".product").css({"height":productDH});
			
			//$(".product_submit").css({"width":productDW});
			//$(".product_sbtn").css({"width":productDW});
			//$(".product_smsg").css({"width":productDW});
			$(".product").css({"width":productDW});
			$("#dragctx").css({"width":dragctxW+productW});
			$(".bar").css({"width":barinitW+productW});
			$(".shadecentertop").css({"width":barinitW+productW-27});
			$(".shadecenterbottom").css({"width":barinitW+productW-27});
			$("#finder0").css({"width":_findW-2}).show();
			
			if(type == 2) bar.onProductShow(btn);
			$("#dragctx").show().prepend('<div onclick="resetInitialState();" class="product_detailclose"></div>');
		}
		i = i+1;
	}, btn*10);
};

Bar.prototype.onProductShow = function(btn) {
	var _barEvH = Math.ceil(productShowH/btn);
	var i = 1;
	$(".product_title").hide();
	var _dragItl = setInterval(function() {
		if(_barEvH*i < productShowH) {
			//$("#__dragStrip").css({"height":dragstpH+productH+_barEvH*i});
			$("#dragctx").css({"height":dragctxH+productH+_barEvH*i});
			$(".bar").css({"height":barinitH+productH+_barEvH*i});
			$(".shadeleft").css({"height":barinitH+productH+_barEvH*i-27});
			$(".shaderight").css({"height":barinitH+productH+_barEvH*i-27});
			$(".product").css({"height":productDH+_barEvH*i});
		} else {
			clearInterval(_dragItl);
			$(".product_title").show();
			//$("#__dragStrip").css({"height":dragstpH+productH+productShowH});
			$("#dragctx").css({"height":dragctxH+productH+productShowH});
			$(".bar").css({"height":barinitH+productH+productShowH});
			$(".shadeleft").css({"height":barinitH+productH+productShowH-27});
			$(".shaderight").css({"height":barinitH+productH+productShowH-27});
			$(".product").css({"height":productDH+productShowH});
		}
		i = i+1;
	}, btn*10);
};

Bar.prototype.onProductClose = function(btn) {
	var _barEvH = Math.ceil(productShowH/btn);
	var i = 1;
	var _dragItl = setInterval(function() {
		if(_barEvH*i < productShowH) {
			//$("#__dragStrip").css({"height":dragstpH+productH+productShowH-_barEvH*i});
			$("#dragctx").css({"height":dragctxH+productH+productShowH-_barEvH*i});
			$(".bar").css({"height":barinitH+productH+productShowH-_barEvH*i});
			$(".shadeleft").css({"height":barinitH+productH+productShowH-_barEvH*i-27});
			$(".shaderight").css({"height":barinitH+productH+productShowH-_barEvH*i-27});
			$(".product").css({"height":productDH+productShowH-_barEvH*i});
		} else {
			clearInterval(_dragItl);
			//$("#__dragStrip").css({"height":dragstpH+productH});
			$("#dragctx").css({"height":dragctxH+productH});
			$(".bar").css({"height":barinitH+productH});
			$(".shadeleft").css({"height":barinitH+productH-27});
			$(".shaderight").css({"height":barinitH+productH-27});
			$(".product").css({"height":productDH});
			$(".product_title").html("").hide();
		}
		i = i+1;
	}, btn*10);
};

Bar.prototype.onConvertSelected = function(levelid1, levelid2, levelid3) {
	if(levelid1 != null) {
		this.onNavigKey(1, levelid1, 0)//show level 2
		//$("#finder00").val(levelid1);
		$("#finder00 li").each(function() {
			if($(this).attr("id") == levelid1) 
				$(this).attr("jsvalue","selected").css({"background-color":"#FFFF32"});
			else
				$(this).attr("jsvalue","").css({"background-color":""});
		});
	}
	if(levelid2 != null) {
		this.onNavigKey(2, levelid2, 0)//show level 3
		//$("#finder01").val(levelid2);
		$("#finder01 li").each(function() {
			if($(this).attr("id") == levelid2) 
				$(this).attr("jsvalue","selected").css({"background-color":"#FFFF32"});
			else
				$(this).attr("jsvalue","").css({"background-color":""});
		});
	}
	if(levelid3 != null) {
		this.onNavigKey(3, levelid3, 0)//show level 4
		//$("#finder02").val(levelid3);
		$("#finder02 li").each(function() {
			if($(this).attr("id") == levelid3) 
				$(this).attr("jsvalue","selected").css({"background-color":"#FFFF32"});
			else
				$(this).attr("jsvalue","").css({"background-color":""});
		});
	}
};

Bar.prototype.onThirdSelected = function(levelid3) {
	$("#finder02 li").each(function() {
		if($(this).attr("id") == levelid3) 
			$(this).attr("jsvalue","selected").css({"background-color":"#FFFF32"});
		else
			$(this).attr("jsvalue","").css({"background-color":""});
	});
};

Bar.prototype.onDragctxInit = function() {
	$("#dragctx").html(dargctext).css({'height':dragctxH}).show();//text init
	//$("#__dragStrip").css({"height":dragstpH});
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
	$("#finder01").css({'width':selectW-2});//writings
};

Bar.prototype.onSelectEvent = function(objnme, level) {
	$("#"+objnme+" li").click(function() {
		obj = this;
		$(obj).attr("jsvalue","selected").css({"background-color":"#FFFF32"});
		$("#"+objnme+" li").each(function() {
			if(obj != this) $(this).attr("jsvalue","").css({"background-color":""});
		});
		bar.onNavigKey(level, $(obj).attr("id"), $(obj).attr("objid"));
	}).mouseenter(function() {
		if($(this).attr("jsvalue") != "selected") $(this).css({"background-color":"#FFFF32"});
	}).mouseleave(function(obj) {
		if($(this).attr("jsvalue") != "selected") $(this).css({"background-color":""});
	});
};

Bar.prototype.onSelected = function(objnme, objid) {
	$("#"+objnme+" li").each(function() {
		if($(this).attr("id") == objid) {
			$(this).attr("jsvalue","selected").css({"background-color":"#FFFF32"});
		} else {
			$(this).attr("jsvalue","").css({"background-color":""});
		}
	});
};
