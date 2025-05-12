package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private LocalDate createdAt;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private double saldo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<TransactionEntity> transactions;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<ObligationEntity> obligations;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<BudgetEntity> budgets;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<SavingsGoalEntity> savingsGoals;

    public UserEntity(@NotNull String username,@NotNull double saldo,@NotNull String password,@NotNull String role) {
        this.username = username;
        this.saldo = saldo;
        this.password = password;
        this.role = role;
        createdAt = LocalDate.now();
    }

    @PrePersist
    void prePersist(){createdAt = LocalDate.now();}

    void setUsername(String username) {
        this.username = username;
    }

    void setPassword(String password) {
        this.password = password;
    }
}
