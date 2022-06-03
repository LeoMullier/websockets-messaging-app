// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.model;


// ========== IMPORTATION DES RESSOURCES ========== //
import javax.persistence.*;


// ========== CLASSE MESSAGE ========== //
@Entity
@Table(name = "liste_messages")
public class Message {
    // ========== ATTRIBUTS DE MESSAGE ========== //
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Pour l'auto-incrementation mySQL
    private long id;

    @Column(name = "canal")
    private int canal;

    @Column(name = "auteur")
    private int auteur;

    @Column(name = "date")
    private String date;

    @Column(name = "heure")
    private String heure;

    @Column(name = "texte")
    private String texte;


    // ========== CONSTRUCTEUR DE MESSAGE ========== //
    public Message(){}


    // ========== FONCTIONS GET ET SET DE MESSAGE ========== //
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }

    public int getAuteur() {
        return auteur;
    }

    public void setAuteur(int auteur) {
        this.auteur = auteur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
