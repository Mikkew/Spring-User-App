package mx.com.mms.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.com.mms.users.models.User;
import mx.com.mms.users.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> getUsers(@RequestParam(required = false) String startWith) {
		return ResponseEntity.ok().body(userService.findAll(startWith));
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getUserByName(@PathVariable String username) {
		User user = userService.getUserByUsername(username);

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().body(userService.getUserByUsername(username));
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user) {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
	}

	@PutMapping("/{username}")
	public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username) {
		return ResponseEntity.ok().body(userService.updateUser(user, username));
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<?> updateUser(@PathVariable String username) {
		return ResponseEntity.ok().body(userService.deleteteUser(username));
	}

}
