package org.papaorange.mymovielist.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class URLFilter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
	System.out.println("==>URLFilter启动");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException
    {
	System.out.println("==>URLFilter拦截请求");
	System.out.println(getFullURL((HttpServletRequest) request));
	System.out.println("urI:" + ((HttpServletRequest) request).getRequestURI());
	System.out.println("querystring:" + ((HttpServletRequest) request).getQueryString());
	System.out.println("urL:" + ((HttpServletRequest) request).getRequestURL());
	System.out.println("ServletPath:" + ((HttpServletRequest) request).getServletPath());

	chain.doFilter(request, response);
    }

    public static String getFullURL(HttpServletRequest request)
    {
	StringBuffer requestURL = request.getRequestURL();
	String queryString = request.getQueryString();

	if (queryString == null)
	{
	    return requestURL.toString();
	}
	else
	{
	    return requestURL.append('?').append(queryString).toString();
	}
    }

    @Override
    public void destroy()
    {

    }
}