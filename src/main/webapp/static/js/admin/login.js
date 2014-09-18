(function(){
	$('#login_form').validator({
	    fields: {
	        'login_name': '用户名:required; username;',
	        'pass_word': '密码:required; password;'
	    }
	}).on("click", "btn.btn-primary", function(e){
	    $(e.delegateTarget).trigger("validate");
	}).bind('valid.form', function(){
		$.ajax({
	        url: $unique.base + '/admin/login',
	        type: 'POST',
	        cache : false,
	        dataType: 'text',
	        data: $(this).serialize(),
	        success: function(result){
	        	if(result){
	        		if(result === 'success'){
	        			window.location.href = $unique.base + '/admin/index';
	        		} else{
	        			$unique.alert('用户名或密码不正确！');
	        		}
	        	}
	        }
	    });
	});
})(window);