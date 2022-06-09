// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.model;


// ========== IMPORTATION DES RESSOURCES ========== //
import javax.persistence.*;


// ========== CLASSE USER ========== //
public class UserTmp {
    // ========== ATTRIBUTS DE USER ========== //
    private String login;
    private String mdp;



    // ========== CONSTRUCTEUR DE USER ========== //

    public UserTmp(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
    }


    // ========== FONCTIONS GET ET SET DE USER ========== //

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
}
