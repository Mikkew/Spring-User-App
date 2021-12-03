package mx.com.mms.users.controllers;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/hello")
public class Devs4jController {

	@GetMapping
	public String hello() {
		return "hello World!";
	}
}
