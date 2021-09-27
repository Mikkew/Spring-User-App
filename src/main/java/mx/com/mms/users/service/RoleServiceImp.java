package mx.com.mms.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.mms.users.dao.IRoleDao;
import mx.com.mms.users.entities.Role;

@Service
public class RoleServiceImp implements IRoleService {

	@Autowired
	private IRoleDao roleDao;

	@Override
	@Transactional(readOnly = true)
	public Page<Role> findAll(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Role> pagedResult = roleDao.findAll(paging);
		return pagedResult;
	}

	@Override
	@Transactional(readOnly = true)
	public Role findById(String id) {
		return roleDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Role save(Role role) {
		return roleDao.save(role);
	}

	@Override
	@Transactional
	public void delete(Role role) {
		roleDao.delete(role);
	}

}
