package org.cust.devicemanagesystem.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 预约登记
 * </p>
 *
 * @author Long
 * @since 2020-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReservationDeviceVo {

    /**
     * 主键
     */
    private String id;
    /**
     * 设备
     */
    private DeviceVo device;
    /**
     * 用户id
     */
    private UserInfo user;
    /**
     * 预约状态
     */
    private String state;
    /**
     * 预约原因
     */
    private String reservationReason;
    /**
     * 使用开始时间
     */
    private LocalDateTime startTime;
    /**
     * 使用到期时间
     */
    private LocalDateTime stopTime;
    /**
     * 审核失败原因
     */
    private String failReason;
    /**
     * 预约创建时间
     */
    private LocalDateTime createTime;

}
