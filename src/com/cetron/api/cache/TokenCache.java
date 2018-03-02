package com.cetron.api.cache;

import java.util.HashMap;
import java.util.Map;

import com.cetron.api.vo.TokenUserVo;

public class TokenCache {
	private static Map<String,TokenUserVo> map = new HashMap<String,TokenUserVo>();
	
	private static Map<String, String> userIdTokenMap = new HashMap<String, String>();
	
	public static TokenUserVo get(String key){
		if(key==null){
			return null;
		}else{
			return map.get(key);
		} 
	}
	
	public static void set(String key, TokenUserVo value){
		if(key!=null){
			map.put(key, value);
		}
	}
	
	public static void remove(String key){
		if(key!=null){
			map.remove(key);
		}
	}
	
	/**
	 *Description: 删除过期token
	 *@author wugj
	 *@date 2017年12月29日 上午1:20:45
	 */
	public static void clear() {
		for(String key : map.keySet()) {
			TokenUserVo userVo = map.get(key);
			if(!(userVo!=null && userVo.getDeadline()>=System.currentTimeMillis())) {
				//找到token，过期
				map.remove(key);
				userIdTokenMap.remove(userVo.getUserId());
			}
		}
	}
	
	/**
	 *Description: 通过userId查询token
	 *@author wugj
	 *@date 2018年1月6日 下午12:34:49
	 */
	public static String getTokenByUserId(String userId) {
		
		return userIdTokenMap.get(userId);
	}
	
	public static void setUserIdToken(String userId, String token) {
		userIdTokenMap.put(userId, token);
	}
	
	public static void removeUserIdToken(String userId) {
		userIdTokenMap.remove(userId);
	}
}
