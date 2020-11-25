/**
 * @(#) Moniteur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Moniteur extends Plongeur {

    public int numeroDiplome;
    
    // Liste des emplois terminés ou non du Moniteur (un seul est en cours)
    private List<Embauche> myEmplois = new ArrayList<>();

    public Moniteur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, GroupeSanguin groupeSanguin, int niveau, int numeroDiplome) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance, groupeSanguin, niveau);
        this.numeroDiplome = numeroDiplome;
    }

    /**
     * Si ce moniteur n'a pas d'embauche, ou si sa dernière embauche est terminée,
     * ce moniteur n'a pas d'employeur.
     * @return l'employeur actuel de ce moniteur sous la forme d'un Optional
     */
    
    
    public Optional<Club> employeurActuel() {
        
        Optional<Club> opt;
        // Si le moniteur a un emploi qui n'est pas terminé (qui est unique et qui est le dernier de la liste)
        if (!myEmplois.isEmpty() && !myEmplois.get(myEmplois.size() - 1).estTerminee()) {
            // On récupère l'employeur
            Club c = myEmplois.get(myEmplois.size() - 1).getEmployeur();
            opt = Optional.ofNullable(c);
        }
        else {
            // Sinon, ça veut dire que le moniteur est actuellement au chomage (bien qu'il puisse avoir des emplois terminés)
            Club c = null;
            // Il n'a donc pas d'employeur, opt prend la valeur null
            opt = Optional.ofNullable(c);
        }
        // On retourne l'employeur (qui peut être null ou non)
        return opt;
    }
    
    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * @param employeur le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmbauche(Club employeur, LocalDate debutNouvelle) {   
        Embauche e = new Embauche(debutNouvelle, this, employeur);
        // Un moniteur ne peut avoir qu'un seul emploi à la fois, on doit terminer son emploi précédent
        terminerEmbauche(debutNouvelle);
        myEmplois.add(e);
    }
    
    public void terminerEmbauche(LocalDate dateFin) {
        // Si le moniteur a un emploi en cours
        if (!myEmplois.isEmpty() && !myEmplois.get(myEmplois.size() - 1).estTerminee())
            // On met fin à l'emploi
            myEmplois.get(myEmplois.size() - 1).terminer(dateFin);
        //Sinon il ne se passe rien
    }

    public List<Embauche> emplois() {
        return myEmplois;
    }

}
