import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.NoSuchElementException;

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

    @Test
    @DisplayName("1.Créer un client avec des informations valides.")
    public void testEnregistrerClientValide() {
        String Id = "C1";
        Client client = compagnie.enregistrerClient(Id, "Mousaa", "oussa@gmail.com", "773125677");
        assertNotNull(client);
        assertEquals(Id, client.getIdentifiant());
    }

    @Test
    @DisplayName("2.Empêcher la création d'un client avec un identifiant déjà existant.")
    public void testEmpicherCreationClientDuplique() {
        String Id = "C1";
        compagnie.enregistrerClient(Id, "Mousaa", "oussa@gmail.com", "773125677");
        assertThrows(IllegalArgumentException.class, () -> {
            compagnie.enregistrerClient(Id, "Autre", "autre@gmail.com", "773125678");
        });
    }

    @Test
    @DisplayName("3.Retrouver un client enregistré à partir de son identifiant.")
    public void testRechercherClientExistant() {
        String Id = "C1";
        compagnie.enregistrerClient(Id, "Mousaa", "oussa@gmail.com", "773125677");
        assertTrue(compagnie.rechercherClient(Id).isPresent());
        assertEquals(Id, compagnie.rechercherClient(Id).get().getIdentifiant());
    }

    @Test
    @DisplayName("4.Retourner vide si un client inexistant est rechercher.")
    public void testRechercherClientInexistant() {
        assertFalse(compagnie.rechercherClient("C999").isPresent());
    }

    @Test
    @DisplayName("5.Créer une police d'assurance auto pour un client existant.")
    public void testCreerPoliceAuto() {
        String clientId = "C1";
        String policeId = "P1";
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        PoliceAssurance police = compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        assertNotNull(police);
        assertEquals(TypePolice.AUTO, police.getType());
    }

    @Test
    @DisplayName("6.Créer une police d'assurance santé avec une prime correcte.")
    public void testCreerPoliceSante() {
        String clientId = "C1";
        String policeId = "P1";
        double primeAttendue = 300.0;
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        PoliceAssurance police = compagnie.creerPolice(policeId, TypePolice.SANTE, primeAttendue, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        assertNotNull(police);
        assertEquals(TypePolice.SANTE, police.getType());
        assertEquals(primeAttendue, police.getPrime());
    }

    @Test
    @DisplayName("7.Empêcher la création d'une police pour un client inexistant.")
    public void testCreerPoliceClientInexistant() {
        String clientId = "C1";
        String policeId = "P1";
        assertThrows(NoSuchElementException.class, () -> {
            compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                    LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        });
    }

    @Test
    @DisplayName("8.Annuler une police active et vérifier son statut.")
    public void testAnnulerPoliceActive() {
        String clientId = "C1";
        String policeId = "P1";
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        assertTrue(compagnie.rechercherPolice(policeId).get().isActive());
        
        compagnie.annulerPolice(policeId);
        assertFalse(compagnie.rechercherPolice(policeId).get().isActive());
    }

    @Test
    @DisplayName("9.Tenter d'annuler une police déjà annulée doit lancer une exception.")
    public void testAnnulerPoliceDejaAnnulee() {
        String clientId = "C1";
        String policeId = "P1";
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        PoliceAssurance police = compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        
        // First cancellation
        police.annuler();
        
        // Second cancellation should throw exception
        assertThrows(IllegalStateException.class, () -> {
            police.annuler();
        });
    }

    @Test
    @DisplayName("10.Vérifier que la police est bien associée à son client.")
    public void testPoliceAssocieeClient() {
        String clientId = "C1";
        String policeId = "P1";
        Client client = compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        PoliceAssurance police = compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        
        assertEquals(client, police.getClient());
    }

    @Test
    @DisplayName("11.Déclarer un sinistre sur une police active.")
    public void testDeclarerSinistrePoliceActive() {
        String clientId = "C1";
        String policeId = "P1";
        String sinistreId = "S1";
        
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        PoliceAssurance police = compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        
        Sinistre sinistre = compagnie.declarerSinistre(sinistreId, "Accident", 1000.0, policeId);
        assertNotNull(sinistre);
        assertEquals(StatutSinistre.SOUMIS, sinistre.getStatut());
    }

    @Test
    @DisplayName("12.Tenter de déclarer un sinistre sur une police annulée doit échouer.")
    public void testDeclarerSinistrePoliceAnnulee() {
        String clientId = "C1";
        String policeId = "P1";
        
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        PoliceAssurance police = compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        police.annuler();
        
        assertThrows(IllegalStateException.class, () -> {
            compagnie.declarerSinistre("S1", "Accident", 1000.0, policeId);
        });
    }

    @Test
    @DisplayName("13.Approuver un sinistre soumis.")
    public void testApprouverSinistre() {
        String clientId = "C1";
        String policeId = "P1";
        String sinistreId = "S1";
        
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        Sinistre sinistre = compagnie.declarerSinistre(sinistreId, "Accident", 1000.0, policeId);
        
        compagnie.approuverSinistre(sinistreId);
        assertEquals(StatutSinistre.APPROUVE, sinistre.getStatut());
    }

    @Test
    @DisplayName("14.Rejeter un sinistre soumis.")
    public void testRejeterSinistre() {
        String clientId = "C1";
        String policeId = "P1";
        String sinistreId = "S1";
        
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        Sinistre sinistre = compagnie.declarerSinistre(sinistreId, "Accident", 1000.0, policeId);
        
        compagnie.rejeterSinistre(sinistreId);
        assertEquals(StatutSinistre.REJETE, sinistre.getStatut());
    }

    @Test
    @DisplayName("15.Payer un sinistre approuvé.")
    public void testPayerSinistreApprouve() {
        String clientId = "C1";
        String policeId = "P1";
        String sinistreId = "S1";
        
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        Sinistre sinistre = compagnie.declarerSinistre(sinistreId, "Accident", 1000.0, policeId);
        
        compagnie.approuverSinistre(sinistreId);
        compagnie.payerSinistre(sinistreId);
        assertEquals(StatutSinistre.PAYE, sinistre.getStatut());
    }

    @Test
    @DisplayName("16.Empêcher le paiement d'un sinistre rejeté.")
    public void testPayerSinistreRejete() {
        String clientId = "C1";
        String policeId = "P1";
        String sinistreId = "S1";
        
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        Sinistre sinistre = compagnie.declarerSinistre(sinistreId, "Accident", 1000.0, policeId);
        
        compagnie.rejeterSinistre(sinistreId);
        assertThrows(IllegalStateException.class, () -> {
            compagnie.payerSinistre(sinistreId);
        });
    }

    @Test
    @DisplayName("17.Empêcher l'approbation d'un sinistre déjà payé.")
    public void testApprouverSinistreDejaPaye() {
        String clientId = "C1";
        String policeId = "P1";
        String sinistreId = "S1";
        
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        Sinistre sinistre = compagnie.declarerSinistre(sinistreId, "Accident", 1000.0, policeId);
        
        compagnie.approuverSinistre(sinistreId);
        compagnie.payerSinistre(sinistreId);
        assertThrows(IllegalStateException.class, () -> {
            compagnie.approuverSinistre(sinistreId);
        });
    }

    @Test
    @DisplayName("18.Vérifier que les sinistres sont bien liés à leur police.")
    public void testSinistreLiedPolice() {
        String clientId = "C1";
        String policeId = "P1";
        String sinistreId = "S1";
        
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        PoliceAssurance police = compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        Sinistre sinistre = compagnie.declarerSinistre(sinistreId, "Accident", 1000.0, policeId);
        
        assertEquals(police, sinistre.getPolice());
    }

    @Test
    @DisplayName("19.Assurer qu'un client contient bien la police qu'on lui a ajoutée.")
    public void testClientContientPolice() {
        String clientId = "C1";
        String policeId = "P1";
        
        Client client = compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        PoliceAssurance police = compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        
        assertTrue(client.getPolices().contains(police));
    }

    @Test
    @DisplayName("20.Empêcher la création d'un sinistre avec un identifiant déjà utilisé.")
    public void testEmpicherCreationSinistreDuplique() {
        String clientId = "C1";
        String policeId = "P1";
        String sinistreId = "S1";
        
        compagnie.enregistrerClient(clientId, "Mousaa", "oussa@gmail.com", "773125677");
        compagnie.creerPolice(policeId, TypePolice.AUTO, 500.0, 
                LocalDate.now(), LocalDate.now().plusYears(1), clientId);
        compagnie.declarerSinistre(sinistreId, "Accident", 1000.0, policeId);
        
        assertThrows(IllegalArgumentException.class, () -> {
            compagnie.declarerSinistre(sinistreId, "另一个事故", 500.0, policeId);
        });
    }
}
