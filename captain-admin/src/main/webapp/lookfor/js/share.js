function _shareto(id, _name, btn){
	var url = encodeURIComponent(document.location.href);
	if(id=="twitter"){
		window.open("http://twitter.com/home?status=" + ($('div.' + _name).html() + btn) + encodeURIComponent(" ") + url, "_blank");
		return;
	}else if(id=="sina"){
		window.open("http://v.t.sina.com.cn/share/share.php?url=" + url + "&appkey=3486983565&title=" + ($('div.' + _name).html() + btn) + "&pic=", "_blank");
		return;
	}else if(id=="mail"){
		window.open("mailto:?subject=" + $('div.' + _name).html() + "&body=" + ($('div.' + _name).html() + btn) + encodeURIComponent("\r\n") + url);
		return;
	}
}