package fr.utc.sr03.chat.model;

public class ValidationAuthentification {
    // ========== ATTRIBUTS DE USER ========== //
    private String tokenClient;
    private long idClient;


    // ========== CONSTRUCTEUR DE USER ========== //
    public ValidationAuthentification(long idClient, String tokenClient) {
        this.idClient = idClient;
        this.tokenClient = tokenClient;

    }


    // ========== FONCTIONS GET ET SET DE USER ========== //
    public String getTokenClient() {
        return tokenClient;
    }

    public void setTokenClient(String tokenClient) {
        this.tokenClient = tokenClient;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }
}
