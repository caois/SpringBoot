package cn.cao.travel.interceptors;

import cn.cao.travel.utils.JWTUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cao
 * @create 2020/9/10 - 20:51
 */
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,Object> map = new HashMap<>();
        //一般默认token放在 请求头中
        String token = request.getHeader("token");

        //认证 token
        try {
            JWTUtils.verifyToken(token);
            //认证通过
            return true;
        } catch (SignatureVerificationException e){
            map.put("msg","签名错误");
            //e.getMessage();//签名不一致
        }catch (TokenExpiredException e){
            map.put("msg","令牌过期");//令牌过期异常
        }catch (AlgorithmMismatchException e){
            map.put("msg","算法不匹配");//算法不匹配异常
        }catch (Exception e){
            e.getMessage();
            map.put("msg","token无效");//token已失效
        }

        //将 map 转为 json
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);
        return false;
    }
}
