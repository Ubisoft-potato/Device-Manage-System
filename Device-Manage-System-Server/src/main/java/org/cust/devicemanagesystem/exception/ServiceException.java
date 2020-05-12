package org.cust.devicemanagesystem.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** create by: long description: create time: 2020/1/5 下午9:46 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ServiceException extends Exception {
  private String code;
  private String message;
}
