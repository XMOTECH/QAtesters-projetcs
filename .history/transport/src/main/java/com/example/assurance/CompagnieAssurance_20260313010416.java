package com.example.assurance;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CompagnieAssurance {
    private Map<String, Client> clients = new HashMap<>();
    private Map<String, PoliceAssurance> polices = new HashMap<>();
    private Map<String, Sinistre> sinistres = new HashMap<>();


    public Client enregistrerClient(String id, String nom, String email, String tel) {
        if (clients.containsKey(id)) throw new IllegalArgumentException("Client déjà existant");
        Client client = new Client(id, nom, email, tel);
        clients.put(id, client);
        return client;
    }

    
    public PoliceAssurance creerPolicer(String numeroPolice, TypePolice type, double prime, LocalDate dateDebut, LocalDate dateFin  ,String clientId) {
        Client client = rechercherClient(clientId).orElseThrow(() -> new NoSuchElementException("clients inexistant"));
        PoliceAssurance police = new PoliceAssurance(numeroPolice, type, prime, dateDebut, dateFin, client);
        polices.put(numeroPolice, police);
        client.ajouterPolice(police);
        return police;
    }
    public Sinistre declarerSinistre(String id, String desc, double montant, String policeId){
        PoliceAssurance police = rechercherPolices(policeId).orElseThrow();
        if (!police.isActive()) throw new IllegalStateException("Police annule");
        if (sinistres.containsKey(id)) throw new IllegalArgumentException("Id deja utiliser");

        Sinistre sinistre = new Sinistre(id, desc, montant, police);
        sinistres.put(id, sinistre);
        police.ajouterSinistre(sinistre);
        return sinistre;

    }

    public Optional<Client> rechercherClient(String id) {
        return  Optional.ofNullable(clients.get(id)); 
    }
    public Optional<PoliceAssurance> rechercherPolices(String id) { 
        return Optional.ofNullable(polices.get(id));
    }
    
    public void annulerPolice(String id){
        rechercherPolices(id).ifPresent(PoliceAssurance::annuler);
    }

    public void approuverSinistre(String id){
        Optional.ofNullable(sinistres.get(id)).ifPresent(Sinistre::approuver);
    }

    public void rejeterSinistre(String id){
        Optional.ofNullable(sinistres.get(id)).ifPresent(Sinistre::rejeter);
    }

    public void payerSinistre(String id) {
        Optional.ofNullable(sinistres.get(id)).ifPresent(Sinistre::marquerPayer);
    }


}
