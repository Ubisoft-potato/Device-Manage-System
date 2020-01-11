package org.cust.devicemanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.cust.devicemanagesystem.config.handler.StringIdHandler;
import org.cust.devicemanagesystem.model.ReservationDevice;
import org.cust.devicemanagesystem.vo.ReservationDeviceVo;

/**
 * <p>
 * 预约登记 Mapper 接口
 * </p>
 *
 * @author Long
 * @since 2020-01-08
 */
@Mapper
public interface ReservationDeviceMapper extends BaseMapper<ReservationDevice> {


    @Results({
            @Result(property = "id", column = "rdid", typeHandler = StringIdHandler.class),
            @Result(property = "state", column = "rdstate"),
            @Result(property = "reservationReason", column = "rdreservation_reason"),
            @Result(property = "reservationReason", column = "rdreservation_reason"),
            @Result(property = "startTime", column = "rdstart_time"),
            @Result(property = "stopTime", column = "rdstop_time"),
            @Result(property = "failReason", column = "rdfail_reason"),
            @Result(property = "createTime", column = "rdcreate_time"),
            @Result(property = "device.id", column = "did", typeHandler = StringIdHandler.class),
            @Result(property = "device.name", column = "dname"),
            @Result(property = "device.type", column = "dtype"),
            @Result(property = "device.serialNumber", column = "dserial_number"),
            @Result(property = "device.manager.id", column = "suid",typeHandler = StringIdHandler.class),
            @Result(property = "device.manager.realName", column = "sureal_name"),
            @Result(property = "device.manager.telPhone", column = "sutel_phone"),
            @Result(property = "device.manager.institute", column = "suinstitute"),
            @Result(property = "device.price", column = "dprice"),
            @Result(property = "device.description", column = "dprice"),
            @Result(property = "device.availableState", column = "davailable_state"),
            @Result(property = "user.id", column = "uid", typeHandler = StringIdHandler.class),
            @Result(property = "user.institute", column = "uinstitute"),
            @Result(property = "user.telPhone", column = "utel_phone"),
            @Result(property = "user.realName", column = "ureal_name"),
    })
    @Select("SELECT \n" +
            "  temp.*,\n" +
            "  su.id AS suid,\n" +
            "  su.real_name AS sureal_name,\n" +
            "  su.institute AS suinstitute,\n" +
            "  su.tel_phone AS sutel_phone \n" +
            "FROM\n" +
            "  (SELECT \n" +
            "    rd.id AS rdid,\n" +
            "    rd.state AS rdstate,\n" +
            "    rd.reservation_reason AS rdreservation_reason,\n" +
            "    rd.start_time AS rdstart_time,\n" +
            "    rd.stop_time AS rdstop_time,\n" +
            "    rd.fail_reason AS rdfail_reason,\n" +
            "    rd.create_time AS rdcreate_time,\n" +
            "    d.id AS did,\n" +
            "    d.name AS dname,\n" +
            "    d.type AS dtype,\n" +
            "    d.serial_number AS dserial_number,\n" +
            "    d.manager AS dmanager,\n" +
            "    d.price AS dprice,\n" +
            "    d.description AS ddescription,\n" +
            "    d.available_state AS davailable_state,\n" +
            "    d.create_time AS dcreate_time,\n" +
            "    u.id AS uid,\n" +
            "    u.real_name AS ureal_name,\n" +
            "    u.tel_phone AS utel_phone,\n" +
            "    u.institute AS uinstitute \n" +
            "  FROM\n" +
            "    reservation_device rd \n" +
            "    JOIN device d \n" +
            "      ON rd.device_id = d.id \n" +
            "    JOIN users u \n" +
            "      ON rd.user_id = u.id) temp \n" +
            "  JOIN users su \n" +
            "    ON temp.dmanager = su.id ")
    IPage<ReservationDeviceVo> queryReservationPage(IPage<ReservationDeviceVo> page);

}
