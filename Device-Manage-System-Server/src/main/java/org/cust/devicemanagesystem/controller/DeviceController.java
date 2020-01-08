package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cust.devicemanagesystem.model.Device;
import org.cust.devicemanagesystem.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


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


    /**
     * 新增
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/addNewDevice")
    public boolean save(@Validated @RequestBody Device device) {
        return deviceService.save(device);
    }

    /**
     * 通过id删除
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/deleteDevice")
    public boolean delete(@NotBlank String id) {
        return deviceService.removeById(id);
    }

    /**
     * 修改
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PutMapping("updateDevice")
    public boolean updateById(@Validated @RequestBody Device device) {
        return deviceService.updateById(device);
    }


    /**
     * 查询列表
     */
    @GetMapping("/list")
    public List<Device> list(Device device) {
        QueryWrapper<Device> wp = new QueryWrapper<>();
        //todo init wp
        return deviceService.list(wp);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public IPage<Device> page(Page<Device> page, Device device) {
        QueryWrapper<Device> wp = new QueryWrapper<>();
        //todo init wp
        return deviceService.page(page, wp);
    }


    @Autowired
    public DeviceController(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }
}

