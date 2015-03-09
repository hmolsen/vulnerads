package de.cqrity.vulnerapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class CorsDemoController {

    @RequestMapping(value = "/cors/demo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void corsDemo() {
    }
}
