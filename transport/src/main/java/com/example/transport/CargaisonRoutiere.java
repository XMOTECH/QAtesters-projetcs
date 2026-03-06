package com.example.transport;

public class CargaisonRoutiere extends Cargaison{
    private static final double LIMITE_POIDS_ROUTIERE = 38000.0;
    private static final double FACTEUR_COUT = 4.0;

    public CargaisonRoutiere(double distance){
        super(distance, LIMITE_POIDS_ROUTIERE);
    }

    @Override
    public double getCout(){
        return FACTEUR_COUT * distance * getPoidsTotal();
    }

}
