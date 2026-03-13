package com.example.assurance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter @Setter
@RequiredArgsConstructor
public class PoliceAssurance  {
    private final String numeroPolice;
    private final TypePolice type;
    private final double prime;
    private final LocalDate dateDebut;
    private final LocalDate dateFin;
    private final Client client;
    private boolean active = true;
    private List<Sinistre> sinistre = new ArrayList<>();


    public void annuler() {
        if (!active)  throw new IllegalStateException("Plolice deja annule") ;{
            this.active = false;
        }
    }

    public void ajouterSinistre(Sinistre sinistre){
        this.sinistre.add(sinistre);
    }
}
