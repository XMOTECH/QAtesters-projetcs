package com.example.transport;

public class CargaisonAerienne extends Cargaison{
    private static final double LIMITE_POIDS_AERIENNE = 80000.0;
    private static final double FACTEUR_COUT = 10.0;

    public CargaisonAerienne(double distance){
        super(distance, LIMITE_POIDS_AERIENNE);
    }

    @Override
    public double getCout(){
        return FACTEUR_COUT * distance * getPoidsTotal();
    }
}
