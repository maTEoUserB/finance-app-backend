package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity(name = "budgets")
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
    private AccountEntity userAccount;
    @OneToOne
    @JoinColumn(name = "category_id", nullable = false, unique = true)
    private CategoryEntity category;
    @NotNull
    private double amountLimit;

    public BudgetEntity(@NotNull AccountEntity userAccount, @NotNull CategoryEntity category, @NotNull double amountLimit) {
        this.userAccount = userAccount;
        this.category = category;
        this.amountLimit = amountLimit;
    }
}
