package org.cust.devicemanagesystem.config.handler;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationMessage {

    private String message;

    private String httpCode;
}
