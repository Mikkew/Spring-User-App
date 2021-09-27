package mx.com.mms.users.config;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mx.com.mms.users.dao.*;
import mx.com.mms.users.entities.User;
import mx.com.mms.users.entities.UserInRole;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserInRoleDao userInRoleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = userDao.findByUsername(username);
		if(optional.isPresent()) {
			User user = optional.get();
			List<UserInRole> userInRoles = userInRoleDao.findByUser(user);
			String[] roles = userInRoles.stream().map( r -> r.getRole().getName()).toArray(String[]::new);
			return org.springframework.security.core.userdetails
					.User.withUsername(user.getUsername())
					.password( passwordEncoder.encode(user.getPassword()))
					.roles( roles ).build();
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
	}

}
