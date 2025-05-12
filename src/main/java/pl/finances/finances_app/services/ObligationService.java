package pl.finances.finances_app.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.dto.NearestObligationsDTO;
import pl.finances.finances_app.dto.requestAndResponse.ObligationRequest;
import pl.finances.finances_app.dto.requestAndResponse.ObligationResponse;
import pl.finances.finances_app.repositories.ObligationRepository;
import pl.finances.finances_app.repositories.entities.CategoryEntity;
import pl.finances.finances_app.repositories.entities.ObligationEntity;
import pl.finances.finances_app.repositories.entities.UserEntity;

import java.net.URI;
import java.util.List;

@Service
public class ObligationService {
    private final ObligationRepository obligationRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public ObligationService(ObligationRepository obligationRepository, UserService userService, CategoryService categoryService) {
        this.obligationRepository = obligationRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    public List<NearestObligationsDTO> getNearestObligations(long id) {
        return obligationRepository.getNearest2Obligations(id);
    }

    @Transactional
    public ResponseEntity<ObligationResponse> createNewObligation(ObligationRequest obligation) {
        UserEntity user = userService.findUserByUsername("user1").orElseThrow(() -> new EntityNotFoundException("User not found."));
        CategoryEntity category = categoryService.findCategoryById(obligation.categoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found."));

        ObligationEntity newObligation = new ObligationEntity(user, obligation.title(), obligation.amount(),
                obligation.dateToPay(), category);
        obligationRepository.save(newObligation);

        ObligationResponse response = new ObligationResponse(newObligation.getTitle(), newObligation.getAmount(),
                newObligation.getDateToPay(), category.getId());

        return ResponseEntity.created(URI.create("/new_obligation/" + newObligation.getId())).body(response);
    }
}
