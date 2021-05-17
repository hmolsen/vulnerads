package de.cqrity.vulnerapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller							// Implementiert den ErrorController von Spring Boot, welcher alle Requests mit ungefangenen Exceptions empfängt.
public class ErrorHandlerController implements ErrorController {

	// RequestMapping sorgt dafür, dass Requests an die URL "/error" von dieser Methode bearbeitet werden.
	// "/error" ist dabei die Seite, die von Spring Boot intern bei einer ungefangenen Exception angesteuert wird.
	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code"); // holt den Statuscode, falls vorhanden, aus der Request, zB. 404 oder 500
		ModelAndView modelAndView = new ModelAndView("error_page");								// die error_page.jsp Datei wird zur Anzeige der neuen Errorpage genutzt
		modelAndView.addObject("statusCode", statusCode);										// error_page.jsp empfängt den Statuscode und zeigt ihn auf der Seite an.
		return modelAndView;
	}

	
	// Bitte einkommentieren, aber ansonsten ignorieren. Wird für die Implementierung von ErrorController benötigt, aber nicht zum Anzeigen einer Errorpage verwendet.
	@Override
	public String getErrorPath() {
		return null;
	} 
	
}
