package com.example.assurance;

import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@Setter
public class Client {
    private final String identifiant;
    private final String nom;
    private final String email;
    private final String telephone;

    private List<PoliceAssurance> polices =  new ArrayList<>();


    public void ajouterPolice(PoliceAssurance police){
        this.polices.add(police);
    }

    
}
