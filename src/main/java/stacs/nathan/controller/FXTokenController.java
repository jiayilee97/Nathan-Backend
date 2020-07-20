package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenDataEntryRequestDto;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.response.*;
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

    @PreAuthorize("hasAuthority('OPS')")
    @GetMapping("/init")
    public CreateFXTokenInitDto initForm(){
        return fxTokenService.fetchInitForm();
    }

    @PreAuthorize("hasAuthority('OPS')")
    @PostMapping("/create")
    public void createFXToken(@RequestBody FXTokenRequestDto token) throws ServerErrorException {
        fxTokenService.createFXToken(token);
    }

    @PreAuthorize("hasAuthority('OPS')")
    @PostMapping("/close/{tokenCode}")
    public void closeFXToken(@PathVariable String tokenCode) throws ServerErrorException {
        fxTokenService.closeFXToken(tokenCode);
    }

    @PreAuthorize("hasAuthority('CRO') or hasAuthority('OPS') or hasAuthority('MKT') or hasAuthority('CP')")
    @GetMapping("/open-positions/{clientId}")
    public List<ClientOpenPositionResponseDto> fetchClientOpenPosition(@PathVariable String clientId) {
        return fxTokenService.fetchClientOpenPosition(clientId);
    }

    @PreAuthorize("hasAuthority('OPS')")
    @GetMapping("/fetch-all")
    public List<FXTokenResponseDto> fetchAllTokens() {
        return fxTokenService.fetchAllFxTokens(userService.fetchLoginUser());
    }

    @PreAuthorize("hasAuthority('OPS')")
    @GetMapping("/fetch/{tokenCode}")
    public FXTokenResponseDto fetchToken(@PathVariable String tokenCode) {
        return fxTokenService.fetchTokenById(tokenCode);
    }

    @PreAuthorize("hasAuthority('OPS') or hasAuthority('CP')")
    @PostMapping("/spotPrice")
    public void enterSpotPrice(@RequestBody FXTokenDataEntryRequestDto dto) throws ServerErrorException {
        fxTokenService.enterSpotPrice(dto);
    }

    @PreAuthorize("hasAuthority('OPS') or hasAuthority('CP')")
    @GetMapping("/fetch-all-open")
    public List<FXTokenResponseDto> fetchAllOpenTokens() {
        return fxTokenService.fetchAvailableFXTokens();
    }

    @PreAuthorize("hasAuthority('OPS') or hasAuthority('CP')")
    @GetMapping("/data-entry-history")
    public List<FXTokenDataEntryResponseDto> fetchDataEntryHistory() {
        return fxTokenService.fetchDataEntryHistory();
    }

    @PreAuthorize("hasAuthority('OPS')")
    @GetMapping("/fetch-matured-knockout")
    public List<FXTokenResponseDto> fetchMaturedOrKnockout() {
        return fxTokenService.fetchMaturedOrKnockout();
    }
}
