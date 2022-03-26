// Déclaration du package
package utc;


// Importation des bibliothèques


// Classe Utilisateur
public class Utilisateur {

	
	// Attributs
	public int id_user;
	public String login;
	public String passwd;
	public boolean is_admin;
	public boolean is_active;
	
	
	// Création d'un nouvel utilisateur
	public Utilisateur(int new_id, String new_login, String new_passwd, boolean new_is_admin, boolean new_is_active) {
		
		
		this.id_user = new_id;
		this.login = new_login;
		this.passwd = new_passwd;
		this.is_admin = new_is_admin;
		this.is_active = true;
		
		
		System.out.println("(!) Confirmation de l'ajout d'un utilisateur.");
	}
	
	
	// Désactivation d'un utilisateur particulier
	public void Desactiver_utilisateur() {
		
		
		this.is_active = false;	
		System.out.println("(!) Confirmation de la désactivation de l'utilisateur.");
	}
	
	
	// Suppression d'un utilisateur particulier
	public void finalize() {
		
		
		System.out.println("(!) Confirmation de la suppression d'un utilisateur.");	
	}
	
	
	// Mise à jour du login d'un utilisateur particulier
	public void Set_login(String new_login) {
		
		
		this.login = new_login;	
		System.out.println("(!) Confirmation de la mise à jour du login de l'utilisateur.");
	}
	
	
	// Mise à jour du mot de passe d'un utilisateur particulier
	public void Set_passwd(String new_passwd) {
		
		
		this.passwd = new_passwd;	
		System.out.println("(!) Confirmation de la mise à jour du mot de passe de l'utilisateur.");
	}
}
