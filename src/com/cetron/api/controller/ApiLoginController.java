package com.cetron.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cetron.api.cache.TokenCache;
import com.cetron.api.constant.ApiLoginConstant;
import com.cetron.api.vo.TokenUserVo;
import com.cetron.core.constant.ResultConstant;
import com.cetron.core.constant.SysConstant;
import com.cetron.core.controller.BaseController;
import com.cetron.organ.bo.UserBaseModel;
import com.cetron.organ.constant.UserConstant;
import com.cetron.organ.service.UserService;
import com.cetron.util.StringUtil;

@Controller("apiLoginController")
@RequestMapping("api/login")
public class ApiLoginController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	/**
	 *Description: 用户登录
	 *@author wugj
	 *@date 2017年12月14日 上午11:54:32
	 */
	@RequestMapping(value = "/logon", method = RequestMethod.POST)
	public void logon(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		UserBaseModel user = userService.getBaseByUserAccount(userName);
		
		Map<String,Object> map = new HashMap<String,Object>();//返回结果
		if(StringUtil.isBlank(password) || user==null) {
			map.put("result", ApiLoginConstant.RESULT_USERNAME_OR_PASSWORD_ERROR);
		}else if(UserConstant.IS_DELETE_YES.equals(user.getDeleteFlag())) {//已删除
			map.put("result", ApiLoginConstant.RESULT_USERNAME_DELETE);
		}else {//比较用户名和密码
			String salt = new Md5Hash(user.getCreateTime().getTime()+"").toString();
			password = new Md5Hash(password, salt).toString();//md5加盐的加密算法，使破解难度增加。
			if(password.equals(user.getUserPassword())) {
				map.put("result", ResultConstant.SUCCESS);
				map.put("token", request.getSession().getId());//将sessionId作为token
				
				//判断用户是否已经登录
				String token = TokenCache.getTokenByUserId(user.getUserId()+"");
				//如果已经登录，将原token删除
				if(StringUtil.isNotBlank(token)) {
					TokenCache.remove(token);
				}
				
				token = request.getSession().getId();
				//判断token是否存在,如果存在，将原token删除
				TokenUserVo userVo = TokenCache.get(token);
				if(userVo!=null) {
					TokenCache.remove(token);
					TokenCache.removeUserIdToken(userVo.getUserId());
				}
				
				//设置用户token
				userVo = new TokenUserVo();
				userVo.setUserId(user.getUserId()+"");
				userVo.setDeadline(System.currentTimeMillis()+SysConstant.TOKEN_VALIDITY);
				TokenCache.set(token, userVo);//token放入缓存
				//保存userId和token
				TokenCache.setUserIdToken(user.getUserId()+"", token);
			}else {
				map.put("result", ApiLoginConstant.RESULT_USERNAME_OR_PASSWORD_ERROR);
			}
		}
		
		writeJson(response, objectMapper.writeValueAsString(map));
	}
	
	/**
	 *Description: 用户退出
	 *@author wugj
	 *@date 2017年12月14日 上午11:54:17
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String token = request.getParameter("token");
		String userId = checkToken(token);
		
		Map<String,Object> map = new HashMap<String,Object>();//返回结果
		if(userId == null) {//token无效
			map.put("result", ResultConstant.TOKEN_FAILURE);
			response.setStatus(ResultConstant.TOKEN_FAILURE_STATUS);
		}else {
			TokenCache.remove(token);//删除token
			map.put("result", ResultConstant.SUCCESS);
		}
		
		writeJson(response, objectMapper.writeValueAsString(map));
	}
}
