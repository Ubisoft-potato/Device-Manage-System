package org.cust.devicemanagesystem.config.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** create by: long description: create time: 2020/1/5 下午9:44 */
@Slf4j
public class CorsFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String origin = request.getHeader("Origin");
    log.debug("Origin is : {}", origin);
    response.setHeader("Access-Control-Allow-Origin", origin);
    response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader(
        "Access-Control-Allow-Headers",
        "token,Origin, X-Requested-With, Content-Type, Accept,mid,X-Token,Access-Control-Allow-Headers");
    filterChain.doFilter(request, response);
  }
}
