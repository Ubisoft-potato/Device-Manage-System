package org.cust.devicemanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.*;
import org.cust.devicemanagesystem.config.handler.StringIdHandler;
import org.cust.devicemanagesystem.model.Device;
import org.cust.devicemanagesystem.vo.DeviceVo;

/**
 * 设备信息 Mapper 接口
 *
 * @author Long
 * @since 2020-01-08
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

  @Select(
      "<script>"
          + "SELECT \n"
          + "  d.id AS did,\n"
          + "  d.name AS dname,\n"
          + "  d.type AS dtype,\n"
          + "  d.serial_number AS dserial_number,\n"
          + "  d.price AS dprice,\n"
          + "  d.description AS ddescription,\n"
          + "  d.available_state AS davailable_state,\n"
          + "  d.create_time AS dcreate_time,\n"
          + "  u.id AS uid,\n"
          + "  u.username AS uusername,\n"
          + "  u.work_id AS uwork_id,\n"
          + "  u.real_name AS ureal_name,\n"
          + "  u.tel_phone AS utel_phone,\n"
          + "  u.institute AS uinstitute,\n"
          + "  u.create_time AS ucreate_time \n"
          + "FROM\n"
          + "  device d \n"
          + "  JOIN users u \n"
          + "    ON u.id = d.manager "
          + " <if test='deviceName != null'>"
          + " where  d.name  like  #{deviceName}"
          + "</if>"
          + " <if test='deviceManager != null'>"
          + " where  u.real_name  like  #{deviceManager}"
          + "</if>"
          + "</script>")
  @Results({
    @Result(property = "id", column = "did", typeHandler = StringIdHandler.class),
    @Result(property = "name", column = "dname"),
    @Result(property = "type", column = "dtype"),
    @Result(property = "serialNumber", column = "dserial_number"),
    @Result(property = "manager.id", column = "uid", typeHandler = StringIdHandler.class),
    @Result(property = "manager.username", column = "uusername"),
    @Result(property = "manager.workId", column = "uwork_id"),
    @Result(property = "manager.realName", column = "ureal_name"),
    @Result(property = "manager.telPhone", column = "utel_phone"),
    @Result(property = "manager.institute", column = "uinstitute"),
    @Result(property = "manager.createTime", column = "ucreate_time"),
    @Result(property = "price", column = "dprice"),
    @Result(property = "description", column = "ddescription"),
    @Result(property = "availableState", column = "davailable_state"),
    @Result(property = "createTime", column = "dcreate_time"),
  })
  IPage<DeviceVo> getDeviceVoPage(
      IPage<DeviceVo> page,
      @Param("deviceName") String deviceName,
      @Param("deviceManager") String deviceManager);

  @Select(
      "SELECT \n"
          + "  d.id AS did,\n"
          + "  d.name AS dname,\n"
          + "  d.type AS dtype,\n"
          + "  d.serial_number AS dserial_number,\n"
          + "  d.price AS dprice,\n"
          + "  d.description AS ddescription,\n"
          + "  d.available_state AS davailable_state,\n"
          + "  d.create_time AS dcreate_time,\n"
          + "  u.id AS uid,\n"
          + "  u.username AS uusername,\n"
          + "  u.work_id AS uwork_id,\n"
          + "  u.real_name AS ureal_name,\n"
          + "  u.tel_phone AS utel_phone,\n"
          + "  u.institute AS uinstitute,\n"
          + "  u.create_time AS ucreate_time \n"
          + "FROM\n"
          + "  device d \n"
          + "  JOIN users u \n"
          + "    ON u.id = d.manager "
          + "where d.id=#{id}")
  @Results({
    @Result(property = "id", column = "did", typeHandler = StringIdHandler.class),
    @Result(property = "name", column = "dname"),
    @Result(property = "type", column = "dtype"),
    @Result(property = "serialNumber", column = "dserial_number"),
    @Result(property = "manager.id", column = "uid", typeHandler = StringIdHandler.class),
    @Result(property = "manager.username", column = "uusername"),
    @Result(property = "manager.workId", column = "uwork_id"),
    @Result(property = "manager.realName", column = "ureal_name"),
    @Result(property = "manager.telPhone", column = "utel_phone"),
    @Result(property = "manager.institute", column = "uinstitute"),
    @Result(property = "manager.createTime", column = "ucreate_time"),
    @Result(property = "price", column = "dprice"),
    @Result(property = "description", column = "ddescription"),
    @Result(property = "availableState", column = "davailable_state"),
    @Result(property = "createTime", column = "dcreate_time"),
  })
  DeviceVo getDeviceById(@Param("id") String id);
}
