// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.model;


// ========== IMPORTATION DES RESSOURCES ========== //
import javax.persistence.*;


// ========== CLASSE CANAL ========== //
@Entity
@Table(name = "liste_canaux")
public class Canal {
    // ========== ATTRIBUTS DE CANAL ========== //
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Pour l'auto-incrementation mySQL
    private long id;

    @Column(name = "userproprio")
    private long userproprio;

    @Column(name = "titre")
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private String date;

    @Column(name = "duree")
    private String duree;

    @Column(name = "sauvegarde")
    private int sauvegarde;


    // ========== CONSTRUCTEUR DE CANAL ========== //
    public Canal(){}


    // ========== FONCTIONS GET ET SET DE CANAL ========== //
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserproprio() {
        return userproprio;
    }

    public void setUserproprio(long userproprio) {
        this.userproprio = userproprio;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public int getSauvegarde() {
        return sauvegarde;
    }

    public void setSauvegarde(int sauvegarde) {
        this.sauvegarde = sauvegarde;
    }
}
