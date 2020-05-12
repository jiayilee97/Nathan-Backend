package stacs.nathan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.context.RequestContext;
import stacs.nathan.entity.User;
import stacs.nathan.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RequestContext requestContext;

    @Autowired
    private UserService userService;

    @GetMapping("/fetch/clients")
    public List<User> fetchAllClients(){
        LOGGER.info("Entering fetchAllClients().");
        return userService.fetchAllClients();
    }
}
