// Déclaration du package
package utc;


// Importation des bibliothèques
import java.io.*;
import java.util.*;


// Classe Utilisateur_manager
public class Utilisateur_manager {

	
	// Attributs
	public int compteur = 0;
	public Hashtable<Integer, Utilisateur> tab_utilisateurs = new Hashtable<Integer, Utilisateur>();
	
	
	// Insertion d'un nouvel utilisateur dans le tableau
	public Utilisateur UM_Creer_utilisateur(String new_login, String new_passwd, boolean new_is_admin, boolean new_is_active) {
		
		
		Utilisateur user_temp = new Utilisateur(compteur, new_login, new_passwd, new_is_admin, new_is_active);
		tab_utilisateurs.put(compteur, user_temp);
		compteur ++;
		
		
		System.out.println("(!) Confirmation de l'insertion d'un nouvel utilisateur dans le tableau.");
		return tab_utilisateurs.get(compteur-1);
	}
	
	
	// Désactivation d'un utilisateur dans le tableau
	public void UM_Desactiver_utilisateur(int id) {
		
		
		if (tab_utilisateurs.containsKey(id)) {
			
			
            tab_utilisateurs.get(id).Desactiver_utilisateur();
    		System.out.println("(!) Confirmation de la désactivation d'un utilisateur dans le tableau.");
        }
		
		
		else {
			
			
			System.out.println("(!) L'utilisateur dont l'identifiant est spécifié est introuvable.");
		}
	}
	
	
	// Suppression d'un utilisateur dans le tableau.
	public void UM_finalize(int id) {
		
		
		
		if (tab_utilisateurs.containsKey(id)) {
			
			
            tab_utilisateurs.get(id).finalize();
            tab_utilisateurs.remove(id);
    		System.out.println("(!) Confirmation de la suppression d'un utilisateur dans le tableau.");
        }
		
		
		else {
			
			
			System.out.println("(!) L'utilisateur dont l'identifiant est spécifié est introuvable.");
		}
	}
	
	
	// Mise à jour du login d'un utilisateur dans le tableau
	public void UM_Set_login(int id, String new_login) {
		
		
		if (tab_utilisateurs.containsKey(id)) {
			
			
            tab_utilisateurs.get(id).Set_login(new_login);
    		System.out.println("(!) Confirmation de la mise à jour du login d'un utilisateur dans le tableau.");
        }
		
		
		else {
			
			
			System.out.println("(!) L'utilisateur dont l'identifiant est spécifié est introuvable.");
		}
	}
	
	
	// Mise à jour du mot de passe d'un utilisateur dans le tableau
	public void UM_Set_passwd(int id, String new_passwd) {
		
		
		if (tab_utilisateurs.containsKey(id)) {
			
			
            tab_utilisateurs.get(id).Set_passwd(new_passwd);
    		System.out.println("(!) Confirmation de la mise à jour du mot de passe d'un utilisateur dans le tableau.");
        }
		
		
		else {
			
			
			System.out.println("(!) L'utilisateur dont l'identifiant est spécifié est introuvable.");
		}
	}
}
