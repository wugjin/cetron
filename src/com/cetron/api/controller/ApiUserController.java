package com.cetron.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cetron.api.constant.ApiUserConstant;
import com.cetron.core.constant.ResultConstant;
import com.cetron.core.controller.BaseController;
import com.cetron.organ.bo.RoleModel;
import com.cetron.organ.bo.UserBaseModel;
import com.cetron.organ.bo.UserModel;
import com.cetron.organ.bo.UserRoleModel;
import com.cetron.organ.constant.UserConstant;
import com.cetron.organ.service.RoleService;
import com.cetron.organ.service.UserService;
import com.cetron.util.NumberUtil;
import com.cetron.util.StringUtil;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller("apiUserController")
@RequestMapping("api/user")
public class ApiUserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 *Description: 用户新增
	 *@author wugj
	 *@date 2018年2月27日 下午3:15:17
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String token = request.getParameter("token");
		String userAccount = request.getParameter("userAccount");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String contactInfo = request.getParameter("contactInfo");
		String email = request.getParameter("email");
		String userName = request.getParameter("userName");
		String roleId = request.getParameter("roleId");
		
		Map<String,Object> map = new HashMap<String,Object>();//返回结果
		//校验token
		String optUserId = checkToken(token);
		if(optUserId == null) {//token无效
			map.put("result", ResultConstant.TOKEN_FAILURE);
			response.setStatus(ResultConstant.TOKEN_FAILURE_STATUS);
		}else if(StringUtil.isBlank(userAccount) || StringUtil.isBlank(password) 
			||StringUtil.isBlank(password2) || StringUtil.isBlank(contactInfo)
			||StringUtil.isBlank(email) || StringUtil.isBlank(userName)
			||StringUtil.isBlank(roleId)) {//验证参数合法性，是否存在空
			
			map.put("result", ApiUserConstant.RESULT_ADD_PRARAMS_ERROR);
		}else if(!password.equals(password2)){//检验两次密码是否一直
			map.put("result", ApiUserConstant.RESULT_ADD_DIFFERENT_PWD);
		}else {
			
			//用户基本信息
			UserBaseModel o = new UserBaseModel();
			o.setUserAccount(userAccount);
			o.setContactInfo(contactInfo);
			o.setEmail(email);
			o.setCreateUser(NumberUtil.toInteger(optUserId));
			o.setCreateTime(new Date());
			o.setDeleteFlag(UserConstant.IS_DELETE_NO);
			//用户密码salt用新增时间
			String salt = new Md5Hash(o.getCreateTime().getTime()+"").toString();
			o.setUserPassword(new Md5Hash(password, salt).toString());
			
			String result = userService.saveOrUpdate(o, userName, roleId);
			
			if(result.equals(UserConstant.RESULT_USERACCOUNT_DUPLICATE)) {//用户登录名重复
				map.put("result", ApiUserConstant.RESULT_ADD_USERACCOUNT_EXIST);
			}else {
				map.put("result", ResultConstant.SUCCESS);
			}
		}
		
		writeJson(response, objectMapper.writeValueAsString(map));
	}
	
	/**
	 *Description: 用户修改
	 *@author wugj
	 *@date 2018年2月27日 下午3:15:17
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String token = request.getParameter("token");
		String userId = request.getParameter("userId");
		String userAccount = request.getParameter("userAccount");
		String password = request.getParameter("password");//修改时，密码和确认密码可以为空，为空时不修改
		String password2 = request.getParameter("password2");
		String contactInfo = request.getParameter("contactInfo");
		String email = request.getParameter("email");
		String userName = request.getParameter("userName");
		String roleId = request.getParameter("roleId");
		
		Map<String,Object> map = new HashMap<String,Object>();//返回结果
		//校验token
		String optUserId = checkToken(token);
		if(optUserId == null) {//token无效
			map.put("result", ResultConstant.TOKEN_FAILURE);
			response.setStatus(ResultConstant.TOKEN_FAILURE_STATUS);
		}else if(StringUtil.isBlank(userAccount) || StringUtil.isBlank(contactInfo)
			||StringUtil.isBlank(email) || StringUtil.isBlank(userName)
			||StringUtil.isBlank(roleId)) {//验证参数合法性，是否存在空
			
			map.put("result", ApiUserConstant.RESULT_UPDATE_PRARAMS_ERROR);
		}else if(StringUtil.isNotBlank(password) && !password.equals(password2)){//检验两次密码是否一直
			map.put("result", ApiUserConstant.RESULT_UPDATE_DIFFERENT_PWD);
		}else {
			//用户基本信息
			UserBaseModel o = userService.getBaseById(userId);
			o.setUserAccount(userAccount);
			o.setContactInfo(contactInfo);
			o.setEmail(email);
			o.setUpdateUser(NumberUtil.toInteger(optUserId));
			o.setUpdateTime(new Date());
			o.setDeleteFlag(UserConstant.IS_DELETE_NO);
			if(StringUtil.isNotBlank(password)) {
				//用户密码salt用新增时间
				String salt = new Md5Hash(o.getCreateTime().getTime()+"").toString();
				o.setUserPassword(new Md5Hash(password, salt).toString());
			}
			String result = userService.saveOrUpdate(o, userName, roleId);
			
			if(result.equals(UserConstant.RESULT_USERACCOUNT_DUPLICATE)) {//用户登录名重复
				map.put("result", ApiUserConstant.RESULT_UPDATE_USERACCOUNT_EXIST);
			}else {
				map.put("result", ResultConstant.SUCCESS);
			}
		}
		
		writeJson(response, objectMapper.writeValueAsString(map));
	}
	
	/**
	 *Description: 查询用户详细信息
	 *@author wugj
	 *@date 2018年2月27日 下午3:38:49
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public void profile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String token = request.getParameter("token");
		String userId = request.getParameter("userId");
		Map<String,Object> map = new HashMap<String,Object>();//返回结果
		//校验token
		String optUserId = checkToken(token);
		if(optUserId == null) {//token无效
			map.put("result", ResultConstant.TOKEN_FAILURE);
			response.setStatus(ResultConstant.TOKEN_FAILURE_STATUS);
		}else if(StringUtil.isBlank(userId)) {
			map.put("result", ApiUserConstant.RESULT_PROFILE_PARAMS_ERROR);
		}else{
			//查询用户
			UserBaseModel userBase = userService.getBaseById(userId);
			map.put("result", ResultConstant.SUCCESS);
			map.put("userId", userBase.getUserId());
			map.put("userAccount", userBase.getUserAccount());
			map.put("email", userBase.getEmail());
			map.put("contactInfo", userBase.getContactInfo());
			
			UserModel user = userService.getById(userId);
			map.put("userName", user.getUserName());
			
			UserRoleModel ur = userService.getUserRole(userId);
			map.put("roleId", ur.getRoleId());
		}
		
		writeJson(response, objectMapper.writeValueAsString(map));
	}
	
	/**
	 *Description: 删除用户
	 *@author wugj
	 *@date 2018年2月27日 下午3:42:32
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String token = request.getParameter("token");
		String ids = request.getParameter("ids");//要删除的userId
		
		Map<String,Object> map = new HashMap<String,Object>();//返回结果
		//校验token
		String optUserId = checkToken(token);
		if(optUserId == null) {//token无效
			map.put("result", ResultConstant.TOKEN_FAILURE);
			response.setStatus(ResultConstant.TOKEN_FAILURE_STATUS);
		}else if(StringUtil.isBlank(ids)) {//验证参数合法性，是否存在空
			
			map.put("result", ApiUserConstant.RESULT_DELETE_PRARAMS_ERROR);
		}else {
			userService.delete(ids);
			map.put("result", ResultConstant.SUCCESS);
		}
		
		writeJson(response, objectMapper.writeValueAsString(map));
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String token = request.getParameter("token");
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		String start = request.getParameter("start");
		String pageSize = request.getParameter("pageSize");
		
		String optUserId = checkToken(token);
		
		Map<String,Object> map = new HashMap<String,Object>();//返回结果
		if(optUserId == null) {//token无效
			map.put("result", ResultConstant.TOKEN_FAILURE);
			response.setStatus(ResultConstant.TOKEN_FAILURE_STATUS);
		}else if(StringUtil.isBlank(type)
				 || StringUtil.isBlank(start) || StringUtil.isBlank(pageSize)){
			map.put("result", ApiUserConstant.RESULT_LIST_PARAMS_ERROR);
		}else {
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("type", type);
			paramMap.put("keyword", keyword);
			 
			PageBounds pb = new PageBounds(NumberUtil.toInt(start), NumberUtil.toInt(pageSize), Order.formString("create_time.desc"));
			PageList<UserBaseModel> pageList= userService.list(paramMap, pb);
			
			map.put("result", ResultConstant.SUCCESS);
			map.put("total", pageList.getPaginator().getTotalCount());
			map.put("rows", format(pageList));
		}
		
		writeJson(response, objectMapper.writeValueAsString(map));
	}
	
	private List<Map<String,Object>> format(List<UserBaseModel> list) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		if(list!=null && list.size()>0) {
			Map<String,Object> map = null;
			UserModel user = null;
			RoleModel role = null;
			for(UserBaseModel o : list) {
				map = new HashMap<String,Object>();
				map.put("contactInfo", o.getContactInfo());
				map.put("email", o.getEmail());
				map.put("userAccount", o.getUserAccount());
				
				user = userService.getById(o.getUserId()+"");
				map.put("userName", user.getUserName());
				
				role = roleService.getById(userService.getUserRole(o.getUserId()+"").getRoleId()+"");
				map.put("roleName", role.getRoleName());
				
				result.add(map);
			}
		}
		
		return result;
	}

}
