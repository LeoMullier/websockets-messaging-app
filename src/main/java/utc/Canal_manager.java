// Déclaration du package
package utc;


// Importation des bibliothèques
import java.io.*;
import java.util.*;


// Classe Canal_manager
public class Canal_manager {

	
	// Attributs
	public int compteur = 0;
	public static Hashtable<Integer, Canal> tab_canaux = new Hashtable<Integer, Canal>();
	
	
	// Insertion d'un nouveau canal dans le tableau
	public Canal CM_Creer_canal(Utilisateur new_proprio, Utilisateur[] new_invites) {
		
		
		Canal canal_temp = new Canal(compteur, new_proprio, new_invites);
		tab_canaux.put(compteur, canal_temp);
		compteur ++;
		
		
		System.out.println("(!) Confirmation de l'insertion d'un nouveau canal dans le tableau.");
		return tab_canaux.get(compteur-1);
	}
	
	
	// Suppression d'un canal dans le tableau.
	public void CM_finalize(int id) {
		
		
		
		if (tab_canaux.containsKey(id)) {
			
			
            tab_canaux.get(id).finalize();
            tab_canaux.remove(id);
    		System.out.println("(!) Confirmation de la suppression d'un canal dans le tableau.");
        }
		
		
		else {
			
			
			System.out.println("(!) Le canal dont l'identifiant est spécifié est introuvable.");
		}
	}
	
	
	// Affichage d'un canal du tableau.
	public void CM_afficher_canal(int id) {
		
		
		
		if (tab_canaux.containsKey(id)) {
			
			
            tab_canaux.get(id).Afficher_canal();
    		System.out.println("(!) Confirmation de l'affichage d'un canal du tableau.");
        }
		
		
		else {
			
			
			System.out.println("(!) Le canal dont l'identifiant est spécifié est introuvable.");
		}
	}
}
