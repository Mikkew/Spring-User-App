package mx.com.mms.users.config;

import org.springframework.context.annotation.*;

import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("MMS API").version("v1.0").license("Apache 2.0").build();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.select()
				//.apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("mx.com.mms.users.controllers"))
				.paths(PathSelectors.any())
				//.paths(PathSelectors.ant("/users/*"))
				.build().useDefaultResponseMessages(false);
	}
	
}
