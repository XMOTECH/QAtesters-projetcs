import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.transport.CargaisonAerienne;
import com.example.transport.Marchandise;

public class CargaisonTest {
    @Test
    @DisplayName("Le coût du transport aérien doit être : 10 * distance * poids")
    void testCalculCoutAerien() {
        CargaisonAerienne avion = new CargaisonAerienne(100);
        avion.ajouter(new Marchandise("iphone", 50));

        assertEquals(50000.0, avion.getCout(), "Le calcul du coût aérien est incorrect");
    }
}
