package com.example.assurance;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter @Setter
public class Sinistre {
    private final String identifiant;
    private final String description;
    private final double montant;
    private  LocalDate dateDeCreation = LocalDate.now();
    private StatutSinistre statut = StatutSinistre.SOUMIS;
    private final PoliceAssurance police;

    public void approuver(){
        if (statut == StatutSinistre.PAYE) throw new IllegalStateException(); 
            
        this.statut = StatutSinistre.APPROUVE;
    }
    public void rejeter(){
        this.statut = StatutSinistre.REJETE;
    }

    public void  marquerPayer(){
        if (statut != StatutSinistre.APPROUVE) throw new IllegalStateException(); 
            
        this.statut = StatutSinistre.PAYE;
    }
}
