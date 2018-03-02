package com.cetron.organ.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cetron.organ.bo.RoleModel;
import com.cetron.organ.dao.RoleDao;
import com.cetron.organ.service.RoleService;
import com.cetron.util.NumberUtil;

@Service("roleService")
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<RoleModel> listAll() {
		return roleDao.listAll();
	}

	@Override
	public RoleModel getById(String roleId) {
		return roleDao.getById(NumberUtil.toInteger(roleId));
	}

}
