package me.fm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.fm.cloud.model.User;
import me.fm.util.SessionUtil;
import me.fm.util.WebConst;

import org.unique.ioc.annotation.Component;
import org.unique.web.core.Controller;

/**
 * 控制器基类
 * @author:rex
 * @date:2014年9月9日
 * @version:1.0
 */
@Component
public class BaseController extends Controller {

	protected Integer uid;
	
	protected Integer page;

	protected Integer pageSize;

	@Override
	public void init(HttpServletRequest request, HttpServletResponse response, String[] urlPara) {
		
		super.init(request, response, urlPara);
		
		this.page = this.getParaToInt("page", 1);
		
		this.pageSize = this.getParaToInt("pageSize", WebConst.PAGE_SIZE);
		
		User user = SessionUtil.getLoginUser();
		if(null != user){
			uid = user.getUid();
		}
	}

}
