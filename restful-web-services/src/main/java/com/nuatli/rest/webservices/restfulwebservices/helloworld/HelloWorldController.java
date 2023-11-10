package com.nuatli.rest.webservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//REST API
@RestController
public class HelloWorldController {
	// /hello-world
	//@RequestMapping(method=RequestMethod.GET, path = "/hello-world")
	
	
	@Autowired
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World Nazım utku ATLI";
	}
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World Nazım utku ATLI");
	}
	
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World %s",name)) ;
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternationalized() {
		
		Locale locale = LocaleContextHolder.getLocale();
		//return messageSource.getMessage("goog.morning.message",null,"Default asd",locale);
		return messageSource.getMessage("good.morning.message",null,locale);
	}
	
	
	
	
}
	