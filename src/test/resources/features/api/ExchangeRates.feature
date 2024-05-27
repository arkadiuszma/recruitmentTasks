Feature: NBP API Test

  Scenario: GET exchange rates
    Given Set base url of NBP API
    When Sent GET request to: 'exchangerates/tables/A' and check status is: 200
    And Convert response to rates objects list
    Then Show rate for code: 'USD'
    And Show rate for currency: 'dolar amerykański'
    And Show codes with exchange rate higher than: 5
    And Show codes with exchange rate lower than: 3