package org.cust.devicemanagesystem.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResponseMessage {
    private String httpCode;
    private String message;
}
