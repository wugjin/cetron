package com.cetron.organ.bo;

import java.io.Serializable;


/**
 *Description: 
 *@author wugj
 *@date 2018年2月27日 上午11:18:33
 */
public class UserRoleModel  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
