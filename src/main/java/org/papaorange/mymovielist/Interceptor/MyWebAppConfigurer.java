package org.papaorange.mymovielist.Interceptor;

import java.util.ArrayList;
import java.util.List;

import org.papaorange.mymovielist.filter.URLFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter
{

    @Bean
    public FilterRegistrationBean getDemoFilter()
    {
	URLFilter demoFilter = new URLFilter();
	FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	registrationBean.setFilter(demoFilter);
	List<String> urlPatterns = new ArrayList<String>();
	urlPatterns.add("/*");// 拦截路径，可以添加多个
	registrationBean.setUrlPatterns(urlPatterns);
	registrationBean.setOrder(1);
	return registrationBean;
    }

//    @Bean
//    public HandlerInterceptor getMyInterceptor()
//    {
//	return new URLInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry)
//    {
//	// 多个拦截器组成一个拦截器链
//	// addPathPatterns 用于添加拦截规则
//	// excludePathPatterns 用户排除拦截
//	registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
//	super.addInterceptors(registry);
//    }
}
