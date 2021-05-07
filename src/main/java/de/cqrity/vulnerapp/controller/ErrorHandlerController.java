package de.cqrity.vulnerapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorHandlerController /*implements ErrorController*/ {
	
	public ModelAndView handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		ModelAndView modelAndView = new ModelAndView("error_page");
		modelAndView.addObject("statusCode", statusCode);
		return modelAndView;
	}

	/*
	// ignore this, required for implementing ErrorController but not used
	@Override
	public String getErrorPath() {
		return null;
	} 
	*/
}
