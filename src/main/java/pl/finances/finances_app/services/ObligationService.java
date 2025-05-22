package pl.finances.finances_app.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.dto.NearestObligationsDTO;
import pl.finances.finances_app.dto.requestAndResponse.ObligationRequest;
import pl.finances.finances_app.dto.requestAndResponse.ObligationResponse;
import pl.finances.finances_app.repositories.ObligationRepository;
import pl.finances.finances_app.repositories.entities.AccountEntity;
import pl.finances.finances_app.repositories.entities.CategoryEntity;
import pl.finances.finances_app.repositories.entities.ObligationEntity;
import java.net.URI;
import java.util.List;

@Service
@Transactional
public class ObligationService {
    private final UserService userService;
    private final ObligationRepository obligationRepository;
    private final CategoryService categoryService;

    @Autowired
    public ObligationService(UserService userService, ObligationRepository obligationRepository, CategoryService categoryService) {
        this.userService = userService;
        this.obligationRepository = obligationRepository;
        this.categoryService = categoryService;
    }

    public List<NearestObligationsDTO> getNearestObligations(long id) {
        return obligationRepository.getNearest2Obligations(id);
    }

    public ResponseEntity<ObligationResponse> createNewObligation(Jwt jwt, ObligationRequest obligation) {
        String username = jwt.getClaimAsString("preferred_username");
        AccountEntity userAccount = userService.getOrCreateUserAccount(username);
        CategoryEntity category = categoryService.findCategoryById(obligation.categoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found."));

        ObligationEntity newObligation = new ObligationEntity(userAccount, obligation.title(), obligation.amount(),
                obligation.dateToPay(), category);
        obligationRepository.save(newObligation);

        ObligationResponse response = new ObligationResponse(newObligation.getObligationTitle(), newObligation.getObligationAmount(),
                newObligation.getDateToPay(), category.getId());

        return ResponseEntity.created(URI.create("/new/obligation/" + newObligation.getId())).body(response);
    }
}
