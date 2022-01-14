/*
package cn.skyjilygao.cloud.interceptor;

import cn.skyjilygao.cloud.enums.ErrorCode;
import cn.skyjilygao.cloud.exception.PwExceptionBase;
import com.powerwin.adorado.common.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * 默认异常处理信息
 *
 * @author skyjilygao
 * @date 20200305
 * @since 1.0
 *//*

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	*/
/**
	 * 默认异常处理页面
	 *//*

	public static final String DEFAUL_ERROR_VIEW = "error";

	*/
/**
	 * 默认异常处理方法,返回异常请求路径和异常信息
	 *//*

	@ResponseBody
	@ExceptionHandler(value = {Throwable.class})
	public ReturnT defaulErrorHandler(HttpServletRequest request, HttpServletResponse response, Throwable e) throws Throwable {
		String uri = request.getRequestURI();
		String queryStr = request.getQueryString();
		if (StringUtils.isNotBlank(queryStr)) {
			uri += "?" + queryStr;
		}
		log.error("defaulErrorHandler catch exception, request uri=" + uri, e);
		String msg = e.getMessage();
		if (msg.contains("parameter 'p' is not present") || msg.contains("parameter 't' is not present")) {
			return new ReturnT(ErrorCode.TAG_P_T_ERROR.getCode(), ErrorCode.TAG_P_T_ERROR.getMsg());
		}
		ReturnT r = new ReturnT(ReturnT.FAIL_CODE, "服务器内部异常");
		return r;
	}




	*/
/**
	 * 自定义异常处理方法,返回异常请求路径和异常信息
	 *
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @param e        {@link PwExceptionBase} 自定义异常基础类
	 * @return {code: 状态吗, msg: 状态信息, content: 请求的url}
	 * @throws PwExceptionBase
	 *//*

	@ResponseBody
	@ExceptionHandler(value = {PwExceptionBase.class})
	public ReturnT<String> handlerCustomerException(HttpServletRequest request, HttpServletResponse response, PwExceptionBase e) {
		String uri = getUri(request);
		log.error("handler Customer Exception: request uri={}. with error message={}", uri, e.getMessage(), e);
		return new ReturnT<>(e.getHttpCode(), e.getHttpMessage(), uri);
	}
	*/
/**
	 * 只针对get请求或post请求url携带参数。至于post时的post-data中参数暂时不支持
	 *
	 * @param request
	 * @return
	 *//*

	private String getUri(HttpServletRequest request) {
		String uri = request.getRequestURI();
		// 请求参数
		String queryStr = request.getQueryString();
		if (!ObjectUtils.isEmpty(queryStr)) {
			uri += "?" + queryStr;
		}
		return uri;
	}
}
*/
