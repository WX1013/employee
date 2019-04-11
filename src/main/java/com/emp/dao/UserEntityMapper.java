package com.emp.dao;

import com.emp.pojo.UserEntity;
import com.emp.pojo.UserEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserEntityMapper {
    long countByExample(UserEntityExample example);

    int deleteByExample(UserEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    List<UserEntity> selectByExample(UserEntityExample example);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserEntity record, @Param("example") UserEntityExample example);

    int updateByExample(@Param("record") UserEntity record, @Param("example") UserEntityExample example);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    UserEntity getUserByUsername(String username);

    void deleteByEmpId(Integer empId);
}
