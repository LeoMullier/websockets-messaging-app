// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.model;


// ========== IMPORTATION DES RESSOURCES ========== //
import javax.persistence.*;


// ========== CLASSE USER ========== //
@Entity
@Table(name = "liste_users")
public class User {
    // ========== ATTRIBUTS DE USER ========== //
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Pour l'auto-incrementation mySQL
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "mdp")
    private String mdp;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "admin")
    private int admin;

    @Column(name = "enligne")
    private int enligne;

    @Column(name = "desactive")
    private int desactive;


    // ========== CONSTRUCTEUR DE USER ========== //
    public User(){}


    // ========== FONCTIONS GET ET SET DE USER ========== //
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getEnligne() {
        return enligne;
    }

    public void setEnligne(int enligne) {
        this.enligne = enligne;
    }

    public int getDesactive() {
        return desactive;
    }

    public void setDesactive(int desactive) {
        this.desactive = desactive;
    }
}
