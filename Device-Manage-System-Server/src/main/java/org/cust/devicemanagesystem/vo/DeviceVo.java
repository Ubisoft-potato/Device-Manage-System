package org.cust.devicemanagesystem.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class DeviceVo {
    /**
     * 主键
     */
    private String id;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备类型
     */
    private String type;
    /**
     * 设备序列号
     */
    private String serialNumber;
    /**
     * 设备负责人
     */
    private UserInfo manager;
    /**
     * 租用单价
     */
    private Integer price;
    /**
     * 设备描述
     */
    private String description;
    /**
     * 当前是否可用
     */
    private Boolean availableState;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
