package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ObligationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String title;
    @NotNull
    private double amount;
    @NotNull
    private boolean isDone;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @NotNull
    private LocalDate dateToPay;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;


    public ObligationEntity(@NotNull UserEntity user, @NotNull String title, @NotNull double amount, @NotNull LocalDate dateToPay, @NotNull CategoryEntity category) {
        this.user = user;
        this.title = title;
        this.amount = amount;
        this.dateToPay = dateToPay;
        this.category = category;
        this.isDone = false;
    }
}
