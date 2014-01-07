<%@ page contentType="text/html; charset=UTF-8"%>

<s:iterator var="msg" value="actionMessages">
	<script type="text/javascript">alert("${msg}");</script>
</s:iterator>

<s:iterator var="err" value="actionErrors">
      	<script type="text/javascript">alert("${err}");</script>
</s:iterator>
<%--
<s:iterator var="ferr" value="fieldErrors">
            <div style="padding: 0pt 0.7em;" class="ui-state-error ui-corner-all"> 
				<p><span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span> 
				<strong>Alert:</strong> ${ferr}</p>
			</div>
</s:iterator>
 --%>

<%--
<s:if test='%{#parameters["invalid.token"][0] == "true"}'>
      	<script type="text/javascript">alert("请勿重复提交！");</script>
</s:if>
 --%>



<!-- dialogs ///////////////////////////////////////////////// -->
<!-- dialogs.div -->
<div style="display:none;">
	<!-- dialog-message -->
	<div id="dialog-message" title="Information" style="padding-top:15px;">
	  <div id="dialog-message-cnt"></div>
	</div><!-- dialog-message //end -->
</div><!-- dialog.div display:none; -->

<script type="text/javascript">
// dialogs init script
$(function($){
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
function captainProgressbar(hide) {
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
</script>
<!-- // end dialog -->
