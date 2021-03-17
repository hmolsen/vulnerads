package de.cqrity.vulnerapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorHandlerController implements ErrorController{

	Logger logger = LoggerFactory.getLogger(ErrorHandlerController.class);

	
	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		/* Logging not necessary since Springboot automatically logs uncaught exceptions
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (throwable != null) {
			logger.error("Uncaught exception", throwable);
		}
		*/
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("statusCode", statusCode);
		return modelAndView;
	}

	// required for implementing ErrorController but not used
	@Override
	public String getErrorPath() {
		return null;
	}
}
