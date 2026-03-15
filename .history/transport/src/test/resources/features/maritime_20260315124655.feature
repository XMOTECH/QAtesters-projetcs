Feature: Gestion du Cargo Maritime

  Scenario: Ajouter une marchandise avec un poids en dessous de la limite maximale
    Given un cargo maritime avec une distance de 150.0
    And une marchandise nommee "Toy Boats" avec un poids de 250000.0
    When j'ajoute la marchandise au cargo
    Then la marchandise devrait etre ajoutee avec succes
    And le poids total du cargo devrait etre 250000.0
    And le cout du transport devrait etre 37500000.0
