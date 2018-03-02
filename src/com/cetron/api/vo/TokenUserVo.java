package com.cetron.api.vo;

import java.io.Serializable;

public class TokenUserVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private long deadline;//过期时间

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
}
