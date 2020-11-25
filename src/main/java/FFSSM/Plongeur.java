package FFSSM;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import java.util.List;


public class Plongeur extends Personne {
    
    public int niveau;
    public GroupeSanguin groupeSanguin;
    public List<Licence> myLicences = new ArrayList<>();

    public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, GroupeSanguin groupeSanguin, int niveau) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
        this.groupeSanguin = groupeSanguin;
        this.niveau = niveau;
    }
    
    
    
    public Licence getCurrentLicence(LocalDate date) {
        // Par défaut, il n'y a pas de licence valide à la LocalDate date
        Licence currentLicence = null;
        // On parcourt les licences du plongeur
        for (Licence licence : myLicences) {
            // period est la periode entre la date de délivrance et la LocalDate date
            Period period = Period.between(licence.getDelivrance(), date);
            // Si period est compris entre 0 (inclus) et 12 mois(non inclus), la licence est valide à la LocalDate date
            if (period.toTotalMonths() >= 0 && period.toTotalMonths() < 12) {
                // On retourne la licence valide, c'est-à-dire la licence actuelle
                return licence;
            }
                
        }
        // Si le plongeur n'a pas une licence valide à la LocalDate date, on retourne null
        return currentLicence;
    }

    
    public void ajouteLicence(String numero, LocalDate delivrance, Club club) {
        Licence e = new Licence(this, numero, delivrance, niveau, club);
        myLicences.add(e);
    }
	
        
}
