package com.cetron.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cetron.core.constant.ResultConstant;
import com.cetron.core.controller.BaseController;
import com.cetron.organ.bo.RoleModel;
import com.cetron.organ.service.RoleService;

@Controller("apiRoleController")
@RequestMapping("api/role")
public class ApiRoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	/**
	 *Description: 查询所有角色信息
	 *@author wugj
	 *@date 2018年2月27日 下午3:41:59
	 */
	@RequestMapping(value = "/listAll", method = RequestMethod.POST)
	public void listAllRoles(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String token = request.getParameter("token");
		Map<String,Object> map = new HashMap<String,Object>();//返回结果
		//校验token
		String optUserId = checkToken(token);
		if(optUserId == null) {//token无效
			map.put("result", ResultConstant.TOKEN_FAILURE);
			response.setStatus(ResultConstant.TOKEN_FAILURE_STATUS);
		}else{
			//查询用户
			List<RoleModel> roleList = roleService.listAll();
			if(roleList==null) {
				roleList = new ArrayList<RoleModel>();
			}
			
			map.put("result", ResultConstant.SUCCESS);
			map.put("rows", roleList);
		}
		
		writeJson(response, objectMapper.writeValueAsString(map));
	}
}
