package com.house.project.business.service;

import java.util.List;
import com.house.project.business.domain.BizReceptionLog;

/**
 * 接待日志Service接口
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface IBizReceptionLogService 
{
    /**
     * 查询接待日志
     * 
     * @param id 接待日志ID
     * @return 接待日志
     */
    public BizReceptionLog selectBizReceptionLogById(Integer id);

    /**
     * 查询接待日志列表
     * 
     * @param bizReceptionLog 接待日志
     * @return 接待日志集合
     */
    public List<BizReceptionLog> selectBizReceptionLogList(BizReceptionLog bizReceptionLog);

    /**
     * 新增接待日志
     * 
     * @param bizReceptionLog 接待日志
     * @return 结果
     */
    public int insertBizReceptionLog(BizReceptionLog bizReceptionLog);

    /**
     * 修改接待日志
     * 
     * @param bizReceptionLog 接待日志
     * @return 结果
     */
    public int updateBizReceptionLog(BizReceptionLog bizReceptionLog);

    /**
     * 批量删除接待日志
     * 
     * @param ids 需要删除的接待日志ID
     * @return 结果
     */
    public int deleteBizReceptionLogByIds(Integer[] ids);

    /**
     * 删除接待日志信息
     * 
     * @param id 接待日志ID
     * @return 结果
     */
    public int deleteBizReceptionLogById(Integer id);
}
