package com.natli.springboot.template;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
	
	@RequestMapping("/courses")
	public List<Course> retreiveAllCourses(){
		return Arrays.asList(
				new Course(1,"AWS","in28minutes"),
				new Course(2,"React","in28minutes"),
				new Course(3,"Azure","in28minutes")
				
		);
	}
}
