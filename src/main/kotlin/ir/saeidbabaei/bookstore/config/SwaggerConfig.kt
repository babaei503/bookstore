package ir.saeidbabaei.bookstore

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.ApiInfoBuilder

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Restful API application with Kotlin and Spring boot")
                .description("Restful API for book store")
                .termsOfServiceUrl("http://www.saeidbabaei.ir")
                .contact(Contact("Saeid Babaei", "http://www.saeidbabaei.ir", "babaei503@gmail.com"))
                .version("1.0.0")
                .build()
    }
	
}