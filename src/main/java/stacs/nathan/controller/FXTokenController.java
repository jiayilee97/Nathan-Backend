package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
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

    @GetMapping("/fetch-available-sp")
    public List<SPTokenResponseDto> fetchAvailableTokens() throws ServerErrorException {
        return fxTokenService.fetchAvailableTokens(userService.fetchLoginUser());
    }

    @PostMapping("/create")
    public void createFXToken(@RequestBody FXTokenRequestDto token) throws ServerErrorException {
        fxTokenService.createFXToken(token);
    }

    @PostMapping("/close/{tokenCode}")
    public void closeFXToken(@PathVariable String tokenCode) throws ServerErrorException {
        fxTokenService.closeFXToken(tokenCode);
    }

}
