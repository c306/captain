//图片宽
var WIDTH=300;
//图片高
var HEIGHT=262;
function reset() {
    //以Ext获得指定元素，并指定此元素左边距、上方边距、右边距、下方边距
    Ext.get('target').highlight().setBounds(10,10,WIDTH+10,HEIGHT+10);
}
 
function act() {
    //刷新
    reset();
    //在指定时间内移动到指定位置
    Ext.get('target').setBounds(150,80,WIDTH+150,WIDTH+80,{duration:1.0});
}
/**//**
 * 播放连续动作
 */
function Anime(){
    Ext.get('target').highlight().fadeOut().fadeIn().pause(2).switchOff().puff();
    //IE下不支持switchOn()方法，这是一个Ext的bug
}
/**//**
 * 淡出
 */ 
function fadeout() {
    //设定最后不透明度为0.0(完全透明),持续时间为1.0，方式为easeNone
    Ext.get('target').setOpacity(0.0,{ duration:1.0,easing:'easeNone'});
}

/**//**
 * 淡入
 */
function fadein() {
    Ext.get('target').setOpacity(1.0,{duration:1.0,easing:'easeNone'});
}

function act2() {
    reset();
    var easingMethod=document.getElementById('easing').value;
    Ext.get('target').setLocation(150,150,{
        duration:1.0,
        easing:easingMethod
    });
}