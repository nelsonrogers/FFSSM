/**
 * @(#) Club.java
 */
package FFSSM;

import java.util.HashSet;
import java.util.Set;

public class Club {

 
    public Moniteur president;

    public String nom;

    public String adresse;

    public String telephone;
    
    private Set<Plongee> myPlongees = new HashSet<>();
    
    public Club(Moniteur president, String nom, String telephone) {
        this.president = president;
        this.nom = nom;
        this.telephone = telephone;
    }

    /**
     * Calcule l'ensemble des plongées non conformes organisées par ce club.
     * Une plongée est conforme si tous les plongeurs de la palanquée ont une licence
     * valide à la date de la plongée
     * @return l'ensemble des plongées non conformes
     */
    public Set<Plongee> plongeesNonConformes() {
        Set plongees = new HashSet<>();
        for (Plongee plongee : myPlongees) {
            if (!plongee.estConforme())
                // Si une plongée n'est pas conforme, on l'ajoute à l'ensemble
                plongees.add(plongee);
        }
        // Retourne l'ensemble des plongées non conformes
        return plongees;
    }

    /**
     * Enregistre une nouvelle plongée organisée par ce club
     * @param p la nouvelle plongée
     */
    public void organisePlongee(Plongee p) {
        myPlongees.add(p);
    }

    public Set<Plongee> getMyPlongees() {
        return myPlongees;
    }
    
    
    
    
    public Moniteur getPresident() {
        return president;
    }

    public void setPresident(Moniteur president) {
        this.president = president;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Club{" + "président=" + president + ", nom=" + nom + ", adresse=" + adresse + ", telephone=" + telephone + '}';
    }

}
