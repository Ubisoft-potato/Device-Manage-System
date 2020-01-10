package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.cust.devicemanagesystem.exception.ServiceException;
import org.cust.devicemanagesystem.mapstruct.DeviceConverter;
import org.cust.devicemanagesystem.model.Device;
import org.cust.devicemanagesystem.service.IDeviceService;
import org.cust.devicemanagesystem.vo.DeviceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 设备信息 前端控制器
 * </p>
 *
 * @author Long
 * @since 2020-01-08
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    private IDeviceService deviceService;

    private DeviceConverter deviceConverter;


    @GetMapping("/getDevice/{id}")
    @ApiOperation("根据id查询设备信息")
    public DeviceVo getDeviceById(@PathVariable @NotBlank String id) {
        return deviceService.getDeviceInfoById(id);
    }

    /**
     * 新增
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/addNewDevice")
    @ApiOperation("添加设备")
    public boolean save(@Validated @RequestBody Device device) throws ServiceException {
        if (Objects.nonNull(deviceService.getOne(Wrappers.lambdaQuery(new Device()).eq(Device::getSerialNumber, device.getSerialNumber())))) {
            throw new ServiceException().setMessage("序列号已存在，请重新输入").setCode("400");
        }
        return deviceService.save(device);
    }

    /**
     * 通过id删除
     */
    @ApiOperation("删除设备")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/deleteDevice/{id}")
    public boolean delete(@NotBlank @PathVariable String id) {
        return deviceService.removeById(id);
    }

    /**
     * 修改
     */
    @ApiOperation("更新设备信息")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("updateDevice")
    public boolean updateById(@Validated @RequestBody Device device) {
        return deviceService.updateById(device);
    }


    /**
     * 查询列表
     */
    @GetMapping("/list")
    @ApiOperation("查询设备列表")
    public List<Device> list(Device device) {
        QueryWrapper<Device> wp = new QueryWrapper<>();
        //todo init wp
        return deviceService.list(wp);
    }

    /**
     * 分页查询
     */
    @ApiOperation("分页查询设备")
    @PostMapping("/page")
    public IPage<DeviceVo> page(@NotNull @RequestBody Page<DeviceVo> page) {
        return deviceService.queryDevicePage(page);
    }


    @Autowired
    public DeviceController(IDeviceService deviceService,
                            DeviceConverter deviceConverter) {
        this.deviceService = deviceService;
        this.deviceConverter = deviceConverter;
    }
}

