package com.cuning.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cuning.annotation.CheckToken;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月11日 15:06:00
 */
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        log.info("token：" + token);
        response.setContentType("text/html;charset=utf-8");
        if(this.checkTargetMethodHasCheckToken(handler)){
            if (token == null) {
                log.error("token为空");
                RequestResult<String> message = ResultBuildUtil.fail("token为空");
                response.getWriter().write(JSON.toJSONString(message));
                return false;
            }
            try {
                JwtUtil.verifyToken(token);
            } catch (SignatureVerificationException e) {
                log.error("无效签名");
                RequestResult<String> message = ResultBuildUtil.fail("无效签名");
                response.getWriter().write(JSON.toJSONString(message));
                return false;
            } catch (TokenExpiredException e) {
                log.error("token过期");
                RequestResult<String> message = ResultBuildUtil.fail("token过期");
                response.getWriter().write(JSON.toJSONString(message));
                return false;
            } catch (AlgorithmMismatchException e) {
                log.error("token算法不一致");
                RequestResult<String> message = ResultBuildUtil.fail("token算法不一致");
                response.getWriter().write(JSON.toJSONString(message));
                return false;
            } catch (Exception e) {
                log.error("token无效");
                RequestResult<String> message = ResultBuildUtil.fail("token无效");
                response.getWriter().write(JSON.toJSONString(message));
                return false;
            }
        }

       // 放行
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * @description 判断目标请求处理方法上，是否增加请求许可注解@RequestPermission,如果加了，返回true
     * @author  tengjiaozhai
     * @updateTime 2022/6/1 10:46 No such property: code for class: Script1
     */
    private boolean checkTargetMethodHasCheckToken(Object handler){
        // 判断当前handler对象是否已经映射到了目标请求处理方法(是否是HandlerMethod实例对象)
        if (handler instanceof HandlerMethod){
            // 强转为目标请求处理方法对象（HandlerMethod）
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 获取目标处理方法上的指定注解RequestPermission
            CheckToken checkToken = handlerMethod.getMethod().getAnnotation(CheckToken.class);

            // 判断目标方法上获取的注解是否存在，如果不存在，不代表不需要校验，可能次注解添加在类上
            if (null == checkToken){
                checkToken = handlerMethod.getMethod().getDeclaringClass().getAnnotation(CheckToken.class);

            }
            // 如果获取到，说明需要进行请求许可鉴权
            return null != checkToken;

        }
        // 不需要请求许可鉴权,直接返回false
        return  false;
    }

    /**
     * @description token鉴权失败,统一返回结果
     * @author  tengjiaozhai
     * @updateTime 2022/6/1 10:59 No such property: code for class: Script1
     */
    private void returnCheckJson(HttpServletResponse response,String returnCode,String returnMessage){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().print(JSON.toJSONString(ResultBuildUtil.fail(returnCode,returnMessage)));
        } catch (IOException e) {
            log.error("响应结果异常{}",e.getMessage());
            e.printStackTrace();
        }
    }
}
