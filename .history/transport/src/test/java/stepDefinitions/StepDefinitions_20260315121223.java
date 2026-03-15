package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import com.example.transport.CargaisonMaritime;
import com.example.transport.Marchandise;

import static org.junit.Assert.*;

public class StepDefinitions {
    private CargaisonMaritime cargo;
    private Marchandise merchandise;
    private boolean addResult;

    @Given("a maritime cargo with a distance of {double}")
    public void aMaritimeCargoWithADistanceOf(double distance) {
        cargo = new CargaisonMaritime(distance);
    }

    @And("a merchandise named {string} with a weight of {double}")
    public void aMerchandiseNamedWithAWeightOf(String name, double weight) {
        merchandise = new Marchandise(name, weight);
    }

    @When("I add the merchandise to the cargo")
    public void iAddTheMerchandiseToTheCargo() {
        addResult = cargo.ajouter(merchandise);
    }

    @Then("the merchandise should be successfully added")
    public void theMerchandiseShouldBeSuccessfullyAdded() {
        assertTrue("Merchandise should be added successfully", addResult);
    }

    @And("the total weight of the cargo should be {double}")
    public void theTotalWeightOfTheCargoShouldBe(double expectedWeight) {
        assertEquals(expectedWeight, cargo.getPoidsTotal(), 0.01);
    }

    @And("the transport cost should be {double}")
    public void theTransportCostShouldBe(double expectedCost) {
        assertEquals(expectedCost, cargo.getCout(), 0.01);
    }
}
