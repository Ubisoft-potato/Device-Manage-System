package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cust.devicemanagesystem.model.ReservationDevice;
import org.cust.devicemanagesystem.service.IReservationDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    
    private  IReservationDeviceService reservationDeviceService;


    /**
    * 新增
    */
    @PostMapping("/addNewReservation")
    public boolean save(ReservationDevice reservationDevice){
        return reservationDeviceService.save(reservationDevice);
    }

    /**
    * 通过id删除
    */
    @DeleteMapping("/deleteReservation")
    public boolean delete(ReservationDevice reservationDevice){
        return reservationDeviceService.removeById(reservationDevice.getId());
    }

    /**
    * 修改
    */
    @PutMapping("/updateReservation")
    public boolean updateById(ReservationDevice reservationDevice){
        return reservationDeviceService.updateById(reservationDevice);
    }


    /**
    * 查询列表
    */
    @GetMapping("/list")
    public List<ReservationDevice> list(ReservationDevice reservationDevice ){
        QueryWrapper<ReservationDevice> wp = new QueryWrapper<>();
        //todo init wp
        return reservationDeviceService.list(wp);
    }

    /**
    * 分页查询
    */
    @GetMapping("/page")
    public IPage<ReservationDevice> page(Page<ReservationDevice> page,ReservationDevice reservationDevice){
        QueryWrapper<ReservationDevice> wp = new QueryWrapper<>();
        //todo init wp
        return reservationDeviceService.page(page,wp);
    }



    @Autowired
    public ReservationDeviceController(IReservationDeviceService reservationDeviceService) {
        this.reservationDeviceService = reservationDeviceService;
    }
}

