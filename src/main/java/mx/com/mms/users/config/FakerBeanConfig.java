package mx.com.mms.users.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.*;

@Configuration
public class FakerBeanConfig {
	
	@Bean
	public Faker getFaker() {
		return new Faker();
	}
}
