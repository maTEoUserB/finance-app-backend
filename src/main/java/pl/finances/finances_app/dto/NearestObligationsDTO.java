package pl.finances.finances_app.dto;

import java.sql.Date;

public record NearestObligationsDTO(String obligationTitle, Date dateToPay, double obligationAmount) {
}
