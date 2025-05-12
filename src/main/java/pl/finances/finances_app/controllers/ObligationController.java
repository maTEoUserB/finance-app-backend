package pl.finances.finances_app.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.finances.finances_app.dto.requestAndResponse.ObligationRequest;
import pl.finances.finances_app.dto.requestAndResponse.ObligationResponse;
import pl.finances.finances_app.dto.requestAndResponse.TransactionRequest;
import pl.finances.finances_app.services.ObligationService;

@Controller
public class ObligationController {
    private final ObligationService obligationService;

    public ObligationController(ObligationService obligationService) {
        this.obligationService = obligationService;
    }

    @PostMapping("/new_obligation")
    ResponseEntity<ObligationResponse> createObligation(@RequestBody @Valid ObligationRequest obligation){
        return obligationService.createNewObligation(obligation);
    }
}
