package de.cqrity.vulnerapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CorsDemoController {

    @PostMapping("/cors/demo")
    @ResponseStatus(HttpStatus.OK)
    public void corsDemoStandardPost() {
    }

    @GetMapping("/cors/demo")
    @ResponseStatus(HttpStatus.OK)
    public void corsDemoGet() {
    }

    @DeleteMapping("/cors/demo")
    @ResponseStatus(HttpStatus.OK)
    public void corsDemoDelete() {
    }

}
