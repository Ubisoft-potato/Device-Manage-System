package org.cust.devicemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cust.devicemanagesystem.mapper.DeviceMapper;
import org.cust.devicemanagesystem.model.Device;
import org.cust.devicemanagesystem.service.IDeviceService;
import org.cust.devicemanagesystem.vo.DeviceVo;
import org.springframework.beans.factory.annotation.Autowired;
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

    private DeviceMapper deviceMapper;

    @Override
    public IPage<DeviceVo> queryDevicePage(IPage<DeviceVo> page) {
        return deviceMapper.getDeviceVoPage(page);
    }

    @Autowired
    public DeviceServiceImpl(DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }
}
