package es.storeapp.web.interceptors;

import es.storeapp.common.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticatedUserInterceptor extends HandlerInterceptorAdapter {
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                             HttpServletResponse response, 
                             Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        if(session.getAttribute(Constants.USER_SESSION) == null) {
            response.sendRedirect(request.getContextPath() + Constants.LOGIN_ENDPOINT +
                    Constants.PARAMS + Constants.NEXT_PAGE + Constants.PARAM_VALUE + request.getRequestURL());
            return false;
        }
        return true;
    }
}
