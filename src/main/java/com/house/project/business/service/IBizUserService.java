package com.house.project.business.service;

import java.util.List;

import com.house.framework.web.domain.AjaxResult;
import com.house.project.business.domain.BizUser;
import com.house.project.business.domain.vo.BizUserVo;
import com.house.project.business.domain.vo.RegisterVo;
import com.house.project.business.domain.vo.SuperiorsVo;

/**
 * 业务用户Service接口
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface IBizUserService 
{

    /**
     * 注册验证码验证
     * @param register 验证码信息
     * @return 结果
     */
    public AjaxResult verifyResult(RegisterVo register);

    /**
     * 导入业务用户数据
     *
     * @param userList 业务用户数据列表
     * @param createBy 操作用户
     * @return 结果
     */
    public String importBizUser(List<BizUser> userList, String createBy);

    /**
     * 新增用户
     * @param register 注册信息
     * @param login 是否登录
     * @return 结果
     */
    public AjaxResult registerUser(RegisterVo register, boolean login);

    /**
     * 新增用户
     * @param bizUser 用户信息
     * @param login 是否登录
     * @return 结果
     */
    public int addUser(BizUser bizUser, boolean login);

    /**
     * 批量将业务用户停用
     *
     * @param ids 用户id集合
     * @return 结果
     */
    public int updateBizUserDelStatus(Integer[] ids);

    /**
     * 查询业务用户上级集合
     *
     * @param superiorsVo 查询条件
     * @return 业务用户上级集合
     */
    public List<BizUser> selectSuperiors(SuperiorsVo superiorsVo);

    /**
     * 业务用户登录验证
     *
     * @param loginName 用户名
     * @param password 密码
     * @return 结果
     */
    String loginByBizUser(String loginName, String password);

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
     * 批量删除业务用户
     * 
     * @param ids 需要删除的业务用户ID
     * @return 结果
     */
    public int deleteBizUserByIds(Integer[] ids);

    /**
     * 删除业务用户信息
     * 
     * @param id 业务用户ID
     * @return 结果
     */
    public int deleteBizUserById(Integer id);
}
