package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SavingsGoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String title;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String description;
    @NotNull
    private double currentAmount;
    @NotNull
    private double finalAmmount;
    @NotNull
    private boolean done;
    private LocalDate deadline;

    public SavingsGoalEntity(@NotNull String title, @NotNull UserEntity user, String description, @NotNull double currentAmount, @NotNull double finalAmmount, LocalDate deadline) {
        this.title = title;
        this.user = user;
        this.description = description;
        this.currentAmount = currentAmount;
        this.finalAmmount = finalAmmount;
        this.done = false;
        this.deadline = deadline; //jeśli uzytkownik nie chce podac deadline to null trzeba dac z frontu
    }
}
