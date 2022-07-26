package perfume.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import perfume.webservice.auth.api.entity.user.User;
import perfume.webservice.auth.config.properties.AppProperties;
import perfume.webservice.auth.config.properties.CorsProperties;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
public class WebserviceApplication {

	@Bean
	public BCryptPasswordEncoder psswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebserviceApplication.class, args);
	}


}
