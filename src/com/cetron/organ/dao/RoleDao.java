package com.cetron.organ.dao;

import java.util.List;

import com.cetron.organ.bo.RoleModel;

public interface RoleDao{
	
	/**
	 *Description: 查询所有系统角色
	 *@author wugj
	 *@date 2018年2月27日 下午1:49:04
	 */
	List<RoleModel> listAll();
	
	/**
	 *Description: 通过主键查询
	 *@author wugj
	 *@date 2018年3月1日 下午3:01:22
	 */
	RoleModel getById(Integer id);
}
