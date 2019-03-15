package com.emp.dao;

import com.emp.pojo.LogEntity;
import com.emp.pojo.LogEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogEntityMapper {
    long countByExample(LogEntityExample example);

    int deleteByExample(LogEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogEntity record);

    int insertSelective(LogEntity record);

    List<LogEntity> selectByExample(LogEntityExample example);

    LogEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogEntity record, @Param("example") LogEntityExample example);

    int updateByExample(@Param("record") LogEntity record, @Param("example") LogEntityExample example);

    int updateByPrimaryKeySelective(LogEntity record);

    int updateByPrimaryKey(LogEntity record);
}