package mx.com.mms.users.controllers;

import java.util.*;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import mx.com.mms.users.entities.Profile;
import mx.com.mms.users.service.IProfileService;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileRestController {

	@Autowired
	private IProfileService profileService;

	@GetMapping
	public ResponseEntity<?> getProfiles(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "profileId") String sortBy) {
		Page<Profile> list = profileService.findAll(pageNo, pageSize, sortBy);
		return ResponseEntity.ok(list.getContent());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProfile(@PathVariable String id) {

		Profile profile = null;
		Map<String, Object> response = new HashMap<>();

		try {
			profile = profileService.findById(id);
			
			if (profile == null) {
				response.put(
                        "message",
                        "The role ID: ".concat(id.toString()).concat(" not found")
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException ex) {
			response.put("message", "Error al realizar la consulta a la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(profile);
	}

	@PostMapping
	public ResponseEntity<?> createProfile(@Valid @RequestBody Profile profile, Errors valid) {
		Profile profileNew = null;
		Map<String, Object> response = new HashMap<>();

		if (valid.hasErrors()) {
			List<String> errors = valid.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).toList();

			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			profileNew = profileService.save(profile);
		} catch (DataAccessException ex) {
			response.put("message", "Error al realizar la consulta a la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(profileNew, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody Profile profile, @PathVariable String id, Errors valid) {

		Profile profileUpdate = null;
		Map<String, Object> response = new HashMap<>();

		if (valid.hasErrors()) {
			List<String> errors = valid.getFieldErrors().stream().map(err -> {
				return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
			}).toList();

			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			profileUpdate = profileService.findById(id);
			if (profile == null) {
				response.put("message", "The role ID: ".concat(id.toString()).concat(" not found"));
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				profileUpdate.setFirstName(profile.getFirstName());
				profileUpdate.setLastName(profile.getLastName());
				profileUpdate.setBirthDate(profile.getBirthDate());
				profileUpdate.setUser(profile.getUser());
				return ResponseEntity.accepted().body(profileUpdate);
			}
		} catch (DataAccessException ex) {
			response.put("message", "Error al realizar la consulta a la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProfile(@PathVariable String id) {

		Profile profile = null;
		Map<String, Object> response = new HashMap<>();

		try {
			profile = profileService.findById(id);
			if (profile == null) {
				response.put("message", "The role ID: ".concat(id.toString()).concat(" not found"));
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				profileService.delete(profile);
			}
		} catch (DataAccessException ex) {
			response.put("message", "Error al realizar la consulta a la base de datos");
			response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(profile);
	}
}
