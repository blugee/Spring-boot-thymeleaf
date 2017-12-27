package com.springboot.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.demo.enums.Gender;
import com.springboot.demo.model.Image;
import com.springboot.demo.model.Student;
import com.springboot.demo.repositry.ImageRepositry;
import com.springboot.demo.repositry.StudentRepositry;
import com.springboot.demo.util.GeneralUtils;

@Service
public class StudentServiceImpl implements StudentService {

	@Value("${path}")
	private String path;
	
	@Autowired
	private StudentCountrySportService studentCountrySportService;
	
	@Autowired
	private StudentRepositry studentRepositry;

	@Autowired
	private ImageRepositry imageRepositry;

	Image image2;
	
	@Override
	public Boolean saveStudent(Student student, MultipartFile image,
			Set<Integer> countrySportRelations) {
		boolean isEdit = student.getId() == 0 ? false : true; 
		
		String 	studentImagePath=null; 
		
		//isEdit==true && image!=null
		if(isEdit && image != null && image.getSize() > 0) {
			
			studentImagePath = GeneralUtils.updateName(image.getOriginalFilename());
			//Get old image from image table
			image2 = imageRepositry.findOne(student.getId());
			String oldImage= image2.getStudentImagePath();
			
			//Delete image from folder path
			deleteFile(oldImage);
			
			//delete sport and country of user
			studentCountrySportService.deleteStudentSport(student);
			
			
			//Save new selected image to folder path
			saveFile(image,studentImagePath);
			
		
			//Update studentImagePath in present image object
			studentRepositry.save(student);
			image2.setStudentImagePath(studentImagePath);
			imageRepositry.save(image2);
			
			studentCountrySportService.saveStudentSport(countrySportRelations, student);
			return true;
		}
		
		//isEdit==true && image==null
		else if(isEdit && image.isEmpty() && image.getSize()==0){
			//replace old image or as it is
			studentRepositry.save(student);
			
			//delete old sport and country
			studentCountrySportService.deleteStudentSport(student);
			studentCountrySportService.saveStudentSport(countrySportRelations, student);
			return true;
		}
		
		//isEdit==false means Registration time
		else if (image != null && image.getSize() > 0) {
			studentImagePath = GeneralUtils.updateName(image.getOriginalFilename());
			if(saveFile(image,studentImagePath)){
				studentRepositry.save(student);
				image2 = new Image();
				image2.setStudent(student);
				image2.setStudentImagePath(studentImagePath);
				imageRepositry.save(image2);
				studentCountrySportService.saveStudentSport(countrySportRelations, student);
				return true;
			}else
				return false;
		}
		else{
			return false;
		}
	}

	/* delete exiting file */
	private void deleteFile(String oldImage) {
		File file=new File("resources/images/"+oldImage);
		file.delete();
	}

	/* file upload in folder */
	private boolean saveFile(MultipartFile image, String newFileName) {
		
		String studentImagePath =newFileName;
		try {
			FileUtils.writeByteArrayToFile(new File("resources/images/"+ studentImagePath),image.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	return true;
	}

	/*Find all Students*/
	@Override
	public List<Student> listAll() {
		return (List<Student>) studentRepositry.findAll();
	}
	
	/*delete specific student*/
	@Override
	public void deleteStudent(int id) {
	
		Student student=studentRepositry.findOne(id);
		image2=imageRepositry.findOne(id);
		image2.setStudent(student);
		image2.setStudentImagePath(image2.getStudentImagePath());
		studentCountrySportService.deleteStudentSport(student);
		imageRepositry.delete(image2);
		studentRepositry.delete(id);
	}

	/*Find Specific student by ID*/
	@Override
	public Student findById(int id) {
		return studentRepositry.findOne(id);
	}

	@Override
	public List<Student> listByGender(Gender gender) {
		return (List<Student>) studentRepositry.findByGender(gender);
	}

	@Override
	public List<Student> searchByFirstNameOrLastName(String search) {

		return studentRepositry.findByFirstNameLikeOrLastNameOrEmailLike(search, search, search);
	}

	@Override
	public String findStudentImageById(int id) {
		Image image = imageRepositry.findOne(id);
		return image.getStudentImagePath();
	}

	@Override
	public String securityCheck() {

		// If user is login then redirect to user home page
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {

			if(auth.getAuthorities().toString().contains("ROLE_ADMIN"))
				return "redirect:/admin";
		
			else
				return "redirect:/user/list";
		}
		
		return null;
	}

	@Override
	public Boolean emailFinder(String email) {
			
		Student student=  studentRepositry.findByEmail(email);
		if(student != null)
			return false;
		return true;
	}
}