Feature: Maritime Cargo Management

  Scenario: Add merchandise with weight below the maximum limit
    Given a maritime cargo with a distance of 150.0
    And a merchandise named "Toy Boats" with a weight of 250000.0
    When I add the merchandise to the cargo
    Then the merchandise should be successfully added
    And the total weight of the cargo should be 250000.0
    And the transport cost should be 37500000.0
