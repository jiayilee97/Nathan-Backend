package stacs.nathan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TokenController {

    @CrossOrigin
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return  new ResponseEntity<>("Nathan health check has been successful.", HttpStatus.OK);
    }


}
