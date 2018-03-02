package com.cetron.organ.bo;

import java.io.Serializable;


/**
 *Description: 
 *@author wugj
 *@date 2018年2月27日 上午11:14:11
 */
public class RoleModel  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer roleId;
	
	private String roleName;
	
	private String parentRoleId;
	
	private String roleDesc;
	
	private String roleCode;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getParentRoleId() {
		return parentRoleId;
	}

	public void setParentRoleId(String parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}
