package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity(name = "savings_goals")
@Getter
@Setter
@NoArgsConstructor
public class SavingsGoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String goalTitle;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountEntity userAccount;
    private String goalDescription;
    @NotNull
    private double currentAmount;
    @NotNull
    private double finalAmmount;
    @NotNull
    private boolean isDone;
    @NotNull
    private LocalDate goalDeadline;

    public SavingsGoalEntity(@NotNull String goalTitle, @NotNull AccountEntity userAccount, String goalDescription, @NotNull double currentAmount, @NotNull double finalAmount, @NotNull LocalDate goalDeadline) {
        this.goalTitle = goalTitle;
        this.userAccount = userAccount;
        this.goalDescription = goalDescription;
        this.currentAmount = currentAmount;
        this.finalAmmount = finalAmount;
        this.isDone = false;
        this.goalDeadline = goalDeadline;
    }
}
