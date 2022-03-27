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
	public Canal (int new_id_canal, Utilisateur new_proprio, Utilisateur[] new_invites) {
		
		
		this.id_canal = new_id_canal;
		this.proprio = new_proprio;
		this.invites = new_invites;
		
		
		System.out.println("(!) Confirmation de l'ajout d'un canal.");
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
