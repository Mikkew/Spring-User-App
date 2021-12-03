package mx.com.mms.users.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.mms.users.dao.IUserInRoleDao;
import mx.com.mms.users.entities.*;

@Service
public class UserInRoleServiceImp implements IUserInRoleService {
	
	@Autowired
	private IUserInRoleDao userInRoleDao;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;

	@Override
	@Transactional(readOnly = true)
	public List<UserInRole> findAll(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<UserInRole> result = userInRoleDao.findAll(paging);
		return result.getContent();
	}

	@Override
	@Transactional
	public UserInRole save(UserInRole userInRole) {
		User user = userService.findById(userInRole.getUserId());
		Role role = roleService.findById(userInRole.getRoleId());
		
		if(user != null && role != null) {
			UserInRole userInRoleNew = new UserInRole();
			userInRoleNew.setRole(role);
			userInRoleNew.setUser(user);
			return userInRoleDao.save(userInRoleNew);
		}
		return null;
	}
}
