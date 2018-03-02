package com.cetron.core.constant;

/**
 *Description: 系统常量
 *@author wugj
 *@date 2017年12月12日 下午2:55:29
 */
public class SysConstant {
	//项目根目录
	public static final String ROOT_DIR = "/";
	
	//用户图像目录
	public static final String PHOTO = "/photo";
	
	//项目临时目录
	public static final String TEMP_DIR = "/temp";
	
	//用户图像大小，2M
	public static final long PHOTO_MAX_SIZE = 2L*1024L*1024L;
	
	//表格默认每页显示条数
	public static final int DEFAULT_PAGE_SIZE = 20;
	
	//token有效期,60分钟
	public static final long TOKEN_VALIDITY = 60L*60L*1000L;
	
	//boolean型值值1：是
	public static final int BOOLEAN_YES = 1;
	
	//boolean型值:0：否
	public static final int BOOLEAN_NO = 0;
}
