/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FFSSM;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author nelsonrogers
 */
public class Test_FFSSM {
    
    
    LocalDate dateNaissNelson, dateNaissRemi, datePlongee, delivrance;
    Site castres;
    Plongee plongee;
    Plongeur p, part;
    Moniteur m, presidentCastres, presidentAlbi;
    Club castresDiveClub, albiDiveClub;
    Licence licenceBastide;


    
    @BeforeEach
    public void setUp() {
        
        p = new Plongeur("FRA000", "Rogers", "Nelson", "8 Rue Fuzies", "0634058379", dateNaissNelson, GroupeSanguin.APLUS, 1);
        m = new Moniteur("FRA011", "Bastide", "Rémi", "Place Soult", "0634050000", dateNaissRemi, GroupeSanguin.BMOINS, 1, 22);
        part = new Plongeur("FRA000", "Roger", "Bapt", "8 Rue Fuzies", "0634000000", dateNaissNelson, GroupeSanguin.APLUS, 1);
        
        datePlongee = LocalDate.of(2020, 11, 25);
        castresDiveClub = new Club(presidentCastres, "Castres Dive Club", "0578940382");
        albiDiveClub = new Club(presidentAlbi, "Albi Dive Club", "0578940365");
        
        plongee = new Plongee(castres, m, datePlongee, 5, 2);
        
        // une licence délivrée à la date delivrance est valide pour la Plongee plongee.
        delivrance = LocalDate.of(2019, 12, 27);

    }
    

    
    @Test
    public void testAjouteParticipant() {
        // une plongee a forcément un chef de palanquee
        assertEquals(1, plongee.getPlongeurs().size());
        
        plongee.ajouteParticipant(p);
        // On a ajouté un participant
        assertEquals(1 + 1, plongee.getPlongeurs().size());
        
        // On ajoute le même participant
        plongee.ajouteParticipant(p);
        // On vérifie que le participant n'est pas en double
        assertEquals(1 + 1, plongee.getPlongeurs().size());
    }
    
    @Test
    public void testEstConforme() {
            // le moniteur a une licence qui n'est plus valable
            m.ajouteLicence("0000", delivrance.plusYears(-1), castresDiveClub);
            
            // La licence du moniteur n'est pas à jour
            assertFalse(plongee.estConforme());
            
            // On ajoute une licence au moniteur : valide au moment de la plongee
            m.ajouteLicence("0001", delivrance, castresDiveClub);

            // La plongee doit être conforme
            assertTrue(plongee.estConforme());
            
            // On ajoute un participant, mais celui-ci n'a pas encore de licence
            plongee.ajouteParticipant(p);
            // La plongee n'est pas conforme cra le plongeur p n'a pas encore de licence
            assertFalse(plongee.estConforme());

    }
    
    @Test
    // On teste que cette méthode nous renvoie bien les plongées non conformes
    public void testPlongeesNonConformes() {
        
        // Cette plongée n'est pas conforme car le moniteur n'a pas de licence
        castresDiveClub.organisePlongee(plongee);
        // On veut donc 1 plongee non conforme
        assertEquals(1, castresDiveClub.plongeesNonConformes().size());
        
        // Le moniteur a maintenant une licence
        m.ajouteLicence("0001", delivrance, castresDiveClub);
        // Le nombre de plongées non comformes doit être de 0
        assertEquals(0, castresDiveClub.plongeesNonConformes().size());
    }
    
    @Test 
    public void testOrganisePlongee() {
        // Le club de Castres n'a pas encore organisé de plongée
        assertEquals(0, castresDiveClub.getMyPlongees().size());
        
        castresDiveClub.organisePlongee(plongee);
        // Le club de Castres a organisé une plongée
        assertEquals(1, castresDiveClub.getMyPlongees().size());
    }
    
    
    @Test
    public void unMoniteurMaxUnEmploi() {
        
        // À sa création, le moniteur n'a pas d'emploi ni d'employeur
        assertTrue(m.emplois().isEmpty(),"m n'a pas encore d'emploi");
        assertTrue(m.employeurActuel().isEmpty(), "m n'a pas encore d'employeur");
        
        //On créé une nouvelle embauche pour m
        m.nouvelleEmbauche(castresDiveClub, delivrance);
        Embauche emploi1 = m.emplois().get(m.emplois().size() - 1);
        
        // On vérifie qu'elle n'est pas terminée
        assertFalse(emploi1.estTerminee(), "emploi1 doit être en cours");
        
        // On vérifie l'employeur
        assertTrue(m.employeurActuel().get() == castresDiveClub, "m doit avoir pour empoyeur castresDiveClub");
        
        // On créé une nouvelle embauche pour m
        m.nouvelleEmbauche(albiDiveClub, datePlongee);
        Embauche emploi2 = m.emplois().get(m.emplois().size() - 1);
        
        
        // L'embauche précedente doit être terminée pas pas l'actuelle
        assertTrue(emploi1.estTerminee(),"emploi1 devrait être terminé");
        assertFalse(emploi2.estTerminee(),"emploi2 ne devrait pas être terminé");
        
        // On vérifie que l'employeur a été mis à jour
        assertTrue(m.employeurActuel().get() == albiDiveClub, "m doit avoir pour empoyeur albiDiveClub");
        
        
    }
    
    
    @Test
    // Ne peut pas terminer un emploi si il est déjà terminé
    public void nePeutPasTerminerEmploiSiAuChaumage() {
        
        m.nouvelleEmbauche(albiDiveClub, datePlongee);
        
        Embauche emploi = m.emplois().get(0);
        emploi.terminer(datePlongee);
        // On tente de terminer à nouveau l'emploi ==> pas possible donc la date de fin ne doit pas être changée
        emploi.terminer(datePlongee.minusYears(1));
        
        // Le deuxième appel de la méthode terminer() ne change pas la date de fin
        assertTrue(datePlongee.compareTo(emploi.getFin()) == 0);
        
        
    }
    
   
    
    @Test
    public void testEstValide() {
        
        licenceBastide = new Licence(m, "0003", delivrance, 2, castresDiveClub);
        
        // licence est valide au moment de la délivrance
        assertTrue(licenceBastide.estValide(delivrance.plusMonths(0)));
        
        // licence est valide 11 mois après délivrance
        assertTrue(licenceBastide.estValide(delivrance.plusMonths(11)));
        
        // licence n'est plus valide 12 mois après délivrance
        assertFalse(licenceBastide.estValide(delivrance.plusMonths(12)));
        
        // licence n'est pas valide avant délivrance
        assertFalse(licenceBastide.estValide(delivrance.minusMonths(1)));
    }
    
    
    @Test 
    public void testGetCurrentLicence() {
        // On ajoute 3 licences mais une seule est valide au moment de la plongee
        // n'est pas valide à la LocalDate datePlongee
        p.ajouteLicence("0004", delivrance.minusYears(1), albiDiveClub);
        
        // est valide à la LocalDate datePlongee
        p.ajouteLicence("0007", delivrance, albiDiveClub);
        
        // n'est pas valide à la LocalDate datePlongee
        p.ajouteLicence("0004", delivrance.plusYears(1), albiDiveClub);
        // On vérifie que la licence sélectionnée par getCurrentLicence() est bien celle valide au moment de la date de plongee 
        assertTrue(p.getCurrentLicence(datePlongee).getDelivrance().compareTo(delivrance) == 0);
    }

    
    
    
}