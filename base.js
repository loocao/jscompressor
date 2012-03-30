var config = {
	version : '2.0.3',
	published : '2012-03-30',
	copyright : '© 2012 - <a href="http://www.oncereply.me/">oncereply</a>'
};
(function(window) {
	var jc = window.jc = config;
	jc.init = function() {
		var publised_date = 'Last Updated: ' + jc.published + ' | Version: '
				+ jc.version;
		$('#publishDate').text(publised_date);
		$('#footer').html(jc.copyright);
	};
})(window);

window.onload = function() {
	jc.init();
};
