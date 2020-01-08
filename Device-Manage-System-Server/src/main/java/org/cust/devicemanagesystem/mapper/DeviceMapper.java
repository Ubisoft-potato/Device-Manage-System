package org.cust.devicemanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cust.devicemanagesystem.model.Device;

/**
 * <p>
 * 设备信息 Mapper 接口
 * </p>
 *
 * @author Long
 * @since 2020-01-08
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

}
