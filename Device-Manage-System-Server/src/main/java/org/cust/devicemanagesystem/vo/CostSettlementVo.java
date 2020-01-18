package org.cust.devicemanagesystem.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @ClassName CostSettlementVo
 * @Description
 * @Author long
 * @Date 2020/1/18 21:25
 * @Version 1.0
 **/

@Data
@EqualsAndHashCode
public class CostSettlementVo {
    /**
     * 主键
     */
    private String id;
    /**
     * 结算用户
     */
    private String userId;
    /**
     * 设备名
     */
    private String deviceName;
    /**
     * 设备序列号
     */
    private String deviceSerialNumber;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备管理员
     */
    private String deviceManagerName;
    /**
     * 设备管理员联系电话
     */
    private String deviceManagerContact;
    /**
     * 预约使用开始时间
     */
    private LocalDateTime reservationStartTime;
    /**
     * 预约使用到期时间
     */
    private LocalDateTime reservationStopTime;
    /**
     * 结算状态
     */
    private Boolean state;
    /**
     * 总费用
     */
    private String costSum;
}
