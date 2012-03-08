(function(window) {
	var jc = window.jc = {
		version : '1.0.0',
		published : '2012-03-05'	
	};
	jc.init = function() {
		var publised_date = 'Last Published: ' + jc.published + ' | Version: '
				+ jc.version;
		$('#publishDate').text(publised_date);
	};
})(window);

window.onload = function() {
	jc.init();
	
	var _gaq = _gaq || [];
	_gaq.push(['_setAccount', 'UA-29822122-1']);
	_gaq.push(['_setDomainName', 'oncereply.me']);
	_gaq.push(['_trackPageview']);

	(function() {
	var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	})();
};
