package com.cetron.organ.service;

import java.util.Map;

import com.cetron.organ.bo.UserBaseModel;
import com.cetron.organ.bo.UserModel;
import com.cetron.organ.bo.UserRoleModel;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface UserService {
	
	/**
	 *Description: 新增、修改用户信息
	 *@author wugj
	 *@date 2018年2月27日 下午1:39:15
	 */
	String saveOrUpdate(UserBaseModel o, String userName, String roleId);
	
	/**
	 *Description: 查询用户信息
	 *@author wugj
	 *@date 2018年2月27日 下午1:39:44
	 */
	UserModel getById(String userId);
	
	/**
	 *Description: 查询用户基本信息
	 *@author wugj
	 *@date 2018年2月27日 下午1:40:02
	 */
	UserBaseModel getBaseById(String userId);
	
	/**
	 *Description: 查询用户角色
	 *@author wugj
	 *@date 2018年2月27日 下午1:40:19
	 */
	UserRoleModel getUserRole(String userId);
	
	/**
	 *Description: 分页查询用户信息，tyep:查询类型，1：用户名称，2：角色类型，3：联系方式；keyword查询关键字，模糊匹配
	 *@author wugj
	 *@date 2018年2月27日 下午1:40:37
	 */
	PageList<UserBaseModel> list(Map<String, Object> paramMap, PageBounds pb);
	
	/**
	 *Description: 逻辑删除
	 *@author wugj
	 *@date 2018年2月27日 下午3:47:21
	 */
	void delete(String ids);
	
	/**
	 *Description: 根据用户登录名查询
	 *@author wugj
	 *@date 2018年3月2日 下午1:57:51
	 */
	UserBaseModel getBaseByUserAccount(String userAccount);
}
