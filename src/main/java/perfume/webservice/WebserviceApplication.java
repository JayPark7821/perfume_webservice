package perfume.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import perfume.webservice.user.config.properties.AppProperties;
import perfume.webservice.user.config.properties.CorsProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class,

})
//@Import(AdminLoginConfig.class)
public class WebserviceApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
		+ "classpath:application.yml,"
		+ "/app/config/perfume-webservice/real-application.yml";

	@Bean
	public BCryptPasswordEncoder psswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(WebserviceApplication.class)
			.properties(APPLICATION_LOCATIONS)
			.run(args);
	}


}
