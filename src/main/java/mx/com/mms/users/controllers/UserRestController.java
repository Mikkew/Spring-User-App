package mx.com.mms.users.controllers;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.*;

import mx.com.mms.users.entities.User;
import mx.com.mms.users.service.IUserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

	@Autowired
	private IUserService userService;

	@GetMapping
	@Timed(value = "get.users")
	public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "userId") String sortBy) {
		List<User> list = userService.findAll(pageNo, pageSize, sortBy);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/usernames")
	public ResponseEntity<?> getUsernames(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		List<String> list = userService.findUsernames(pageNo, pageSize);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Returns a user for a given", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, message = "The record was found"),
			@ApiResponse(code=404, message = "The record was not found")
	})
	public ResponseEntity<?> getUser(@PathVariable String id) {
		User user = userService.findById(id);

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().body(user);
	}

	@PostMapping
	public ResponseEntity<?> createUser(@Valid @RequestBody User user, Errors valid) {

		User userNew;
		Map<String, Object> response = new HashMap<>();

		if (valid.hasErrors()) {
			List<String> errors = valid.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).toList();

			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			userNew = userService.save(user);
		} catch (DataAccessException ex) {
			response.put("message", "Error al realizar el insert a la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(userNew, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user, String id, Errors valid) {
		User userUpdate;
		Map<String, Object> response = new HashMap<>();

		if (valid.hasErrors()) {
			List<String> errors = valid.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).toList();

			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			userUpdate = userService.findById(id);
			if (userUpdate == null) {
				response.put("message", "El cliente ID: ".concat(id.toString()).concat(" no existe"));
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				userUpdate.setUsername(user.getUsername());
				userUpdate.setPassword(user.getPassword());
				return ResponseEntity.accepted().body(userUpdate);
			}
		} catch (DataAccessException ex) {
			response.put("message", "Error al realizar la actualizacion en la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@Valid @RequestBody User user, String id, Errors valid) {
		User userDelete;
		Map<String, Object> response = new HashMap<>();

		try {
			userDelete = userService.findById(id);
			
			if (userDelete == null) {
				response.put(
                        "message",
                        "The role ID: ".concat(id.toString()).concat(" not found")
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
            	userService.delete(userDelete);
            }
		} catch (DataAccessException ex) {
			response.put("message", "Error al realizar la consulta a la base de datos");
            response.put(
                    "error",
                    ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage())
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(userDelete);
	}

}
