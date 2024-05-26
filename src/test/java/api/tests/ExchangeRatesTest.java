package api.tests;

import api.base.BaseAPITest;
import io.restassured.response.Response;
import logging.Log;
import lombok.SneakyThrows;
import models.api.ExchangeRates;
import models.api.Rate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeRatesTest extends BaseAPITest {
    private static final String EXCHANGE_RATES_TABLE_A = "exchangerates/tables/A";
    private static List<Rate> ratesList;
    private Rate rate;
    private List<String> filteredCodesList;

    @SneakyThrows
    @BeforeAll
    public static void shouldShowExchangesWithUSDCode() {
        Response response = get(EXCHANGE_RATES_TABLE_A);
        ExchangeRates exchangeRates = new ObjectMapper().readValue(response.asString(), ExchangeRates[].class)[0];
        ratesList = exchangeRates.rates();
    }

    @ParameterizedTest
    @CsvSource({"USD"})
    public void shouldShowRateForGivenCode(String code) {
        rate = ratesList.stream()
                .filter(rate -> code.equals(rate.code()))
                .findFirst()
                .orElse(null);

        assertThat(rate).isNotNull();

        Log.info("Exchange rate for code: '" + code + "' is: '" + rate.mid() + "'");
    }

    @ParameterizedTest
    @CsvSource({"dolar amerykański"})
    public void shouldShowRateForGivenCurrency(String currency) {
        rate = ratesList.stream()
                .filter(rate -> currency.equals(rate.currency()))
                .findFirst()
                .orElse(null);

        assertThat(rate).isNotNull();

        Log.info("Exchange rate for name: '" + currency + "' is: '" + rate.mid() + "'");
    }

    @ParameterizedTest
    @CsvSource({"5"})
    public void shouldShowCodesWithExchangeRateHigherThanGiven(Double value) {
        filteredCodesList = ratesList.stream()
                .filter(rate -> rate.mid() - value > 0)
                .map(Rate::code)
                .toList();

        assertThat(filteredCodesList.size()).isGreaterThan(0);

        if (filteredCodesList.size() == 1) Log.info("Code with exchange rate higher than: '" + value + "' is: '" + filteredCodesList.get(0) + "'");
        else Log.info("Codes with exchanges rates higher than: '" + value + "' are: '" + String.join(", ", filteredCodesList) + "'");
    }

    @ParameterizedTest
    @CsvSource({"3"})
    public void shouldShowCodesWithExchangeRateLowerThanGiven(Double value) {
        filteredCodesList = ratesList.stream()
                .filter(rate -> rate.mid() - value < 0)
                .map(Rate::code)
                .toList();

        assertThat(filteredCodesList.size()).isGreaterThan(0);

        if (filteredCodesList.size() == 1) Log.info("Code with exchange rate lower than: '" + value + "' is: '" + filteredCodesList.get(0) + "'");
        else Log.info("Codes with exchanges rates lower than: '" + value + "' are: '" + String.join(", ", filteredCodesList) + "'");
    }
}
