import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.assurance.Client;
import com.example.assurance.CompagnieAssurance;

public class CompagnieAssuranceTest {
    private CompagnieAssurance compagnie ;

    @BeforeEach
    void setup(){
        compagnie = new CompagnieAssurance();
    }

    @Test
    @DisplayName("1.Créer un client avec des informations valides.")
    public void testEnregistrerClientValide(){
        String Id = "C1";
        Client client = compagnie.enregistrerClient(Id, "Mousaa", "oussa@gmail.com", "773125677");
        assertNotNull(client);
        assertEquals(Id, client.getIdentifiant());
    }
}
