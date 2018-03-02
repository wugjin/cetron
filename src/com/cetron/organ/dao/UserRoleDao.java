package com.cetron.organ.dao;

import com.cetron.organ.bo.UserRoleModel;

public interface UserRoleDao{
	/**
	 *Description: 新增
	 *@author wugj
	 *@date 2018年3月1日 下午4:50:03
	 */
	void add(UserRoleModel o);
	
	/**
	 *Description: 修改
	 *@author wugj
	 *@date 2018年3月1日 下午4:50:14
	 */
	void update(UserRoleModel o);
	
	/**
	 *Description: 通过主键查找
	 *@author wugj
	 *@date 2018年3月1日 下午4:50:29
	 */
	UserRoleModel getById(Integer id);
}
