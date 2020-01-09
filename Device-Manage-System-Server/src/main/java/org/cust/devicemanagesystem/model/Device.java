package org.cust.devicemanagesystem.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 设备信息
 * </p>
 *
 * @author Long
 * @since 2020-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("device")
public class Device {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
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
    private Long manager;
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
