package mx.com.mms.users;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.javafaker.Faker;

import mx.com.mms.users.dao.*;
import mx.com.mms.users.entities.*;
import mx.com.mms.users.service.IUserInRoleService;

@SpringBootApplication
public class UserAppApplication implements ApplicationRunner {
		
	private static final Logger log = LoggerFactory.getLogger(UserAppApplication.class);

	@Autowired
	private Faker faker;

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IUserInRoleService userInRoleService;

	public static void main(String[] args) {
		SpringApplication.run(UserAppApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("ADMIN"));
		roles.add(new Role("SUPPORT"));
		roles.add(new Role("USER"));
		
		roles.forEach( role -> {
			Role roleSaved = roleDao.save(role);
			role = roleSaved;
		});
		
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername(faker.name().username());
			user.setPassword(faker.dragonBall().character());
			
			User created = userDao.save(user);
			int position = new Random().nextInt(3);
			Role role =  roles.get(position);
			
			UserInRole userInRoleNew = new UserInRole();
			userInRoleNew.setRoleId(role.getRoleId());
			userInRoleNew.setUserId(created.getUserId());
			
			log.info("{}", created.toString());
			log.info("{}", role.toString());
			
			userInRoleService.save(userInRoleNew);
		}

	}

}
