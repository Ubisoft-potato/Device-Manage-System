package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.cust.devicemanagesystem.model.*;
import org.cust.devicemanagesystem.service.ICostSettlementService;
import org.cust.devicemanagesystem.service.IDeviceService;
import org.cust.devicemanagesystem.service.IReservationDeviceService;
import org.cust.devicemanagesystem.service.IUsersService;
import org.cust.devicemanagesystem.vo.ReservationDeviceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 预约登记 前端控制器
 * </p>
 *
 * @author Long
 * @since 2020-01-08
 */
@RestController
@RequestMapping("/reservationDevice")
public class ReservationDeviceController {


    private final IReservationDeviceService reservationDeviceService;

    private final ICostSettlementService costSettlementService;

    private final IDeviceService deviceService;

    private final IUsersService usersService;

    /**
     * 新增
     */
    @PostMapping("/addNewReservation")
    @ApiOperation("保存预约信息")
    public boolean save(@RequestBody @Validated ReservationDevice reservationDevice) {
        return reservationDeviceService.save(reservationDevice.setFailReason("")
                .setState(ReservationCodeEnum.CHECKING.toString()));
    }

    /**
     * 通过id删除
     */
    @DeleteMapping("/deleteReservation/{id}")
    @ApiOperation("删除预约信息")
    public boolean delete(@PathVariable String id) {
        return reservationDeviceService.removeById(id);
    }

    /**
     * 修改
     */
    @Transactional
    @PutMapping("/updateReservation")
    @ApiOperation("修改预约信息")
    public boolean updateById(@RequestBody ReservationDevice reservationDevice) {
        if (Objects.equals(reservationDevice.getState(), "CHECK_SUCCESS")) {
            //获取当前设备
            Device device = deviceService.getOne(Wrappers.lambdaQuery(Device.class)
                    .eq(Device::getId, reservationDevice.getDeviceId()));
            //获取当前管理员
            Users user = usersService.getOne(Wrappers.lambdaQuery(Users.class)
                    .eq(Users::getId, device.getManager()));
            //计算当前租用时间
            Duration duration = Duration.between(reservationDevice.getStartTime(), reservationDevice.getStopTime());
            costSettlementService.save(new CostSettlement().setDeviceType(device.getType())
                    .setDeviceName(device.getName())
                    .setDeviceSerialNumber(device.getSerialNumber())
                    .setState(false)
                    .setDeviceManagerName(user.getRealName())
                    .setDeviceManagerContact(user.getTelPhone())
                    .setUserId(reservationDevice.getUserId())
                    .setReservationStartTime(reservationDevice.getStartTime())
                    .setReservationStopTime(reservationDevice.getStopTime())
                    .setCostSum(duration.toDays() * device.getPrice()));
        }
        return reservationDeviceService.updateById(reservationDevice);
    }


    /**
     * 查询列表
     */
    @GetMapping("/list")
    @ApiOperation("查询预约信息列表")
    public List<ReservationDevice> list(ReservationDevice reservationDevice) {
        QueryWrapper<ReservationDevice> wp = new QueryWrapper<>();
        //todo init wp
        return reservationDeviceService.list(wp);
    }

    /**
     * 分页查询
     */
    @PostMapping("/page/{state}/{userId}")
    @ApiOperation("分页查询预约信息")
    public IPage<ReservationDeviceVo> page(@RequestBody @NotNull Page<ReservationDeviceVo> page, @PathVariable String state, @PathVariable String userId) {
        return reservationDeviceService.queryReservationPage(page, state, userId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/page")
    @ApiOperation("分页查询预约信息")
    public IPage<ReservationDeviceVo> adminPage(@RequestBody @NotNull Page<ReservationDeviceVo> page) {
        return reservationDeviceService.queryReservationPage(page, null, null);
    }

    @Autowired
    public ReservationDeviceController(IReservationDeviceService reservationDeviceService,
                                       ICostSettlementService costSettlementService,
                                       IDeviceService deviceService,
                                       IUsersService usersService) {
        this.reservationDeviceService = reservationDeviceService;
        this.costSettlementService = costSettlementService;
        this.deviceService = deviceService;
        this.usersService = usersService;
    }
}

