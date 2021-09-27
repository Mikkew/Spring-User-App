package mx.com.mms.users.service;

import org.springframework.data.domain.Page;

import mx.com.mms.users.entities.Role;

public interface IRoleService {
	public Page<Role> findAll(Integer pageNo, Integer pageSize, String sortBy);

	public Role findById(String id);

	public Role save(Role role);

	public void delete(Role role);
}
