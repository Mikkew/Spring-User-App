package mx.com.mms.users.service;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.mms.users.dao.IUserDao;
import mx.com.mms.users.entities.User;

@Service
public class UserServiceImp implements IUserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);

	@Autowired
	private IUserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<User> pagedResult = userDao.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<User>();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> findUsernames(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<String> pagedResult = userDao.findUsernames(paging);
		return pagedResult.getContent();
	}

	@Override
	@Transactional(readOnly = true)
	@CacheEvict("users")
	public User findById(String id) {
		log.info("Getting user by id {}", id);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace(System.out);
		}
		
		return userDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	@Transactional
	public void delete(User user) {
		userDao.delete(user);
	}

}
