package com.emp.dao;

import com.emp.pojo.DeptEntity;
import com.emp.pojo.DeptEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptEntityMapper {
    long countByExample(DeptEntityExample example);

    int deleteByExample(DeptEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeptEntity record);

    int insertSelective(DeptEntity record);

    List<DeptEntity> selectByExample(DeptEntityExample example);

    DeptEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeptEntity record, @Param("example") DeptEntityExample example);

    int updateByExample(@Param("record") DeptEntity record, @Param("example") DeptEntityExample example);

    int updateByPrimaryKeySelective(DeptEntity record);

    int updateByPrimaryKey(DeptEntity record);
}
