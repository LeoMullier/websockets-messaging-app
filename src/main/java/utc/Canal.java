// Déclaration du package
package utc;


// Importation des bibliothèques


// Classe Utilisateur
public class Canal {

	
	// Attributs
	public int id_canal;
	public Utilisateur proprio;
	public Utilisateur[] invites;
	
	
	// Création d'un nouveau canal
	public Canal Ajouter_canal(int new_id_canal, Utilisateur new_proprio, Utilisateur[] new_invites) {
		
		
		Canal new_canal = new Canal();
		new_canal.id_canal = new_id_canal;
		new_canal.proprio = new_proprio;
		new_canal.invites = new_invites;
		
		
		System.out.println("(!) Confirmation de l'ajout d'un canal.");
		return new_canal;
	}
	
	
	// Suppression d'un canal particulier
	public void finalize() {
		
		
		System.out.println("(!) Confirmation de suppression d'un canal.");
	}
	
	
	// Afficher le contenu d'un canal particulier
	public void Afficher_canal() {
		
		
		System.out.println("(!) Confirmation de l'affichage d'un canal.");
	}
}
