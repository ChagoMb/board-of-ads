package com.avito.controllers.simple;

import com.avito.configs.security.AuthProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public String users() { return "users"; }

}
