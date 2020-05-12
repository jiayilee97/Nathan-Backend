package stacs.nathan.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @CrossOrigin
    @GetMapping("/health/nathan")
    public String healthCheck() {
//        throw new IllegalArgumentException();
        return  "Nathan health check has been successful.";
    }


}
