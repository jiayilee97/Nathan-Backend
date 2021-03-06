package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.SPTokenRequestDto;
import stacs.nathan.dto.response.CreateSPTokenInitDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.service.SPTokenService;
import stacs.nathan.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/sptoken")
@PreAuthorize("hasAnyAuthority('OPS')")
public class SPTokenController {

    @Autowired
    private SPTokenService spTokenService;

    @Autowired
    private UserService userService;

    @GetMapping("/init")
    public CreateSPTokenInitDto initForm(){
        return spTokenService.fetchInitForm();
    }

    @PostMapping("/create")
    public void createSPToken(@RequestBody SPTokenRequestDto token) throws ServerErrorException {
        spTokenService.createSPToken(token);
    }

    @GetMapping("/fetch-all")
    public List<SPTokenResponseDto> fetchAllSPTokens() throws ServerErrorException {
        return spTokenService.fetchAllTokens();
    }

    @GetMapping("/fetch/{tokenCode}")
    public SPTokenResponseDto fetchSPTokenByTokenCode(@PathVariable String tokenCode) throws ServerErrorException {
        return spTokenService.fetchByTokenCode(tokenCode);
    }

//    @PostMapping("/transfer/{tokenCode}")
//    public void transferSPToken(@PathVariable String tokenCode) throws ServerErrorException {
//        spTokenService.transferToBurnAddress(tokenCode);
//    }

    @GetMapping("/executeUnavailableChain")
    public void executeUnavailableChain() {
        spTokenService.executeUnavailableChain();
    }

    @GetMapping("/executeUnconfirmedChain")
    public void executeUnconfirmedChain() {
        spTokenService.executeUnconfirmedChain();
    }

    @GetMapping("/executeSPTokenMaturity")
    public void executeSPTokenMaturity() throws ServerErrorException {
        spTokenService.checkSPTokenMaturity();
    }
}
