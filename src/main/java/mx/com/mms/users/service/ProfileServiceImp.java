package mx.com.mms.users.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.mms.users.dao.*;
import mx.com.mms.users.entities.*;

@Service
public class ProfileServiceImp implements IProfileService {
	
	@Autowired
	private IProfileDao profileDao;
	
	@Autowired
	private IUserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public Page<Profile> findAll(Integer pageNo, Integer pageSize, String sort) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sort));
		Page<Profile> pagedResult = profileDao.findAll(paging);
		return pagedResult;
	}

	@Override
	@Transactional(readOnly = true)
	public Profile findById(String id) {
		return profileDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Profile save(Profile profile) {
		Optional<User> user = userDao.findById(profile.getUserId());
		if(user.isPresent()) {
			profile.setUser(user.get());
			return profileDao.save(profile);
		}
		return null;
	}

	@Override
	@Transactional
	public void delete(Profile profile) {
		profileDao.delete(profile);
	}
}
