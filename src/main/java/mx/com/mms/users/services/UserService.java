package mx.com.mms.users.services;

import java.util.*;
import javax.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.web.server.ResponseStatusException;

import com.github.javafaker.Faker;

import mx.com.mms.users.models.User;

@Service
public class UserService {

	@Autowired
	private Faker faker;
	private List<User> users = new ArrayList<>();

	@PostConstruct
	public void init() {
		for (int i = 0; i < 100; i++) {
			users.add(new User(faker.funnyName().name(), faker.name().username(), faker.dragonBall().character()));
		}
	}

	public List<User> findAll(String startWith) {
		if(startWith != null) {
			return users.stream().filter( u -> u.getUsername().startsWith(startWith) ).toList();
		} else {
			return users;			
		}
	}

	public User getUserByUsername(String username) {
		return users.stream()
					.filter(u -> u.getUsername().equals(username))
					.findAny()
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						String.format("User %s not found", username)));
	}
	
	public User createUser(User user) {
		if(users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					String.format("User %s already exist", user.getUsername()));
		}
		users.add(user);
		return user;
	}
	
	public User updateUser(User user, String username) {
		User userUpdated = getUserByUsername(username);
		userUpdated.setNickName(user.getNickName());
		userUpdated.setPassword(user.getPassword());
		return userUpdated;
	}
	
	public User deleteteUser(String username) {
		User userUpdated = getUserByUsername(username);
		users.remove(userUpdated);
		return userUpdated;
	}
	
}
