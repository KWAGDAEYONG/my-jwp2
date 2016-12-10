package slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import slipp.model.User;
import slipp.model.UserRepository;
import slipp.utils.HttpSessionUtils;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User dbUser = userRepository.findByUserId(userId);
		if (dbUser == null) {
			return "redirect:/users/login";
		}
		
		if (!dbUser.matchPassword(password)) {
			return "redirect:/users/login";
		}
		
		session.setAttribute("loginUser", dbUser);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "/user/login";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User user, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!loginUser.matchId(id)) {
			throw new IllegalStateException("다른 사람의 정보를 수정할 수 없습니다.");
		}
		
		User dbUser = userRepository.findOne(id);
		dbUser.update(user);
		userRepository.save(dbUser);
		return "redirect:/users";
	}

	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!loginUser.matchId(id)) {
			throw new IllegalStateException("다른 사람의 정보를 수정할 수 없습니다.");
		}
		
		model.addAttribute("user", userRepository.findOne(id));
		return "/user/updateForm";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@PostMapping("")
	public String create(User user, Model model) {
		System.out.println("user : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}
}
