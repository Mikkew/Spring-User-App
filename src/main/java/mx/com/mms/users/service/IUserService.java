package mx.com.mms.users.service;

import java.util.*;

import mx.com.mms.users.entities.User;

public interface IUserService {
	public List<User> findAll(Integer pageNo, Integer pageSize, String sortBy);

	public User findById(String id);

	public User save(User user);

	public void delete(User user);
	
	public List<String> findUsernames(Integer pageNo, Integer pageSize);
}
