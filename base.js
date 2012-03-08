var config = {
	version : '2.0.1',
	published : '2012-03-09',
	copyright : '© 2012 - oncereply'
};
(function(window) {
	var jc = window.jc = config;
	jc.init = function() {
		var publised_date = 'Last Updated: ' + jc.published + ' | Version: '
				+ jc.version;
		$('#publishDate').text(publised_date);
		$('#footer').text(jc.copyright);
	};
})(window);

window.onload = function() {
	jc.init();
};
