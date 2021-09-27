package mx.com.mms.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.com.mms.users.service.IUserInRoleService;

@RestController
@RequestMapping("/api/v1/userinrole")
public class UserInRoleRestController {

	@Autowired
	private IUserInRoleService userInRoleService;
	
	@GetMapping
	public ResponseEntity<?> getUsersInRoles(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "id") String sortBy) {
		return ResponseEntity.ok(userInRoleService.findAll(pageNo, pageSize, sortBy));
	}
}
