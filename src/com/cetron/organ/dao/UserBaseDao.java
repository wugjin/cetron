package com.cetron.organ.dao;

import java.util.List;
import java.util.Map;

import com.cetron.organ.bo.UserBaseModel;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface UserBaseDao{
	/**
	 *Description: 新增
	 *@author wugj
	 *@date 2018年3月1日 下午4:50:03
	 */
	void add(UserBaseModel o);
	
	/**
	 *Description: 修改
	 *@author wugj
	 *@date 2018年3月1日 下午4:50:14
	 */
	void update(UserBaseModel o);
	
	/**
	 *Description: 通过主键查找
	 *@author wugj
	 *@date 2018年3月1日 下午4:50:29
	 */
	UserBaseModel getById(Integer id);
	
	/**
	 *Description: 查询用户是否有重复的登录账号
	 *@author wugj
	 *@date 2018年2月27日 下午1:59:35
	 */
	List<UserBaseModel> listSameName(UserBaseModel o);
	
	/**
	 *Description: 分页查询用户信息，tyep:查询类型，1：用户名称，2：角色类型，3：联系方式；keyword查询关键字，模糊匹配
	 *@author wugj
	 *@date 2018年2月27日 下午1:40:37
	 */
	PageList<UserBaseModel> list(Map<String, Object> paramMap, PageBounds pb);
	
	/**
	 *Description: 根据用户登录名查询
	 *@author wugj
	 *@date 2018年3月2日 下午2:06:23
	 */
	UserBaseModel getByUserAccount(String userAccount);
}
