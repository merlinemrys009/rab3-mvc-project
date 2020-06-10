package rab3.mvc.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rab3.mvc.controller.dto.ProfileDTO;
import rab3.mvc.service.ProfileService;

@Controller
public class AuthController {
	
	@Autowired
	private ProfileService profileService;
	@Autowired
//	@Qualifier("ddataSource")
//	private DataSource dataSource;

	@GetMapping("/foo")
	public String helloWorld() {
		return "Login";   //   /aha.jsp
	}
	

	//action=auth
	//method = POST
	@PostMapping("/auth")
	public String auth(@RequestParam String username,@RequestParam String password,Model model,HttpSession  session) {
		ProfileDTO profileDTO = profileService.auth(username, password);//not w
		System.out.println("profile dto"+profileDTO);
		if (profileDTO!=null) {
			session.setAttribute("proS", profileDTO);//it should not
			model.addAttribute("p", profileDTO);
		
//			String sql="select id,name,email,gender,role from rab3.webtabledemo where username =? and password = ?";
//			List<ProfileDTO> profileDTOs=jdbcTemplate.query(sql, new Object[] {username,password},new BeanPropertyRowMapper<ProfileDTO>(ProfileDTO.class));			if(profileDTOs.size()==1) {
//				session.setAttribute("proS", profileDTOs.get(0));
//				model.addAttribute("p", profileDTOs.get(0));
				return "Home";
			}else {
				model.addAttribute("msg", "Either Username or Password is Incorrect");
				return "Login";
			}
	}

}
