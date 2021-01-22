package com.house.project.business.mapper;

import java.util.List;
import com.house.project.business.domain.BizUser;
import com.house.project.business.domain.vo.BizUserVo;
import com.house.project.business.domain.vo.SuperiorsVo;

/**
 * 业务用户Mapper接口
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface BizUserMapper 
{
    /**
     * 查询业务用户上级集合
     *
     * @param superiors 查询条件
     * @return 业务用户上级集合
     */
    public List<BizUser> selectSuperiors(SuperiorsVo superiors);

    /**
     * 登录号查询业务用户
     * @param loginName 登录号
     * @return 业务用户
     */
    public BizUser selectBizUserByName(String loginName);

    /**
     * 手机号查询业务用户
     * @param phone 手机号
     * @return 业务用户
     */
    public BizUser selectBizUserByPhone(String phone);

    /**
     * 查询业务用户
     * 
     * @param id 业务用户ID
     * @return 业务用户
     */
    public BizUserVo selectBizUserById(Integer id);

    /**
     * 查询业务用户列表
     * 
     * @param bizUser 业务用户
     * @return 业务用户集合
     */
    public List<BizUserVo> selectBizUserList(BizUser bizUser);
    /**
     * 新增业务用户
     * 
     * @param bizUser 业务用户
     * @return 结果
     */
    public int insertBizUser(BizUser bizUser);

    /**
     * 修改业务用户
     * 
     * @param bizUser 业务用户
     * @return 结果
     */
    public int updateBizUser(BizUser bizUser);

    /**
     * 修改登录失败次数
     * @param bizUser
     * @return
     */
    public int updateWrongPwdTimes(BizUser bizUser);

    /**
     * 删除业务用户
     * 
     * @param id 业务用户ID
     * @return 结果
     */
    public int deleteBizUserById(Integer id);

    /**
     * 批量删除业务用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizUserByIds(Integer[] ids);

    /**
     * 批量将业务用户停用
     * 
     * @param ids
     * @return
     */
    public int updateBizUserDelStatus(Integer[] ids);
}
