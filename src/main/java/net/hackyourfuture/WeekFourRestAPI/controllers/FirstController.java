package net.hackyourfuture.WeekFourRestAPI.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
  @GetMapping("/")
  public String sayHello() {
    return "Hello World!";
  }
}