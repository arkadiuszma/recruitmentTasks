package api.step_definitions;

import config.ConfigProperties;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import api.models.ExchangeRates;
import api.models.Rate;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;

import java.util.List;

import static api.helpers.ApiHelpers.get;
import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeRatesStepDef {
    private List<Rate> ratesList;
    private Rate rate;
    private List<String> filteredCodesList;
    private Response response;

    @Before
    public void shouldShowExchangesWithUSDCode() {
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.STATUS));
    }

    @Given("Set base url of NBP API")
    public void setBaseUrl() {
        RestAssured.baseURI = ConfigProperties.baseAPIUrl;
    }

    @When("Sent GET request to: {string} and check status is: 200")
    public void sentGetRequest(String endpoint) {
        response = get(endpoint);
    }

    @SneakyThrows
    @When("Convert response to rates objects list")
    public void convertResponse() {
        ExchangeRates exchangeRates = new ObjectMapper().readValue(response.asString(), ExchangeRates[].class)[0];
        ratesList = exchangeRates.rates();
    }

    @Then("Show rate for code: {string}")
    public void shouldShowRateForGivenCode(String code) {
        rate = ratesList.stream()
                .filter(rate -> code.equals(rate.code()))
                .findFirst()
                .orElse(null);

        assertThat(rate).isNotNull();

        System.out.println("Exchange rate for code: '" + code + "' is: '" + rate.mid() + "'");
    }

    @Then("Show rate for currency: {string}")
    public void shouldShowRateForGivenCurrency(String currency) {
        rate = ratesList.stream()
                .filter(rate -> currency.equals(rate.currency()))
                .findFirst()
                .orElse(null);

        assertThat(rate).isNotNull();

        System.out.println("Exchange rate for name: '" + currency + "' is: '" + rate.mid() + "'");
    }

    @Then("Show codes with exchange rate higher than: {double}")
    public void shouldShowCodesWithExchangeRateHigherThanGiven(double value) {
        filteredCodesList = ratesList.stream()
                .filter(rate -> rate.mid() - value > 0)
                .map(Rate::code)
                .toList();

        assertThat(filteredCodesList.size()).isGreaterThan(0);

        if (filteredCodesList.size() == 1) System.out.println("Code with exchange rate higher than: '" + value + "' is: '" + filteredCodesList.get(0) + "'");
        else System.out.println("Codes with exchanges rates higher than: '" + value + "' are: '" + String.join(", ", filteredCodesList) + "'");
    }

    @Then("Show codes with exchange rate lower than: {double}")
    public void shouldShowCodesWithExchangeRateLowerThanGiven(double value) {
        filteredCodesList = ratesList.stream()
                .filter(rate -> rate.mid() - value < 0)
                .map(Rate::code)
                .toList();

        assertThat(filteredCodesList.size()).isGreaterThan(0);

        if (filteredCodesList.size() == 1) System.out.println("Code with exchange rate lower than: '" + value + "' is: '" + filteredCodesList.get(0) + "'");
        else System.out.println("Codes with exchanges rates lower than: '" + value + "' are: '" + String.join(", ", filteredCodesList) + "'");
    }
}
