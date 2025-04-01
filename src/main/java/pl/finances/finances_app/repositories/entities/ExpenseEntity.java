package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private double expenseAmount;
    private String expenseDescription;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ExpenseEntity(double expenseAmount, String expenseDescription) {
        this.expenseAmount = expenseAmount;
        this.expenseDescription = expenseDescription;
    }
}
