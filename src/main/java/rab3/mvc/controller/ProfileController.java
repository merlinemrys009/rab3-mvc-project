package rab3.mvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rab3.mvc.controller.dto.ProfileDTO;
import rab3.mvc.dao.entity.ProfileEntity;
//import rab3.mvc.dao.ProfileDao;
import rab3.mvc.service.ProfileService;

@Controller
//@RequestMapping(value="profile")
public class ProfileController {
	@Autowired
	private ProfileService profileService;//link with profile Service
	//yes
	
	
	@GetMapping("/profile")//correct login work right now watch
	public String showPaginatedData(@RequestParam(required=false,defaultValue="1") String  pageid,Model model) {
		int pageSize=2;
		int ppageid=Integer.parseInt(pageid);
		if(ppageid<=0) {
			ppageid=1;
		}
		//2
		//6
		if(ppageid>1) {
			ppageid=(ppageid-1)*pageSize+1;  
		}
		List<ProfileDTO> profileEntities=profileService.findProfile(ppageid, pageSize);
		int totalRecords=profileService.AllRegister();
		if (ppageid<totalRecords) {
			
		
		
		model.addAttribute("p", profileEntities);
		model.addAttribute("recordStarts", ppageid);
		model.addAttribute("pgsize", pageSize);
		model.addAttribute("pgid", pageid);
		model.addAttribute("pros", totalRecords);
		return "Profile";	
		}else {
			return "No Data Found";
		}
 } 
	
	
	@GetMapping("/imageLoader")
	public void showImage(@RequestParam int id,
			HttpServletResponse httpServletResponse) throws IOException {
		byte[] photo = profileService.findPhotoById(id);
		//I am going to write photo into reponse
		httpServletResponse.setContentType("image/png && image/jpg && image/gif" );
		ServletOutputStream outputStream=httpServletResponse.getOutputStream();
		if(photo!=null && photo.length>0) {
			//writtng photo as a byte array into the response body
			outputStream.write(photo);
		}else {
			outputStream.write(new byte[] {});
		}
		//go to the client
		outputStream.flush();
	}
	
	@GetMapping("/Search")
	public String searchForProfilepg() {
		return "Search";
	}
	@PostMapping("/Search")
	public String searchForProfilepg(@RequestParam String email, Model model) {
		ProfileEntity s_profile=profileService.findSingleProfile(email);
		System.out.println("profile values is"+s_profile);
		if(s_profile!=null) {
			model.addAttribute("searchP",s_profile);
		}else {
				model.addAttribute("searchP", "Profile not found");
			}
			return "Search";
	}

	@PostMapping("/ForgotPassword")
	public String forgetPasswordPage(@RequestParam String email, Model model) {
		   String passsoword=profileService.findPassword(email);
			if(passsoword!=null) {
				model.addAttribute("p", "Your Password  =  " + passsoword);				
			}else {
				model.addAttribute("p", "Email is Invalid- please enter valid email");
			}
		   return "ForgotPassword";
	}

	@GetMapping("/ForgotPassword")
	public String forgetPasswordPage() {
		return "ForgotPassword";
	}

//	@GetMapping("/Registration")
//	public String totalRegistration (Model model) {
//		model.addAttribute("pros", profileService.AllRegister());
//		return "Register";
//	}
//	@PostMapping("/Registration")
//	public String totalRegistration (@ModelAttribute ProfileDTO profileDTO,Model model ) {
//		 profileService.AllRegister();
//		model.addAttribute("pros", profileService.AllRegister());
//		return "Register";
//	}
	
	@GetMapping("/Register")//hold on
	public String registerPage(Model model) {
		model.addAttribute("pros", profileService.AllRegister());
		return "Register";
	}
	
	
	@GetMapping("/gregister")
	public String getRegistration() {
		return "Register";
	}
	
	

	@PostMapping("/Register")
	public String registerPostPage(@ModelAttribute ProfileDTO profileDTO,RedirectAttributes redirectAttributes, Model model) {
		System.out.println(model);
        profileService.persist(profileDTO);//
        model.addAttribute("pros", profileService.AllRegister());
        redirectAttributes.addFlashAttribute("pros",profileService.AllRegister());
        redirectAttributes.addFlashAttribute("msg", "You are successfully registered!!!");
		return "redirect:/gregister?msg=You are successfully registered!!!&pros="+profileService.AllRegister();
	}

	@GetMapping("/editProfile")
	public String editProfile(@RequestParam int id,Model  model) {
			ProfileEntity profileDTO=profileService.findProfileById(id);
			model.addAttribute("p", profileDTO);
			return "Edit";
	}

	@PostMapping("/updateProfile")
	public String postEditProfile(@ModelAttribute ProfileDTO profileDTO, Model model) {
		profileService.updateProfile(profileDTO);
		model.addAttribute("p", profileDTO);
		model.addAttribute("msg", "Profile has successfully updated!!");
		return "Edit";
	}

//	@GetMapping("/profile")
//	public String showProfiles(Model model) {
//			List<ProfileDTO> profileDTOs=profileService.findProfile();
//			model.addAttribute("p", profileDTOs);
//			 model.addAttribute("pros", profileService.AllRegister());
//			return "Profile";
//	}
	@GetMapping("/deleteProfile")
	public String deleteProfile(@RequestParam String uname, Model model) {
		profileService.deleteByusername(uname);
		model.addAttribute("msg", "Profile Deleted Successfully");
		return "redirect:/profile";
	}

}
