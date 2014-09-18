package org.unique.cloud.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.cloud.util.SessionUtil;
import org.unique.cloud.util.WebConst;
import org.unique.common.tools.StringUtils;
import org.unique.ioc.annotation.Component;
import org.unique.web.core.ActionContext;
import org.unique.web.core.ActionInvocation;
import org.unique.web.interceptor.GlobalInterceptor;

/**
 * 全局拦截器
 * @author Rex
 */
@Component
public class BaseInterceptor extends GlobalInterceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		//System.out.println("全局before");
	    HttpServletRequest request = ActionContext.single().getHttpServletRequest();
	    HttpServletResponse response = ActionContext.single().getHttpServletResponse();
	    
	    //设置basepath
	    String basePath = request.getContextPath();
	    request.setAttribute("base", basePath);
	    request.setAttribute("cdn", request.getContextPath());
	    //request.setAttribute("cdn", QiniuConst.DOMAIN);
	    
	    String reqUrl = request.getRequestURI();
	    
//	    if(reqUrl.contains("login")){
//	    	ai.invoke();
//	    	return;
//	    }
//	    //拦截后台登录
//	    if(reqUrl.startsWith(basePath + "/admin/")){
//	    	if(StringUtils.isBlank(SessionUtil.getAdminUser())){
//	    		try {
//					response.sendRedirect(basePath + WebConst.ADMIN_LOGIN);
//					return;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//	    	}
//	    }
	    ai.invoke();
		//System.out.println("全局after");
	}
	
}
