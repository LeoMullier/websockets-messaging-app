// ========== ATTRIBUTS DE TOKEN ========== //
package fr.utc.sr03.chat.model;


// ========== ATTRIBUTS DE TOKEN ========== //
public class Token {
    // ========== ATTRIBUTS DE TOKEN ========== //
    private long idClient;
    private String tokenClient;


    // ========== CONSTRUCTEUR DE TOKEN ========== //
    public Token(long idClient, String tokenClient) {
        this.idClient = idClient;
        this.tokenClient = tokenClient;
    }


    // ========== FONCTIONS GET ET SET DE TOKEN ========== //
    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public String getTokenClient() {
        return tokenClient;
    }

    public void setTokenClient(String tokenClient) {
        this.tokenClient = tokenClient;
    }
}
