package com.emp.dao;

import com.emp.pojo.FeedbackEntity;
import com.emp.pojo.FeedbackEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeedbackEntityMapper {
    long countByExample(FeedbackEntityExample example);

    int deleteByExample(FeedbackEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FeedbackEntity record);

    int insertSelective(FeedbackEntity record);

    List<FeedbackEntity> selectByExample(FeedbackEntityExample example);

    FeedbackEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FeedbackEntity record, @Param("example") FeedbackEntityExample example);

    int updateByExample(@Param("record") FeedbackEntity record, @Param("example") FeedbackEntityExample example);

    int updateByPrimaryKeySelective(FeedbackEntity record);

    int updateByPrimaryKey(FeedbackEntity record);
}
