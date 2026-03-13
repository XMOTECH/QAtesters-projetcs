import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.assurance.Client;
import com.example.assurance.CompagnieAssurance;
import com.example.assurance.PoliceAssurance;
import com.example.assurance.Sinistre;
import com.example.assurance.StatutSinistre;
import com.example.assurance.TypePolice;

class CompagnieAssuranceTest {

    private CompagnieAssurance compagnie;

    @BeforeEach
    void setup() {
        compagnie = new CompagnieAssurance();
    }

    // Test 1 (déjà fourni)
    @Test
    @DisplayName("1. Créer un client avec des informations valides")
    void testEnregistrerClientValide() {
        String id = "C1";
        Client client = compagnie.enregistrerClient(id, "Mousaa", "oussa@gmail.com", "773125677");
        assertNotNull(client);
        assertEquals(id, client.getIdentifiant());
    }

    @Test
    @DisplayName("2. Empêcher la création d’un client avec un identifiant déjà existant")
    void testEnregistrerClientExistantLanceException() {
        String id = "C1";
        compagnie.enregistrerClient(id, "Mousaa", "oussa@gmail.com", "773125677");

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> compagnie.enregistrerClient(id, "Autre", "autre@gmail.com", "777777777")
        );

        assertTrue(ex.getMessage().toLowerCase().contains("existant"));
    }

    @Test
    @DisplayName("3. Retrouver un client enregistré à partir de son identifiant")
    void testRechercherClientExistant() {
        String id = "C2";
        Client cree = compagnie.enregistrerClient(id, "Aminata", "ami@gmail.com", "781234567");

        Client retrouve = compagnie.rechercherClient(id).orElse(null);

        assertNotNull(retrouve);
        assertEquals(cree.getIdentifiant(), retrouve.getIdentifiant());
        assertEquals(cree.getNom(), retrouve.getNom());
    }

    @Test
    @DisplayName("4. Retourner vide si un client inexistant est recherché")
    void testRechercherClientInexistant() {
        assertTrue(compagnie.rechercherClient("XXXX").isEmpty());
    }

    @Test
    @DisplayName("5. Créer une police d’assurance auto pour un client existant")
    void testCreerPoliceAutoClientExistant() {
        Client client = compagnie.enregistrerClient("C3", "Fatou", "fatou@gmail.com", "761234567");

        PoliceAssurance police = compagnie.creerPolicer(
            "P001", TypePolice.AUTO, 45000.0,
            LocalDate.now().minusMonths(1),
            LocalDate.now().plusYears(1),
            "C3"
        );

        assertNotNull(police);
        assertEquals(TypePolice.AUTO, police.getType());
        assertEquals("P001", police.getNumeroPolice());
        assertTrue(police.isActive());
    }

    @Test
    @DisplayName("6. Créer une police d’assurance santé avec une prime correcte")
    void testCreerPoliceSantePrimeCorrecte() {
        Client client = compagnie.enregistrerClient("C4", "Ibra", "ibra@gmail.com", "701234567");

        PoliceAssurance police = compagnie.creerPolicer(
            "P002", TypePolice.SANTE, 32000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C4"
        );

        assertEquals(TypePolice.SANTE, police.getType());
        assertEquals(32000.0, police.getPrime(), 0.001);
    }

    @Test
    @DisplayName("7. Empêcher la création d’une police pour un client inexistant")
    void testCreerPoliceClientInexistantLanceException() {
        NoSuchElementException ex = assertThrows(
            NoSuchElementException.class,
            () -> compagnie.creerPolicer("P003", TypePolice.HABITATION, 60000.0,
                LocalDate.now(), LocalDate.now().plusYears(1), "CLIENT_INCONNU")
        );

        assertTrue(ex.getMessage().toLowerCase().contains("inexistant"));
    }

    @Test
    @DisplayName("8. Annuler une police active et vérifier son statut")
    void testAnnulerPoliceActive() {
        Client client = compagnie.enregistrerClient("C5", "Awa", "awa@gmail.com", "781111111");
        PoliceAssurance police = compagnie.creerPolicer("P004", TypePolice.AUTO, 50000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C5");

        compagnie.annulerPolice("P004");

        assertFalse(police.isActive());
    }

    @Test
    @DisplayName("9. Tenter d’annuler une police déjà annulée doit lancer une exception")
    void testAnnulerPoliceDejaAnnuleeLanceException() {
        Client client = compagnie.enregistrerClient("C6", "Moussa", "moussa@gmail.com", "771234567");
        PoliceAssurance police = compagnie.creerPolicer("P005", TypePolice.AUTO, 48000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C6");

        compagnie.annulerPolice("P005");

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> compagnie.annulerPolice("P005")
        );

        assertTrue(ex.getMessage().toLowerCase().contains("déjà"));
    }

    @Test
    @DisplayName("10. Vérifier que la police est bien associée à son client")
    void testPoliceAssocieeAuClient() {
        Client client = compagnie.enregistrerClient("C7", "Aminata", "ami2@gmail.com", "761111111");
        PoliceAssurance police = compagnie.creerPolicer("P006", TypePolice.HABITATION, 72000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C7");

        assertTrue(client.getPolice().contains(police));
        assertEquals(client, police.getClient());
    }

    @Test
    @DisplayName("11. Déclarer un sinistre sur une police active")
    void testDeclarerSinistrePoliceActive() {
        Client client = compagnie.enregistrerClient("C8", "Ousmane", "ousmane@gmail.com", "771111111");
        PoliceAssurance police = compagnie.creerPolicer("P007", TypePolice.AUTO, 55000.0,
            LocalDate.now().minusDays(10), LocalDate.now().plusYears(1), "C8");

        Sinistre sinistre = compagnie.declarerSinistre("S001", "Accident collision", 850000.0, "P007");

        assertNotNull(sinistre);
        assertEquals(StatutSinistre.SOUMIS, sinistre.getStatut());
        assertTrue(police.getSinistre().contains(sinistre));
    }

    @Test
    @DisplayName("12. Tenter de déclarer un sinistre sur une police annulée doit échouer")
    void testDeclarerSinistrePoliceAnnuleeEchoue() {
        Client client = compagnie.enregistrerClient("C9", "Fatima", "fatima@gmail.com", "781111111");
        PoliceAssurance police = compagnie.creerPolicer("P008", TypePolice.SANTE, 40000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C9");

        compagnie.annulerPolice("P008");

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> compagnie.declarerSinistre("S002", "Maladie grave", 1200000.0, "P008")
        );

        assertTrue(ex.getMessage().toLowerCase().contains("annul"));
    }

    @Test
    @DisplayName("13. Approuver un sinistre soumis")
    void testApprouverSinistreSoumis() {
        Client c = compagnie.enregistrerClient("C10", "Mame", "mame@gmail.com", "701111111");
        PoliceAssurance p = compagnie.creerPolicer("P009", TypePolice.AUTO, 52000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C10");
        Sinistre s = compagnie.declarerSinistre("S003", "Vol", 450000.0, "P009");

        compagnie.approuverSinistre("S003");

        assertEquals(StatutSinistre.APPROUVE, s.getStatut());
    }

    @Test
    @DisplayName("14. Rejeter un sinistre soumis")
    void testRejeterSinistreSoumis() {
        Client c = compagnie.enregistrerClient("C11", "Saliou", "saliou@gmail.com", "761111111");
        PoliceAssurance p = compagnie.creerPolicer("P010", TypePolice.HABITATION, 68000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C11");
        Sinistre s = compagnie.declarerSinistre("S004", "Incendie", 2500000.0, "P010");

        compagnie.rejeterSinistre("S004");

        assertEquals(StatutSinistre.REJETE, s.getStatut());
    }

    @Test
    @DisplayName("15. Payer un sinistre approuvé")
    void testPayerSinistreApprouve() {
        Client c = compagnie.enregistrerClient("C12", "Aissatou", "aissatou@gmail.com", "771111111");
        PoliceAssurance p = compagnie.creerPolicer("P011", TypePolice.SANTE, 38000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C12");
        Sinistre s = compagnie.declarerSinistre("S005", "Opération", 1800000.0, "P011");

        compagnie.approuverSinistre("S005");
        compagnie.payerSinistre("S005");

        assertEquals(StatutSinistre.PAYE, s.getStatut());
    }

    @Test
    @DisplayName("16. Empêcher le paiement d’un sinistre rejeté")
    void testPayerSinistreRejeteEchoue() {
        Client c = compagnie.enregistrerClient("C13", "Abdou", "abdou@gmail.com", "781111111");
        PoliceAssurance p = compagnie.creerPolicer("P012", TypePolice.AUTO, 49000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C13");
        Sinistre s = compagnie.declarerSinistre("S006", "Bris de glace", 320000.0, "P012");

        compagnie.rejeterSinistre("S006");

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> compagnie.payerSinistre("S006")
        );

        assertTrue(ex.getMessage().toLowerCase().contains("approuvé"));
    }

    @Test
    @DisplayName("17. Empêcher l’approbation d’un sinistre déjà payé")
    void testApprouverSinistreDejaPayeEchoue() {
        Client c = compagnie.enregistrerClient("C14", "Khady", "khady@gmail.com", "701111111");
        PoliceAssurance p = compagnie.creerPolicer("P013", TypePolice.HABITATION, 75000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C14");
        Sinistre s = compagnie.declarerSinistre("S007", "Dégât des eaux", 1400000.0, "P013");

        compagnie.approuverSinistre("S007");
        compagnie.payerSinistre("S007");

        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> compagnie.approuverSinistre("S007")
        );

        assertTrue(ex.getMessage().toLowerCase().contains("payé"));
    }

    @Test
    @DisplayName("18. Vérifier que les sinistres sont bien liés à leur police")
    void testSinistresLiesAPolice() {
        Client c = compagnie.enregistrerClient("C15", "Youssou", "youssou@gmail.com", "761111111");
        PoliceAssurance p = compagnie.creerPolicer("P014", TypePolice.AUTO, 51000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C15");

        Sinistre s1 = compagnie.declarerSinistre("S008", "Accident A", 620000.0, "P014");
        Sinistre s2 = compagnie.declarerSinistre("S009", "Accident B", 380000.0, "P014");

        assertTrue(p.getSinistre().contains(s1));
        assertTrue(p.getSinistre().contains(s2));
        assertEquals(2, p.getSinistre().size());
    }

    @Test
    @DisplayName("19. Assurer qu’un client contient bien la police qu’on lui a ajoutée")
    void testClientContientPoliceAjoutee() {
        Client client = compagnie.enregistrerClient("C16", "Rokhaya", "rokhaya@gmail.com", "771111111");
        PoliceAssurance police = compagnie.creerPolicer("P015", TypePolice.SANTE, 42000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C16");

        assertTrue(client.getPolice().contains(police));
        assertEquals(1, client.getPolice().size());
    }

    @Test
    @DisplayName("20. Empêcher la création d’un sinistre avec un identifiant déjà utilisé")
    void testDeclarerSinistreIdDejaUtiliseLanceException() {
        Client c = compagnie.enregistrerClient("C17", "Cheikh", "cheikh@gmail.com", "781111111");
        PoliceAssurance p = compagnie.creerPolicer("P016", TypePolice.AUTO, 53000.0,
            LocalDate.now(), LocalDate.now().plusYears(1), "C17");

        compagnie.declarerSinistre("S010", "Collision", 750000.0, "P016");

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> compagnie.declarerSinistre("S010", "Autre sinistre", 420000.0, "P016")
        );

        assertTrue(ex.getMessage().toLowerCase().contains("déjà"));
    }
}