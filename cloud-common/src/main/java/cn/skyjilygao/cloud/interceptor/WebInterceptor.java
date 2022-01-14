/*
package cn.skyjilygao.cloud.interceptor;

import com.powerwin.adorado.common.request.RequestUtil;
import com.powerwin.adorado.common.request.ServletRequestPojo;
import com.powerwin.adorado.http.exception.APIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

*/
/**
 * 获取请求信息
 * @author skyjilygao
 * @since 1.0
 * @date 20200305
 *//*

@Slf4j
@Component
public class WebInterceptor implements HandlerInterceptor {

	private static Set<String> restrictUrl = new HashSet<>();

	static {
		restrictUrl.add("/recharge");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		ServletRequestPojo pojo = new ServletRequestPojo(request);
		log.info("request info=" + pojo.toString());
		String ip = RequestUtil.getIPAddress(request);
		log.info("Request from ip=" + ip);
		if (restrictUrl.contains(pojo.getRequestUri())){
			if(!RequestUtil.isAllow(ip)){
				log.info("error_code: 2550, ip=" + ip + ",不再白名单，无法访问");
				throw new APIException("2550");
			}
		}
		return true;
	}
}
*/
