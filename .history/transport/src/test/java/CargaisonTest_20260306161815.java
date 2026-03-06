import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.transport.CargaisonAerienne;
import com.example.transport.CargaisonMaritime;
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
        Marchandise lourde = new Marchandise("Sable", 39000.0);

        boolean aEteAjoute = camion.ajouter(lourde);

        assertFalse(aEteAjoute, "La marchandise trop lourde n'aurait pas dû être acceptée");
        assertEquals(0, camion.getListeMarchandises().size(),  "La liste devrait être vide");
    }

    @Test
    @DisplayName("Le poids total doit être la somme des marchandises ajoutées")
    void testPoidsTotal() {
        CargaisonMaritime bateau = new CargaisonMaritime(2000);
        bateau.ajouter(new Marchandise("Auto", 1500));
        bateau.ajouter(new Marchandise("Moto", 300));
        
        assertEquals(1800.0, bateau.getPoidsTotal(), "Le cumul des poids est faux");
    }

    @Test
    @DisplayName("Vérifier que la méthode getPoids() retourne le poids exact d’une marchandise ;")
    void testgetPoids(){
        Marchandise legumes = new Marchandise("Tomate", 100.0);
        
        
        double p = legumes.getPoids();

        assertEquals(100.0, p, "le poids est la");
    }

    @Test
    @DisplayName("Vérifier que le coût du transport d’une cargaison maritime est correctement calculé selon la formule : 1 × distance × poids total ;")
    void testCalculCoutMaritime(){
        CargaisonMaritime bateau = new CargaisonMaritime(100);
        Marchandise auto = new Marchandise("BMW", 500.0);
        bateau.ajouter(auto);

        assertEquals(50000.0, bateau.getCout());
    }
    @Test
    @DisplayName("Vérifier que le coût du transport d’une cargaison routière est correctement calculé selon la formule : 4 × distance × poids total ;")
    void testCalculCoutRoutiere(){
        CargaisonRoutiere camions = new CargaisonRoutiere(20.0);
        camions.ajouter(new Marchandise("Oignon", 1200));

        assertEquals(96000, camions.getCout());
    }
}
