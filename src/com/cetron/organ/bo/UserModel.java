package com.cetron.organ.bo;

import java.io.Serializable;


/**
 *Description: 
 *@author wugj
 *@date 2018年2月27日 上午11:01:54
 */
public class UserModel  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	
	private String userName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
