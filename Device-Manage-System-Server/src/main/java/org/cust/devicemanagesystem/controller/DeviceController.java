package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.cust.devicemanagesystem.exception.ServiceException;
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
import java.util.stream.Collectors;


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

    private final IDeviceService deviceService;

    private static final String SEARCH_STRING = "%";

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
        if (Objects.nonNull(deviceService.getOne(Wrappers.lambdaQuery(Device.class).eq(Device::getSerialNumber, device.getSerialNumber())))) {
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

    @GetMapping("/deviceNameList/{searchName}")
    @ApiOperation("查询设备名称列表")
    public List<String> deviceNameList(@PathVariable String searchName) {
        if (Objects.equals(searchName, "all")) {
            return deviceService.list()
                    .stream()
                    .map(Device::getName)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return deviceService.list(Wrappers.lambdaQuery(new Device())
                .like(Device::getName, searchName))
                .stream()
                .map(Device::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 分页查询
     */
    @ApiOperation("分页查询设备")
    @PostMapping("/page")
    public IPage<DeviceVo> page(@NotNull @RequestBody Page<DeviceVo> page) {
        return deviceService.queryDevicePage(page, null, null);
    }


    @PostMapping("/search/{searchOption}/{searchValue}")
    public IPage<DeviceVo> searchDevices(@NotNull @RequestBody Page<DeviceVo> page,
                                         @PathVariable String searchOption,
                                         @PathVariable String searchValue) throws ServiceException {
        // 模糊查询
        searchValue = SEARCH_STRING.concat(searchValue).concat(SEARCH_STRING);

        if (Objects.equals(searchOption, "deviceName")) {
            return deviceService.queryDevicePage(page, searchValue, null);
        } else if (Objects.equals(searchOption, "deviceManager")) {
            return deviceService.queryDevicePage(page, null, searchValue);
        }
        throw new ServiceException().setCode("403").setMessage("不支持的搜索选项");
    }


    @Autowired
    public DeviceController(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }
}

