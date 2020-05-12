package org.cust.devicemanagesystem.config.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 转换Long类型id 为 string */
public class StringIdHandler implements TypeHandler<String> {

  @Override
  public void setParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType)
      throws SQLException {}

  @Override
  public String getResult(ResultSet resultSet, String s) throws SQLException {
    return String.valueOf(resultSet.getLong(s));
  }

  @Override
  public String getResult(ResultSet resultSet, int i) throws SQLException {
    return String.valueOf(resultSet.getLong(i));
  }

  @Override
  public String getResult(CallableStatement callableStatement, int i) throws SQLException {
    return String.valueOf(callableStatement.getLong(i));
  }
}
