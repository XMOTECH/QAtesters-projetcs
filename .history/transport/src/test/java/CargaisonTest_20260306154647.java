import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.transport.CargaisonAerienne;
import com.example.transport.CargaisonRoutiere;
import com.example.transport.Marchandise;

public class CargaisonTest {
    @Test
    @DisplayName("Le coût du transport aérien doit être : 10 * distance * poids")
    void testCalculCoutAerien() {
        CargaisonAerienne avion = new CargaisonAerienne(100);
        avion.ajouter(new Marchandise("iphone", 50));

        assertEquals(50000.0, avion.getCout(), "Le calcul du coût aérien est incorrect");
    }

    @Test
    @DisplayName("On ne doit pas pouvoir dépasser la limite de poids (Routière: 38000)")
    void testLimitePoidsRoutiere(){
        CargaisonRoutiere camion = new CargaisonRoutiere(500);
        Marchandise lourde = new Marchandise("Sable", 100);

        boolean aEteAjoute = camion.ajouter(lourde);

        assertFalse(aEteAjoute, "La marchandise trop lourde n'aurait pas dû être acceptée");
        assertEquals(0, camion.getListeMarchandises().size(),  "La liste devrait être vide");
    }
}
