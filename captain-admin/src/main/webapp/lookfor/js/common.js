jQuery.fn.extend({
	checkAll : function(checked) {
		return this.each(function() {
			this.checked = checked;
		});
	}
});
/**
 * 屏遮层
 */
(function($){
	var isIE6 = $.browser.msie && $.browser.version == 6;
	$.OverLay = (function(){return function(){this.initialize.apply(this, arguments);}})();
	$.OverLay.prototype = {
	initialize: function(options){
	 this.SetOptions(options);
	 this.Lay = $(document.createElement("div"));
	 $('body').append(this.Lay)
	 this.Color = this.options.Color;
	 this.Opacity = parseInt(this.options.Opacity);
	 this.zIndex = parseInt(this.options.zIndex);
	 this.Lay.css({display:"none", zIndex:this.zIndex,left:0,top:0,position:"fixed",width:$(document).width(),height:$(document).height()})
	 if(isIE6){
		 this.Lay.css({position : "absolute"});
		 this._resize =(function(object, fun) {
				 return function() {
					 return fun.apply(object, arguments);
				 }
			 })(this,function(){
			 this.Lay.css({width:Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px",height:Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px"});
			 });
		 this.Lay.html('<iframe style="position:absolute;top:0;left:0;width:100%;height:100%;filter:alpha(opacity=0);"></iframe>');
	 }
	},
	SetOptions: function(options) {
	 this.options = {Color:"#666",Opacity:50,zIndex:1000};
	 jQuery.extend(this.options,options || {});
	},
	Show: function() {
	 if(isIE6){ this._resize(); $(window).resize(this._resize)}
	 this.Lay.css({backgroundColor:this.Color,display:"block",position:"absolute"});
	 if($.browser.msie) {this.Lay.css({filter : "alpha(opacity:" + this.Opacity + ")"})} else {this.Lay.css({opacity : this.Opacity / 100})}
	},
	Close: function() {
	 this.Lay.css({display : "none"});
	 if(isIE6){ $(window).resize(function(){})}
	}
};})(jQuery);


$(document).ready(function(){
	var btns = $("#btns :input.bluebtn");

	btns.each(function(){
		var hoverText = $(this).attr("title")||$(this).attr("value");
		if($.browser.msie){
			$(this).after("<em style='padding:20px 5px'>" + hoverText + "</em>");
		}else{
			$(this).after("<em><div style='width:100%;height:66px;overflow:hidden;display:table;position:static;'><div style='display: table-cell;vertical-align:middle;position:static;'><div style='position: relative;margin:0 auto;'>" + hoverText + "</div></div></div></em>");
		}
	});

	btns.hover(function() {
		$(this).next("em").stop(true,true).animate({opacity: "show", top: "-75"}, "slow");
	}, function() {
		$(this).next("em").animate({opacity: "hide", top: "-85"}, "fast");
	});

	btns.click(function() {
		//btns.attr('disabled', true);
		new $.OverLay({Opacity:10,Color:"blue"}).Show();
	});

});