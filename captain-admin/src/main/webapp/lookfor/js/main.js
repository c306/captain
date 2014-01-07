function queryInfo(level, objid, prtid) {
	var page = "";
	$("#finder0").html('');
	//$("#__loadding").css({'height':$(document).height()}).show();
	//$("#finder0").hide();
	if(objid == '5' || objid == '6') {//CN or EN
		if(objid == '5') {//CN
			page = ctx + "/cn";
		} else if(objid == '6') {//EN
			page = ctx + "/en";
		}
		window.location.href = page;
	} else if(objid == '13' || objid == '14' || objid == '16' || objid == '19' || objid == '29' || objid == '9' || objid == '10' || objid == '4' || objid == '8') {
		//13architecture 14interiors 29urban design 16research 19archive 9contact 10collaboration 4writings 8people
		//init bar size
		if(objid == '4') {
			bar.onChgBarSize(1, objid);
		} else {
			bar.onChgBarSize(2, objid);
		}
		var type = 0;
		if(objid == '13')      type = '8';//architecture
		else if(objid == '16') type = '11';//research
		else if(objid == '19') type = '23';//archive
		else if(objid == '14') type = '9';//interiors
		else if(objid == '29') type = '22';//urban design
		else if(objid == '9')  type = '6';//contact
		else if(objid == '10') type = '7';//collaboration
		else if(objid == '4')  type = '3';//writings
		else if(objid == '8')  type = '5';//people
		$.ajax({
			url: ctx + "/query/artle!queryByType",
			data: {'keys': type},
			cache: false,
			async: true,
			dataType: "json",
			success: function(data) {
				$("#__loadding").hide();
				if(objid == '4') {
					bar.onShowTitle(1, data);
				} else {
					bar.onShowTitle(2, data);
				}
				//bar selected
				if(objid == '13' || objid == '14' || objid == '16' || objid == '19' || objid == '29') {
					bar.onSelected("finder02", itemobj);
					itemobj = "";
				}
			},
			error: function(){
				alert('Error !');
			}
		});
	} else {
		if(prtid == '8' || prtid == '9' || prtid == '11' || prtid == '23' || prtid == '22') {//8architecture 9interiors 22urban design 11research 23archive
			bar.onQueryStart(level, 2, 1, 1);
			$("#dragctx").load(ctx + "/query/artle!queryByOid?keys=" + objid + "&" + Math.random(), function() {
				bar.onQueryFinish();
			});
		} else if(prtid == '15') {//products
			var type = 0;
			if(objid == '17') type = '12';//Seating
			else if(objid == '18') type = '13';//Tables
			else if(objid == '27') type = '14';//Storage
			else if(objid == '23') type = '15';//Lighting
			else if(objid == '26') type = '16';//Bath
			else if(objid == '28') type = '17';//Installations
			else if(objid == '25') type = '18';//Finishes
			else if(objid == '24') type = '19';//Hardware
			bar.onQueryStart(level, 2, 1);
			$("#dragctx").load(ctx + "/query/artle!queryProducts?keys=" + type + "&" + Math.random(), function() {
				bar.onQueryFinish();
			});
		} else if(objid == '7') {//7practice
			bar.onQueryStart(level, 2, 1);
			$("#dragctx").load(ctx + "/query/artle!queryPractice?keys=4" + "&" + Math.random(), function() {
				bar.onQueryFinish();
			});
		} else if(prtid == '5'){//8people detail
			bar.onQueryStart(level, 2, 1, 1);
			$("#dragctx").load(ctx + "/query/artle!queryPeople?keys=" + objid + "&" + Math.random(), function() {
				bar.onQueryFinish();
			});
		} else if(prtid == '6'){//contact detail
			$("#finder0").show();
			bar.onQueryStart(level, 2, 1);
			$("#dragctx").load(ctx + "/query/artle!queryPdetail?keys=" + objid + "&" + Math.random(), function() {
				bar.onQueryFinish();
			});
		} else if(prtid == '7'){//collaboration
			bar.onQueryStart(level, 2, 1, 1);
			$("#dragctx").load(ctx + "/query/artle!queryColltion?keys=" + objid + "&" + Math.random(), function() {
				bar.onQueryFinish();
			});
		} else if (prtid == '3') {//writtings
			bar.onQueryStart(level, 2, 1, 1);
			$("#dragctx").load(ctx + "/query/artle!queryColltion?keys=" + objid + "&" + Math.random(), function() {
				bar.onQueryFinish();
			}).show();
		}
	}
}
function _clear(field) {
	if($(field).html().trim() == draft) {
		$(field).html("");
	} else if($(field).html().trim() == "") {
		$(field).html(draft);
	}
}
