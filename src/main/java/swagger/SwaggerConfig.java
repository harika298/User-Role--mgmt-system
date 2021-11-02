//package swagger;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import com.google.common.base.Predicate;
//
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import static springfox.documentation.builders.PathSelectors.regex;
//import springfox.documentation.service.ApiInfo;
//import static com.google.common.base.Predicates.or;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig  extends WebMvcConfigurationSupport{
//	
//	@Bean
//	public Docket postsApi() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select().apis(RequestHandlerSelectors.basePackage("com.management.user.controller"))
//                .paths(regex("/users.*"))
//                .build();		
//	}
//	
//	 @Override
//	    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//	        registry.addResourceHandler("swagger-ui.html")
//	                .addResourceLocations("classpath:/META-INF/resources/");
//
//	        registry.addResourceHandler("/webjars/**")
//	                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//	    }
//
////	private Predicate<String> postPaths(){
////		return or(regex("/users/posts.*"), regex("/user-service/users.*"));
////	}
////	
////
////	private ApiInfo apiInfo() {
////		return new ApiInfoBuilder().title("User Management Service")
////				.description("user management service reference for developers")
////				.version("1.0").build();
////	}
//
//}
