import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @DisplayName("Vérifier qu’une marchandise peut être ajoutée à une cargaison maritime si son poids respecte la limite ;")
    void testLimitePoids(){
        CargaisonMaritime terrain = new CargaisonMaritime(300000);
        
        
        assertTrue(terrain.ajouter(new Marchandise("Zircon", 300000)));

    }

    @Test
    @DisplayName("Vérifier que la méthode getPoidsTotal() retourne la somme correcte des poids des marchandises ajoutées à une cargaison ;")
    void testgetPoidTotal(){
        CargaisonRoutiere r1 = new CargaisonRoutiere(100);

        Marchandise m1 = new Marchandise("oignon", 100);
        Marchandise m2 = new Marchandise("niebe", 1000);

        r1.ajouter(m2);
        r1.ajouter(m1);

        assertEquals(1100, r1.getPoidsTotal());



    }

    @Test
    @DisplayName("Vérifier qu’une marchandise dont le poids dépasse la limite autorisée d’une cargaison aérienne est refusée ;")
    void testLimitePoidsAerienne(){
        CargaisonAerienne avion = new CargaisonAerienne(100);
        assertFalse(avion.ajouter(new Marchandise("Gros Colis", 80001.0)));
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

    @Test
    @DisplayName("Vérifier que le coût du transport d’une cargaison aérienne est correctement calculé selon la formule : 10 × distance × poids total ;")
    void testCalculCoutAerienne(){
        CargaisonAerienne avion = new CargaisonAerienne(100);
        avion.ajouter(new Marchandise("Oil", 100.0));

        assertEquals(100000, avion.getCout());
    }

    @Test
    @DisplayName("Vérifier que plusieurs marchandises peuvent être ajoutées à une cargaison tant que la limite de poids n’est pas dépassée ;")
    void testeLimitePlusieur(){
        CargaisonMaritime mer = new CargaisonMaritime(100);
        assertTrue(mer.ajouter(new Marchandise("M1", 10000)));
        assertTrue(mer.ajouter(new Marchandise("M2", 10000)));
        assertEquals(2, mer.getListeMarchandises().size());
    }

    @Test
    @DisplayName("Vérifier qu’une marchandise pesant exactement la limite maximale autorisée peut\r\n" + //
                "être ajoutée à la cargaison ;")
    void testPoidsExactLimite() {
        CargaisonRoutiere route = new CargaisonRoutiere(100);
        Marchandise m = new Marchandise("Limite", 38000.0);
        assertTrue(route.ajouter(m), "Devrait accepter si poids == limite");
    }
    @Test
    @DisplayName("Vérifier que l’ajout de marchandises successives qui dépassent ensemble la limite de\r\n" + //
                "poids est correctement refusé pour la dernière.")
    void testDepassementSuccessif() {
        CargaisonAerienne air = new CargaisonAerienne(100); // Max 80000
        air.ajouter(new Marchandise("M1", 50000));
        boolean secondAjout = air.ajouter(new Marchandise("M2", 40000)); // Total 90000
        assertFalse(secondAjout, "Le second ajout devrait échouer");
        assertEquals(50000.0, air.getPoidsTotal());
    }
}
