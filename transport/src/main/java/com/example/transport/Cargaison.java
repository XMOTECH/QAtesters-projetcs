package com.example.transport;

import java.util.ArrayList;
import java.util.List;

public abstract class Cargaison implements ICoutTransport{
    protected double distance;
    protected List<Marchandise> listeMarchandises;
    protected double limitePoids;

    public Cargaison(double distance, double limitePoids) {
        this.distance = distance;
        this.limitePoids = limitePoids;
        this.listeMarchandises = new ArrayList<>();
    }

    public boolean ajouter(Marchandise m){
        if (getPoidsTotal() + m.getPoids() <= limitePoids) {
            listeMarchandises.add(m);
            return true;
        }
        return false;
    }

    public double getPoidsTotal(){
        double poidsTotal = 0;
        for (Marchandise m: listeMarchandises) {
            poidsTotal += m.getPoids();
        }
        return poidsTotal;
    }
    @Override
    public abstract double getCout();

    public double getDistance(){
        return distance;
    }
    public double getLimitePoids(){
        return limitePoids;
    }

    public List<Marchandise> getListeMarchandises(){
        return listeMarchandises;
    }

}

