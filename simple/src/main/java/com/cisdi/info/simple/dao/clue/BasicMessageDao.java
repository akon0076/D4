package com.cisdi.info.simple.dao.clue;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.clue.BasicMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "basicMessageDao")
public interface BasicMessageDao {

    /**
    * 根据分页参数查询基础信息集合
    *
    * @param pageDTO 分页条件
    */
    public List<BasicMessage> findBasicMessages(PageDTO pageDTO);

    /**
    * 查询全部基础信息集合
    *
    */
    public List<BasicMessage> findAllBasicMessages();

    /**
    * 查询所有基础信息集合(只提取ID 和 Name)
    *
    */
    public List<BasicMessage> findAllBasicMessagesWithIdName();

    /**
    * 根据名称查询基础信息集合(只提取ID 和 Name)
    *
    * @param basicMessageName 名称
    */
    public List<BasicMessage> findBasicMessagesWithIdNameByName(@Param("basicMessageName") String basicMessageName);

    /**
    * 根据ID查询指定的基础信息(只提取ID 和 Name)
    *
    * @param basicMessageId Id
    */
    public BasicMessage findBasicMessagesWithIdNameById(@Param(" basicMessageId") Long basicMessageId);

    /**
    * 根据分页参数查询基础信息集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findBasicMessageTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的基础信息
    *
    * @param basicMessageId Id
    */
    public BasicMessage findBasicMessage(@Param("basicMessageId") Long basicMessageId);

    /**
    * 根据ID查询指定的基础信息(包含外键)
    *
    * @param basicMessageId Id
    */
    public BasicMessage findBasicMessageWithForeignName(@Param("basicMessageId") Long basicMessageId);

    /**
    * 新增基础信息
    *
    * @param basicMessage 实体对象
    */
    public Integer saveBasicMessage(BasicMessage basicMessage);

    /**
    * 更新基础信息
    *
    * @param basicMessage 实体对象
    */
    public Integer updateBasicMessage(BasicMessage basicMessage);

    /**
    * 根据ID删除基础信息
    *
    * @param basicMessageId ID
    */
    public Integer deleteBasicMessage(@Param("basicMessageId") Long basicMessageId);
}
