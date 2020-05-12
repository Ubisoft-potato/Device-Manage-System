package org.cust.devicemanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cust.devicemanagesystem.model.Device;
import org.cust.devicemanagesystem.vo.DeviceVo;

/**
 * 设备信息 服务类
 *
 * @author Long
 * @since 2020-01-08
 */
public interface IDeviceService extends IService<Device> {

  IPage<DeviceVo> queryDevicePage(IPage<DeviceVo> page, String deviceName, String deviceManager);

  DeviceVo getDeviceInfoById(String id);
}
