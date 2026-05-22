package es.storeapp.web.interceptors;

import es.storeapp.common.Constants;
import es.storeapp.web.session.ShoppingCart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ShoppingCartInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        HttpSession session = request.getSession(true);

        if(session.getAttribute(Constants.SHOPPING_CART_SESSION) == null) {
            session.setAttribute(Constants.SHOPPING_CART_SESSION, new ShoppingCart());
        }
        
        return true;
    }
}
