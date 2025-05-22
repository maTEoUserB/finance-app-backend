package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity(name = "accounts")
@Getter
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private LocalDate createdAt;
    @Column(nullable = false)
    private String role;
    @Setter
    @Column(nullable = false)
    private double saldo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<TransactionEntity> transactions;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<ObligationEntity> obligations;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<SavingsGoalEntity> savingsGoals;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<BudgetEntity> budgets;

    public AccountEntity(@NotNull String username,@NotNull double saldo, @NotNull String role) {
        this.username = username;
        this.saldo = saldo;
        this.role = role;
        createdAt = LocalDate.now();
    }

    @PrePersist
    void prePersist(){createdAt = LocalDate.now();}

}
