package org.cust.devicemanagesystem.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.cust.devicemanagesystem.exception.ResponseMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private ObjectMapper objectMapper = new ObjectMapper();

  private ResponseMessage message = new ResponseMessage().setMessage("登录成功").setHttpCode("200");

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter writer = response.getWriter();
    writer.write(objectMapper.writeValueAsString(message));
    writer.flush();
    writer.close();
  }
}
