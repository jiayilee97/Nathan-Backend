package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenDataEntryRequestDto;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.response.ClientOpenPositionResponseDto;
import stacs.nathan.dto.response.FXTokenDataEntryResponseDto;
import stacs.nathan.dto.response.FXTokenResponseDto;
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

    @GetMapping("/open-positions/{issuerId}")
    public List<ClientOpenPositionResponseDto> fetchClientOpenPosition(@PathVariable String issuerId) {
        return fxTokenService.fetchClientOpenPosition(issuerId);
    }

    @GetMapping("/fetch-all")
    public List<FXTokenResponseDto> fetchAllTokens() {
        return fxTokenService.fetchAllFxTokens(userService.fetchLoginUser());
    }

    @GetMapping("/fetch/{tokenCode}")
    public FXTokenResponseDto fetchToken(@PathVariable String tokenCode) {
        return fxTokenService.fetchTokenById(tokenCode);
    }

    @PostMapping("/spotPrice")
    public void enterSpotPrice(@RequestBody FXTokenDataEntryRequestDto dto) throws ServerErrorException {
        fxTokenService.enterSpotPrice(dto);
    }

    @GetMapping("/fetch-all-open")
    public List<FXTokenResponseDto> fetchAllOpenTokens() {
        return fxTokenService.fetchAvailableFXTokens();
    }

    @GetMapping("/data-entry-history")
    public List<FXTokenDataEntryResponseDto> fetchDataEntryHistory() {
        return fxTokenService.fetchDataEntryHistory();
    }

}
