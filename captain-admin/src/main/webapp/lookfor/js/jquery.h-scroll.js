/*
针对需要分页符重写的无限下拉插件
作者:Hanson Fang
*/
 (function($) {
    $.fn.infinitescroll = function(options) {
        var defualts = {
            navSelector: '#page-nav',
            nextSelector: '#page-nav a',
            itemSelector: '.items',
            normalSelector: '#page',
            mainContain: '#main-contain',
            contentSelector: null,
            msgText: '载入内容中...',
            img: pth+'/images/ajax-loader.gif',
            callbak: null
        };
        var opts = $.extend({},
        defualts, options);
        var p = $(opts.nextSelector),
        q = '<div id="infscr-loading"><img src="' + opts.img + '"><span>' + opts.msgText + '</span></div>';
        box = $(opts.contentSelector).is('table') ? $('<tbody/>') : $('<div/>');
        $.ajaxSetup({
            cache: false
        });
        var loading = function() {
            o = $(opts.navSelector);
            if (o.length != 0) {
                var st = $(window).scrollTop(),
                sth = st + $(window).height();
                var t = p.attr("href") + " " + opts.mainContain;
                post = o.offset().top;
                posb = post + o.height();
                if ((post > st && post < sth) || (posb > st && posb < sth)) {
                    $(window).unbind("scroll");
                    a = $(q).insertBefore(opts.navSelector).slideDown(100);
                    box.load(t, 
                    function(success) {
                        a.remove();
                        var x = $(this).find(opts.normalSelector);
                        var y = $(this).find(opts.itemSelector);
                        var z = $(this).find(opts.nextSelector);
                        if ($.isFunction(opts.callback)) {
                            opts.callback(y)
                        }
                        b = setTimeout(function() {
                            if ((x.length) == 1) {
                                $(opts.mainContain).append(x).find(opts.navSelector).remove()
                            } else {
                                p.attr("href", z.attr("href"))
                            }
                            $(window).scroll(loading);
                            clearTimeout(b)
                        },
                        500)
                    })
                }
            }
        };
        $(window).scroll(loading);
    }
})(jQuery);
