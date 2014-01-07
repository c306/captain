/**

*/

function MM_goToURL() {var i, args=MM_goToURL.arguments; document.MM_returnValue = false;for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");}
var em_reg = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/;
function _clear(field){
	if (field.defaultValue == field.value) field.value = '';
	else if (field.value == '') field.value = field.defaultValue;
}
function onLogClose(btn, evt) {
	var currentKey = evt.charCode || evt.keyCode;
	if(currentKey == 27) {
		$('div.'+btn).fadeOut();
	} else if (currentKey == 13) {
		if (btn == "loginBg") {
			logSubmit();
		} else if (btn == "signupBg") {
			sigSubmit();
		} else if (btn == "fgtBg") {
			fgtSubmit();
		}
	} else {
		var element;
		if(navigator.userAgent.indexOf("MSIE") > 0) {
			element = evt.srcElement.className;
		} else {
			element = evt.target.className;
		}
		if(element != null && element == btn) {
			$('div.'+btn).fadeOut();
		}
	}
}
function onLogin(typ) {
	if(typ == 2) {
		$('div.signupBg').css({'height':$(document).height()+'px'}).fadeIn();
		$('div.loginBg').fadeOut();
	} else if(typ == 3) {
		$('div.loginBg').fadeOut();
	} else if(typ == 4) {
		$('div.fgtBg').css({'height':$(document).height()+'px'}).fadeIn();
		$('div.loginBg').fadeOut();
	} else if(typ == 5) {
		$('div.fgtBg').fadeOut();
	} else if(typ == 6) {
		$('div.forceBg').css({'height':$(document).height()+'px'}).fadeIn();
		$('div.signupBg').fadeOut();
		$('div.loginBg').fadeOut();
	} else if(typ == 7) {
		$('div.signupBg').fadeOut();
		$('div.fgtBg').fadeOut();
		$('div.forceBg').fadeOut();
	} else {
		$.scrollTo(0, $(document).scrollTop(), {axis:'y'});
		setTimeout(function(){
			$('div.loginBg').css({'height':$(document).height()+'px'}).fadeIn();
			$('div.signupBg').fadeOut();
			$('div.fgtBg').fadeOut();
			$('div.forceBg').fadeOut();
			$('#logemail').focus();
		}, 1000);
	}
}
//close login
function _logOut(btn) {
	$.ajax({
		type: "POST",
		url: pth+"query/page!logclose.action",
		data: "",
		async: false,
		dataType: 'text',
		success: function(responsetxt) {
			$(btn).fadeOut();
		},
		error: function() {
			alert("Network anomaly.");
		}
	});
}
//Usernme add onclick event
function onUserClick(uName, avatar) {
	$("#loginfo").html(uName);
	$("#loginfo_div").show();
	$("#logout_div").show();
	$("#login_div").hide();
	
	/*$("#logindiv").hide();
	$("#loginfo").html(uName);
	$("#loout").show();
	if(avatar != "") {
		$("#avatarBox").html('<img src="' + pth + avatar + '" style="max-height:25px;max-width:25px;">');
	}*/
	onLogin(3);
}
//logout
function __logout() {
	$.ajax({
		type: "POST",
		url: pth+"query/page!logout.action",
		data: "",
		async: false,
		dataType: 'text',
		success: function(responsetxt) {
			if(responsetxt == "1") {
				$("#loginfo_div").hide();
				$("#logout_div").hide();
				$("#login_div").show();
			}
		},
		error: function() {
			alert("Network anomaly.");
		}
	});
}
//language change
function _change(btn) {
	var url = window.location.href;
	if( url.indexOf("?") > 0 ) {
		if(url.indexOf("request_locale") > 0) {
			url = url.replace("request_locale=zh_CN", "request_locale=" + btn).replace("request_locale=en_US", "request_locale=" + btn);
		} else {
			url += "&request_locale=" + btn;
		}
	} else {
		url += "?request_locale=" + btn;
	}
	MM_goToURL('parent', url);
}
//search
function _search(obj, evt) {
	var currentKey = evt.charCode || evt.keyCode;
	if(currentKey == 13) {
		if(obj.value == null || jQuery.trim(obj.value) == "") {
			alert(searchtip);
		} else {
			var url = pth+"search/" + jQuery.trim(obj.value);
			while(url.indexOf(" ") > 0 || url.indexOf("&") > 0) {//空格用-代替
				url = url.replace(" ", "-").replace("&", "-");
			}
			MM_goToURL("parent", url);
		}
	}
}
function onSearch(obj) {
	if(obj.value == null || jQuery.trim(obj.value) == "") {
		alert(searchtip);
	} else {
		var url = pth+"search/" + jQuery.trim(obj.value);
		while(url.indexOf(" ") > 0 || url.indexOf("&") > 0) {//空格用-代替
			url = url.replace(" ", "-").replace("&", "-");
		}
		MM_goToURL("parent", url);
	}
}
//subscribe
//subscribe
function onSubscribe() {
	var email = $("#searchbox").val();
	if(email != null && email.trim() != "") {
		$.ajax({
			type: "POST",
			url: pth+"query/msg!save.action",
			data: "domain.content=" + email,
			async: false,
			dataType: 'text',
			success: function(responsetxt) {
				if(responsetxt == "1") {
					alert(msgsucc);
				} else if (responsetxt == "exist") {
					alert(msgfail);
				}
			},
			error: function() {
				alert("Network anomaly.");
			}
		});
	} else {
		alert(msgcontent);
	}
}
function clearText(field){
	if (field.defaultValue == field.value) field.value = '';
	else if (field.value == '') field.value = field.defaultValue;
}
//sale
function onSale() {
	$.ajax({
		type: "POST",
		url: pth+"query/users!check.action",
		data: "oid=" + 1,
		async: false,
		dataType: 'text',
		success: function(responsetxt) {
			if(responsetxt == "login") {
				MM_goToURL("parent", pth+"sale-all");
			} else {
				onLogin(1);
			}
		},
		error: function() {
			alert("Network anomaly.");
		}
	});
}
function changeCart(btn) {
	if(btn > 0) {
		$("#cartNum").html(btn);
	} else {
		$("#cartNum").html('');
	}
}
//替换所有函数
String.prototype.replaceAll = function(s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}
function _geturl(btn) {
	var __show = "";
	if(desid != "") {
		__show = "designer";
	} else {
		if(ptype == "") {
			__show = "newin";
		} else if (ptype == "0") {
			__show = "men";
		} else if (ptype == "1") {
			__show = "women";
		} else if (ptype == "2") {
			__show = "accessories";
		} else if (ptype == "3") {
			__show = "sale";
		} else if (ptype == "6") {
			__show = "recommend";
		} else if (ptype == "7") {
			__show = "groupon";
		} else if (ptype == "8") {
			__show = "street";
		} else if (ptype == "9") {
			__show = "spotlight";
		} else if (ptype == "10") {
			__show = "news";
		}
	}
	__show = pth + __show + "/" + jQuery.trim(btn);
	while(__show.indexOf(" ") > 0 || __show.indexOf("&") > 0) {//空格用-代替
		__show = __show.replace(" ", "-").replace("&", "-");
	}
	return __show;
}

//获取时间当前时间秒数
function currTimeMinus() {
	var date = new Date();
	return (date.getHours() *3600 + date.getMinutes() * 60 + date.getSeconds());
}

//格式化日期
Date.prototype.format = function(format) { 
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
	};

	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}

	for(var k in o) {
		if(new RegExp("("+ k +")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
	}
	return format; 
}
