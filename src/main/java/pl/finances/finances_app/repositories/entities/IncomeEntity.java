package pl.finances.finances_app.repositories.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private double incomeAmount;
    private String expenseDescription;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public IncomeEntity(double incomeAmount, String expenseDescription) {
        this.incomeAmount = incomeAmount;
        this.expenseDescription = expenseDescription;
    }
}
