package models.api;

import java.util.List;

public record ExchangeRates(String table, String no, String effectiveDate, List<Rate> rates) {
}
