package com.springboot.demo.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

public class GeneralUtils {

	@Value("${path}")
	private static String path="resources/images/";
	
	public static void writeFileContentToHttpResponse(final byte[] image, final HttpServletResponse response) throws IOException, SQLException {
		ServletOutputStream out = response.getOutputStream();
		InputStream in = new ByteArrayInputStream(image);
		int lenght = (int)image.length;
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
	
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		while((lenght = in.read(buffer)) != -1) {
			out.write(buffer, 0, lenght);
		}
		in.close();
		out.flush();
		out.close();
	}
	
	public static void emailFinder(Boolean result,HttpServletResponse response) throws IOException {
		
		 ServletOutputStream out=response.getOutputStream();
		 response.setContentType("text/xml");
		 response.setHeader("Cache-Control", "no-cache");
		 if(result)
			 response.getWriter().write("Someone already has that username. Try another?"); 
		 else
			 response.getWriter().write("");
		out.flush();
		out.close();
	}
	
	public static void imageByte(HttpServletResponse response,String image){
			try {
			File file = new File(path+image);
			byte[] imageByte = org.apache.commons.io.FileUtils.readFileToByteArray(file);
		
			GeneralUtils.writeFileContentToHttpResponse(imageByte, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static String updateName(String studentImagePath) {
		String prefix,suffix;
		prefix=studentImagePath.substring(0, studentImagePath.lastIndexOf("."));
		suffix=studentImagePath.substring(5, studentImagePath.length());
		int random=(int)(Math.random()*9000)+1000;
		return(studentImagePath=prefix+random+suffix);
	}
	
	
}
