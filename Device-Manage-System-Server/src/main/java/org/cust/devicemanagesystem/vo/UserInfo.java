package org.cust.devicemanagesystem.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class UserInfo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 账号
     */
    @NotBlank
    private String username;
    /**
     * 密码
     */
    @NotBlank
    private String password;
    /**
     * 学号或工号
     */
    @NotBlank
    private String workId;
    /**
     * 真实人名
     */
    @NotBlank
    private String realName;
    /**
     * 电话
     */
    @NotBlank
    private String telPhone;
    /**
     * 所属学院
     */
    @NotBlank
    private String institute;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 权限名称
     */
    @NotEmpty
    private List<String> authorities;
}
