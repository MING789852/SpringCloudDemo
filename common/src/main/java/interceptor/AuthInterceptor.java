package interceptor;

import cn.hutool.core.util.StrUtil;
import enums.FeignHeaderKey;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import util.AuthContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth=request.getHeader(FeignHeaderKey.AUTH.toString());
        if (StrUtil.isNotBlank(auth)){
            // 存储授权
            AuthContext.setAuth(auth);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 移除授权
        AuthContext.remove();
    }
}
