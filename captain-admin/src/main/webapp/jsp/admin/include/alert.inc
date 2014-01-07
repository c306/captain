
<s:iterator var="ferr" value="fieldErrors">
  <div class="alert alert-error">
	<a class="close" data-dismiss="alert" href="#">&times;</a>
	<h4 class="alert-heading">Warning!</h4>
	${ferr}
  </div>
</s:iterator>

<s:iterator var="err" value="actionErrors">
  <div class="alert alert-error">
	<a class="close" data-dismiss="alert" href="#">&times;</a>
	<h4 class="alert-heading">Warning!</h4>
	${err}
  </div>
</s:iterator>

<s:iterator var="msg" value="actionMessages">
  <div class="alert ${ success ? 'alert-success' : 'alert-error' }">
	<a class="close" data-dismiss="alert" href="#">&times;</a>
	<h4 class="alert-heading">${ success ? 'Well done' : 'Warning' }!</h4>
	${msg}
  </div>
</s:iterator>

