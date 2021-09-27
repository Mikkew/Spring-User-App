package mx.com.mms.users.controllers;

import java.util.*;

//import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
//import org.springframework.security.access.prepost.PostAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import mx.com.mms.users.entities.Role;
import mx.com.mms.users.roles.MMSSecurityRole;
import mx.com.mms.users.service.IRoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleRestController {
	
	private static final Logger log = LoggerFactory.getLogger(RoleRestController.class);


	@Autowired
	private IRoleService roleService;
	
	@GetMapping
//	@RolesAllowed({"ROLE_ADMIN"})
//	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//	@PostAuthorize("hasRole('ROLE_ADMIN')")
	@MMSSecurityRole
	public ResponseEntity<?> getRoles(
			String roleName,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "roleId") String sortBy,
			UriComponentsBuilder uriBuilder) {
		//Map<String, Object> response = new HashMap<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("Name: {}", auth.getName());
		log.info("Principal: {}", auth.getPrincipal());
		log.info("Credencial: {}", auth.getCredentials());
		log.info("Roles: {}", auth.getAuthorities().toString());
		log.info("Roles Name: {}", roleName);

		Page<Role> list = roleService.findAll(pageNo, pageSize, sortBy);
		return ResponseEntity.ok().body(list.getContent());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getRol(@PathVariable String id) {
		
		Role role = null;
		Map<String, Object> response = new HashMap<>();

		try {
            role = roleService.findById(id);

            if (role == null) {

                response.put(
                        "message",
                        "The role ID: ".concat(id.toString()).concat(" not found")
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DataAccessException ex) {
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put(
                    "error",
                    ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage())
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(role);
	}
	
	@PostMapping
	public ResponseEntity<?> createRole(@Valid @RequestBody Role role, Errors valid) {
		Role roleNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if( valid.hasErrors() ) {
            List<String> errors = valid.getFieldErrors()
                    .stream()
                    .map( err -> {
                        return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
                    })
                    .toList();

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
		
		try {
			roleNew = roleService.save(role);
			
		} catch (DataAccessException ex) {
			response.put("message", "Error al realizar el insert a la base de datos");
            response.put(
                    "error",
                    ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage())
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(roleNew, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRole(@Valid @RequestBody Role role, @PathVariable String id, Errors valid) {
		Role roleAct = null;
		Map<String, Object> response = new HashMap<>();
		
		if( valid.hasErrors() ) {
            List<String> errors = valid.getFieldErrors()
                    .stream()
                    .map( err -> {
                        return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
                    })
                    .toList();

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
		
		try {
            roleAct = roleService.findById(id);
            if (role == null) {

                response.put(
                        "message",
                        "El cliente ID: ".concat(id.toString()).concat(" no existe")
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                
            } else {
                roleAct.setName( role.getName() );
                
                return ResponseEntity.accepted().body(roleAct);
            }
            
        } catch (DataAccessException ex) {
            response.put("message", "Error al realizar la actualizacion en la base de datos");
            response.put(
                    "error",
                    ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage())
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }  
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRol(@PathVariable String id) {
		
		Role role = null;
		Map<String, Object> response = new HashMap<>();

		try {
            role = roleService.findById(id);

            if (role == null) {

                response.put(
                        "message",
                        "The role ID: ".concat(id.toString()).concat(" not found")
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
            	roleService.delete(role);
            }

        } catch (DataAccessException ex) {
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put(
                    "error",
                    ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage())
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(role);
	}
	
}
