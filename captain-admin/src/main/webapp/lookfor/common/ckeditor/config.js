/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';

//使用的工具栏 plugins/toolbar/plugin.js
config.toolbar = 'WriteNews';

config.toolbar_Full = [
['Source','-','Save','NewPage','Preview','-','Templates'],
['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
'/',
['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
['Link','Unlink','Anchor'],
['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
'/',
['Styles','Format','Font','FontSize'],
['TextColor','BGColor'],
['Maximize']
];

config.toolbar_Simple = [ [ 'Source', '-', 'Bold', 'Italic', 'Underline', 'Strike','-','Link' ] ];

config.toolbar_WriteNews = [
['Source','-','NewPage','Preview','-','Templates'],
['Cut','Copy','Paste','PasteText','PasteFromWord'],
['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
'/',
['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar'],
'/',
['Styles','Format','Font','FontSize'],
['TextColor','BGColor','-','Link','Unlink','Anchor'],
['Maximize']
];

//
config.height = 250

//是否允许改变大小 plugins/resize/plugin.js
config.resize_enabled = true

//改变大小的最大高度 plugins/resize/plugin.js
config.resize_maxHeight = 1000;

//改变大小的最大宽度 plugins/resize/plugin.js
config.resize_maxWidth = 1000;

//改变大小的最小高度 plugins/resize/plugin.js
config.resize_minHeight = 250;

//改变大小的最小宽度 plugins/resize/plugin.js
config.resize_minWidth = 750;

//当输入：shift+Enter是插入的标签
config.shiftEnterMode = CKEDITOR.ENTER_P;//可选：CKEDITOR.ENTER_BR或CKEDITOR.ENTER_DIV


};
