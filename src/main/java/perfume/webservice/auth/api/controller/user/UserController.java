package perfume.webservice.auth.api.controller.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import perfume.webservice.auth.api.entity.user.User;
import perfume.webservice.auth.api.service.UserService;
import perfume.webservice.common.dto.ApiResponses;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public ApiResponses<User> getUser() {
		User principal = (User)SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		User user = userService.getUser(principal.getUsername());

		return ApiResponses.success(user);
	}
}
