package com.cetron.organ.service;

import java.util.List;

import com.cetron.organ.bo.RoleModel;

public interface RoleService {
	
	/**
	 *Description: 查询所有系统角色
	 *@author wugj
	 *@date 2018年2月27日 下午1:49:04
	 */
	List<RoleModel> listAll();
	
	/**
	 *Description: 通过Id查询
	 *@author wugj
	 *@date 2018年2月27日 下午3:56:56
	 */
	RoleModel getById(String roleId);
}
