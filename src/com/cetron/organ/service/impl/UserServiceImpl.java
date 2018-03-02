package com.cetron.organ.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cetron.core.constant.ResultConstant;
import com.cetron.organ.bo.UserBaseModel;
import com.cetron.organ.bo.UserModel;
import com.cetron.organ.bo.UserRoleModel;
import com.cetron.organ.constant.UserConstant;
import com.cetron.organ.dao.UserBaseDao;
import com.cetron.organ.dao.UserDao;
import com.cetron.organ.dao.UserRoleDao;
import com.cetron.organ.service.UserService;
import com.cetron.util.NumberUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserBaseDao userBaseDao;
	
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public String saveOrUpdate(UserBaseModel o, String userName, String roleId) {
		if(checkUserAccount(o)) {
			UserModel user = null;
			UserRoleModel ur = null;
			if(o.getUserId()==null) {//新增
				//保存基本信息
				userBaseDao.add(o);
				Integer userId = o.getUserId();
				
				//保存主表信息
				user = new UserModel();
				user.setUserId(userId);
				user.setUserName(userName);
				userDao.add(user);
				
				//保存用户角色信息
				ur = new UserRoleModel();
				ur.setUserId(userId);
				ur.setRoleId(NumberUtil.toInteger(roleId));
				userRoleDao.add(ur);
			}else {//修改
				//保存基本信息
				userBaseDao.update(o);
				
				//保存主表信息
				user = userDao.getById(o.getUserId());
				user.setUserName(userName);
				userDao.update(user);
				
				//保存用户角色信息
				ur = userRoleDao.getById(o.getUserId());
				ur.setRoleId(NumberUtil.toInteger(roleId));
				userRoleDao.update(ur);
			}
			return ResultConstant.SUCCESS;
		}
		return UserConstant.RESULT_USERACCOUNT_DUPLICATE;
	}

	@Override
	public UserModel getById(String userId) {
		return userDao.getById(NumberUtil.toInteger(userId));
	}

	@Override
	public UserBaseModel getBaseById(String userId) {
		return userBaseDao.getById(NumberUtil.toInteger(userId));
	}

	@Override
	public UserRoleModel getUserRole(String userId) {
		return userRoleDao.getById(NumberUtil.toInteger(userId));
	}

	@Override
	public PageList<UserBaseModel> list(Map<String, Object> paramMap, PageBounds pb) {
		return userBaseDao.list(paramMap, pb);
	}

	//新增或修改时，判断用户登录账号是否有重复
	private boolean checkUserAccount(UserBaseModel o) {
		List<UserBaseModel> userBaseList = userBaseDao.listSameName(o);
		if(userBaseList!=null && userBaseList.size()>0) {//有重复的用户登录账号
			return false;
		}
		return true;
	}

	@Override
	public void delete(String ids) {
		//用户基本信息
		String[] idArr = ids.split(",");
		if(idArr!=null && idArr.length>0) {
			UserBaseModel o = null;
			for(String userId : idArr) {
				o = this.getBaseById(userId);
				o.setDeleteFlag(UserConstant.IS_DELETE_YES);
				userBaseDao.update(o);
			}
		}
		
	}

	@Override
	public UserBaseModel getBaseByUserAccount(String userAccount) {
		return userBaseDao.getByUserAccount(userAccount);
	}
}
