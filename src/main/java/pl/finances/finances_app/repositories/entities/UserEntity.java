package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private double saldo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<IncomeEntity> incomes;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<ExpenseEntity> expenses;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    void setId(int id) {
        this.id = id;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    void setPassword(String password) {
        this.password = password;
    }

    void setIncomes(Set<IncomeEntity> incomes) {this.incomes = incomes;}
    void setExpenses(Set<ExpenseEntity> expenses) {this.expenses = expenses;}
}
