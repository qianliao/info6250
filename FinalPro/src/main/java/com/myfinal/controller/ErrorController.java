package com.myfinal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorController {
	
	@RequestMapping(value="/error", method = RequestMethod.GET)
	public ModelAndView initializeForm(HttpServletRequest request) throws Exception {	
		return new ModelAndView("error","errorMessage","NO items for check");
	}
}
