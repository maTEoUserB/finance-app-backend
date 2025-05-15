package pl.finances.finances_app.repositories.entities;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String categoryName;
    @NotNull
    private String typeForCategory; // income/expense/obligation
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<TransactionEntity> transactions;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<ObligationEntity> obligations;
    @OneToOne(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BudgetEntity budget;

    public CategoryEntity(@NotNull String categoryName, @NotNull String typeForCategory) {
        this.categoryName = categoryName;
        this.typeForCategory = typeForCategory;
    }
}
