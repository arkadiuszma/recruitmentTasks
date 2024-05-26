package api.models;

import java.util.List;

public record ExchangeRates(String table, String no, String effectiveDate, List<Rate> rates) {
}
