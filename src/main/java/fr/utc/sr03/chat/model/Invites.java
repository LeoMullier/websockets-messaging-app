// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.model;


// ========== IMPORTATION DES RESSOURCES ========== //
import javax.persistence.*;


// ========== CLASSE INVITES ========== //
@Entity
@Table(name = "liste_invites")
public class Invites {
    // ========== ATTRIBUTS DE INVITES ========== //
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Pour l'auto-incrementation mySQL
    private long id;

    @Column(name = "iduser")
    private long iduser;

    @Column(name = "idcanal")
    private long idcanal;


    // ========== CONSTRUCTEUR DE INVITES ========== //
    public Invites(){}


    // ========== FONCTIONS GET ET SET DE INVITES ========== //
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIduser() {
        return iduser;
    }

    public void setIduser(long iduser) {
        this.iduser = iduser;
    }

    public long getIdcanal() {
        return idcanal;
    }

    public void setIdcanal(long idcanal) {
        this.idcanal = idcanal;
    }
}
