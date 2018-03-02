package com.cetron.core.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;

import com.cetron.api.cache.TokenCache;
import com.cetron.api.vo.TokenUserVo;
import com.cetron.core.constant.SysConstant;
import com.cetron.util.StringUtil;
@Controller
public class BaseController {
	protected Log log = LogFactory.getLog(getClass());
	
	protected static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 *Description: 返回数据封装
	 *@author wugj
	 *@date 2017年12月14日 上午10:49:20
	 */
	protected void writeJson(HttpServletResponse response, Object result) {
		try {
			response.setContentType("text/json;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(result.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			log.error("writeJson()", e);
		}
	}
	
	/**
	 *Description: 判断用户token，是否有效；有效是返回userId，无效返回null
	 *@author wugj
	 *@date 2017年12月14日 上午10:54:12
	 */
	protected String checkToken(String token) {
		if(StringUtil.isNotBlank(token)){
			TokenUserVo userVo = TokenCache.get(token);
			if(userVo!=null && userVo.getDeadline()>=System.currentTimeMillis()) {//找到token，并且没有过期
				//延迟token有效期至60分钟
				userVo.setDeadline(System.currentTimeMillis()+SysConstant.TOKEN_VALIDITY);
				
				//返回用户userId
				return userVo.getUserId();
			}
		}
		//token无效返回null
		return null;
	}
}
