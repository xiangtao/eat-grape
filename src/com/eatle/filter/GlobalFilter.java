/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
 *
 * @Company:sxit_chongqing
 */
package com.eatle.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eatle.db.DBManager;

/**
 * 全局过滤器
 * 
 * @author xt
 * @date 2010-1-13 下午08:36:18
 */
public class GlobalFilter implements Filter {

     private ServletContext context;

     /**
      * 过滤器初始化
      */
     public void init(FilterConfig cfg) throws ServletException {
          this.context = cfg.getServletContext();
     }

     /**
      * 执行过滤操作
      */
     public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
          HttpServletRequest request = (HttpServletRequest) req;
          HttpServletResponse response = (HttpServletResponse) res;

          try {
               chain.doFilter(request, response);
          } finally {
               DBManager.closeConnection();
          }
     }

     /**
      * 过滤器释放资源
      */
     public void destroy() {
          
     }

}
