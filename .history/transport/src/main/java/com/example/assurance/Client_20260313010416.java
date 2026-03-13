package com.example.assurance;

import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@Getter @Setter
public class Client {
    private final String identifiant;
    private final String nom;
    private final String email;
    private final String telephone;
    private List<PoliceAssurance> police =  new ArrayList<>();


    public void ajouterPolice(PoliceAssurance police){
        this.police.add(police);
    }

    
}
