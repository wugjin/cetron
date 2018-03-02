package com.cetron.api.constant;

public class ApiUserConstant {
	
	//用户新增，用户名已被存在
	public static final String RESULT_ADD_USERACCOUNT_EXIST = "2";
	//用户新增，两次密码不一致
	public static final String RESULT_ADD_DIFFERENT_PWD = "3";
	//用户新增，参数不合法
	public static final String RESULT_ADD_PRARAMS_ERROR = "4";
	
	//用户修改，用户名已被存在
	public static final String RESULT_UPDATE_USERACCOUNT_EXIST = "2";
	//用户修改，两次密码不一致
	public static final String RESULT_UPDATE_DIFFERENT_PWD = "3";
	//用户修改，参数不合法
	public static final String RESULT_UPDATE_PRARAMS_ERROR = "4";
	
	//查询用户信息,参数不合法
	public static final String RESULT_PROFILE_PARAMS_ERROR = "2";
	
	//用户删除，参数不合法
	public static final String RESULT_DELETE_PRARAMS_ERROR = "2";
	
	//用户查询，参数不合法
	public static final String RESULT_LIST_PARAMS_ERROR = "2";
	
}
