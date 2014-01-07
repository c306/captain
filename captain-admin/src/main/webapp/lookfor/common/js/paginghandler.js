Pagination = {
	customOper : function(e) {
		if(!e)
			e = event.srcElement;
		var frm = this.findParentNode(e, 'FORM');
		frm.reset();
		frm.submit();
	},
	pageOperate : function(pageNo, obj) {
		if (!/\d/g.test(pageNo)) {
			alert("页码错误!");
			return false;
		}
		var pageNoEl = document.getElementById('__Pagination_PageNo');
		pageNoEl.value = pageNo;
		this.customOper(obj);
	},
	// 每页显示行数改变时
	pageSizeChangeHandler : function(el) {
		this.customOper(el);
	},
	// 页码改变时
	pageNoChangeHandler : function() {
		var currentKey = event.charCode || event.keyCode;
		if (currentKey == 13) {
			var pno = new Number(event.srcElement.value);
			if (pno > 2147483647) {
				event.srcElement.value = 2147483647;
			}
			this.customOper();
		} else {
			event.srcElement.value = event.srcElement.value.replace(/[^\d]/g, '');
			if (event.srcElement.value == "")
				event.srcElement.value = "1";
		}
	},
	setCustomOper : function(func) {
		if ("function" === typeof func) {
			this.customOper = func;
		} else {
			alert('请设置function对象');
		}
	},
	findParentNode : function(node, parentNodeName) {
		if (node.nodeName == parentNodeName) {
			return node;
		}
		for ( var i = 0, n = node; n = n.parentNode; i++) {
			if (n.nodeName == parentNodeName) {
				return n;
			}
			if (n.nodeName == document.body.nodeName) {
				return null;
			}
		}
	}
};
