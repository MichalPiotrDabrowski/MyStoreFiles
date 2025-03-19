Feature: Purchase of 5 pieces of sweater

  Scenario: Logging in, and purchase of 5 pieces of sweaters
    Given user has active account in MyStore web store
    When user signs in with mail "misiektest@gmail.com" and password "misiektest123!"
    And user chooses Hummingbird Printed Sweater
    And user chooses size M
    And user selects "5" pieces
    And adds products to cart
    And user goes to Checkout
    And user confirms address
    And user chooses pick up method Pick up in store
    And user chooses payment method Pay by check and clicks Place Order
    Then user takes a screenshot of order confirmation and payment amount
