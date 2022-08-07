package perfume.webservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String homeRedirectToSwagger() {
		return "redirect:/swagger.html";
	}
}

