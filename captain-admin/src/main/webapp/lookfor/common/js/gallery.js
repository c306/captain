/*
@name: jQuery Plugin Template for Coding
*/
;(function($) {//[--jQuery Plugin Container

//declare the plugin's version ; use to check the plugin exists
$.galleryUpload = $.galleryUpload || {version:'0.1.0'};

//[--Plugin Define
var galleryUpload = function(node,opts) {

    var me=this,$me=$(this);
    var $mine=$(node); //get the plugin's Operation jQuery DOM Element

    //Public Methods
    $.extend(me, {
        showGallery: function(html) {
			__show__(html);
        },
        removeUploadFile: function(oid) {
			removeUploadFile(oid);
        },
        options: function(optName) {
            //return the preset options to users code
            //let users can be change options by later code
			if(optName){
				return opts[optName];
			} else {
				return opts;
			}
        }
    });

    //Private Variables ( Module Level )
    var m_var_gal_photo = opts.galleryContainer;
	var $gallery = $('#'+m_var_gal_photo);

//###########--start--############################################################
    //init the plugin
    function __init__(){
		$('#'+me.options('galleryContainer')).addClass('UploadPhotos').append('<div formfields="_alluploadfields" style="display:none;"></div>');
		if(me.options('uploadWay') === 'form'){
			$("#dialog-upload-form").dialog({
				autoOpen: false,
				height: 250,
				width: 420,
				modal: true,
				resizable: false
			});
		} else {
			$("#dialog-upload-ffu"+me.options('galleryContainer')).dialog({
				autoOpen: false,
				height: 480,
				width: 640,
				modal: true,
				resizable: true
			});
		}
		
		//
		if (opts.autoShow && "function" === typeof opts.getLiHtml) {
			__show__(opts.getLiHtml(opts.galleryItems()));
		} else {
			alert('请设置function对象');
		}
    }
//###########--end--############################################################
    __init__();

///////////////////////////////////////////////////////////////////////////////////
///////////////////////////---start--////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////
	function __show__(data){
		if(!data || data == '') return;
		try{
			var $tmpLi = $(data);
			var $img = $tmpLi.find('img');

			$img.css({width: me.options('imgW'), height: me.options('imgH')})
				.bind('load', resizeImage)
				.wrap($('<div style="display: table-cell;vertical-align: middle;"></div>').css({width: me.options('imgW'), height: me.options('imgH')}));
			
			$tmpLi.appendTo($gallery);
			if(me.options('uploadWay') === 'form'){
				$('#_upfile').val();
				//reset upload form
				$("#upload-form").show();
				$('#dialog-upload-form').dialog('close');
				$("#upload-form")[0].reset();
				$( "#_upload_progressbar").hide();
				if(me.options().maximum <= $gallery.find('img').length) {
					$mine.button().button("disable").button("refresh");
				}
			}
			setupFields();
		}catch(e){alert("01"+e.message);}
		// afterShow callback
		if(typeof me.options('afterShow') === 'function') {
			me.options('afterShow')(me.options('galleryContainer'));
		}
	}
	function removeUploadFile(oid){
		$gallery.children('li[oid=' + oid + ']').fadeOut('normal',function() {
				$(this).remove();
				setupFields();
			});
		if($mine.attr('disabled')) {
			$mine.button().button("enable").button("refresh");
		}
	}
	function setupFields(){
		//disabled submit buttons. ex("#tab2 .Button002")
		if(me.options('SubmitBtnSelector') !== '')
			$(me.options('SubmitBtnSelector')).button("disable");

		if(me.options('fieldId') !== null && me.options('fieldId') !== ''){
			var uri = $gallery.find('img').attr('uri');
			$('#'+me.options('fieldId')).val(uri);
		}
		if(me.options('fieldName') === null || me.options('fieldName') === '')
			return;
		var moduleId = me.options('moduleId');
		var objId = me.options('objId');
		var $allFields = $('#'+opts.galleryContainer).children('div[formfields="_alluploadfields"]');
		$allFields.empty();
		$gallery.find('img').each(function(i){
			var fieldName = me.options('fieldName') + '[' + i + ']';
			var furi = '<input type="hidden" name="' + fieldName + '.uri" value="' + $(this).attr('uri') + '"/>';
			var ftitle = '<input type="hidden" name="' + fieldName + '.title" value="' + $(this).attr('filename') + '"/>';
			var fobjid = '<input type="hidden" name="' + fieldName + '.objid" value="' + objId + '"/>';
			var fmoduleid = '<input type="hidden" name="' + fieldName + '.moduleid" value="' + moduleId + '"/>';
			var foid = '<input type="hidden" name="' + fieldName + '.oid" value="' + $(this).attr('oid') + '"/>';
			var fordinal = '<input type="hidden" name="' + fieldName + '.ordinal" value="' + i + '"/>';
			var isvisible = '<input type="hidden" name="' + fieldName + '.isvisible" value="' + $(this).attr('isvisible') + '"/>';//是否首页展示
			$allFields.append(furi).append(ftitle).append(fobjid).append(fmoduleid).append(foid).append(fordinal).append(isvisible);
		});
		//enable submit buttons. ex("#tab2 .Button002")
		if(me.options('SubmitBtnSelector') != '')
			$(me.options('SubmitBtnSelector')).button("enable");
	}

	// let the gallery items be sortable
	$gallery.sortable({
//		containment: $gallery.parent(),
		containment: $gallery,
		items: "> li",
		revert: "invalid",
//		placeholder: "ui-widget-content",
		forcePlaceholderSize: true,
		cancel: "a.ui-icon",
		distance: 5,
		tolerance: "pointer",
		stop: function(event, ui) { setupFields(); }
	});
	$gallery.disableSelection();
	// image deletion function
	var recycle_icon = "<a href='#' title='Recycle this image' class='ui-icon ui-icon-refresh'>Recycle image</a>";
	function deleteImage( $item ) {
		$item.fadeOut('normal',function() {
			//$item.find( "a.ui-icon-trash" ).remove();
		});
	}

	function resizeImage(){
		var iwidth = me.options('imgW'), iheight = me.options('imgH');
		var image = new Image();
		image.src = this.src;
		
		if(image.width>0 && image.height>0){
			if(image.width <= iwidth && image.height<= iheight){
				$(this).css({width: image.width, height: image.height});
			} else if(image.width/image.height >= iwidth/iheight){
				if(image.width > iwidth){
					$(this).css({width: iwidth, height: (image.height*iwidth)/image.width});
				}
			}else{
				if(image.height > iheight){
					$(this).css({width: (image.width*iheight)/image.height, height: iheight});
				}
			}
		}
	}
///////////////////////////////////////////////////////////////////////////////////
//////////////////////////--end-/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////

};//--]Plugin Define


//jQuery Plugin Implementation
$.fn.galleryUpload = function(conf) {

    //return existing instance // let users can use the Public Methods
    //Usage: var obj = $('#id').galleryUpload({ <options> }).data("galleryUpload");
    var el;
    if ( typeof conf === 'string' ) {
    	el = $.data( this.get(0), 'galleryUpload' );
    	return el.options(conf);
    } else {
        el = $.data( this.get(0), 'galleryUpload' );
    }
    
    if (el) { return el; }
    
    if(!conf) {
    	alert('error !');
    	return undefined;
    }

    //setup default options
    var opts = {
			autoShow:true,
			onShow:function(e,o){},
			onHide:function(e,o){},
			api: true,
			maximum: 100,
			uploadWay: 'form',
			imgW: 96,
			imgH: 72,
//			GUID: '__gallery_guid_',
			destDir: '',
			destFileName: '',
			fieldName: '',//多图片上传时，隐藏域name；(ep. domain.atts)
			fieldId: '',//单个图片上传时，隐藏域id；(ep: domain.imguri)
			moduleId: '',//模块类型id
			objId: '',//objid
			uploadUrl: '',//upload url
			SubmitBtnSelector: '',//提交按钮
			galleryContainer:"UploadPhotos",
			GalleryValidator: function(){return true},
			getLiHtml: function(){return "";}
		};

    //if no users options then use the default options
    $.extend(opts, conf);

    // install the plugin for each items in jQuery
    this.each(function() {
        el = new galleryUpload(this, opts);
        $.data( this, "galleryUpload", el );
        var $galleryCtnr = $('#'+el.options('galleryContainer'));
        $.data( $galleryCtnr.get(0), "galleryUpload", el );
        $galleryCtnr.addClass('ffuctnr');
		
		if(el.options('uploadWay') === 'form'){
			$(this).button()
				.click(function() {
					var $galleryUpd = $.data( this, 'galleryUpload' );
					if($galleryUpd.options('destDir') != null && $galleryUpd.options('destDir') != '')
						$('#upload-form #destDir').val($galleryUpd.options('destDir'));
					if($galleryUpd.options('destFileName') != null && $galleryUpd.options('destFileName') != '')
						$('#upload-form #destFileName').val($galleryUpd.options('destFileName'));
					$('#dialog-upload-form').dialog("option", {
								buttons: {
									'Upload To Server': function() {
										if($("#_upfile").val() ==''){
											alert('Please pick a file.');return;
										}
										//validate
										if(!($galleryUpd.options().GalleryValidator($("#_upfile").val()) == true))
											return;
										$( "#_upload_progressbar").progressbar({value: 100}).css({height:22,display:'block'});
										$("#upload-form").hide();
										$("#upload-form").submit();
									},
									Cancel: function() {
										$(this).dialog('close');
									}
								}
							}).dialog('open');
					return false;//如果不返回false，FF会提交 表单
				});
		} else if(el.options('uploadWay') === 'ffu') {
			$(this).button()
				.click(function() {
					//var $galleryUpd = $.data( this, 'galleryUpload' );
					$('#dialog-upload-ffu'+el.options('galleryContainer')).dialog("option", {
								buttons: {
									Close: function() {
										$(this).dialog('close');
									}
								}
							}).dialog('open');
					return false;//如果不返回false，FF会提交 表单
				});
		}
    });

    //api=true let users can immediate use the Public Methods
    return opts.api ? el: this;

};


})(jQuery);//--]jQuery Plugin Container
