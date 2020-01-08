package org.cust.devicemanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cust.devicemanagesystem.mapper.DeviceMapper;
import org.cust.devicemanagesystem.model.Device;
import org.cust.devicemanagesystem.service.IDeviceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备信息 服务实现类
 * </p>
 *
 * @author Long
 * @since 2020-01-08
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

}
