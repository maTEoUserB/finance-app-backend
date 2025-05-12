package pl.finances.finances_app.dto;

import java.time.LocalDate;

public interface NearestObligationsDTO {
    String getObligationTitle();
    boolean getIsDone();
    LocalDate getDateToPay();
    double getObligationAmount();
}
