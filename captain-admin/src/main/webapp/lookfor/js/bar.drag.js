var DragableObj = {	handle : null,
	dx : 0,
	dy : 0,
	init : function(e) {
		e = e || event;
		this.handle = e.target || e.srcElement;
		if (this.handle.className.indexOf('drag') == -1)
			return;
		while (this.handle.tagName != 'HTML' && this.handle.title != "dragable") {
			this.handle = this.handle.parentNode || this.handle.parentElement;
		}
		if (this.handle.title != 'dragable')
			return;
		this.handle.style.position = 'relative';
		this.dx = parseInt(this.handle.style.left + 0) - e.clientX;
		this.dy = parseInt(this.handle.style.top + 0) - e.clientY;
		document.onmousemove = DragableObj.drag;
	},
	drag : function(e) {
		e = e || event;
		if (this.handle != null) {
			this.handle.style.left = (e.clientX + this.dx) + 'px';
			this.handle.style.top = (e.clientY + this.dy) + 'px';
		}
	},
	drop : function(e) {
		this.handle = null;
		document.onmousemove = null;
	}
};
document.onmousedown = DragableObj.init;
document.onmouseup = DragableObj.drop;
document.onselectstart = function(e) {
	e = e || event;
	eo = e.target || event.srcElement;
	if (eo.className.indexOf('drag') != -1)
		return false;
};