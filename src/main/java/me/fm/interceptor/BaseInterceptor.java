package me.fm.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.fm.util.SessionUtil;
import me.fm.util.WebConst;

import org.apache.log4j.Logger;
import org.unique.ioc.annotation.Component;
import org.unique.web.annotation.Intercept;
import org.unique.web.core.ActionContext;
import org.unique.web.core.ActionInvocation;
import org.unique.web.interceptor.Interceptor;

/**
 * 全局拦截器
 * @author Rex
 */
@Component
@Intercept
public class BaseInterceptor implements Interceptor{

	private Logger logger = Logger.getLogger(BaseInterceptor.class);
	
	@Override
	public void intercept(ActionInvocation ai) throws Exception {
		//System.out.println("全局before");
	    HttpServletRequest request = ActionContext.single().getHttpServletRequest();
	    HttpServletResponse response = ActionContext.single().getHttpServletResponse();
	    
	    //设置basepath
	    String basePath = request.getContextPath();
	    request.setAttribute("base", basePath);
	    request.setAttribute("static_v", "1.0");
	    request.setAttribute("cdn", request.getContextPath());
	    
	    String reqUrl = request.getRequestURI();
	    
	    if(reqUrl.contains("login")){
	    	ai.invoke();
	    	return;
	    }
	    //拦截后台登录
	    if(reqUrl.startsWith(basePath + "/admin/")){
	    	if(null == SessionUtil.getLoginUser()){
	    		try {
					response.sendRedirect(basePath + WebConst.ADMIN_LOGIN);
					return;
				} catch (IOException e) {
					logger.warn(e.getMessage());
				}
	    	}
	    	request.setAttribute("login_user", SessionUtil.getLoginUser());
	    }
	    ai.invoke();
	}
	
}
