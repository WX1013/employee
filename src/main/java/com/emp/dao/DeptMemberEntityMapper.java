package com.emp.dao;

import com.emp.pojo.DeptMemberEntity;
import com.emp.pojo.DeptMemberEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMemberEntityMapper {
    long countByExample(DeptMemberEntityExample example);

    int deleteByExample(DeptMemberEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeptMemberEntity record);

    int insertSelective(DeptMemberEntity record);

    List<DeptMemberEntity> selectByExample(DeptMemberEntityExample example);

    DeptMemberEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeptMemberEntity record, @Param("example") DeptMemberEntityExample example);

    int updateByExample(@Param("record") DeptMemberEntity record, @Param("example") DeptMemberEntityExample example);

    int updateByPrimaryKeySelective(DeptMemberEntity record);

    int updateByPrimaryKey(DeptMemberEntity record);
}
