package org.cust.devicemanagesystem.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/** @ClassName PasswordReset @Description @Author long @Date 2020/1/19 11:52 @Version 1.0 */
@Data
@EqualsAndHashCode
public class PasswordReset {
  @NotBlank private String oldPassword;
  @NotBlank private String newPassword;
}
