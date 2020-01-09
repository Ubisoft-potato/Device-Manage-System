package org.cust.devicemanagesystem.mapstruct;


import org.cust.devicemanagesystem.model.Device;
import org.cust.devicemanagesystem.vo.DeviceVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = String.class)
public interface DeviceConverter {

    @Mapping(target = "id", expression = "java(String.valueOf(device.getId()))")
    DeviceVo toDeviceVo(Device device);

}
