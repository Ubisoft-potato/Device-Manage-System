package org.cust.devicemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cust.devicemanagesystem.mapper.ReservationDeviceMapper;
import org.cust.devicemanagesystem.model.ReservationDevice;
import org.cust.devicemanagesystem.service.IReservationDeviceService;
import org.cust.devicemanagesystem.vo.ReservationDeviceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预约登记 服务实现类
 * </p>
 *
 * @author Long
 * @since 2020-01-08
 */
@Service
public class ReservationDeviceServiceImpl extends ServiceImpl<ReservationDeviceMapper, ReservationDevice> implements IReservationDeviceService {

    private final ReservationDeviceMapper reservationDeviceMapper;


    @Override
    public IPage<ReservationDeviceVo> queryReservationPage(IPage<ReservationDeviceVo> page, String state, String userId) {
        return reservationDeviceMapper.queryReservationPage(page, state, userId);
    }

    @Autowired
    public ReservationDeviceServiceImpl(ReservationDeviceMapper reservationDeviceMapper) {
        this.reservationDeviceMapper = reservationDeviceMapper;
    }
}
