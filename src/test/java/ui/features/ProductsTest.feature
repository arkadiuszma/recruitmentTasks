Feature: Products Test

  Scenario: Should choose product to basket
    Given User open T-Mobile home page
    When User choose devices option and click smartwatches and bands without subscription
    And User choose product from list with index: 1
    And User click add to basket button
    And User go back to home page
    Then Verify product on each page had same parameters: name, monthly cost, start price
    And Verify number of product shown on basket icon is: 1