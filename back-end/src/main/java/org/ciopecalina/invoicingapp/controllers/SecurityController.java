package org.ciopecalina.invoicingapp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class SecurityController {
    @GetMapping(value = "/unprotected/whoami")
    public String unprotectedWhoami() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      //  return "You are authenticated as: " + authentication.getName();
        return "user";
    }

    @GetMapping(value = "/protected/whoami")
    public String whoami() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return "You are authenticated as: " + authentication.getName();
        return "user";
    }
}
