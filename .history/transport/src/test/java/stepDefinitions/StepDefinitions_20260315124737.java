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

    @Given("un cargo maritime avec une distance de {double}")
    public void unCargoMaritimeAvecUneDistanceDe(double distance) {
        cargo = new CargaisonMaritime(distance);
    }

    @And("une marchandise nommee {string} avec un poids de {double}")
    public void uneMarchandiseNommeeAvecUnPoidsDe(String name, double weight) {
        merchandise = new Marchandise(name, weight);
    }

    @When("j'ajoute la marchandise au cargo")
    public void jAjouteLaMarchandiseAuCargo() {
        addResult = cargo.ajouter(merchandise);
    }

    @Then("la marchandise devrait etre ajoutee avec succes")
    public void laMarchandiseDevraitEtreAjouteeAvecSucces() {
        assertTrue("La marchandise devrait etre ajoutee avec succes", addResult);
    }

    @And("le poids total du cargo devrait etre {double}")
    public void lePoidsTotalDuCargoDevraitEtre(double expectedWeight) {
        assertEquals(expectedWeight, cargo.getPoidsTotal(), 0.01);
    }

    @And("le cout du transport devrait etre {double}")
    public void leCoutDuTransportDevraitEtre(double expectedCost) {
        assertEquals(expectedCost, cargo.getCout(), 0.01);
    }
}
