import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.transport.CargaisonMaritime;
import com.example.transport.Marchandise;


public class StepDefinitions {
    private CargaisonMaritime maritime;
    private Marchandise marchandise;
    private boolean additionResult;

    @Given("a maritime cargo with a distance of {double}")
    public void a_maritime_cargo_with_a_distance_of(Double distance) {
        // Uses your CargaisonMaritime constructor (default limit 300,000)
        maritime = new CargaisonMaritime(distance);
    }
    @And("a merchandise named {string} with a weight of {double}")
    public void a_merchandise_named_with_a_weight_of(String name, Double weight) {
        marchandise = new Marchandise(name, weight);
    }
    @When("I add the merchandise to the cargo")
    public void i_add_the_merchandise_to_the_cargo() {
        additionResult = maritime.ajouter(marchandise);
    }
    @Then("the merchandise should be successfully added")
    public void the_merchandise_should_be_successfully_added() {
        assertTrue(additionResult, "The merchandise should have been accepted");
    }

    @And("the total weight of the cargo should be {double}")
    public void the_total_weight_should_be(Double expectedWeight) {
        assertEquals(expectedWeight, maritime.getPoidsTotal(), 0.001);
    }

    @And("the transport cost should be {double}")
    public void the_transport_cost_should_be(Double expectedCost) {
        // Calls your getCout() method
        assertEquals(expectedCost, maritime.getCout(), 0.001);
    }
}
