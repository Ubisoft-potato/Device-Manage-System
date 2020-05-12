package org.cust.devicemanagesystem.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 费用结算
 *
 * @author Long
 * @since 2020-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cost_settlement")
public class CostSettlement {

  /** 主键 */
  private Long id;
  /** 结算用户 */
  private Long userId;
  /** 设备名 */
  private String deviceName;
  /** 设备序列号 */
  private String deviceSerialNumber;
  /** 设备类型 */
  private String deviceType;
  /** 设备管理员 */
  private String deviceManagerName;
  /** 设备管理员联系电话 */
  private String deviceManagerContact;
  /** 预约使用开始时间 */
  private LocalDateTime reservationStartTime;
  /** 预约使用到期时间 */
  private LocalDateTime reservationStopTime;
  /** 结算状态 */
  @NotNull private Boolean state;
  /** 总费用 */
  private Long costSum;
}
