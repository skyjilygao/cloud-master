/*
package cn.skyjilygao.cloud.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
	*/
/**
	 * 拦截器
	 *//*

	@Autowired
	private WebInterceptor webInterceptor;

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(webInterceptor).addPathPatterns("/**");
	}

	*/
/**
	 * 修改springboot中默认的静态文件路径，只要配置就会覆盖application中“spring:resources: static-locations”配置
	 *//*

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//addResourceHandler请求路径
		//addResourceLocations 在项目中的资源路径
		//setCacheControl 设置静态资源缓存时间
//		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
		*/
/*.setCacheControl(CacheControl.maxAge(2, TimeUnit.SECONDS).cachePublic())*//*
;
	}
}
*/
