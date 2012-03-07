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
};
