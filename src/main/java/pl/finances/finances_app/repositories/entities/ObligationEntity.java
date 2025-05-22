package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity(name = "obligations")
@Getter
@Setter
@NoArgsConstructor
public class ObligationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String obligationTitle;
    @NotNull
    private double obligationAmount;
    @NotNull
    private boolean isDone;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountEntity userAccount;
    @NotNull
    private LocalDate dateToPay;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;


    public ObligationEntity(@NotNull AccountEntity userAccount, @NotNull String obligationTitle, @NotNull double obligationAmount,
                            @NotNull LocalDate dateToPay, @NotNull CategoryEntity category) {
        this.userAccount = userAccount;
        this.obligationTitle = obligationTitle;
        this.obligationAmount = obligationAmount;
        this.dateToPay = dateToPay;
        this.category = category;
        this.isDone = false;
    }
}
