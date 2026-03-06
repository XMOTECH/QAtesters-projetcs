package com.example.transport;

public class CargaisonMaritime extends Cargaison{
    private static final double LIMITE_POIDS_MARITIME = 300000.0;
    private static final double FACTEUR_COUT = 1.0;

    public CargaisonMaritime(double distance){
        super(distance, LIMITE_POIDS_MARITIME);
    }

    @Override
    public double getCout(){
        return FACTEUR_COUT*distance*getPoidsTotal();
    }
}
