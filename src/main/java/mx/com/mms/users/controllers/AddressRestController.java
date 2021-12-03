package mx.com.mms.users.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import mx.com.mms.users.entities.Address;
import mx.com.mms.users.service.IAddressService;

@RestController
@RequestMapping("/api/v1/address")
public class AddressRestController {

	@Autowired
	private IAddressService addressService;
	
	@GetMapping
	public ResponseEntity<?> getAddresses(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "addressId") String sortBy) {
		List<Address> list = addressService.findAll(pageNo, pageSize, sortBy);
		return ResponseEntity.ok().body(list);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAddress(@PathVariable String id) {
		
		Address address = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			address = addressService.findById(id);
			
			if(address == null ) {
				
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
		return ResponseEntity.ok(address);
	}
	
	@PostMapping
	public ResponseEntity<?> createAddress(@Valid @RequestBody Address address, Errors valid) {
		
		Address addressNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(valid.hasErrors()) {
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
			addressNew = addressService.save(address);
		} catch (DataAccessException ex) {
			response.put("message", "Error al realizar la consulta a la base de datos");
            response.put(
                    "error",
                    ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage())
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(addressNew, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAddress(@Valid @RequestBody Address address, @PathVariable String id, Errors valid) {
		
		Address addressUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if(valid.hasErrors()) {
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
			addressUpdate = addressService.update(address, id);
			
			if( addressUpdate == null ) {
				
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
		
		return ResponseEntity.accepted().body(addressUpdate);
	}
	
}
