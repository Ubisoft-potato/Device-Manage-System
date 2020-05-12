package org.cust.devicemanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cust.devicemanagesystem.model.ReservationDevice;
import org.cust.devicemanagesystem.vo.ReservationDeviceVo;

/**
 * 预约登记 服务类
 *
 * @author Long
 * @since 2020-01-08
 */
public interface IReservationDeviceService extends IService<ReservationDevice> {

  IPage<ReservationDeviceVo> queryReservationPage(
      IPage<ReservationDeviceVo> page, String state, String userId);
}
