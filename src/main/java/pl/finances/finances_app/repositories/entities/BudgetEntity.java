package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BudgetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToOne
    @JoinColumn(name = "category_id", nullable = false, unique = true)
    private CategoryEntity category;
    @NotNull
    private double amountLimit;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    public BudgetEntity(@NotNull UserEntity user, @NotNull double amountLimit, @NotNull LocalDate startDate, @NotNull LocalDate endDate) {
        this.user = user;
        this.amountLimit = amountLimit;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
