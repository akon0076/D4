package com.cisdi.info.simple.service.clue;

import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.clue.BasicMessage;
import com.cisdi.info.simple.dto.base.PageDTO;

import java.util.List;

public interface BasicMessageService {
    /**
     * 根据分页参数查询基础信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findBasicMessages(PageDTO pageDTO);

    /**
     * 查询全部基础信息集合
     *
     */
    public List<BasicMessage> findAllBasicMessages();

    /**
     * 根据名称查询基础信息集合(只提取ID 和 Name)
     *
     * @param basicMessageName 名称
     */
    public List<BasicMessage> findBasicMessagesWithIdNameByName(String basicMessageName);

    /**
     * 查询所有基础信息集合(只提取ID 和 Name)
     *
     */
    public List<BasicMessage> findAllBasicMessagesWithIdName();

    /**
     * 根据ID查询指定的基础信息(只提取ID 和 Name)
     *
     * @param basicMessageId Id
     */
    public BasicMessage findBasicMessagesWithIdNameById(Long basicMessageId);

    /**
     * 根据ID查询指定的基础信息
     *
     * @param basicMessageId Id
     */
    public BasicMessage findBasicMessage(Long basicMessageId);

    /**
     * 根据ID查询指定的基础信息(包含外键)
     *
     * @param basicMessageId Id
     */
    public BasicMessage findBasicMessageWithForeignName(Long basicMessageId);

    /**
     * 新增基础信息
     *
     * @param basicMessage 实体对象
     */
    public BasicMessage saveBasicMessage(BasicMessage basicMessage);

    /**
     * 更新基础信息
     *
     * @param basicMessage 实体对象
     */
    public BasicMessage updateBasicMessage(BasicMessage basicMessage);

    /**
     * 根据ID删除基础信息
     *
     * @param basicMessageId ID
     */
    public void deleteBasicMessage(Long basicMessageId);
}
