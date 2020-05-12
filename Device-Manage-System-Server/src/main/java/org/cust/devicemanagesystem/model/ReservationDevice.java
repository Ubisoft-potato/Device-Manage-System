package org.cust.devicemanagesystem.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 预约登记
 *
 * @author Long
 * @since 2020-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("reservation_device")
public class ReservationDevice {

  /** 主键 */
  @TableId(type = IdType.ASSIGN_ID)
  private Long id;
  /** 设备id */
  @NotNull private Long deviceId;
  /** 用户id */
  @NotNull private Long userId;
  /** 预约状态 */
  private String state;
  /** 预约原因 */
  @NotBlank private String reservationReason;
  /** 使用开始时间 */
  @NotNull private LocalDateTime startTime;
  /** 使用到期时间 */
  @NotNull private LocalDateTime stopTime;
  /** 审核失败原因 */
  private String failReason;
  /** 预约创建时间 */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
}
