package mx.com.mms.users.service;

import java.util.*;

import mx.com.mms.users.entities.UserInRole;

public interface IUserInRoleService {
	public List<UserInRole> findAll(Integer pageNo, Integer pageSize, String sortBy);
	
	public UserInRole save(UserInRole userInRole);
}
