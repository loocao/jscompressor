var config = {
    version : '2.0.4',
    published : '2016-04-19',
    copyright : '© 2012 - <a href="http://www.oncereply.me/">oncereply</a>',
    menus : {
        leftColumn : [ {
            title : 'Jscompressor',
            subMenus : [ {
                href : 'index.html',
                text : 'Home'
            }, {
                href : 'http://www.apache.org/licenses/LICENSE-2.0',
                text : 'Lisence',
                externalLink : true
            }, {
                href : 'download.html',
                text : 'Download'
            }, {
                href : 'changelog.html',
                text : 'ChangeLog'
            }, {
                href : 'howtouse.html',
                text : 'HowToUse'
            } ]
        } ],
        xright : [ {
            href : 'https://github.com/oncereply/jscompressor/issues',
            text : 'Issues',
            externalLink : true
        }, {
            href : 'https://github.com/oncereply/jscompressor/wiki',
            text : 'Wiki',
            externalLink : true
        }, {
            href : 'https://github.com/oncereply/jscompressor',
            text : 'Fork me on GitHub',
            externalLink : true
        } ]
    }
};
(function(window) {
    var jc = window.jc = config;
    jc.initMenus = function() {
        // navcolumn
        var leftColumn = $('#leftColumn #navcolumn').empty();
        $(config.menus.leftColumn).each(function(i) {
            var sub = this;
            var title = sub.title;
            var ui = $('<ui></ui>').appendTo(leftColumn.append('<h5>' + title + '</h5>'));
            $(sub.subMenus).each(function(i) {
                var item = this, li = $('<li class="none"></li>');
                var pathname = window.location.pathname;
                pathname = pathname == '' ? '/' : pathname;
                var paths = pathname.split('/');
                if (paths[paths.length - 1] == '') {
                    paths[paths.length - 1] = 'index.html';
                }
                if (!item.externalLink && item.href == paths[paths.length - 1]) {
                    var strong = $('<strong></strong>');
                    strong.text(item.text);
                    li.append(strong);
                } else {
                    var a = $('<a></a>');
                    a.addClass(function() {
                        return item.externalLink == true ? 'externalLink' : '';
                    }).text(item.text);
                    a.attr('href', item.href);
                    li.append(a);
                }
                ui.append(li);
            });
        });
        leftColumn
                .append('<h5>Download Source</h5>')
                .append(
                        '<a href="https://github.com/oncereply/jscompressor/zipball/master"><img border="0" width="90" src="images/zip.png"></a>')
                .append(
                        '<a href="https://github.com/oncereply/jscompressor/tarball/master"><img border="0" width="90" src="images/tar.png"></a>');
        // xright
        var xright = $('#breadcrumbs .xright:eq(0)').empty();
        $(config.menus.xright).each(function(i) {
            var sub = this;
            var a = $('<a></a>').addClass(function() {
                return sub.externalLink == true ? 'externalLink' : '';
            }).attr('href', sub.href);
            a.text(sub.text);
            xright.append(function() {
                return i == 0 ? '' : '&nbsp;|&nbsp;';
            }).append(a);
        });
    };
    jc.init = function() {
        var publised_date = 'Last Updated: ' + jc.published + ' | Version: ' + jc.version;
        $('#publishDate').text(publised_date);
        $('#footer').html(jc.copyright);
        jc.initMenus();
    };
})(window);
window.onload = function() {
    jc.init();
};
