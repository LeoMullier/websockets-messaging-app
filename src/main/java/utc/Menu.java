// Déclaration du package
package utc;


// Importation des bibliothèques
import java.io.*;
import java.util.*;


// Classe Utilisateur
public class Menu {
	
	
	public Utilisateur user_actuel;
	
	
	// Afficher la liste des canaux propriétaires et invités pour un utilisateur
	public void Afficher_accueil_user() {
		
		
		// Affichage HTML à insérer ici
		
		
		// Récupération de tous les canaux
		Enumeration<Integer> e = Canal_manager.tab_canaux.keys();
        while (e.hasMoreElements()) {


        	// Le canal concerne-t-il l'utilisateur ?
        	Boolean concerne = false;
            int key_temp = e.nextElement();
            Utilisateur proprio_temp = Canal_manager.tab_canaux.get(key_temp).proprio;
            Utilisateur[] invites_temp = Canal_manager.tab_canaux.get(key_temp).invites;
            
            
            // On vérifie si on est le propriétaire sur ce canal
            if (proprio_temp.id_user == user_actuel.id_user) {
            	
            	
            	concerne = true;
            }
            
            
            else {
            	
            	
            	// On vérifie si on est invité sur ce canal
            	int i = 0;
                while (invites_temp[i] != null && concerne == false) {
                	
                	
                	if (invites_temp[i].id_user == user_actuel.id_user) {
                		
                		
                		concerne = true;
                	}
                }
            }
            
            
            // Si on est le propriétaire ou invité sur le canal
            if (concerne == true){
            
            
	            // Récupération des attributs pour chaque canal trouvé et préparation de l'affichage
	            String nom_proprio_temp = Canal_manager.tab_canaux.get(key_temp).proprio.login;
	            String noms_invites_temp = null;
	            for (int j = 0 ; j < 3 ; j++) {
	            	
	            	
	            	if (invites_temp[j] != null && invites_temp[j].login != user_actuel.login){
	            		
	            		
	            		noms_invites_temp = noms_invites_temp + ", " + invites_temp[j].login;
	            	}
	            }
	            
	            
	            if (invites_temp[3] != null) {
	            	
	            	
	            	noms_invites_temp = noms_invites_temp + "...";
	            }
	            
	            
	            // Affichage HTML à insérer ici
            }
        }
        System.out.println("(!) Confirmation de l'affichage du menu utilisateur 'Tous les canaux'.");
	}
	
	
	public void Afficher_mes_canaux() {
		
		
		// Affichage HTML à insérer ici
		// Fonctionnalité d'jout d'un nouveau canal
		
		
		// Récupération de tous les canaux
		Enumeration<Integer> e = Canal_manager.tab_canaux.keys();
        while (e.hasMoreElements()) {


        	// Le canal concerne-t-il l'utilisateur ?
        	Boolean concerne = false;
            int key_temp = e.nextElement();
            Utilisateur proprio_temp = Canal_manager.tab_canaux.get(key_temp).proprio;
            Utilisateur[] invites_temp = Canal_manager.tab_canaux.get(key_temp).invites;
            
            
            // On vérifie si on est le propriétaire sur ce canal
            if (proprio_temp.id_user == user_actuel.id_user) {
            	
            	
            	concerne = true;
            }
            
            
            // Si on est le propriétaire
            if (concerne == true){
            
            
	            // Récupération des attributs pour chaque canal trouvé et préparation de l'affichage
	            String nom_proprio_temp = Canal_manager.tab_canaux.get(key_temp).proprio.login;
	            String noms_invites_temp = null;
	            for (int j = 0 ; j < 3 ; j++) {
	            	
	            	
	            	if (invites_temp[j] != null && invites_temp[j].login != user_actuel.login){
	            		
	            		
	            		noms_invites_temp = noms_invites_temp + ", " + invites_temp[j].login;
	            	}
	            }
	            
	            
	            if (invites_temp[3] != null) {
	            	
	            	
	            	noms_invites_temp = noms_invites_temp + ", ...";
	            }
	            
	            
	            // Affichage HTML à insérer ici
	            // Possibilité de modifier, supprimer chaque canal
            }
        }
		System.out.println("(!) Confirmation de l'affichage du menu utilisateur 'Mes canaux'.");
	}
	
	
	public void Afficher_canaux_invites() {
		
		
		// Affichage HTML à insérer ici
		
		
		// Récupération de tous les canaux
		Enumeration<Integer> e = Canal_manager.tab_canaux.keys();
        while (e.hasMoreElements()) {


        	// Le canal concerne-t-il l'utilisateur ?
        	Boolean concerne = false;
            int key_temp = e.nextElement();
            Utilisateur proprio_temp = Canal_manager.tab_canaux.get(key_temp).proprio;
            Utilisateur[] invites_temp = Canal_manager.tab_canaux.get(key_temp).invites;
            
            
           	// On vérifie si on est invité sur ce canal
        	int i = 0;
            while (invites_temp[i] != null && concerne == false) {
            	
            	
            	if (invites_temp[i].id_user == user_actuel.id_user) {
            		
            		
            		concerne = true;
            	}
            }
            
            
            // Si on est invité sur le canal
            if (concerne == true){
            
            
	            // Récupération des attributs pour chaque canal trouvé et préparation de l'affichage
	            String nom_proprio_temp = Canal_manager.tab_canaux.get(key_temp).proprio.login;
	            String noms_invites_temp = null;
	            for (int j = 0 ; j < 3 ; j++) {
	            	
	            	
	            	if (invites_temp[j] != null && invites_temp[j].login != user_actuel.login){
	            		
	            		
	            		noms_invites_temp = noms_invites_temp + ", " + invites_temp[j].login;
	            	}
	            }
	            
	            
	            if (invites_temp[3] != null) {
	            	
	            	
	            	noms_invites_temp = noms_invites_temp + "...";
	            }
	            
	            
	            // Affichage HTML à insérer ici
            }
        }
        System.out.println("(!) Confirmation de l'affichage du menu utilisateur 'Canaux invités'.");
	}
}
