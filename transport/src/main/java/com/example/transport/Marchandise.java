package com.example.transport;


public class Marchandise {
    private String nom;
    public double poids;

    public Marchandise(String nom, double poids) {
        this.nom = nom;
        this.poids = poids;
    }

    public double getPoids(){
        return poids;
    }
    public String getNom(){
        return nom;
    }
}
