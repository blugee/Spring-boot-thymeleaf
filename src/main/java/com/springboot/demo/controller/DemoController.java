package com.springboot.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.demo.dto.StudentDTO;
import com.springboot.demo.enums.Gender;
import com.springboot.demo.model.Student;
import com.springboot.demo.model.StudentCountrySportRelation;
import com.springboot.demo.service.CountryService;
import com.springboot.demo.service.CountrySportService;
import com.springboot.demo.service.StudentCountrySportService;
import com.springboot.demo.service.StudentService;
import com.springboot.demo.util.GeneralUtils;

@Controller
public class DemoController {
	
	@Value("${isAjax}")
	private String isAjax;
	

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CountryService countryService;

	@Autowired
	private CountrySportService countrySportService;
	
	@Autowired
	private StudentCountrySportService studentCountrySportService;
	
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(ModelMap modelMap) {
		
		String redirectUrl = studentService.securityCheck();
		
		if(!StringUtils.isEmpty(redirectUrl))
			return redirectUrl;
		
		modelMap.addAttribute("message", "Hi, Welcome to mysite");
		modelMap.addAttribute("title", "Registration Demo");
		return "studentList";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(ModelMap modelMap) {

		modelMap.addAttribute("title", "Admin Page");
		modelMap.addAttribute("message", getPrincipal());
		return "admin";
	}
	
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDeniedPage() {
	  	
        return "accessDenied";
    }
	 
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout, ModelMap modelMap) {

		if (error != null) {
			modelMap.addAttribute("error", "Invalid username and password!");
		}

		if (logout != null) {
			modelMap.addAttribute("msg", "You've been logged out successfully.");
		}
		
		String redirectUrl = studentService.securityCheck();
		
		if(!StringUtils.isEmpty(redirectUrl))
			return redirectUrl;
		
        return "login";


	}
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
	 	    
	/*simple URl Request it will display registration form */
	@RequestMapping(value = {"/registration"}, method = RequestMethod.GET)
	public String studentRegistration(ModelMap modelMap) {
		modelMap.addAttribute("country", countryService.getCountry());
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setStudent(new Student());
		modelMap.addAttribute("userForm", studentDTO);
		return "registrationForm";
	}
	
	/*call when student fill the registration form data*/ 
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String studentRegistration(ModelMap modelMap,@Valid @ModelAttribute("userForm") StudentDTO studentDTO,
			BindingResult result,final RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		if (result.hasErrors()) {
			return "registrationForm";
		} else {
			
			Boolean message = studentService.saveStudent(studentDTO.getStudent(), studentDTO.getStudentImagePath(),studentDTO.getCountrySportRelation());
			if (message == true) {
				redirectAttributes.addFlashAttribute("message","Registration is succesfull.");
			} else {

				return "registrationForm";
			}
		}

		return "redirect:/registration";
	}

	/*call when user request for list of all student with GET request*/
	@RequestMapping(value = { "/user/list", "/user"},method = { RequestMethod.GET, RequestMethod.POST })
	public String studentList(@RequestParam(value = "gender", required = false) String gender,ModelMap modelMap) {
		
		if (StringUtils.isEmpty(gender)) {
			modelMap.addAttribute("students", studentService.listAll());
		} else {
			try {
				modelMap.addAttribute("students",studentService.listByGender(Gender.valueOf(gender)));
			} catch (Exception e) {
				modelMap.addAttribute("students", studentService.listAll());
			}
		}
		
		modelMap.addAttribute("gender", gender);
		modelMap.addAttribute("genders", Gender.values());
		modelMap.addAttribute("isAjax", isAjax);
		return "studentList";
	}

	/*Called when delete operation is perform*/
	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
	public String deleteStudent(@PathVariable("id") int id, ModelMap modelMap,
			final RedirectAttributes redirectAttributes) {
		
		studentService.deleteStudent(id);
		redirectAttributes.addFlashAttribute("message","Student Remove Succesfully");
		return "redirect:/user/list";
	}

	/*called when update operation is perform*/
	@RequestMapping(value = "/user/update/{id}", method = RequestMethod.GET)
	public String updateStudent(@PathVariable("id") int id, ModelMap modelMap) {
		StudentDTO studentDto=new StudentDTO();
		Student student=studentService.findById(id);
		studentDto.setStudent(student);
		Set<StudentCountrySportRelation> country = student.getStudentCountrySportRelations();
		List<StudentCountrySportRelation> studentCountrySportRelation = studentCountrySportService.studentSport(student);
		modelMap.addAttribute("sport", studentCountrySportRelation);
		modelMap.addAttribute("userForm",studentDto);
		modelMap.addAttribute("selectedCountry",country);
		modelMap.addAttribute("update", "update");
		modelMap.addAttribute("country", countryService.getCountry());
		modelMap.addAttribute("image", studentService.findStudentImageById(id));
		return "registrationForm";
	}

	/*called when filter is used*/
	@RequestMapping(value = "/dropdownList/{gender}",  method = { RequestMethod.GET, RequestMethod.POST })
	public String dropdownList(@PathVariable("gender") String gender,ModelMap modelMap) {

		if (StringUtils.isEmpty(gender)) {
			return "studentDropDownList";
		}
		if (gender.equals("all"))
			modelMap.addAttribute("students", studentService.listAll());
		else
			modelMap.addAttribute("students",studentService.listByGender(Gender.valueOf(gender)));
		return "studentDropDownList";
	}

	/*called when search operation is perform*/
	@RequestMapping(value = "/search",  method = { RequestMethod.GET, RequestMethod.POST })
	public String searchStudent(@RequestParam("text") String search,ModelMap modelMap) {

		if (StringUtils.isEmpty(search)) {
			modelMap.addAttribute("students", studentService.listAll());
			return "studentDropDownList";
		} else
			modelMap.addAttribute("students",studentService.searchByFirstNameOrLastName(search));
		return "studentDropDownList";
	}

	/*called when image is retrieve*/
	@RequestMapping(value = "/getImage", method = RequestMethod.GET)
	public void downloadImage(HttpServletResponse response,@RequestParam("image") String image, ModelMap modelMap)
			throws IOException {
		GeneralUtils.imageByte(response, image);
	}
	
	/*called when country is select*/
	@RequestMapping(value = "/getSport", method= RequestMethod.GET)
	public String getSport(@RequestParam("id") int countryId, ModelMap modelMap) 
			throws IOException, ServletException{
			modelMap.addAttribute("sport",countrySportService.getSport(countryId));
			return "sport";
	}
	
	/*called when email is enterd*/
	@RequestMapping(value = "/emailFinder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> emailFinder(HttpServletResponse response, @RequestParam("email") String email,ModelMap modelMap) throws IOException{
		Boolean result=studentService.emailFinder(email);
		//GeneralUtils.emailFinder(result,response);
		
		Map<String, String> map = new HashMap<String, String>();
		if(!result) {
			map.put("message", "Email address already exists");
			map.put("status", "error");
		}
		return map;
	}
	
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
