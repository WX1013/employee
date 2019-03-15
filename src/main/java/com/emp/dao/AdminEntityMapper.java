package com.emp.dao;

import com.emp.pojo.AdminEntity;
import com.emp.pojo.AdminEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminEntityMapper {
    long countByExample(AdminEntityExample example);

    int deleteByExample(AdminEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminEntity record);

    int insertSelective(AdminEntity record);

    List<AdminEntity> selectByExample(AdminEntityExample example);

    AdminEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdminEntity record, @Param("example") AdminEntityExample example);

    int updateByExample(@Param("record") AdminEntity record, @Param("example") AdminEntityExample example);

    int updateByPrimaryKeySelective(AdminEntity record);

    int updateByPrimaryKey(AdminEntity record);
}
