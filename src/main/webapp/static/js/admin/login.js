(function(){
	$('#login_form').validator({
	    fields: {
	        'login_name': '用户名:required;',
	        'pass_word': '密码:required;'
	    }
	}).on("click", "btn.btn-primary", function(e){
	    $(e.delegateTarget).trigger("validate");
	}).bind('valid.form', function(form){
		var url = $unique.base + '/admin/login';
		var param = $(this).serializeArray();
		$.post(url, param, function(data) {
			if(data){
        		if(data === 'success'){
        			window.location.href = $unique.base + '/admin/index';
        		} else{
        			$unique.alert('用户名或密码不正确！');
        		}
        	}
		},'text');
	});
})(window);