package com.emp.dao;

import com.emp.pojo.ImgEntity;
import com.emp.pojo.ImgEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImgEntityMapper {
    long countByExample(ImgEntityExample example);

    int deleteByExample(ImgEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ImgEntity record);

    int insertSelective(ImgEntity record);

    List<ImgEntity> selectByExample(ImgEntityExample example);

    ImgEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ImgEntity record, @Param("example") ImgEntityExample example);

    int updateByExample(@Param("record") ImgEntity record, @Param("example") ImgEntityExample example);

    int updateByPrimaryKeySelective(ImgEntity record);

    int updateByPrimaryKey(ImgEntity record);
}
