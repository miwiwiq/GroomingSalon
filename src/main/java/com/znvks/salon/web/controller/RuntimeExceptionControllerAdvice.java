package com.znvks.salon.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RuntimeExceptionControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public String handleError(Model model, RuntimeException exception){
        log.debug(exception.getMessage(), exception);
        model.addAttribute("exceptionMessage", exception.getMessage());
        return "/error";
    }
}
