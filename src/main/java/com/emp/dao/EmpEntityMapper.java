package com.emp.dao;

import com.emp.pojo.EmpEntity;
import com.emp.pojo.EmpEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpEntityMapper {
    long countByExample(EmpEntityExample example);

    int deleteByExample(EmpEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EmpEntity record);

    int insertSelective(EmpEntity record);

    List<EmpEntity> selectByExample(EmpEntityExample example);

    EmpEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EmpEntity record, @Param("example") EmpEntityExample example);

    int updateByExample(@Param("record") EmpEntity record, @Param("example") EmpEntityExample example);

    int updateByPrimaryKeySelective(EmpEntity record);

    int updateByPrimaryKey(EmpEntity record);
}
