package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.BadRequestException;
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

    @PreAuthorize("hasAnyAuthority('OPS')")
    @GetMapping("/init")
    public CreateFXTokenInitDto initForm(){
        return fxTokenService.fetchInitForm();
    }

    @PreAuthorize("hasAnyAuthority('OPS')")
    @PostMapping("/create")
    public void createFXToken(@RequestBody FXTokenRequestDto token) throws ServerErrorException, BadRequestException {
        fxTokenService.createFXToken(token);
    }

    @PreAuthorize("hasAnyAuthority('OPS')")
    @PostMapping("/close/{tokenCode}")
    public void closeFXToken(@PathVariable String tokenCode) throws ServerErrorException {
        fxTokenService.closeFXToken(tokenCode);
    }

    @PreAuthorize("hasAnyAuthority('CRO', 'OPS', 'MKT', 'CP')")
    @GetMapping("/open-positions/{clientId}")
    public List<ClientOpenPositionResponseDto> fetchClientOpenPosition(@PathVariable String clientId) {
        return fxTokenService.fetchClientOpenPosition(clientId);
    }

    @PreAuthorize("hasAnyAuthority('OPS')")
    @GetMapping("/fetch-all")
    public List<FXTokenResponseDto> fetchAllTokens() {
        return fxTokenService.fetchAllFxTokens(userService.fetchLoginUser());
    }

    @PreAuthorize("hasAnyAuthority('OPS')")
    @GetMapping("/fetch/{tokenCode}")
    public FXTokenResponseDto fetchToken(@PathVariable String tokenCode) {
        return fxTokenService.fetchTokenById(tokenCode);
    }

    @PreAuthorize("hasAnyAuthority('OPS', 'CP')")
    @PostMapping("/spotPrice")
    public void enterSpotPrice(@RequestBody FXTokenDataEntryRequestDto dto) throws ServerErrorException {
        fxTokenService.enterSpotPrice(dto);
    }

    @PreAuthorize("hasAnyAuthority('OPS', 'CP', 'CRO')")
    @GetMapping("/init-spot-price")
    public FxSpotPriceInitDto initSpotPriceForm() {
        return fxTokenService.initSpotPriceForm();
    }

    @PreAuthorize("hasAnyAuthority('OPS', 'CP', 'RISK')")
    @GetMapping("/data-entry-history")
    public List<FXTokenDataEntryResponseDto> fetchDataEntryHistory() {
        return fxTokenService.fetchDataEntryHistory();
    }

    @PreAuthorize("hasAnyAuthority('OPS')")
    @GetMapping("/fetch-matured-knockout")
    public List<FXTokenResponseDto> fetchMaturedOrKnockout() {
        return fxTokenService.fetchMaturedOrKnockout();
    }

    @PreAuthorize("hasAnyAuthority('OPS')")
    @GetMapping("/executeUnavailableChain")
    public void executeUnavailableChain() {
        fxTokenService.executeUnavailableChain();
    }

    @PreAuthorize("hasAnyAuthority('OPS')")
    @GetMapping("/executeUnconfirmedChain")
    public void executeUnconfirmedChain() {
        fxTokenService.executeUnconfirmedChain();
    }

    @PreAuthorize("hasAnyAuthority('OPS', 'CP', 'CRO')")
    @GetMapping("/fetch-all-open")
    public List<FXTokenResponseDto> fetchAllAvailableTokens() {
        return fxTokenService.fetchAvailableFXTokens();
    }
}
