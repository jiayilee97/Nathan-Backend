package stacs.nathan.controller;

import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.FXToken;
import stacs.nathan.repository.FXTokenRepository;
import stacs.nathan.service.FXTokenService;
import stacs.nathan.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/fxtoken")
public class FXTokenController {

    @Autowired
    private FXTokenService fxTokenService;

    @Autowired
    private UserService userService;

    @GetMapping("/fetch")
    public List<SPTokenResponseDto> fetchAvailableTokens() throws ServerErrorException {
        return fxTokenService.fetchAvailableTokens(userService.fetchLoginUser());
    }

    @PostMapping("/create")
    public void createFXToken(@RequestBody FXTokenRequestDto token) throws ServerErrorException {
        fxTokenService.createFXToken(token);
    }

}
